<!-- svg配置 -->
<template>
  <div>
    <el-form-item label="线条样式">
      <avue-select v-model="main.activeOption.lineType"
                   :dic="dicOption.lineType"></avue-select>
    </el-form-item>
    <el-form-item label="线条颜色">
      <avue-input-color v-model="main.activeOption.color"></avue-input-color>
    </el-form-item>
    <el-form-item label="线条宽度">
      <avue-input-number v-model="main.activeOption.size"></avue-input-number>
    </el-form-item>
    <el-form-item label="线条动画">
      <avue-select v-model="main.activeOption.animation"
                   :dic="dicOption.lineAnimation"></avue-select>
    </el-form-item>
    <el-form-item label="填充颜色">
      <avue-input-color v-model="main.activeOption.fill"></avue-input-color>
    </el-form-item>
  </div>
</template>

<script>
import { dicOption } from '@/option/config'
export default {
  name: 'graph',
  inject: ["main"],
  components: {
  },
  data () {
    return {
      dicOption: dicOption
    }
  },
  methods: {

  }
}
</script>

<style>
</style>