/**
 * api导出 所有模块都添加到这里
 */
import dsapi from '@/request/api/ds';//数据库管理模块
import eipwsapi from '@/request/api/eip-ws';//企业集成模块
import dynjava from '@/request/api/dyn-java';//企业集成模块
// import signApi from '@/request/api/sign';//登录注册模块

export default {
    dsapi,
    eipwsapi,
    dynjava
    /*signApi*/
}
