package hyx.plateform.controller;

import hyx.plateform.entity.CaseEntity;
import hyx.plateform.model.CaseParams;
import hyx.plateform.model.Result;
import hyx.plateform.service.CaseService;
import hyx.plateform.util.ExecPy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/case")
public class CaseController {
    private static final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Autowired
    CaseService caseService;

    @PutMapping("/**")
    public ResponseEntity<String> createCase(@RequestBody CaseParams caseParams) {
        try {
            caseService.createCase(caseParams.getProjectName(), caseParams.getCaseName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success created " + caseParams.getProjectName() + " " + caseParams.getCaseName());
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteCase(String projectName, String caseName) {
        try {
            caseService.deleteCase(projectName, caseName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success created " + projectName + " " + caseName);
    }

    @GetMapping("/**")
    public ResponseEntity<List<CaseEntity>> getCasesByProject(String projectName) {
        return ResponseEntity.ok(caseService.findCasesByProject(projectName));
    }

    @GetMapping("/result")
    public ResponseEntity<List<Result>> getResults(String projectName, String caseName) {
        try {
            return ResponseEntity.ok(caseService.getResults(projectName, caseName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/execScript")
    public ResponseEntity<String> execScript(@RequestBody CaseParams caseParams) {
        CaseEntity caseEntity = caseService.findCaseByProjectAndName(caseParams.getProjectName(), caseParams.getCaseName());
        if(ExecPy.execPy(caseEntity.getFolderDir())) {
            return ResponseEntity.ok("Running");
        } else {
            return ResponseEntity.ok("Already Running");
        }
    }

    @GetMapping("/runningInfo")
    public ResponseEntity<List<String>> getRunningInfo() {
        return ResponseEntity.ok(ExecPy.getRunningInfo());
    }

}
