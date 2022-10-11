<template>
    <div class="ds">
        <div v-for="item in listData.dslist">
            <p v-on:click="alldsliblist(item)">{{item.dsName}} - {{item.type}}</p>
            <div>
                <ul>
                    <li v-for="(val,key) in item.childList" :key="key">
                        <p v-on:click="alltables(item.type,val)"> {{key+1}} - {{val.dsName}} </p>
                        <div class="table">
                            <p v-for="(v,key) in val.tableList" :key="key"
                               v-on:click="alltableColumns(item.type,v)">{{key+1}} - {{v.tableName}} -
                                {{v.tableRows}} </p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
<script setup>

    import {ref, watch, getCurrentInstance} from 'vue'
    // 导入toRaw函数
    // import {toRaw} from '@vue/reactivity';

    const {proxy} = getCurrentInstance();
    console.log(proxy.$api)
    const api = proxy.$api;

    const emits = defineEmits(['query-columns'])

    const props = defineProps({
        listData: Object
    })
    function alldsliblist(val) {

        let params = {dsType: val.type,dbName:val.dsName}

        api.dsapi.alldsliblist(params).then(function (response) {

            if (response.data.code == '200') {

                val.childList = response.data.data;

            }
        });
    }

    function alltables(dsType, val) {

        console.log(val)

        let params = {dsType: dsType, dsName: val.dsName, dbName:val.dbName}

        api.dsapi.alltables(params).then(function (response) {

            if (response.data.code == '200') {

                val.tableList = response.data.data;

            }
        });
    }

    function alltableColumns(dsType, v) {

        let params = {dsType: dsType, dsName: v.dsName, tableName: v.tableName, tableSchema: v.tableSchema,dbName:v.dbName}

        api.dsapi.alltableColumns(params).then(function (response) {

            if (response.data.code == '200') {

                emits('query-columns', response.data.data)
                emits('query-params', params)
            }
        });
    }
</script>
<style lang="scss">
    .ds {
        height: 100%;
        border-right: 1px solid black;

        p {
            /*text-align: left;*/
            /*padding: 10px;*/
            padding-left: 15px;
            cursor: pointer;
        }

        p:hover {
            background: rgba(78, 238, 173, 0.16);
        }

        div ul li {
            margin-top: 3px;
        }

        div ul li p:hover {
            cursor: pointer;
            background: rgba(78, 238, 173, 0.16);
        }
    }
</style>