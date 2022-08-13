package com.yyld.conair.druid.druid;

import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName AbsDruidDataSurces
 * @Description 多数据源
 * @Author yangj
 * @Date 2022/6/24 10:25
 * @Vresion 1.0
 **/
public abstract class AbsDataSources {

    public String defaultDsName;

    public Map<Object, Object> dataSourceMap() {

        Properties readProperties = readProperties(null);

        if (readProperties == null) {
            return null;
        }

        String dsNameStr = readProperties.getProperty("ds-name");

        setDefaultDsName(readProperties.getProperty("defaultDs"));

        String[] dsNames = StringUtils.isEmpty(dsNameStr) ? null : dsNameStr.split(",");

        Map<Object, Object> dsMap = new HashMap();

        if (dsNames == null) {
            dsMap.put("defaultDs", createdDataSource(readProperties, ""));
        } else {

            for (String dsName : dsNames) {

                dsMap.put(dsName, createdDataSource(readProperties, dsName));
            }
        }

        return dsMap;
    }

    public abstract DataSource createdDataSource(Properties properties, String dName);

    private Properties readProperties(String propertiesUrl) {//读取配置文件

        InputStream in = null;

        Properties p = new Properties();
        try {

            if (StringUtils.isEmpty(propertiesUrl)) {
                in = AbsDataSources.class.getClassLoader().getResourceAsStream("db.properties");
            } else {
                File file = new File(propertiesUrl);
                in = new FileInputStream(file);
            }
            p.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    private void setDefaultDsName(String defaultDsName) {

        this.defaultDsName = defaultDsName;
    }

    public String getDefaultDsName() {

        return this.defaultDsName;
    }
}
