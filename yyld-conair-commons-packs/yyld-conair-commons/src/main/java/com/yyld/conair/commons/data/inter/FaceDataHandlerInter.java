package com.yyld.conair.commons.data.inter;

import java.util.List;
import java.util.Map;

/**
 * @ClassName FaceDataHandlerInter
 * @Description 数据处理接口
 * @Author yangj
 * @Date 2022/5/26 22:42
 * @Vresion 1.0
 **/
public interface FaceDataHandlerInter {

    <T> List<T> handler(List<T> data, Map<String, Object> paramMap, String json) throws Exception;

    <T> String sqlWhereHandler(Map<String, Object> paramMap, String json) throws Exception;

    <T> String sqlManyWhereHandler(List<Map<String, Object>> mapList, String json, String relType) throws Exception;

}
