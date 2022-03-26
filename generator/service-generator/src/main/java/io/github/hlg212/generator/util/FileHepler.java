package io.github.hlg212.generator.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileHepler {


    public static void copyFile(File sourcefile, File targetFile) throws IOException {

        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourcefile);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream outbuff = new BufferedOutputStream(out);

        // 缓冲数组
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = input.read(b)) != -1) {
            outbuff.write(b, 0, len);
        }

        //关闭文件
        outbuff.close();
        input.close();

    }
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {

        String newDir = targetDir + File.separator + new File(sourceDir).getName();
        copyDirectioryFiles(sourceDir,newDir);
    }

    public static void copyDirectioryFiles(String sourceDir, String targetDir) throws IOException {

        // 新建目标目录
        (new File(targetDir)).mkdirs();

        // 获取源文件夹当下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(targetDir + File.separator + sourceFile.getName());
                copyFile(sourceFile, targetFile);
            }

            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();

                copyDirectioryFiles(dir1, dir2);
            }
        }

    }



    public static void deleteFile(File file) {
        if(file.isFile()) {
            file.delete();
        }
        if(file.isDirectory()) {
            File[] listFile = file.listFiles();
            for(File tempFile : listFile) {
                deleteFile(tempFile);
            }
            file.delete();
        }
    }




}
