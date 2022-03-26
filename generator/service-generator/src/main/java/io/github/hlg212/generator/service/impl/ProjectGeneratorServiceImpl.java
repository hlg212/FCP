package io.github.hlg212.generator.service.impl;

import io.github.hlg212.fcf.util.EnvironmentHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.generator.model.bo.ProjectGeneratorBo;
import io.github.hlg212.generator.service.CodeGeneratorService;
import io.github.hlg212.generator.service.ProjectGeneratorService;
import io.github.hlg212.generator.util.Constants;
import io.github.hlg212.generator.util.FileHepler;
import io.github.hlg212.generator.util.ZipHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectGeneratorServiceImpl implements ProjectGeneratorService {

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @Override
    public String generator(ProjectGeneratorBo bo) {
        String tempPath = EnvironmentHelper.resolvePlaceholders(Constants.PROJECT_DIRECTORY_PATH) + "/" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+ "/" + bo.getProjectCode();
        String templatePath = this.getClass().getResource(Constants.PROJECT_TAMPLATE_PATH).getFile();
       // FileHepler.deleteFile(new File(tempPath));
        try {
            ZipHelper.unZip(new File(templatePath),tempPath);
        } catch (Exception e) {
           log.error(e.getMessage(),e);
            ExceptionHelper.throwServerException(e);
        }

        repacle(bo,tempPath);
        if( bo.getCodeGeneratorBo() != null )
        {
            log.info("生成模块代码[{}]!",bo.getCodeGeneratorBo().getCodePackage());
            String codePath = codeGeneratorService.generator(bo.getCodeGeneratorBo());
            log.info("合并生成模块代码!");
            generatorCode(tempPath,codePath,bo);
            FileHepler.deleteFile(new File(codePath));
        }

        return tempPath;
    }

    private void repacle(ProjectGeneratorBo bo, String path) {
        createCodePackage(bo,path);
        repacleDirectory(bo,path);
        if(StringUtils.isEmpty(bo.getParentVersion()))
        {
            bo.setParentVersion("2.5.0-SNAPSHOT");
        }
        if(StringUtils.isEmpty(bo.getParentCode()))
        {
            bo.setParentCode("platform");
        }
        if(StringUtils.isEmpty(bo.getParentMavenPackage()))
        {
            bo.setParentMavenPackage("io.github.hlg212.platform");
        }

        repacleFiles(bo,path);
    }
    private void createCodePackage(ProjectGeneratorBo bo, String path)
    {
        File[] file = (new File(path)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String filePath = file[i].getPath();
                String name = file[i].getName();
                String pname = file[i].getParentFile().getName();

                if( pname.equals("java") && name.equals("template"))
                {
                    String destPath = file[i].getParentFile().getPath()+ File.separator + (bo.getCodePackage().replace(".",File.separator) );
                    try {
                        FileHepler.copyDirectioryFiles(filePath,destPath);
                        FileHepler.deleteFile(file[i]);
                        continue;
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                createCodePackage(bo,filePath);
            }
        }
    }

    private void generatorCode(String path,String codePath,ProjectGeneratorBo bo)
    {
        File[] files = (new File(codePath)).listFiles();
        String codePackageDir = (bo.getCodePackage().replace(".",File.separator) );
        for( File f : files )
        {
            String src = f.getPath();
            String dest = null;
            if( "bo".equals( f.getName()) )
            {
                dest = path + "/facade-"+bo.getProjectCode()+"/src/main/java/" + codePackageDir  + "/model/bo";
            }
            else if( "po".equals( f.getName()) )
            {
                dest = path + "/facade-"+bo.getProjectCode()+"/src/main/java/" + codePackageDir + "/model/po";
            }
            else if( "qco".equals( f.getName()) )
            {
                dest = path + "/facade-"+bo.getProjectCode()+"/src/main/java/" + codePackageDir + "/model/qco";
            }
            else if( "mapper".equals( f.getName()) )
            {
                dest = path + "/service-"+bo.getProjectCode()+"/src/main/resources/mybatis/mapper";
            }
            else
            {
                dest = path + "/service-"+bo.getProjectCode()+"/src/main/java/"+ codePackageDir+ "/" + f.getName();
            }
            try {
                FileHepler.copyDirectioryFiles(src,dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileHepler.deleteFile(new File(codePath));

    }

    private void repacleDirectory(ProjectGeneratorBo bo, String path) {
        File[] file = (new File(path)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String filePath = file[i].getPath();

                String newFilePath = filePath.replace("template", bo.getProjectCode());

                if (!filePath.equals(newFilePath)) {
                    file[i].renameTo(new File(newFilePath));
                }
                repacleDirectory(bo,newFilePath);
            }
        }

    }

    private void repacleFiles(ProjectGeneratorBo bo, String path) {
        File[] file = (new File(path)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                repacleFile(sourceFile,bo);
            }
            if( file[i].isDirectory() )
            {
                repacleFiles(bo,file[i].getPath());
            }
        }
    }

    private void repacleFile(File sourceFile, ProjectGeneratorBo bo) {
        try {
            List<String> lines = Files.readAllLines(sourceFile.toPath());
            for( int i=0;i<lines.size();i++ )
            {
                String context = lines.get(i);
                context = context.replaceAll("template", bo.getProjectCode());
                context = context.replaceAll("\\$\\{codePackage\\}", bo.getCodePackage());
                context = context.replaceAll("\\$\\{mavenPackage\\}", bo.getMavenPackage());
                context = context.replaceAll("\\$\\{projectName\\}", bo.getProjectName());
                context = context.replaceAll("\\$\\{frameVersion\\}", bo.getFrameVersion());
                context = context.replaceAll("\\$\\{parentCode\\}", bo.getParentCode());
                context = context.replaceAll("\\$\\{parentVersion\\}", bo.getParentVersion());
                context = context.replaceAll("\\$\\{parentMavenPackage\\}", bo.getParentMavenPackage());
                lines.set(i,context);
            }

            Files.write(sourceFile.toPath(),lines);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }


}
