package io.github.titaniumcoder.reporting.timeentry

import io.github.titaniumcoder.reporting.project.Project
import io.github.titaniumcoder.reporting.user.User
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.validation.constraints.Size

data class TimeEntry(
        // Autogenerated
        val id: Long?,

        val starting: LocalDateTime,
        val ending: LocalDateTime?,
        val project: Project?,

        @Size(max = 200)
        val description: String?,

        val user: User,

        val billable: Boolean = true,
        val billed: Boolean = false
)

data class TimeEntryDto(
        // Autogenerated
        val id: Long?,

        val date: LocalDate,
        val starting: LocalDateTime,
        val ending: LocalDateTime?,
        val projectId: Long?,

        val projectName: String?,

        @Size(max = 200)
        val description: String?,

        val username: String,

        val billable: Boolean = true,
        val billed: Boolean = false,

        val timeUsed: Duration,
        val amount: Double
)