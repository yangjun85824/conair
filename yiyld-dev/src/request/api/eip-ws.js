/*
数据库模块
*/

import http from '@/request/http';
import qs from 'qs';

const BASE_URL = "/eapi"

const eipwsapi = {
    //现有的数据库现有的数据库
    index(){
        return http.get(BASE_URL + '/eip');
    },
    //spring boot api
    analysis(params){
        return http.get(BASE_URL + '/eip/analysis',{params: params});
    },
    //camel发布的rest发布的api使用sevlet组件
    eipanalysis(params){
        return http.get(BASE_URL + '/eip/ws-analysis',{params: params});
    },
    //camel发布的rest发布的api使用sevlet组件
    eipanalysis_call(params){
        return http.post(BASE_URL + '/eip/ws-analysis',params);
    },
    //通过camel调用rest接口
    rest_call(params){
        return http.post(BASE_URL + '/eip-rest/dtrest',params);
    },
    //通过camel调用rest接口
    rest_dynamictest_call(params){
        return http.post(BASE_URL + '/eip-rest/dynamictest',params);
    },
    // //添加数据库
    // addDs(params){
    //     return http.post('/ds/addDs',qs.stringify(params));
    // }



};

export default eipwsapi;
