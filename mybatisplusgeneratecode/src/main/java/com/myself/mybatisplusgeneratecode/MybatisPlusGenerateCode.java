package com.myself.mybatisplusgeneratecode;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * <p>
 * 代码生成器
 * </p>
 */

public class MybatisPlusGenerateCode {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        //System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Tangzongzhong");//文件创建注释
        gc.setOpen(false); //生辰完成之后，是否弹出生成文件路径（默认为true）
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是 false
        gc.setActiveRecord(true);// 不需要ActiveRecord 特性的请改为 false
        gc.setEnableCache(false);// 是否需要 XML 二级缓存
        gc.setBaseResultMap(true);// 是否生成 XML ResultMap
        gc.setBaseColumnList(true);// 是否生成 XML columList
        gc.setSwagger2(true); //实体属性是否使用 Swagger2 注解
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setServiceName("%sService");
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/be_model?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setTypeConvert(new MySqlTypeConvert() {//数据库字段类型转换
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if (fieldType.toLowerCase().contains("number")) {//number 转 Integer
                    return DbColumnType.INTEGER;
                }
                if (fieldType.toLowerCase().contains("datetime")) {//datetime 转 Date
                    return DbColumnType.DATE;
                }
                if (fieldType.toLowerCase().contains("date")) {//date 转 Date
                    return DbColumnType.DATE;
                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));//指定生成到具体的模块名
        pc.setParent("com.lone.scc");//指定生成的父路径
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";


        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {// 调整 xml 生成目录演示
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //让默认生成 mapper.xml 的目录不再生成
        templateConfig.setXml(null);
//        templateConfig.setMapper(null);//不生成 mapper
//        templateConfig.setController(null);//不生成 controller
//        templateConfig.setService(null);// 不生成 service
//        templateConfig.setServiceImpl(null);//  不生成serviceImpl
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//表名生成策略（下划线转换成驼峰命名）
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//字段生成策略（下划线转换成驼峰命名）
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");//实体类集成 BaseEntity
        strategy.setEntityLombokModel(true);//使用lombok注解
        strategy.setRestControllerStyle(true);//controller 加上 restController 注解
        strategy.setControllerMappingHyphenStyle(true);//controller 中 RequestMapping 注解使用驼峰命令
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController"); //公共父类BaseController
        strategy.setSuperEntityColumns("id"); // 写于父类中的公共字段
//        strategy.setInclude(new String[]{"",""});//需要生成的表名，多个表可以用英文逗号分开
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));//需要生成的表名，多个表可以用英文逗号分开
//        strategy.setTablePrefix(pc.getModuleName() + "_");//生成时是否以模块名为前缀，默认模块名为前缀
        strategy.setEntityTableFieldAnnotationEnable(true);//实体类属性增加 @TableField 注解与数据库字段对应
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}


