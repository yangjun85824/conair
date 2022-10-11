<template>
    <div class="dslayout">
        <div class="header-ds-class">
            <span>源数据源: </span>
            <el-select value-key="dsName" v-model="dsView" filterable @change="dsinfo($event)"
                       placeholder="请选择">
                <el-option
                        v-for="(item) in listData.dslist"
                        :key="item"
                        :label="[item.dsName] +' - '+ [item.type]"
                        :value="item"
                />
            </el-select>
        </div>
        <div style="margin: 20px;">
            <el-row align="middle" :gutter="10" style="margin-bottom: 20px;">
                <el-button type="info" plain @click="jdbctest">测试连接</el-button>
                <el-button type="primary" plain @click="addDs">添加</el-button>
            </el-row>
            <el-form v-model="formLabelAlign" :label-position="labelPosition" label-width="150px"
                     :model="formLabelAlign">
                <el-row align="middle" :gutter="10">
                    <el-form-item label="数据源类型">
                        <el-input v-model="formLabelAlign.type" style="width: 300px"/>
                    </el-form-item>
                    <el-form-item label="数据源驱动包">
                        <el-input v-model="formLabelAlign.driver" style="width: 300px"/>
                    </el-form-item>
                </el-row>
                <el-form-item label="数据源名称">
                    <el-input v-model="formLabelAlign.dsName" style="width: 300px"/>
                </el-form-item>
                <el-form-item label="数据源链接">
                    <el-input v-model="formLabelAlign.url" style="width: 800px"/>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="formLabelAlign.username" style="width: 300px"/>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="formLabelAlign.password" style="width: 300px"/>
                </el-form-item>
                <hr/>
                <el-row align="middle" :gutter="10">
                    <el-form-item label="初始化时建立物理连接的个数">
                        <el-input v-model="formLabelAlign.initialSize"/>
                    </el-form-item>
                    <el-form-item label="最大连接池数量">
                        <el-input v-model="formLabelAlign.maxActive"/>
                    </el-form-item>
                    <el-form-item label="最小连接池数量">
                        <el-input v-model="formLabelAlign.minIdle"/>
                    </el-form-item>

                    <el-form-item label="获取连接时最大等待时间，单位毫秒">
                        <el-input v-model="formLabelAlign.maxWait"/>
                    </el-form-item>
                    <el-form-item label="1) Destroy线程会检测连接的间隔时间2) testWhileIdle的判断依据">
                        <el-input v-model="formLabelAlign.timeBetweenEvictionRunsMillis"/>
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="formLabelAlign.minEvictableIdleTimeMillis"/>
                    </el-form-item>

                    <el-form-item label="用来检测连接是否有效的sql">
                        <el-input v-model="formLabelAlign.validationQuery"/>
                    </el-form-item>

                    <el-form-item label="建议配置为true，不影响性能，并且保证安全性">
                        <el-input v-model="formLabelAlign.testWhileIdle"/>
                    </el-form-item>
                    <el-form-item label="申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能">
                        <el-input v-model="formLabelAlign.testOnBorrow"/>
                    </el-form-item>
                    <el-form-item label="归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能">
                        <el-input v-model="formLabelAlign.testOnReturn"/>
                    </el-form-item>

                    <el-form-item label="是否缓存preparedStatement">
                        <el-input v-model="formLabelAlign.poolPreparedStatements"/>
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="formLabelAlign.maxPoolPreparedStatementPerConnectionSize"/>
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="formLabelAlign.connectionProperties"/>
                    </el-form-item>
                    <el-form-item label="">
                        <el-input v-model="formLabelAlign.filters"/>
                    </el-form-item>
                </el-row>
                <el-row align="middle" :gutter="10" style="margin-top: 20px;">
                    <el-button type="info" plain @click="jdbctest">测试连接</el-button>
                    <el-button type="primary" plain  @click="addDs">添加</el-button>
                </el-row>
            </el-form>
        </div>
    </div>

</template>

<script setup>

    const props = defineProps({
        listData: Object
    })

    import {ref, reactive, getCurrentInstance} from 'vue'
    import { ElMessage } from 'element-plus'
    // 导入toRaw函数
    import {toRaw} from '@vue/reactivity';

    const {proxy} = getCurrentInstance();
    console.log(proxy.$api)
    const api = proxy.$api;

    const labelPosition = ref('right')

    const formLabelAlign = ref({})

    function dsinfo(val) {

        console.log(val)
        let temp = val;
        formLabelAlign.value = JSON.parse(JSON.stringify(temp));

    }
    
    function jdbctest() {

        console.log(formLabelAlign.value)

        api.dsapi.jdbctest(formLabelAlign.value).then(function (response){

            console.log("response",response.data)
            if (response.data.code == '200') {
                ElMessage({message:'数据库连接成功',type: 'success',})
            }else {
                ElMessage.error('数据库连接失败')
            }
        });
    }

    function addDs() {

        console.log(formLabelAlign.value)

        api.dsapi.addDs(formLabelAlign.value).then(function (response){

            console.log("response",response.data)
            if (response.data.code == '200') {
                ElMessage({message:'数据库添加成功',type: 'success',})
            }else {
                ElMessage.error(response.data.message)
            }
        });
    }

</script>
<style lang="scss">
    .dslayout {
        width: 100%;

        .header-ds-class {
            padding-bottom: 10px;
            border-bottom: 1px solid #dcdfe6;
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

    .el-row {
        width: 100%;

        .el-input {
            width: 300px;
        }
    }
</style>
<style>
    .el-dialog {
        /*--el-dialog-width:100%!important;*/
        /*width:100%;*/
    }
</style>