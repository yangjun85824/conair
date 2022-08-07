package com.yyld.conair.ds.users.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyld.conair.ds.users.entity.Users;
import com.yyld.conair.ds.users.mapper.UsersMapper;
import com.yyld.conair.ds.users.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YANG JIAN JUN
 * @since 2022-07-24
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
