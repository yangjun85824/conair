<!-- 文字配置 -->
<template>
  <div>
    <el-form-item label="文本内容">
      <avue-input type="textarea"
                  v-model="main.activeObj.data.value"></avue-input>
    </el-form-item>
    <el-form-item label="字体颜色">
      <avue-input-color v-model="main.activeOption.color"></avue-input-color>
    </el-form-item>
    <el-form-item label="背景颜色">
      <avue-input-color v-model="main.activeOption.background"></avue-input-color>
    </el-form-item>
    <el-form-item label="容错">
      <avue-select :dic="dic"
                   v-model="main.activeOption.level"
                   placeholder="请选择模型类型"></avue-select>
    </el-form-item>
  </div>
</template>

<script>
import { dicOption } from '@/option/config'
export default {
  name: 'code',
  data () {
    return {
      dic: [{
        label: '25%',
        value: 'L'
      }, {
        label: '50%',
        value: 'M'
      }, {
        label: '75%',
        value: 'Q'
      }, {
        label: '100%',
        value: 'H'
      }]
    }
  },
  inject: ["main"]
}
</script>

<style>
</style>