package hyx.plateform.service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import hyx.plateform.dao.CaseRepository;
import hyx.plateform.dao.ProjectRepository;
import hyx.plateform.entity.CaseEntity;
import hyx.plateform.entity.ProjectEntity;
import hyx.plateform.model.Result;
import hyx.plateform.util.MyFileUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class CaseService {

    public final String originalPath = "E:\\Data\\plateform\\data\\originPy";

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CaseRepository caseRepository;

    public void createCase(String projectName, String caseName) throws IOException {
        ProjectEntity projectEntity = projectRepository.findByName(projectName);
        if(projectEntity == null) throw new RuntimeException("no such project");
        CaseEntity caseEntity = caseRepository.findByNameAndProject(caseName, projectName);
        if(caseEntity != null) throw new RuntimeException("case already exists");
        String caseDir = projectEntity.getProjectDir() + "\\" + caseName;
        caseEntity = new CaseEntity();
        caseEntity.setProject(projectName);
        caseEntity.setName(caseName);
        caseEntity.setFolderDir(caseDir);
        caseEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        MyFileUtils.copyDirectory(originalPath, caseDir);
        caseRepository.save(caseEntity);
    }

    public void deleteCase(String projectName, String caseName) throws IOException {
        CaseEntity caseEntity = caseRepository.findByNameAndProject(caseName, projectName);
        caseRepository.delete(caseEntity);
        FileUtils.deleteDirectory(new File(caseEntity.getFolderDir()));
    }

    public CaseEntity findCaseByProjectAndName(String projectName, String caseName) {
        return caseRepository.findByNameAndProject(caseName, projectName);
    }

    public List<CaseEntity> findCasesByProject(String projectName) {
        return caseRepository.findByProject(projectName);
    }

    public List<Result> getResults(String projectName, String caseName) throws FileNotFoundException {
        List<Result> results = new LinkedList<>();
        CaseEntity caseEntity = caseRepository.findByNameAndProject(caseName, projectName);
        String path = caseEntity.getFolderDir();
        File res = new File(path + "/" + "result.txt");
        Scanner sc = new Scanner(res);
        sc.nextLine();
        while(sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] strArray = str.trim().split("\\s+");
            StringBuilder time = new StringBuilder(strArray[4]);
            for(int i = 5; i < strArray.length; i++) {
                time.append(" ").append(strArray[i]);
            }
            results.add(Result.builder()
                    .subCaseName(strArray[0])
                    .average(String.format("%.2f", Double.valueOf(strArray[1])))
                    .standardDev(String.format("%.2f", Double.valueOf(strArray[2])))
                    .ratio(String.format("%.2f", Double.parseDouble(strArray[3]) * 100) + "%")
                    .time(time.toString())
                    .build());
        }
        return results;
    }




}
