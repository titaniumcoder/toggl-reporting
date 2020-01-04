package io.github.titaniumcoder.reporting.timeentry

import com.fasterxml.jackson.annotation.JsonFormat
import io.github.titaniumcoder.reporting.project.Project
import io.github.titaniumcoder.reporting.user.User
import org.codehaus.jackson.annotate.JsonIgnore
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "time_entry")
data class TimeEntry(
        // Autogenerated
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,

        @Column(name = "starting")
        var starting: LocalDateTime,

        @Column(name = "ending")
        var ending: LocalDateTime?,

        @ManyToOne(optional = true, cascade = [CascadeType.ALL])
        @JoinColumn(name = "project_id")
        val project: Project?,

        @Size(max = 200)
        var description: String?,

        @ManyToOne(optional = false, cascade = [CascadeType.ALL])
        @JoinColumn(name = "email")
        var user: User,

        var billable: Boolean = true,
        var billed: Boolean = false
)

data class TimeEntryDto(
        // Autogenerated
        val id: Long?,

        @JsonFormat(pattern = "yyyy-MM-dd")
        val date: LocalDate,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        val starting: LocalDateTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        val ending: LocalDateTime?,

        val projectId: Long?,
        val projectName: String?,

        @Size(max = 200)
        val description: String?,

        val username: String,

        val billable: Boolean,
        val billed: Boolean,

        val timeUsed: Long?,
        val amount: Double?
)

data class TimeEntryUpdateDto(
        // Autogenerated
        val id: Long?,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        val starting: LocalDateTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        val ending: LocalDateTime?,

        val projectId: Long?,

        @Size(max = 200)
        val description: String?,

        val username: String,

        val billable: Boolean,
        val billed: Boolean
)
