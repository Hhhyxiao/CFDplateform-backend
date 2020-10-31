package hyx.plateform.controller;

import hyx.plateform.dao.ProjectRepository;
import hyx.plateform.entity.ProjectEntity;
import hyx.plateform.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PutMapping("/**")
    public ResponseEntity<String> createProject(@Valid@RequestBody String projectName) {
        try {
            projectService.creatProject(projectName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteProject(String projectName) {
        try {
            projectService.deleteProject(projectName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectEntity>> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}
