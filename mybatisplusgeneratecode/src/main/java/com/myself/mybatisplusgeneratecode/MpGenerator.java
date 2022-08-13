package com.myself.mybatisplusgeneratecode;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * @description:
 * @author: AT
 * @Date: 2020/12/26 2:36 下午
 */
public class MpGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获取当前的项目根路径
        String projectPath = System.getProperty("user.dir");
        // Java代码的存放位置
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者 ，会在生成的类上显示
        gc.setAuthor("tangzongzhong");
        //是否打开文件
        gc.setOpen(false);
        //ID生成策略
        gc.setIdType(IdType.ASSIGN_UUID);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        // 生成mapper.xml文件中的 ResultMap 对象
        gc.setBaseResultMap(true);
        // XML ColumnList: mapper.xml生成查询结果列
        gc.setBaseColumnList(true);
        //设置到 mpg 中
        mpg.setGlobalConfig(gc);

        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        //数据库链接的url 部分 只需更换 ip地址+端口 + 数据库名称  ？号前的部分
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/?characterEncoding=utf8&useSSL=true&&serverTimezone=GMT%2B8");
////        根据数据库版本自定义的驱动类型 5.几 以前用  com.mysql.jdbc.Driver
////        8.0以上用 com.mysql.cj.jdbc.Driver
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        //数据库用户名
//        dsc.setUsername("root");
//        //数据库密码
//        dsc.setPassword("123456");
//        //将数据库信息设置到 mpg中
//        mpg.setDataSource(dsc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:oracle:thin:@172.19.3.24:1521:orcl");
        dsc.setDbType(DbType.ORACLE);
        dsc.setTypeConvert(new OracleTypeConvert());
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("c##be_trade");
        dsc.setPassword("be_trade");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // 自定义包名字 一般是根路径下的基础包
        pc.setParent("com.sip.grc");

//         自定义包名字，及各个生成类的存放包名字 如果没有特定义，
//         就是常规的 controller 、service、(service 下的 impl)、mapper、entity
//        pc.setParent("")
//                .setEntity("")
//                .setMapper("")
//                .setService("")
//                .setServiceImpl("")
//                .setController("")

        //将包相关设置放入到 mpg
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
        // 如果模板引擎是 velocity 切换成 下面这个
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ，
                // 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        //将配置好的输出模板信息放入 cfg
        cfg.setFileOutConfigList(focList);
        //将配置好的cfg放入mpg
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置需要继承的父类
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");

        //在实体类上开启lombok注解
        strategy.setEntityLombokModel(true);
        //设置 TableField注解内容 属性对应的数据库字段
        strategy.setEntityTableFieldAnnotationEnable(true);
//        strategy.setRestControllerStyle(true);
//        // 公共父类 所有的controller 继承一个baseController 不用在页面 @Autowired
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        这里不再用输入的方式手动输入表面，直接获取全部的表，如果没有生成会自动生成，已存在的不用理会
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//         controller映射地址：驼峰转连字符
        //RestController注解
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
//        表的前缀 没有就不写
//        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
