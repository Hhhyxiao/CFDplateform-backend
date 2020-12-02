package hyx.plateform.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hanyuxiao
 * @date 2020/12/2
 **/
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @PostMapping("/**")
    public void upLoadFile(@RequestParam("file")MultipartFile uploadFiles){
        System.out.println(uploadFiles);
    }
}
