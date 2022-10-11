<template>
  <el-table :data="tableData" style="width: 100%">
  
    <el-table-column prop="name" label="第三方名称" width="150" />
    <el-table-column prop="state" label="第三方标识" width="180" />
    <el-table-column prop="city" label="第三方Domain" width="180" />
    <el-table-column prop="address" label="密钥" width="300" />
    <el-table-column prop="zip" label="作用域" width="180" />
    <el-table-column prop="auth" label="授权类型" width="180" />
    <el-table-column prop="tag" label="过期时间" width="180" />
    <el-table-column prop="ctime" label="操作时间" width="180" />
    <el-table-column prop="c" label="操作人" width="180" />
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
