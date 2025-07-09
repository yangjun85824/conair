<!-- 通用形配置 -->
<template>
  <div>
    <el-form-item label="模板">
      <el-input v-model="main.activeOption.templateCode">
        <template #append>
          <span @click="box=true">选择</span>
        </template>

      </el-input>
    </el-form-item>
    <el-form-item label="参数">
      <json-viewer :value="main.activeOption.templateContent"
                   copyable
                   theme="avue-json-theme"
                   boxed></json-viewer>
    </el-form-item>
    <el-form-item label="调用">
      <avue-highlight :height="300"
                      v-model="main.activeOption.templateDebug"></avue-highlight>
    </el-form-item>
    <el-dialog title="选择模板"
               width="80%"
               class="avue-dialog"
               v-model="box">
      <task @submit="handleSelect"
            :menu="true"
            v-if="box"></task>
    </el-dialog>
  </div>
</template>

<script>
import task from '@/page/list/task.vue'
export default {
  name: 'notice',
  inject: ["main"],
  components: {
    task
  },
  data () {
    return {
      box: false
    }
  },
  methods: {
    handleSelect (item) {
      this.main.activeOption.templateCode = item.templateCode
      this.main.activeOption.templateContent = item.templateParam
      this.main.activeOption.templateDebug = "refs['" + this.main.activeObj.index + "'].send({})"
      this.box = false
    }
  }
}
</script>

<style>
</style>