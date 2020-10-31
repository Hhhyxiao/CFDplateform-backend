package hyx.plateform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public class MyFileUtils {

    public static void main(String[] args) throws IOException {

        String srcPath = "E:\\Data\\plateform\\data\\originPy";
        String destPath = "E:\\Data\\plateform\\data\\aa";
        copyDirectory(srcPath, destPath);

    }

    // 创建文件夹
    public static void makeDir(String destPath) {
        File newFilePath = new File(destPath);
        if (!newFilePath.exists()) {
            newFilePath.mkdirs();
        }
    }

    // 复制文件夹及其内容
    public static void copyDirectory(String srcPath, String destPath) throws IOException {
        File file = new File(srcPath);
        makeDir(destPath);
        File destFile = new File(destPath);
        FileUtils.copyDirectory(file, destFile);
    }

    // 复制文件(应用场景：复制的同时更改文件名称)
    public static void copyFile(String srcPath, String destPath) throws IOException {
        File src = new File(srcPath);
        makeDir(destPath);
        File[] srcFiles = src.listFiles();
        if (srcFiles.length > 0) {
            for (File file : srcFiles) {
                InputStream input = null;
                OutputStream output = null;
                try {
                    input = new FileInputStream(file);
                    output = new FileOutputStream(new File(destPath + "\\proinstall.py"));
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buf)) != -1) {
                        output.write(buf, 0, bytesRead);
                    }
                } finally {
                    input.close();
                    output.close();
                }
            }
        }
    }

    // 把文件 内容转成字符串
    public static void readFileToString(String srcPath) throws IOException {
        File src = new File(srcPath);
        File[] srcFiles = src.listFiles();
        if (srcFiles.length > 0) {
            for (File file : srcFiles) {
                String filename = file.getName();
                if (file.isDirectory()) {
                    readFileToString(file.getAbsolutePath());
                }
                if (filename.endsWith("html")) {
                    String str = FileUtils.readFileToString(file, "utf-8");
                    System.out.println(str);
                }
            }
        }
    }
    public static void copy(String fromFilePath, String toFilePath) {
        try {
            FileInputStream fis = new FileInputStream(fromFilePath);
            FileOutputStream fos = new FileOutputStream(toFilePath);
            byte[] b = new byte[100];
            try {
                while (fis.read(b) != (-1)) {
                    fos.write(b);
                }
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                System.out.println("io异常");
            }
        } catch (FileNotFoundException e) {
            System.out.println("源文件不存在");
        }

    }

}
