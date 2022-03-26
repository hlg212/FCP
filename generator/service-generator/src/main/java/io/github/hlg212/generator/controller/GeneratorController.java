package io.github.hlg212.generator.controller;

import io.github.hlg212.fcf.util.ExceptionHelper;
import io.github.hlg212.fcf.util.LockHelper;
import io.github.hlg212.fcf.web.util.HttpServletHelper;
import io.github.hlg212.generator.model.bo.CodeGeneratorBo;
import io.github.hlg212.generator.model.bo.ProjectGeneratorBo;
import io.github.hlg212.generator.model.bo.ProjectGroupGeneratorBo;
import io.github.hlg212.generator.service.CodeGeneratorService;
import io.github.hlg212.generator.service.ProjectGeneratorService;
import io.github.hlg212.generator.service.ProjectGroupGeneratorService;
import io.github.hlg212.generator.util.FileHepler;
import io.github.hlg212.generator.util.ZipHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/generator")
@Api(value="代码生成控制器",tags={"代码生成"})
public class GeneratorController{

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @Autowired
    private ProjectGeneratorService projectGeneratorService;

    @Autowired
    private ProjectGroupGeneratorService projectGroupGeneratorService;


    private String LOCK_KEY = "GENERATOR_LOCK_KEY";


    @ApiOperation(value = "系统代码生成,一般开发全新系统时使用",notes="传入多个项目，可以一起生成")
    @RequestMapping(value = "/projectGroupGenerator", method = RequestMethod.POST)
    public byte[] projectGroupGenerator(@RequestBody ProjectGroupGeneratorBo bo)
    {
        if( !LockHelper.tryLock(LOCK_KEY) )
        {
            ExceptionHelper.throwBusinessException("操作正在进行，请稍后再试!");
        }
        try {
            String path = projectGroupGeneratorService.generator(bo);
            setHeaders(bo.getGroupCode() + ".zip");
            return readAndDeleteFile(path);
        }finally {
            LockHelper.unlock(LOCK_KEY);
        }
    }

    @ApiOperation(value = "项目代码生成",notes="传入数据库连接信息，可以生成模块代码")
    @RequestMapping(value = "/projectGenerator", method = RequestMethod.POST)
    public byte[] projectGenerator(@RequestBody ProjectGeneratorBo bo)
    {
        if( !LockHelper.tryLock(LOCK_KEY) )
        {
            ExceptionHelper.throwBusinessException("操作正在进行，请稍后再试!");
        }
        try {
            String path = projectGeneratorService.generator(bo);
            setHeaders(bo.getProjectCode() + ".zip");
            return  readAndDeleteFile(path);
        }finally {
            LockHelper.unlock(LOCK_KEY);
        }
    }

    @ApiOperation("模块代码生成")
    @RequestMapping(value = "/codeGenerator", method = RequestMethod.POST)
    public byte[] codeGenerator(@RequestBody  CodeGeneratorBo bo)
    {
        if( !LockHelper.tryLock(LOCK_KEY) )
        {
            ExceptionHelper.throwBusinessException("操作正在进行，请稍后再试!");
        }
        try {
            String path = codeGeneratorService.generator(bo);
            setHeaders(bo.getCodePackage() + ".zip");
            return readAndDeleteFile(path);
        }finally {
            LockHelper.unlock(LOCK_KEY);
        }
    }

    private byte[] readAndDeleteFile(String path)
    {
        byte bs[] = ZipHelper.toZipBytes(path,true);
        FileHepler.deleteFile(new File(path).getParentFile());
        return bs;
    }

    static void setHeaders(String fileName)
    {
        HttpServletHelper.getResponse().setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        try {
            HttpServletHelper.getResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            ExceptionHelper.throwServerException(e);
        }
    }

}
