<template>
<div class="head">
    <p class="title">Web Services解析器</p>
</div>

<el-form :inline="true" style="width:100%;border-top:1px solid #CCCCCC;border-bottom:1px solid #CCCCCC;">
    <el-form-item label="选择类型" class="form-item-class" style="padding-left:30px;width:200px;margin-top: 20px;">
        <el-select v-model="val" placeholder="Select">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled" />
        </el-select>
    </el-form-item>
    <el-form-item label="WSDL地扯" class="form-item-class" style="padding-left:30px;width:600px;margin-top: 20px;">
        <el-input v-show="val == 'wsdl'" v-model="wsdlurl" placeholder="WSDL URI" />
        <el-upload ref="upload" v-show="val == 'file'" class="upload-demo" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :limit="1" :on-exceed="handleExceed" :auto-upload="false">
            <template #trigger>
                <el-button type="primary">select file</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="submitUpload">
                upload to server
            </el-button>
            <template #tip>
                <div class="el-upload__tip text-red">
                    limit 1 file, new file will cover the old file
                </div>
            </template>
        </el-upload>
    </el-form-item>
    <el-form-item class="form-item-class">
        <el-button type="primary" @click="analysis">解析</el-button>
    </el-form-item>
</el-form>

<el-row class="tac">
    <el-col :span="3">
        <h5 class="mb-2">方法列表</h5>
        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
            <el-menu-item :index="key" v-for="(val,key) in mList" :key="key" @click="info(val)">
                <el-icon>
                    <document />
                </el-icon>
                <span>{{val.mothed}}</span>
            </el-menu-item>
        </el-menu>
    </el-col>
    <el-col :span="12">
        <h5 class="mb-2">参数列表</h5>
        <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm" :size="formSize" status-icon>
            <el-form-item label="方法名" prop="name">
                <el-input v-model="ruleForm.mothed" />
            </el-form-item>
            <el-form-item label="服务名" prop="region">
                <el-input v-model="ruleForm.localpart" />
            </el-form-item>
            <el-form-item label="命名空间地址" prop="count" style="width:600px">
                <el-input v-model="ruleForm.namespaceuri" style="width:600px" />
            </el-form-item>
            <el-form-item label="返回值方法" >
                <el-input v-model="ruleForm.mothedResponse" />
            </el-form-item>
            <el-form-item label="类型" prop="delivery">
                <el-input v-model="ruleForm.type" />
            </el-form-item>
            <el-form-item label="前缀" prop="type">
                <el-input v-model="ruleForm.prefix" />
            </el-form-item>
            <el-form-item label="输入参数" prop="resource">
                <el-input v-model="ruleForm.iparams" type="textarea" />
            </el-form-item>
            <el-form-item label="输出字段" prop="desc">
                <el-input v-model="ruleForm.oparams" type="textarea" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="eipanalysis_call(ruleFormRef)">调用</el-button>
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
const val = ref('wsdl')
const wsdlurl = ref("http://localhost:8001/ws/TestWebservices?wsdl");
const options = [{
        value: 'file',
        label: '文件',
    },
    {
        value: 'wsdl',
        label: 'wsdl',
        // disabled: true,
    }
]

const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    console.log(key, keyPath)
}

const mList = ref();

const upload = ref('')

const handleExceed = (files) => {
    upload.value.clearFiles()
    const file = files[0]
    file.uid = genFileId()
    upload.value.handleStart(file)
}

const submitUpload = () => {
    upload.value.submit()
}

function analysis() {

    let params = {
        wsdl: wsdlurl.value
    }

    api.eipwsapi.eipanalysis(params).then(function (response) {

        console.log("response", response)
        if (response.data.code == '200') {

            mList.value = response.data.data;

        }
    });

}

function eipanalysis_call() {

    ruleForm.value.wsdl = wsdlurl.value;

    let params = ruleForm.value ;

    let iparams = params.iparams;
    let oparams = params.oparams;
    
    params.iparams = JSON.parse(iparams)
    params.oparams = JSON.parse(oparams)
    
    
    api.eipwsapi.eipanalysis_call(params).then(function (response) {

        console.log("response", response)
        if (response.data.code == '200') {

            mList.value = response.data.data;

        }
    });

}

const formSize = ref('default')
const ruleFormRef = ref()
const ruleForm = ref({
    mothed: '',
    localpart: '',
    namespaceuri: '',
    mothedResponse: '',
    type: '',
    prefix: '',
    iparams: '',
    oparams: '',
    wsdl: ''
})

function info(val) {

    ruleForm.value = val; //reactive({val});
    if(!(typeof val.iparams === "string")){
      ruleForm.value.iparams = JSON.stringify(val.iparams);
      ruleForm.value.oparams = JSON.stringify(val.oparams);
    }
}

const rules = reactive({
    // name: [{
    //         required: true,
    //         message: 'Please input Activity name',
    //         trigger: 'blur'
    //     },
    //     {
    //         min: 3,
    //         max: 5,
    //         message: 'Length should be 3 to 5',
    //         trigger: 'blur'
    //     },
    // ],
    // region: [{
    //     required: true,
    //     message: 'Please select Activity zone',
    //     trigger: 'change',
    // }, ],
    // count: [{
    //     required: true,
    //     message: 'Please select Activity count',
    //     trigger: 'change',
    // }, ],
    // date1: [{
    //     type: 'date',
    //     required: true,
    //     message: 'Please pick a date',
    //     trigger: 'change',
    // }, ],
    // date2: [{
    //     type: 'date',
    //     required: true,
    //     message: 'Please pick a time',
    //     trigger: 'change',
    // }, ],
    // type: [{
    //     type: 'array',
    //     required: true,
    //     message: 'Please select at least one activity type',
    //     trigger: 'change',
    // }, ],
    // resource: [{
    //     required: true,
    //     message: 'Please select activity resource',
    //     trigger: 'change',
    // }, ],
    // desc: [{
    //     required: true,
    //     message: 'Please input activity form',
    //     trigger: 'blur'
    // }, ],
})
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
