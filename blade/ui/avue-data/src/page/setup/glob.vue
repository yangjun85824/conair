<template>
  <el-dialog append-to-body
             title="更多设置"
             class="avue-dialog"
             :close-on-click-modal="false"
             v-model="box"
             width="70%">
    <el-form label-width="140px">
      <el-form-item label="全局请求地址">
        <avue-input v-model="contain.config.url"
                    placeholder="/"></avue-input>
      </el-form-item>
      <el-form-item label="全局请求参数">
        <avue-highlight height="200"
                        v-model="contain.config.query"></avue-highlight>
        <el-button class="el-button--edit"
                   type="primary"
                   icon="el-icon-edit"
                   @click="contain.openCode('query','全局请求参数')">编辑</el-button>
      </el-form-item>
      <el-form-item label="全局请求头">
        <avue-highlight height="200"
                        v-model="contain.config.header">
        </avue-highlight>
        <el-button class="el-button--edit"
                   type="primary"
                   icon="el-icon-edit"
                   @click="contain.openCode('header','全局请求头')">编辑</el-button>
      </el-form-item>
      <el-form-item label="大屏渲染前">
        <avue-highlight height="200"
                        v-model="contain.config.before">
        </avue-highlight>
        <el-button class="el-button--edit"
                   type="primary"
                   icon="el-icon-edit"
                   @click="contain.openCode('before','大屏渲染前')">编辑</el-button>
      </el-form-item>
      <el-form-item label="全局CSS样式">
        <avue-highlight height="200"
                        v-model="contain.config.style">
        </avue-highlight>
        <el-button class="el-button--edit"
                   type="primary"
                   icon="el-icon-edit"
                   @click="contain.openCode('style','全局CSS样式')">编辑</el-button>
      </el-form-item>
    </el-form>
    <span class="avue-dialog__footer avue-dialog__footer--right">
      <el-button type="danger"
                 icon="el-icon-close"
                 @click="box=false">关闭</el-button>
    </span>
  </el-dialog>
</template>
<script>
import MonacoEditor from '@/page/components/monaco-editor'
export default {
  inject: ["contain"],
  components: {
    MonacoEditor
  },
  data () {
    return {
      box: false
    }
  }
}
</script>
<style>
</style>