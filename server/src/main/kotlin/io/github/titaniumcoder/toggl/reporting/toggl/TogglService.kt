package io.github.titaniumcoder.toggl.reporting.toggl

import io.github.titaniumcoder.toggl.reporting.toggl.TagCreator.tagbody
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.ceil

@Service
class TogglService(val webClient: TogglWebClient) {
    private val log = LoggerFactory.getLogger(TogglService::class.java)

    suspend fun clients(): List<TogglModel.Client> = webClient.clients()

    suspend fun summary(from: LocalDate, to: LocalDate): TogglModel.TogglSummary {
        val summary = webClient.summary(from, to)
        return summary.copy(data = summary.data.filter { it.time > 0 })
    }

    suspend fun entries(clientId: Long, from: LocalDate, to: LocalDate): TogglModel.TogglReporting {
        val firstPage = webClient.entries(clientId, from, to, 1)
        val range = 2.rangeTo(
                ceil(firstPage.totalCount.toDouble() / firstPage.perPage).toInt()
        )
                .map { webClient.entries(clientId, from, to, it) }

        data class ClientSort(val client: String, val start: LocalDateTime) : Comparable<ClientSort> {
            override fun compareTo(other: ClientSort): Int =
                    when {
                        client.compareTo(other.client) != 0 -> client.compareTo(other.client)
                        else -> start.compareTo(other.start)
                    }
        }

        val summary = (listOf(firstPage) + range)
                .reduce { t: TogglModel.TogglReporting, u: TogglModel.TogglReporting -> t.copy(data = t.data + u.data) }

        return summary.copy(data = summary.data
                .sortedBy { x -> ClientSort(x.client ?: "", x.start.toLocalDateTime()) }
        )
    }

    suspend fun tagBilled(clientId: Long, from: LocalDate, to: LocalDate) {
        tagRange(clientId, from, to, true)
    }

    suspend fun untagBilled(clientId: Long, from: LocalDate, to: LocalDate) {
        tagRange(clientId, from, to, false)
    }

    suspend fun tagBilled(entryId: Long) {
        webClient.tagId(listOf(entryId.toString()).joinToString(","), tagbody(true))
    }

    suspend fun untagBilled(entryId: Long) {
        webClient.tagId(listOf(entryId.toString()).joinToString(","), tagbody(false))
    }

    private suspend fun tagRange(clientId: Long, from: LocalDate, to: LocalDate, billed: Boolean) {
        val entriesMatched = entries(
                clientId = clientId,
                from = from,
                to = to
        )

        val ids = entriesMatched.data.map { it.id.toString() }.sorted().chunked(50)

        val completeResult = ids.map { webClient.tagId(it.joinToString(","), tagbody(billed)) }
        if (!completeResult.all { it.is2xxSuccessful }) {
            log.warn("Could not update the ids, got an error from the Toggl API")
        }
    }
}