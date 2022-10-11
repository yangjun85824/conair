<template>
  <div style="margin:20px">
      
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
    <el-button type="success" @click="onSubmit">添加</el-button>
  </el-form-item>
  <el-form-item label="系统名称">
      
    <el-input v-model="formInline.user" placeholder="系统名称" />
  </el-form-item>
  <el-form-item label="系统编码">
      <el-input v-model="formInline.user" placeholder="系统编码" />
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmit">查询</el-button>
  </el-form-item>
</el-form>
  </div>
  <el-table :data="tableData" style="width: 100%">
      
    <el-table-column prop="name" label="系统名称" width="150" />
    <el-table-column prop="state" label="系统编码" width="180" />
    <el-table-column prop="tag" label="过期时间" width="180" />
    <el-table-column prop="c" label="操作人" width="180" />
    <el-table-column prop="ctime" label="操作时间" width="180" />
    
    <!-- <el-table-column fixed="right" label="操作" width="120">
      <template #default>
        <el-button link type="primary" size="small" @click="handleClick"
          ></el-button
        >
        <el-button link type="primary" size="small">Edit</el-button>
      </template>
    </el-table-column> -->
  </el-table>
</template>

<script setup>
  import { Delete, Edit, Search, Share, Upload } from '@element-plus/icons-vue'
  import { Calendar } from '@element-plus/icons-vue'
  import { reactive } from 'vue'

const formInline = reactive({
user: '',
region: '',
})
const handleClick = () => {
  console.log('click')
}

const tableData = [
  {
    date: '2016-05-03',
    name: '银行系统',
    state: 'BANK_001',
    city: '长沙五一路78号',
    address: 'BANK_YYY-0002-0091',
    auth: '授权码',
    zip: '接口调用',
    tag: '2023-02-03',
    c: '张三',
    ctime: '2022-09-21 11:22:33',
  },
  {
      date: '2016-05-03',
    name: '银行系统',
    state: 'BANK_001',
    city: '长沙五一路78号',
    address: 'BANK_YYY-0152-0091',
    auth: '密码',
    zip: '接口调用',
    tag: '2023-02-03',
    c: '张三',
    ctime: '2022-09-21 11:22:33',
  },
  {
      date: '2016-05-03',
    name: '银行系统',
    state: 'BANK_001',
    city: '长沙五一路78号',
    address: 'BANK_YYY-0002-0094',
    auth: '授权码',
    zip: '单点登录',
    tag: '2023-02-03',
    c: '张三',
    ctime: '2022-09-21 11:22:33',
  },
  {
      date: '2016-05-03',
    name: '银行系统',
    state: 'BANK_001',
    city: '长沙五一路78号',
    address: 'BANK_YYY-0002-0078',
    auth: '客户端',
    zip: '接口调用',
    tag: '2023-02-03',
    c: '张三',
    ctime: '2022-09-21 11:22:33',
  },
]
</script>
