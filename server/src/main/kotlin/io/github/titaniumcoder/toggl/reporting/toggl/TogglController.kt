package io.github.titaniumcoder.toggl.reporting.toggl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class TogglController(val service: TogglService) {
    @GetMapping("/api/clients")
    fun clients() = GlobalScope.mono {
        // FIXME this can be replaced with "suspend" with Spring 5.2
        service.clients()
    }

    @PutMapping("/api/client/{clientId}/billed")
    fun tagBilled(
            @PathVariable clientId: Long,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ) = GlobalScope.mono {
        // FIXME this can be replaced with "suspend" with Spring 5.2
        service.tagBilled(clientId, from, to)
    }

    @DeleteMapping("/api/client/{clientId}/billed")
    fun untagBilled(
            @PathVariable clientId: Long,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ) = GlobalScope.mono {
        // FIXME this can be replaced with "suspend" with Spring 5.2
        service.untagBilled(clientId, from, to)
    }

    @PutMapping("/api/tag/{entry}")
    fun tagEntry(@PathVariable entry: Long) = GlobalScope.mono {
        // FIXME this can be replaced with "suspend" with Spring 5.2
        service.tagBilled(entry)
    }

    @DeleteMapping("/api/tag/{entry}")
    fun untagEntry(@PathVariable entry: Long) = GlobalScope.mono {
        // FIXME this can be replaced with "suspend" with Spring 5.2
        service.untagBilled(entry)
    }
}