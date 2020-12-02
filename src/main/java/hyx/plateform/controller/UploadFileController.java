package hyx.plateform.controller;

import hyx.plateform.util.MyFileUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author hanyuxiao
 * @date 2020/12/2
 **/
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    public final String baseDir = "E:\\Data\\plateform\\data";

    @PostMapping("/**")
    public void upLoadFile(@RequestParam("file")MultipartFile uploadFiles) throws IOException {
        String projectDir = baseDir + "\\" + "test";
        MyFileUtils.makeDir(projectDir);
        File uploadDir = new File(projectDir);
        File dst = new File(uploadDir,
                uploadFiles.getName());
        uploadFiles.transferTo(dst);
        System.out.println(uploadFiles);
    }
}
