<template>
  <el-dialog append-to-body
             class="avue-dialog"
             title="更多设置"
             :close-on-click-modal="false"
             v-model="box"
             width="70%">
    <el-form label-width="130px">
      <el-form-item label="数据类型">
        <avue-select v-model="contain.activeObj.dataType"
                     :dic="contain.dicOption.dataType"></avue-select>
        &nbsp;
        <template v-if="contain.isStatic">
          <el-button type="primary"
                     icon="el-icon-edit"
                     @click="contain.openCode('data','数据值')">编辑</el-button>
          &nbsp;
          <el-upload :show-file-list="false"
                     :auto-upload="false"
                     style="display:inline-block"
                     accept=".xls,.xlsx"
                     :on-change="handleImport">
            <el-button icon="el-icon-upload"
                       type="success">导入数据(Excel)</el-button>
          </el-upload>
        </template>
        <template v-else-if="contain.isApi">
          <avue-switch v-model="contain.activeObj.proxy"
                       :dic="[{label:'',value:false},{label:'开启跨域',value:true}]"
                       @click="contain.handleRefresh"></avue-switch>
        </template>
      </el-form-item>
      <template v-if="contain.isSql">
        <el-form-item label="数据源选择">
          <avue-select :dic="contain.DIC.sql"
                       v-model="contain.db"></avue-select>
        </el-form-item>

        <el-form-item label="SQL语句"
                      label-position="top">
          <el-tooltip effect="dark"
                      content="如果要获取变量，直接写成函数返回sql语句即可"
                      placement="top">
            <el-icon>
              <el-icon-info-filled></el-icon-info-filled>
            </el-icon>
          </el-tooltip>
          <monaco-editor v-model="contain.sql"
                         language="sql"
                         height="100"></monaco-editor>
        </el-form-item>
      </template>
      <template v-else-if="contain.isApi || contain.isNode">
        <el-form-item label="地址">
          <el-row>
            <el-col :span="8"
                    v-if="contain.isApi">
              <avue-select v-model="contain.activeObj.dataMethod"
                           placeholder="请选择请求方式"
                           :dic="contain.dicOption.dataMethod"></avue-select>
            </el-col>
            <el-col :span="16">
              <avue-input v-model="contain.activeObj.url"
                          placeholder="请输入地址"></avue-input>
            </el-col>
          </el-row>
        </el-form-item>
      </template>
      <template v-else-if="contain.isWs">
        <el-form-item label="WS地址">
          <el-input v-model="contain.activeObj.wsUrl">
          </el-input>
        </el-form-item>
      </template>
      <template v-else-if="contain.isRecord">
        <el-form-item label="数据集选择">
          <avue-select :dic="contain.DIC.record"
                       v-model="contain.activeObj.record"></avue-select>
        </el-form-item>
      </template>
      <template v-else-if="contain.isPublic">
        <el-form-item label="公共数据集选择">
          <avue-select :dic="contain.DIC.public"
                       v-model="contain.activeObj.public"></avue-select>
        </el-form-item>
      </template>
      <template v-else-if="contain.isMqtt">
        <el-form-item label="MQTT地址">
          <el-input v-model="contain.activeObj.mqttUrl">
          </el-input>
        </el-form-item>
        <el-form-item label="MQTT配置">
          <monaco-editor v-model="contain.activeObj.mqttConfig"
                         height="250"></monaco-editor>
        </el-form-item>
      </template>
      <el-form-item label="请求配置"
                    v-if="contain.isWs || contain.isApi">
        <el-tabs class="menu__tabs"
                 v-model="contain.dataTabs">
          <el-tab-pane label="请求参数（Body）"
                       name="0">
            <template v-if="contain.dataTabs==0">
              <el-radio-group v-if="['post','put'].includes(contain.activeObj.dataMethod)"
                              v-model="contain.activeObj.dataQueryType">
                <el-radio value="json">JSON数据</el-radio>
                <el-radio value="form">FORM表单</el-radio>
              </el-radio-group>
              <avue-highlight v-model="contain.activeObj.dataQuery"
                              height="200"></avue-highlight>
              <el-button class="el-button--edit"
                         type="primary"
                         icon="el-icon-edit"
                         @click="contain.openCode('dataQuery','请求参数')">编辑</el-button>
            </template>

          </el-tab-pane>
          <el-tab-pane label="请求头（Headers）"
                       name="1"
                       v-if="contain.isApi">
            <template v-if="contain.dataTabs==1">
              <avue-highlight v-model="contain.activeObj.dataHeader"
                              height="200"></avue-highlight>
              <el-button class="el-button--edit"
                         type="primary"
                         icon="el-icon-edit"
                         @click="contain.openCode('dataHeader','请求头')">编辑</el-button>
            </template>

          </el-tab-pane>

        </el-tabs>
      </el-form-item>
      <el-form-item label="过滤器">
        <avue-select v-model="contain.activeObj.dataFormatterId"
                     :dic="contain.filterDic"
                     :props="{label:'name',value:'id'}"></avue-select>
        <avue-highlight :value="contain.activeObj.dataFormatterId?contain.config.filters[contain.activeObj.dataFormatterId].dataFormatter:contain.activeObj.dataFormatter"
                        height="200"></avue-highlight>
        <el-button class="el-button--edit"
                   style="top:44px"
                   v-if="!contain.activeObj.dataFormatterId"
                   type="primary"
                   icon="el-icon-edit"
                   @click="contain.openCode('dataFormatter','编辑过滤器')">编辑</el-button>

      </el-form-item>
      <el-form-item label="响应数据">
        <el-tabs class="menu__tabs"
                 v-model="contain.resTabs">
          <el-tab-pane label="处理后数据"
                       name="0">
            <json-viewer v-loading="contain.dataLoading"
                         v-bind="$loadingParams"
                         :value="contain.dataRes"
                         copyable
                         theme="avue-json-theme"
                         boxed></json-viewer>
          </el-tab-pane>
          <el-tab-pane label="原始数据"
                       name="1">
            <json-viewer v-loading="contain.dataLoading"
                         v-bind="$loadingParams"
                         :value="contain.dataOldRes"
                         copyable
                         theme="avue-json-theme"
                         boxed></json-viewer>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>
    </el-form>
    <span class="avue-dialog__footer avue-dialog__footer--right">
      <el-button @click="handleTest"
                 v-if="contain.isSql&&contain.db"
                 icon="el-icon-connection"
                 type="success">测试连接</el-button>
      <el-button icon="el-icon-edit"
                 type="danger"
                 @click="contain.openCode('stylesFormatter','编辑样式')">编辑样式</el-button>
      <el-button type="primary"
                 icon="el-icon-refresh"
                 @click="contain.handleRes">请求数据</el-button>
      <el-button type="danger"
                 icon="el-icon-close"
                 @click="box=false">关闭</el-button>
    </span>
    <db ref="db"></db>
  </el-dialog>
</template>

<script>
import db from '@/page/components/db.vue'
import MonacoEditor from '@/page/components/monaco-editor'
export default {
  inject: ["contain"],
  components: {
    db,
    MonacoEditor
  },
  data () {
    return {
      box: false
    }
  },
  methods: {
    handleTest () {
      this.$refs.db.handleOpen(this.contain.db)
    },
    handleImport (file) {
      this.$Export.xlsx(file.raw)
        .then(data => {
          this.contain.activeObj.data = data.results;
          this.$message.success('导入成功')
          this.contain.handleRes()
        })
    },
  }
}
</script>
<style>
</style>