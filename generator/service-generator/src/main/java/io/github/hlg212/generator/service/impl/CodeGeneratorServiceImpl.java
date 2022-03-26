package io.github.hlg212.generator.service.impl;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import io.github.hlg212.fcf.util.EnvironmentHelper;
import io.github.hlg212.generator.dao.GeneratorDao;
import io.github.hlg212.generator.dao.GeneratorH2Dao;
import io.github.hlg212.generator.dao.GeneratorMysqlDao;
import io.github.hlg212.generator.dao.GeneratorOracleDao;
import io.github.hlg212.generator.model.bo.CodeGeneratorBo;
import io.github.hlg212.generator.model.bo.Col;
import io.github.hlg212.generator.model.bo.DbBo;
import io.github.hlg212.generator.model.bo.Table;
import io.github.hlg212.generator.service.CodeGeneratorService;
import io.github.hlg212.generator.service.DataSourceService;
import io.github.hlg212.generator.util.Constants;
import io.github.hlg212.generator.util.FileHepler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RefreshScope
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Autowired
    private GeneratorOracleDao generatorOracleDao;

    @Autowired
    private GeneratorMysqlDao generatorMysqlDao;

    @Autowired
    private GeneratorH2Dao generatorH2Dao;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataSource dataSource;

    @Override
    public String generator(CodeGeneratorBo bo) {
        String tempPath = EnvironmentHelper.resolvePlaceholders(Constants.CODE_DIRECTORY_PATH) + "/" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        setDataSouce(bo);
        // generatorDao.getTableList(null);
        generatorCode(bo.getAuthor(), tempPath, bo.getCodePackage(),bo.getTablePrefix(), null);
        return tempPath;
    }

    private GeneratorDao getDao()
    {
        String dname = "";
        try {
            dname =  dataSource.getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            log.warn("{}",e);
        }
        dname = dname.toUpperCase();
        if( dname.indexOf("MYSQL") != -1 )
        {
            return generatorMysqlDao;
        }
        else if( dname.indexOf("H2") != -1 )
        {
           return generatorH2Dao;
        }
        HashMap m;
        m.put()
        return generatorOracleDao;

    }

    private void setDataSouce(CodeGeneratorBo bo) {
        DbBo db = bo.getDb();
        if (db != null) {
            dataSourceService.configDb(db);
        }
    }

    /**
     * 逆向生成代码
     *
     * @param author        代码作者
     * @param generatorPath 代码生成路径
     * @param tables        逆向生成代码对应的表用,分割，不传时默认取当前用户下所有的表
     */
    private void generatorCode(String author, String generatorPath, String systemPackage,String tablePrefix, String... tables) {
        //*****项目包路径定义****
        //项目po类(数据库实体)生成的包目录
        String modelPoPackage = systemPackage + ".model.po";
        //项目bo类生成的包目录
        String modelBoPackage = systemPackage + ".model.bo";
        //项目bo类生成的包目录
        String modelQcoPackage = systemPackage + ".model.qco";
        //项目dao类生成的包目录
        String daoPackage = systemPackage + ".dao";
        //项目service类生成的包目录
        String servicePackage = systemPackage + ".service";
        //项目controller类生成的包目录
        String controllerPackage = systemPackage + ".controller";

        //定义模板替换参数
        Map<String, String> templReplace = new HashMap<String, String>();
        String authorKey = "\\$\\{author\\}";
        templReplace.put(authorKey, author);
        templReplace.put("\\$\\{date\\}", "${currentDate}");
        templReplace.put("\\$\\{comment\\}", "${currentTableComment}");
        templReplace.put("\\$\\{modelPoPackage\\}", modelPoPackage);
        templReplace.put("\\$\\{modelBoPackage\\}", modelBoPackage);
        templReplace.put("\\$\\{modelQcoPackage\\}", modelQcoPackage);
        templReplace.put("\\$\\{daoPackage\\}", daoPackage);
        templReplace.put("\\$\\{servicePackage\\}", servicePackage);
        templReplace.put("\\$\\{controllerPackage\\}", controllerPackage);
        templReplace.put("\\$\\{poModel\\}", "${currentPoModel}");
        templReplace.put("\\$\\{poModelRequestMapping\\}", "${currentPoModelRequestMapping}");
        templReplace.put("\\$\\{pkIdType\\}", "${currentPkIdType}");

        File file = new File(generatorPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (File tempFile : file.listFiles()) {
            FileHepler.deleteFile(tempFile);
        }

        //如果替换了模板也需要修改对应的文件
        String bo = this.getClass().getResource("/template/TemplateBo.java").getFile();
        String qco = this.getClass().getResource("/template/TemplateQco.java").getFile();
        String dao = this.getClass().getResource("/template/TemplateDao.java").getFile();
        String mapper = this.getClass().getResource("/template/TemplateMapper.xml").getFile();
        String service = this.getClass().getResource("/template/TemplateService.java").getFile();
        String serviceImpl = this.getClass().getResource("/template/TemplateServiceImpl.java").getFile();
        String controller = this.getClass().getResource("/template/TemplateController.java").getFile();


        try {
            tables = checkTables(tables);
            List ts = null;
            if (tables != null) {
                ts = Lists.newArrayList(tables);
            }
            //取表
            List<Table> listTable = getTables(tablePrefix,ts);
            //生成POJO
            tablesToPojos(listTable, generatorPath + "/po/", modelPoPackage, templReplace.get(authorKey));

            //根据模板生成代码
            geneCodeByTempl(listTable, templReplace, bo, generatorPath + "/bo/", "Bo.java");
            geneCodeByTempl(listTable, templReplace, qco, generatorPath + "/qco/", "Qco.java");
            geneCodeByTempl(listTable, templReplace, dao, generatorPath + "/dao/", "Dao.java");
            geneCodeByTempl(listTable, templReplace, mapper, generatorPath + "/mapper/", "Mapper.xml");
            geneCodeByTempl(listTable, templReplace, service, generatorPath + "/service/", "Service.java");
            geneCodeByTempl(listTable, templReplace, serviceImpl, generatorPath + "/service/impl/", "ServiceImpl.java");
            geneCodeByTempl(listTable, templReplace, controller, generatorPath + "/controller/", "Controller.java");

            log.info("总共生成" + listTable.size() + "个pojo类对应的bo、qco、dao、mapper、service、controller等文件.");
            if (tables != null) {
                for (String tempTableName : tables) {
                    boolean flag = false;
                    for (Table table : listTable) {
                        if (tempTableName.equalsIgnoreCase(table.getName())) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        log.warn("用户下面未找到表:" + tempTableName);
                    }
                }
            }

        } catch (Exception e) {
            log.warn(e.getMessage(),e);
        }

        log.info("complete");
    }

    private static String[] checkTables(String... tables) {
        if (tables != null && tables.length == 1) {
            if (tables[0] == null) {
                tables = null;
            }
        }
        return tables;
    }


    public List<Table> getTables(String tablePrefix,List<String> tables) throws Exception {
        GeneratorDao dao =  getDao();
        List<Table> tableList = dao.getTableList(tables);

        //将字段组装到表中
        for (Table table : tableList) {
            table.setClzName(this.toClzName(tablePrefix,table.getName()));//表名 -> 实体名
            if (table.getComments() == null) {
                table.setComments("");
            }

            //表名 --> 序列名
            table.setSeqName("SEQ_" + table.getName().substring(table.getName().indexOf("_") + 1));
            List<Col> colList = dao.getColList(table.getName());

            for (Col col : colList) {
                if (mapType(col) != null) {
                    col.setJavaType(mapType(col));//字段类型 -> java类型
                    col.setPropName(this.toPropName(col.getName()));//字段名 -> 属性名
                    if (col.getComments() == null) {
                        col.setComments("");
                    }
                }
            }

            table.setPriKey(colList.get(0).getName());
            table.setPriKeyType(mapType(colList.get(0)));
            table.setColList(colList);
        }
        return tableList;
    }

    /**
     * 将tables转化位实体类s
     *
     * @param tables
     * @param dir
     */
    public void tablesToPojos(List<Table> tables, String dir, String tarPackage, String author) {
        for (Table table : tables) {
            String strPackage = "package " + tarPackage + ";\n\n";
            String strImport = "";
            //String strImport = "import java.io.Serializable;\n";
            strImport += "import io.github.hlg212.fcf.annotation.Field;\n";
            strImport += "import io.github.hlg212.fcf.annotation.PkId;\n";
            strImport += "import io.github.hlg212.fcf.model.Model;\n";
            strImport += "import io.github.hlg212.fcf.annotation.Table;\n";
            strImport += "import lombok.Data;\n";

            StringBuffer sbProps = new StringBuffer("\n");
            StringBuffer sbFuncs = new StringBuffer("\n");

            sbProps.append(this.formatComment(table.getComments(), author, true, "", null, false, false));
//			sbProps.append("public class "+table.getClzName()+" extends Model implements Serializable{\n");
            sbProps.append("@Table(\""+table.getName()+"\")\n");
            sbProps.append("@Data \n");
            sbProps.append("public class " + table.getClzName() + " extends Model {\n");
            sbProps.append("\n\tprivate static final long serialVersionUID = 1L;\n\n");

            for (Col col : table.getColList()) {
                if (col.getPropName().equalsIgnoreCase(table.getPriKey())) {
                    sbProps.append("\t@PkId").append("\n");
                }
                if (!StringUtils.isEmpty(col.getComments())) {
                    sbProps.append("\t@Field(description=\"" + col.getComments() + "\")").append("\n");
                }
                sbProps.append("\tprivate " + col.getJavaType() + " " + col.getPropName() + ";" + "\n\n");
                if (col.getJavaType() == null) {
                    continue;
                }
                if (col.getJavaType().equals("Date") && !strImport.contains("java.util.Date")) {
                    strImport += "import java.util.Date;\n";
                }
                //sbFuncs.append(this.genGetterAndSetter(col));
            }
            sbFuncs.append("}\n");

            String txt = strPackage + strImport + sbProps + sbFuncs;
            this.writeFile(dir + table.getClzName() + ".java", txt);
        }
    }

    private String formatComment(String comment, String author, boolean isAddDate, String tab, String commentParamName, boolean isGetter, boolean isSetter) {
        if (StringUtils.isEmpty(comment) && (isGetter || isSetter)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(tab).append("/** ").append("\n");
        if (StringUtils.isEmpty(commentParamName)) {
            sb.append(tab).append(" * ").append(comment).append(" \n");
            sb.append(tab).append(" * \n");
        } else if (isGetter) {
            sb.append(tab).append(" * ").append("get ").append(commentParamName).append(" \n");
            sb.append(tab).append(" * @return ").append(comment).append(" \n");
        } else if (isSetter) {
            sb.append(tab).append(" * ").append("set ").append(commentParamName).append(" \n");
            sb.append(tab).append(" * ").append("@param ").append(commentParamName).append(" ").append(comment).append(" \n");
        }
        if (!StringUtils.isEmpty(author)) {
            sb.append(tab).append(" * @author").append(" ").append(author).append("\n");
        }
        if (isAddDate) {
            sb.append(tab).append(" * @date").append(" ").append(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())).append("\n");
        }
        sb.append(tab).append(" */").append("\n");
        return sb.toString();
    }

    /**
     * 根据模板生成代码
     *
     * @param tables        表s
     * @param replace       替换参数
     * @param templFilePath 模板文件路径
     * @param targetDir     目标路径
     * @param suffix        后缀，如 "Dao.java", "Mapper.xml"
     */
    public void geneCodeByTempl(List<Table> tables, Map<String, String> replace, String templFilePath, String targetDir, String suffix) {
        for (Table table : tables) {
            File file = new File(templFilePath);
            if (!file.exists()) {
                continue;
            }

            //取模板文本
            String txt = this.readFile(file);

            //替换内容
            for (Map.Entry<String, String> entry : replace.entrySet()) {
                if ("${currentDate}".equals(entry.getValue())) {
                    txt = txt.replaceAll(entry.getKey(), DateFormatUtils.ISO_DATE_FORMAT.format(new Date()));
                } else if ("${currentTableComment}".equals(entry.getValue())) {
                    txt = txt.replaceAll(entry.getKey(), StringUtils.isEmpty(table.getComments()) ? table.getClzName() : table.getComments());
                } else if ("${currentPoModel}".equals(entry.getValue())) {
                    txt = txt.replaceAll(entry.getKey(), table.getClzName());
                } else if ("${currentPoModelRequestMapping}".equals(entry.getValue())) {
                    String mapping = Character.toLowerCase(table.getClzName().charAt(0)) + table.getClzName().substring(1);
                    txt = txt.replaceAll(entry.getKey(), mapping);
                } else if ("${currentPkIdType}".equals(entry.getValue())) {
                    txt = txt.replaceAll(entry.getKey(), table.getPriKeyType());
                } else {
                    txt = txt.replaceAll(entry.getKey(), entry.getValue());
                }
            }

            //写文本
            this.writeFile(targetDir + table.getClzName() + suffix, txt);
        }
    }

    //-------------privates------------

    /**
     * 由字段映射出java数据类型
     *
     * @param col
     * @return
     */
    private String mapType(Col col) throws Exception {
        String javaType = null;
        if ("NUMBER".equalsIgnoreCase(col.getDataType())
                || "TINYINT".equalsIgnoreCase(col.getDataType())
                || "SMALLINT".equalsIgnoreCase(col.getDataType())
                || "INT".equalsIgnoreCase(col.getDataType())
                || "DECIMAL".equalsIgnoreCase(col.getDataType())
                || "BIGINT".equalsIgnoreCase(col.getDataType()) ) {
            if (col.getScale() == null) {
                javaType = "Long";
            } else {
                if (col.getScale() != 0) {
                    javaType = "Double";
                } else {
                    if (col.getPrecision() != null) {
                        if (col.getPrecision() < 8) {
                            javaType = "Integer";
                        } else {
                            javaType = "Long";
                        }
                    } else if (col.getLength() != null) {
                        if (col.getLength() < 8) {
                            javaType = "Integer";
                        } else {
                            javaType = "Long";
                        }
                    } else {
                        return null;
                    }
                }
            }
        } else if ("NVARCHAR2".equalsIgnoreCase(col.getDataType())) {
            javaType = "String";
        }
        else if ("VARCHAR".equalsIgnoreCase(col.getDataType())) {
            javaType = "String";
        }
        else if ("VARCHAR2".equalsIgnoreCase(col.getDataType())) {
            javaType = "String";
        } else if ("CHAR".equalsIgnoreCase(col.getDataType())) {
            javaType = "String";
        } else if ("TEXT".equalsIgnoreCase(col.getDataType())
                || "MEDIUMTEXT".equalsIgnoreCase(col.getDataType())
                || "CLOB".equalsIgnoreCase(col.getDataType())) {
            javaType = "String";
        }
        else if ("BLOB".equalsIgnoreCase(col.getDataType())) {
            javaType = "Byte[]";
        }
        else if ("FLOAT".equalsIgnoreCase(col.getDataType())) {
            javaType = "Double";
        } else if ("DATE".equalsIgnoreCase(col.getDataType())) {
            javaType = "Date";
        } else if ("DATETIME".equalsIgnoreCase(col.getDataType())
                || "TIMESTAMP".equalsIgnoreCase(col.getDataType())
                || col.getDataType().startsWith("TIMESTAMP")) {
            javaType = "Date";
        } else if (col.getDataType().startsWith("LONG")) {
            javaType = "String";
        }
        if (javaType == null) {
            throw new Exception("无法映射Java数据类型," + col.getDataType());
        }
        return javaType;
    }

    /**
     * 表名转实体类名
     */
    private String toClzName(String tablePrefix,String tableName) {
        String str = tableName.toLowerCase();
        if( StringUtils.isNotEmpty(tablePrefix) )
        {
            if ( str.startsWith(tablePrefix.toLowerCase()) ) {
                str = str.substring(tablePrefix.length());
            }
        }
        str = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,str);
        return str;
    }

    /**
     * 字段名转为属性名
     */
    private String toPropName(String colName) {
        String str = colName.toLowerCase();
        str = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,str);
        return str;
    }

    /**
     * 转首字母大写
     *
     * @param str
     * @return
     */
    private String upperFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 创建getter & setter
     *
     * @return
     */
    private String genGetterAndSetter(Col col) {
        String propName = col.getPropName();
        String type = col.getJavaType();
        String comment = col.getComments();

        StringBuffer bf = new StringBuffer();

        bf.append(this.formatComment(comment, null, false, "\t", propName, false, true));
        bf.append("\tpublic void set" + upperFirst(propName) + "(" + type + " " + propName + ") {\n");
        bf.append("\t	this." + propName + " = " + propName + ";\n");
        bf.append("\t}\n\n");

        bf.append(this.formatComment(comment, null, false, "\t", propName, true, false));
        bf.append("\tpublic " + type + " get" + upperFirst(propName) + "() {\n");
        bf.append("\t	return " + propName + ";\n");
        bf.append("\t}\n\n");

        return bf.toString();
    }

    /**
     * 读文本
     *
     * @param file
     * @return
     */
    private String readFile(File file) {
        String txt = null;
        //取模板文本
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            txt = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return txt;
    }

    /**
     * 写文本
     *
     * @param filePath
     * @param txt
     */
    private void writeFile(String filePath, String txt) {
        //如果目录不存在则自动创建
        String dir = filePath.substring(0, filePath.lastIndexOf("/"));
        File file = new File(dir);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }

        //写文本
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(filePath));
            fw.write(txt);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
