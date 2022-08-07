package com.yyld.conair.commons.data.inter;

/**
 * @ClassName DataTypeInter
 * @Description 数据字段类型接口
 * @Author yangj
 * @Date 2022/5/26 22:42
 * @Vresion 1.0
 **/
public interface DataFieldTypeInter<T> {

    T fieldTypeHandler(Object value) throws Exception;

}
