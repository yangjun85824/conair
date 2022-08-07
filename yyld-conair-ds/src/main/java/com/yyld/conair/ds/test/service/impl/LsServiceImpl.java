package com.yyld.conair.ds.test.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyld.conair.ds.test.entity.Ls;
import com.yyld.conair.ds.test.mapper.LsMapper;
import com.yyld.conair.ds.test.service.LsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Service
public class LsServiceImpl extends ServiceImpl<LsMapper, Ls> implements LsService {

    @Resource
    private LsMapper mapper;

    @Override
    public List<Ls> selectPage() {

        IPage<Ls> page = new Page<>();

        page.setCurrent(1);
        page.setSize(2);

        page = mapper.selectPage(page,null);

        System.out.println(JSON.toJSONString(page));

        return page.getRecords();
    }
}
