package io.github.hlg212.generator.service.impl;

import io.github.hlg212.fcf.util.EnvironmentHelper;
import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.generator.model.bo.ProjectGeneratorBo;
import io.github.hlg212.generator.model.bo.ProjectGroupGeneratorBo;
import io.github.hlg212.generator.service.CodeGeneratorService;
import io.github.hlg212.generator.service.ProjectGeneratorService;
import io.github.hlg212.generator.service.ProjectGroupGeneratorService;
import io.github.hlg212.generator.util.Constants;
import io.github.hlg212.generator.util.FileHepler;
import io.github.hlg212.generator.util.ZipHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectGroupGeneratorServiceImpl implements ProjectGroupGeneratorService {

    @Autowired
    private ProjectGeneratorService projectGeneratorService;

    @Override
    public String generator(ProjectGroupGeneratorBo bo) {
        String tempPath = EnvironmentHelper.resolvePlaceholders(Constants.PROJECTGROUP_DIRECTORY_PATH) + "/" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+ "/" + bo.getGroupCode();
        String templatePath = this.getClass().getResource(Constants.PROJECTGROUP_TAMPLATE_PATH).getFile();
        //FileHepler.deleteFile(new File(tempPath));
        try {
            ZipHelper.unZip(new File(templatePath),tempPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        repacle(bo,tempPath);
        List<ProjectGeneratorBo> list = bo.getProjects();
        if( list != null )
        {
            for( ProjectGeneratorBo pgbo: list )
            {
                if(StringUtils.isEmpty(pgbo.getParentVersion()))
                {
                    pgbo.setParentVersion(bo.getVersion());
                }
                if(StringUtils.isEmpty(pgbo.getParentCode()))
                {
                    pgbo.setParentCode(bo.getGroupCode());
                }
                if(StringUtils.isEmpty(pgbo.getParentMavenPackage()))
                {
                    pgbo.setParentMavenPackage(bo.getMavenPackage());
                }

                log.info("生成项目代码[{}:{}]!",pgbo.getProjectCode(),pgbo.getProjectName());
                String proejctPath = projectGeneratorService.generator(pgbo);
                log.info("合并项目模块代码!");
                try {
                    copyProject(tempPath, proejctPath);
                }catch (Exception e)
                {
                    ExceptionHelper.throwServerException(e);
                }
            }
        }
        return tempPath;
    }

    private void repacle(ProjectGroupGeneratorBo bo, String path) {
        repacleDirectory(bo,path);
        repacleFiles(bo,path);
    }

    private void copyProject(String path,String projectPath) throws IOException {
        FileHepler.copyDirectiory(projectPath,path);
        FileHepler.deleteFile(new File(projectPath));
    }


    private void repacleDirectory(ProjectGroupGeneratorBo bo, String path) {
        File[] file = (new File(path)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String filePath = file[i].getPath();

                String newFilePath = filePath.replace("groupTemplate", bo.getGroupCode());

                if (!filePath.equals(newFilePath)) {
                    file[i].renameTo(new File(newFilePath));
                }
                repacleDirectory(bo,newFilePath);
            }
        }

    }

    private void repacleFiles(ProjectGroupGeneratorBo bo, String path) {
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

    private void repacleFile(File sourceFile, ProjectGroupGeneratorBo bo) {
        try {
            List<String> lines = Files.readAllLines(sourceFile.toPath());
            for( int i=0;i<lines.size();i++ )
            {
                String context = lines.get(i);
                context = context.replaceAll("groupTemplate", bo.getGroupCode());
                context = context.replaceAll("\\$\\{mavenPackage\\}", bo.getMavenPackage());
                context = context.replaceAll("\\$\\{frameVersion\\}", bo.getFrameVersion());
                context = context.replaceAll("\\$\\{groupCode\\}", bo.getGroupCode());
                context = context.replaceAll("\\$\\{parentCode\\}", bo.getParentCode());
                context = context.replaceAll("\\$\\{parentMavenPackage\\}", bo.getParentMavenPackage());
                context = context.replaceAll("\\$\\{parentVersion\\}", bo.getParentVersion());
                lines.set(i,context);
            }

            Files.write(sourceFile.toPath(),lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
