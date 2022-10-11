/*
数据库模块
*/

import http from '@/request/http';
import qs from 'qs';

const BASE_URL = "/api"

const dsapi = {
    //现有的数据库现有的数据库
    alldslist(){
        return http.get(BASE_URL + '/ds/alldslist');
    },
    //测试数据源链接
    jdbctest(params){
        return http.post(BASE_URL + '/ds/jdbctest',qs.stringify(params));
    },
    //添加数据库
    addDs(params){
        return http.post(BASE_URL + '/ds/addDs',qs.stringify(params));
    },
    //查询所有库
    alldsliblist(params){
        return http.get(BASE_URL + '/ds/alldsliblist',{params: params});
    },
    //查询数据库表
    alltables(params){
        return http.get(BASE_URL + '/ds/alltables',{params: params});
    },
    //查询数据库表字段
    alltableColumns(params){
        return http.get(BASE_URL + '/ds/alltableColumns',{params: params});
    },
    //查询数据库表数据
    alltableData(params){
        return http.get(BASE_URL + '/ds/alltableData',{params: params});
    }



};

export default dsapi;
