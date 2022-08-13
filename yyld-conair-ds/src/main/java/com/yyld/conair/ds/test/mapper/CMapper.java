package com.yyld.conair.ds.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyld.conair.ds.test.entity.Ls;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Mapper
public interface CMapper extends BaseMapper<Ls> {

    @Select("select * from Y_BS_MLCF_BT_BUTTON")
    List<Object> query();

}
