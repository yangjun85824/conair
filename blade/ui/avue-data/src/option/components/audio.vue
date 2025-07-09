
<template>
  <div>
    <el-form-item label="文本内容">
      <avue-input type="textarea"
                  v-model="main.activeObj.data.value"></avue-input>
    </el-form-item>
    <el-form-item label="语言">
      <avue-input v-model="main.activeOption.lang">
      </avue-input>
    </el-form-item>
    <el-form-item label="音调">
      <avue-input v-model="main.activeOption.pitch">
      </avue-input>
    </el-form-item>
    <el-form-item label="速度">
      <avue-input v-model="main.activeOption.rate">
      </avue-input>
    </el-form-item>
    <el-form-item label="音量">
      <avue-input v-model="main.activeOption.volume">
      </avue-input>
    </el-form-item>
  </div>
</template>

<script>
export default {
  name: 'audio',
  data () {
    return {

    }
  },
  inject: ["main"]
}
</script>

<style>
</style>