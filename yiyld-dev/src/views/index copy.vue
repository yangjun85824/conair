<template>
    <div class="head">
        <p class="title">数据库迁移工具</p>
        <p>
            <el-button @click="dialogTableVisible = true">数据迁移 / 同步</el-button>
            <el-button @click="dialogDsVisible = true">数据数据源维护</el-button>
            <el-button @click="eip">EIP首页接口测试</el-button>
            <el-button @click="tdlist">跳转到测试列表</el-button>
            <router-view></router-view>
        </p>
        <el-dialog append-to-body fullscreen v-model="dialogTableVisible" width="100%" class="dialogTable" title="跨数据库表同数据迁移 / 同步">
            <SyncTableData :listData="listData"></SyncTableData>
        </el-dialog>
        <el-dialog append-to-body fullscreen v-model="dialogDsVisible" width="100%" class="dialogTable" title="数据数据源维护">
            <DS :listData="listData"></DS>
        </el-dialog>
    </div>
    <div class="content">
        <div class="left-list">
            <LeftTree :listData="listData" @query-params="queryParams" @query-columns="queryColumns"></LeftTree>
        </div>
        <div class="left">
            <TableColumn :columns="columns" :tableName="paramsEntity.tableName"></TableColumn>
        </div>
        <div class="right">
            <TableData :paramsEntity="paramsEntity" :columns="columns"></TableData>
        </div>
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
    