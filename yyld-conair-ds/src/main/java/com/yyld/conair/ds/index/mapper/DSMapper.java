package com.yyld.conair.ds.index.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyld.conair.ds.users.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Mapper
public interface DSMapper extends BaseMapper<Map<Object,Object>> {

    @Select("${dssql}")
    List<Map<Object, Object>> allDatabases(@Param("dssql")String dssql);
}
