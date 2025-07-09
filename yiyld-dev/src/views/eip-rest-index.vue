<template>
<div class="head">
    <p class="title">Rest 接口在线测试</p>
</div>
<el-row class="tac" style="width:100%;border-top:1px solid #CCCCCC;">
    <el-col style="width:700px">
        <h5 class="mb-2">参数列表</h5>
        <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm" :size="formSize" status-icon>
            <el-form-item label="类型" prop="name">
                <el-input v-model="ruleForm.mothed" />
            </el-form-item>
            <el-form-item label="头部信息" prop="region">
                <el-input v-model="ruleForm.header"  type="textarea"/>
            </el-form-item>
            <el-form-item label="请求地址" prop="count" style="width:600px">
                <el-input v-model="ruleForm.httpurl" style="width:600px" />
            </el-form-item>
            <el-form-item label="输入参数" prop="resource">
                <el-input v-model="ruleForm.params" type="textarea" />
            </el-form-item>
            <el-form-item label="输出结果" prop="desc">
                <el-input v-model="result" type="textarea" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="rest_call(ruleFormRef)">调用</el-button>
            </el-form-item>
        </el-form>
    </el-col>
</el-row>
</template>

<script setup>
import {
    ref,
    getCurrentInstance,
    reactive
} from 'vue'
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
const {
    proxy
} = getCurrentInstance();

const api = proxy.$api;

const ruleForm = ref({
    mothed: 'GET',
    httpurl: 'http://localhost:45678/users/alist',
    header: '{"Content-Type":"application/json;charset=UTF-8"}',
    params: '{"dsType":"mysql","dbName":"a"}',
})
const result = ref('');

function rest_call() {

    let params = ruleForm.value ;
    params.header = JSON.parse(params.header);
    params.params = JSON.parse(params.params);
    
    api.eipwsapi.rest_dynamictest_call(params).then(function (response) {

        console.log("response", response)
        if (response.data.code == '200') {

            result.value = response.data.data;

        }
    });

}

</script>

<style lang="scss">
.head {
    padding: 5px;

    .title {
        text-align: center;
        font-weight: bold;
        font-size: 18px;
        color: #42b983;
    }
}

.form-item-class {
    margin-top: 20px;
}

.tac {
    
    margin-left: 20px;
}
</style>
<style scoped>
    
</style>
