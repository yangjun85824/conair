
<template>
    <div class="table">
        <div>
            <div>数据列表</div>
<!--            <el-button @click="allDataList">查询</el-button>-->
        </div>
        <!--<div class="columns"><span v-for="(val,vkey) in columns">{{val.columnsName}}</span></div>
        <div class="dcontent" v-for="(value,key) in resultData" :key=key>
            <p class="data-content"><span v-for="(val,k) in columns">{{k+1}}{{value[val.columnsName]}}</span></p>
        </div>-->

        <el-table style="width: 100%" border :data="resultData" ref="multipleTableRef"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"/>
            <template v-for="(item,index) in columns">
                <el-table-column :prop="item.columnsName" :label="item.columnsName"></el-table-column>
            </template>
        </el-table>

        <div class="demo-pagination-block">
            <!--            <div class="demonstration">All combined</div>-->
            <el-pagination
                    v-model:currentPage="currentPage"
                    v-model:page-size="pageSize"
                    :page-sizes="[20, 50, 100, 200]"
                    :small="small"
                    :disabled="disabled"
                    :background="background"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalCount"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<script setup>
    import {ref, getCurrentInstance, defineExpose, watch} from 'vue'
        // 导入toRaw函数
        import {toRaw} from '@vue/reactivity';
        const props = defineProps({
            paramsEntity: Object,
            columns: Object,
            tableHead: Object
        })
    
        
    
        const {proxy} = getCurrentInstance();
    
        const api = proxy.$api;
    
        const multipleSelection = ref([])
    
        const resultData = ref();
    
        const currentPage = ref(1)
        const pageSize = ref(20)
        const small = ref(false)
        const background = ref(false)
        const disabled = ref(false)
        const totalCount = ref(0);
    
        const handleSizeChange = (val) => {
    
            props.paramsEntity.currentPage = currentPage.value;
            props.paramsEntity.pageSize = val;
            allDataList();
            console.log(`${val} items per page`)
        }
        const handleCurrentChange = (val) => {
    
            props.paramsEntity.currentPage = val;
            props.paramsEntity.pageSize = pageSize.value;
            allDataList();
            console.log(`current page: ${val}`)
        }
    
        const handleSelectionChange = (val) => {
            multipleSelection.value = val
    
            console.log(toRaw(multipleSelection.value[0]))
        }
    
        const allDataList = () => {
    
            if (props.paramsEntity.currentPage == null){
    
                props.paramsEntity.currentPage = currentPage.value;
                props.paramsEntity.pageSize = pageSize.value;
    
            }
            console.log("props.paramsEntity", props.paramsEntity)
    
            let params = props.paramsEntity
    
            api.dsapi.alltableData(params).then(function (response) {
    
                console.log("response", response.data)
                if (response.data.code == '200') {
    
                    let result = response.data.data;
                    resultData.value = result.records;
                    currentPage.value = result.current;
                    pageSize.value = result.size;
                    totalCount.value = result.total;
    
                }
            });
        }
    
        const queryDataList = (pentity, cloumns) => {
    
            props.paramsEntity = pentity;
            props.columns = cloumns;
    
            console.log("props.columns", props.columns)
            console.log("props.paramsEntity", props.paramsEntity)
    
            // setTimeout(
            allDataList()
            // , 500);
    
        }
    
        const cleanData = () => {
    
            props.paramsEntity = ref();
            props.columns = ref();
    
        }
        //当组件参数发生变化时调用
        watch(() => props.paramsEntity, () => {
            allDataList()
        });
    
        //暴露方法
        defineExpose({queryDataList, cleanData});
    
    </script>
    
<style lang="scss">
    .table {
        width: 100%;
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

</style>
<style scoped>
    .demo-pagination-block + .demo-pagination-block {
        margin-top: 10px;
    }
    .demo-pagination-block .demonstration {
        margin-bottom: 16px;
    }
</style>