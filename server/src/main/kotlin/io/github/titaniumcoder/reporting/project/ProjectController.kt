package io.github.titaniumcoder.reporting.project

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProjectController(val service: ProjectService) {
    @Secured("ROLE_ADMIN")
    @GetMapping("/projects")
    fun projects() = service.projects()

    @Secured("isAuthenticated()")
    @GetMapping("/project-list")
    fun projectList() =
            service.projects().map { ProjectList(it.id, it.clientName ?: "<UNK>", it.name) }

    @Secured("ROLE_ADMIN")
    @PostMapping("/projects")
    fun save(@RequestBody @Validated project: ProjectAdminDto): ProjectAdminDto {
        return service.saveProject(project)
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/projects/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        service.deleteProject(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
