package com.yyld.conair.ds.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyld.conair.ds.test.entity.Ls;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
public interface LsService extends IService<Ls> {

    List<Ls> selectPage();
}
