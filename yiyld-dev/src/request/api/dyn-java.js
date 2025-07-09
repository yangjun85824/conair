/*
动态java加载模块
*/

import http from '@/request/http';
import qs from 'qs';

const BASE_URL = "/dyn-java"

const dynjavaapi = {
    //现有的数据库现有的数据库
    // alldslist(){
    //     return http.get(BASE_URL + '/ds/alldslist');
    // },
    // //测试数据源链接
    index(){
        console.log("1111111111");
        return http.post(BASE_URL + '/index');
    }
    


};

export default dynjavaapi;
