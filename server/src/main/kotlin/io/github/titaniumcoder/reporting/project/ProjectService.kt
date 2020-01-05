package io.github.titaniumcoder.reporting.project

import io.github.titaniumcoder.reporting.client.ClientRepository
import io.github.titaniumcoder.reporting.config.Roles.Admin
import io.github.titaniumcoder.reporting.config.Roles.Booking
import io.github.titaniumcoder.reporting.user.UserService
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProjectService(val repository: ProjectRepository, val clientRepository: ClientRepository, val userService: UserService) {
    @Secured(Admin)
    fun projects(): List<ProjectAdminDto> =
            repository.findAll(Sort.by("name"))
                    .map { toAdminDto(it) }

    @Secured("isAuthenticated()")
    fun projectList(): List<ProjectList> =
            repository
                    .findAll(Sort.by("name"))
                    .filter { userService.currentUser().admin || userService.currentUser().clients.map { c -> c.id }.contains(it.client.id) }
                    .map { ProjectList(it.id, it.client.name, it.name) }

    @Secured(Admin)
    fun saveProject(dto: ProjectAdminDto): ProjectAdminDto {
        val client = clientRepository.findByIdOrNull(dto.clientId)
                ?: throw IllegalArgumentException("Unknown client ${dto.clientId} with name ${dto.clientName}")
        val project = Project(
                dto.id,
                client,
                dto.active,
                dto.name,
                dto.maxMinutes,
                dto.rateInCentsPerHour,
                dto.billable
        )
        return toAdminDto(repository.save(project))
    }

    @Secured(Admin, Booking)
    fun findProject(id: Long): Project? {
        val project = repository.findByIdOrNull(id)

        val user = userService.currentUser()

        return project?.let {
            if (it.client in user.clients) it else null
        }
    }

    @Secured(Admin)
    fun deleteProject(id: Long) {
        repository.deleteById(id)
    }

    private fun toAdminDto(it: Project): ProjectAdminDto {
        return ProjectAdminDto(
                it.id,
                it.client.id,
                it.client.name,
                it.active,
                it.name,
                it.maxMinutes,
                it.rateInCentsPerHour,
                it.billable
        )
    }

}
