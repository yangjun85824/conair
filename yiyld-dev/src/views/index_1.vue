<template>
    <div class="head">

    </div>
</template>

<script setup>
import LeftTree from '@/components/LeftTree.vue'
import TableColumn from '@/components/TableColumn.vue'
import TableData from '@/components/TableData'
import SyncTableData from '@/components/SyncTableData'
import DS from '@/components/DS'

import {
    ref,
    reactive,
    onMounted,
    getCurrentInstance
} from 'vue'

import  ccworkBridge  from 'ccwork-jsbridge';
ccworkBridge.init(() => {
    //origin --获取免密登录的服务域 如 https://bigdata.myhtime.com:8443
    // let origin = "https://bigdata.myhtime.com:8443";
    // 用法1：回调函数方式调用
    // ccworkBridge.getSSOCode(origin, function (code) {
    //     alert(code);
    // }, function (err) {
    //     alert(err);
    // });
    // 用法2：promise方式调用
    //    ccworkBridge.getSSOCode(origin, code=>{
    //         alert(code);
    //     }).catch(err=>{
    //         alert(err);
    //     })

});

const {
    proxy
} = getCurrentInstance();

const api = proxy.$api;
const dialogTableVisible = ref(false);
const dialogDsVisible = ref(false);

let listData = ref({}); //=["111", "2222", "2333"];

let columns = ref({});

let paramsEntity = ref({});

const queryColumns = (columnList) => {
    columns.value = columnList;
};

const queryParams = (pm) => {
    paramsEntity.value = pm;
    console.log("paramsEntity=======", paramsEntity)
};

function initDsList() {

    api.dsapi.alldslist().then(function (response) {

        console.log("response", response)
        if (response.data.code == '200') {

            listData.value = response.data.data;

        }
    });
}

onMounted(() => initDsList());

function tdlist() {
    console.log("sssss");
    proxy.$router.push('/tableDataList')
}

function restlist() {
    console.log("sssss");
    proxy.$router.push('/eip-rest-index')
}

function eip() {
    proxy.$router.push('/eip-index');
}
</script>

<style lang="scss">
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    /*text-align: center;*/
    color: #2c3e50;
}

nav {
    padding: 30px;

    a {
        font-weight: bold;
        color: #2c3e50;

        &.router-link-exact-active {
            color: #42b983;
        }
    }
}

.head {
    padding: 5px;

    .title {
        text-align: center;
        font-weight: bold;
        font-size: 18px;
        color: #42b983;
    }
}

.content {
    display: flex;
    border-top: 1px solid black;
    border-color: #2c3e50;
    padding-top: 5px;
    height: 88%;

    .left-list {
        fliex: 1;
        width: 300px;
    }

    .left {
        flex: 1;
        /*border: 1px solid red;*/
        /*height: 100px;*/
    }

    .right {
        flex: 3;
        /*border: 1px solid red;*/
        /*height: 100px;*/
    }
}
</style>
<style scoped>
.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 100%;
}

.el-input {
    width: 100%;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>