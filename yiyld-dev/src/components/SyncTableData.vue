<template>
    <div class="layout">
        <div class="right-border db-padding">
            <div class="dbClass">
                <p>
                    <span>源数据源: </span>
                    <br/>
                    <el-select value-key="dsName" v-model="dsView" filterable  @change="alldsliblist($event,1)" placeholder="请选择">
                        <el-option
                                v-for="(item) in listData.dslist"
                                :key="item"
                                :label="[item.dsName] +' - '+ [item.type]"
                                :value="item"
                        />
                    </el-select>
                </p>
                <p>
                    <span>源数据库: </span>
                    <br/>
                    <el-select v-model="dsViewList" filterable value-key="dsName" @change="alltables($event,1)" placeholder="请选择">
                        <el-option
                                v-for="(item,key) in dsFormList"
                                :key="key"
                                :label="[item.dsName] +' - '+ [item.dbType]"
                                :value="item"
                        />
                    </el-select>
                </p>
                <p>
                    <span>源数据表: </span>
                    <br/>
                    <el-select v-model="tableView" filterable value-key="tableName" @change="alltableColumns($event,1)"
                               placeholder="请选择">
                        <el-option
                                v-for="(item,key) in tableViewForm"
                                :key="key"
                                :label="[item.tableName] +' - '+ [item.tableRows]"
                                :value="item"
                        />
                    </el-select>
                </p>
            </div>
            <div>
                <p class="data-list-class">
                    <el-button type="info" plain @click="dialogOpen(1)">查看表数据</el-button>
                </p>
                <el-table :data="tableCoulmnsForm" style="width: 100%">
                    <el-table-column prop="columnsName" label="字段名"/>
                    <el-table-column prop="dataType" label="字段类型"/>
                    <el-table-column prop="dataLength" label="字段长度"/>
                    <el-table-column prop="dbName" label="数据库名"/>
                    <el-table-column prop="dbType" label="数据库类型"/>
                    <el-table-column prop="tableName" label="表名"/>
                </el-table>
            </div>
        </div>
        <div class="db-padding">
            <div class="dbClass">
                <p>
                    <span>目标数据源: </span>
                    <br/>
                    <el-select value-key="dsName" v-model="dsViewF" filterable  @change="alldsliblist($event,2)" placeholder="请选择">
                        <el-option
                                v-for="(item) in listData.dslist"
                                :key="item"
                                :label="[item.dsName] +' - '+ [item.type]"
                                :value="item"
                        />
                    </el-select>
                </p>
                <p>
                    <span>目标数据库: </span>
                    <br/>
                    <el-select v-model="dsViewListT" filterable value-key="dsName" @change="alltables($event,2)" placeholder="请选择">
                        <el-option
                                v-for="(item,key) in dsToList"
                                :key="key"
                                :label="[item.dsName] +' - '+ [item.dbType]"
                                :value="item"
                        />
                    </el-select>

                </p>
                <p>
                    <span>目标数据表: </span>
                    <br/>
                    <el-select v-model="dsViewT" filterable value-key="tableName" @change="alltableColumns($event,2)"
                               placeholder="请选择">
                        <el-option
                                v-for="(item,key) in tableViewTo"
                                :key="key"
                                :label="[item.tableName] +' - '+ [item.tableRows]"
                                :value="item"
                        />
                    </el-select>

                </p>
            </div>
            <div>
                <p class="data-list-class">
                    <el-button type="info" @click="dialogOpen(2)">查看表数据</el-button>
                </p>
                <el-table :data="tableCoulmnsTo" style="width: 100%">
                    <el-table-column prop="columnsName" label="字段名"/>
                    <el-table-column prop="dataType" label="字段类型"/>
                    <el-table-column prop="dataLength" label="字段长度"/>
                    <el-table-column prop="dbName" label="数据库名"/>
                    <el-table-column prop="dbType" label="数据库类型"/>
                    <el-table-column prop="tableName" label="表名"/>
                </el-table>
            </div>
        </div>
    </div>
    <el-dialog v-model="dialogTableVisible" @close="clean" width="100%" class="dialogTable" title="数据列表">
        <TableData ref="dataListTable" :paramsEntity="paramsEntity" :columns="columns"></TableData>
    </el-dialog>
</template>

<script setup>

    import TableData from '@/components/TableData'

    // const TableData = ref();     //RefChilde 要和Son组件上的class名相同
    // const fnCallChild = () => {
    //     TableData.value.sonFn();      //sonFn是子组件里的方法
    // };
    const props = defineProps({
        listData: Object
    })

    import {ref, getCurrentInstance} from 'vue'
    // 导入toRaw函数
    import { toRaw } from '@vue/reactivity';

    const {proxy} = getCurrentInstance();
    console.log(proxy.$api)
    const api = proxy.$api;

    let resultData = ref();

    const dsForm = props.listData;
    const dsTo = props.listData;
    const columns = ref('')
    const paramsEntity = ref();
    const dsFormList = ref('')
    const dsToList = ref('')

    const dialogTableVisible = ref(false);

    const tableViewForm = ref();
    const tableViewTo = ref();

    const dsView = ref();
    const dsViewList = ref();
    const tableView = ref();
    const dsViewF = ref();
    const dsViewListT = ref();
    const dsViewT = ref("");

    const tableCoulmnsForm = ref();
    const tableCoulmnsTo = ref();

    const dataListTable = ref(null);

    function alldsliblist(val, type) {

        let ds = val;//props.listData[val];

        let params = {dsType: ds.type,dbName:ds.dsName}

        api.dsapi.alldsliblist(params).then(function (response) {

            console.log("response", response.data)
            if (response.data.code == '200') {

                if (type == 1) {
                    dsFormList.value = response.data.data;
                } else if (type == 2) {
                    dsToList.value = response.data.data;
                }

            }
        });
    }

    function alltables(val, type) {

        console.log("val",val)

        let params = {dsType: val.dbType, dsName: val.dsName,dbName:val.dbName}

        console.log("params", params)

        api.dsapi.alltables(params).then(function (response) {

            console.log("response", response.data)
            if (response.data.code == '200') {

                if (type == 1) {
                    tableViewForm.value = response.data.data;
                } else if (type == 2) {
                    tableViewTo.value = response.data.data;
                }

            }
        });
    }

    function alltableColumns(val, type) {


        console.log("val",val)

        paramsEntity.value = {
            dsType: val.dbType, dsName: val.dsName, tableName: val.tableName, dbName:val.dbName
        }

        let params = {dsType: val.dbType, dsName: val.dsName, tableName: val.tableName, dbName:val.dbName}

        api.dsapi.alltableColumns(params).then(function (response) {

            console.log("response", response.data)
            if (response.data.code == '200') {

                if (type == 1) {
                    tableCoulmnsForm.value = response.data.data;
                } else if (type == 2) {
                    tableCoulmnsTo.value = response.data.data;
                }

                columns.value = response.data.data;

            }
        });
    }

    function dialogOpen(type) {

        let dsInfo = ref();

        if (type == 1) {

            dsInfo = tableView.value;

            columns.value = tableCoulmnsForm.value;
        }
        if (type == 2) {

            dsInfo = dsViewT.value;
            columns.value = tableCoulmnsTo.value;
        }
        paramsEntity.value = {
            dsType: dsInfo.dbType, dsName: dsInfo.dsName, tableName: dsInfo.tableName,tableSchema:dsInfo.tableSchema, dbName:dsInfo.dbName
        }
        dialogTableVisible.value = true;

        console.log("paramsEntity.value",paramsEntity.value)

        //父调子组件的方法
        // dataListTable.value.queryDataList(paramsEntity.value,columns.value);
    }

    function clean() {
//父调子组件的方法
        // dataListTable.value.cleanData();

    }

</script>
<style lang="scss">
    .layout {
        width: 100%;
        display: flex;

        .right-border {
            border-right: 1px solid #2c3e50 !important;
        }

        .db-padding {
            /*flex-shrink: 0;*/
            flex: 1;
        }

        .dbClass {
            flex: 1;
            display: flex;
            border: none;
            padding-left: 10px;
            padding-right: 10px;

            p {
                flex: 1;

            }

            p span {
                font-size: 16px;
            }

        }

    }

    .columns {
        width: 100%;
        display: flex;
        border-bottom: 1px solid #000000;

        span {
            flex: 1;
        }

    }

    .dcontent {
        width: 100%;

        .data-content {
            display: flex;
            border-top: 1px solid #000000;
            border-left: 1px solid #000000;

            span {
                flex: 1;
                border-bottom: 1px solid #000000;
                border-right: 1px solid #000000;
            }
        }
    }

    .data-list-class {
        text-align: right;
        padding-right: 20px;
    }
</style>
<style>
    .el-dialog {
        /*--el-dialog-width:100%!important;*/
        /*width:100%;*/
    }
</style>