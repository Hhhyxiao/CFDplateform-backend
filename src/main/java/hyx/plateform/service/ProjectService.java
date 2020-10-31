package hyx.plateform.service;

import ch.qos.logback.core.util.TimeUtil;
import hyx.plateform.dao.CaseRepository;
import hyx.plateform.dao.ProjectRepository;
import hyx.plateform.entity.ProjectEntity;
import hyx.plateform.util.MyFileUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProjectService {

    public final String baseDir = "E:\\Data\\plateform\\data";

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CaseRepository caseService;

    @Transactional
    public void creatProject(String projectName) throws IOException {
        ProjectEntity old = projectRepository.findByName(projectName);
        if(old != null || projectName.trim().equals("")) throw new RuntimeException("already exists");
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectName);
        String projectDir = baseDir + "\\" + projectName;
        MyFileUtils.makeDir(projectDir);
        projectEntity.setProjectDir(projectDir);
        projectEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        projectRepository.save(projectEntity);
    }

    public void deleteProject(String projectName) throws IOException {
        ProjectEntity old = projectRepository.findByName(projectName);
        if(old == null) throw new RuntimeException("project not exists");
        projectRepository.delete(old);
        caseService.findByProject(projectName).forEach(
                caseEntity -> caseService.delete(caseEntity));
        FileUtils.deleteDirectory(new File(old.getProjectDir()));
    }

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }
}
