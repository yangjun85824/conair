package com.yyld.conair.ds.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyld.conair.ds.test.entity.Ls;
import com.yyld.conair.ds.test.mapper.CMapper;
import com.yyld.conair.ds.test.mapper.LsMapper;
import com.yyld.conair.ds.test.service.CService;
import com.yyld.conair.ds.test.service.LsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Service
public class CServiceImpl implements CService {

    @Resource
    private CMapper cMapper;

    public List<Object> list() {

        return cMapper.query();
    }

}
