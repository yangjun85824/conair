<template>
  <el-container class="list">
    <avue-crud ref="crud"
               :option="option"
               v-model="form"
               style="display: none;"
               v-model:page="page"
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
               @on-load="onLoad"
               v-loading="loading"
               v-bind="$loadingParams"
               :before-close="beforeClose"
               :before-open="beforeOpen"
               :data="data">
      <template #content-form="{}">
        <template v-if="isStatic">
          <el-form-item label="数据值"
                        label-position="top">
            <monaco-editor v-model="form.data"
                           language="javascript"
                           height="100"></monaco-editor>
            <br />
            <el-upload :show-file-list="false"
                       :auto-upload="false"
                       style="display:inline-block"
                       accept=".xls,.xlsx"
                       :on-change="handleImport">
              <el-button icon="el-icon-upload"
                         type="success">导入数据(Excel)</el-button>
            </el-upload>

          </el-form-item>
        </template>
        <template v-else-if="isSql">
          <el-form-item label="数据源选择">
            <avue-select :dic="DIC.sql"
                         v-model="form.dbId"></avue-select>
          </el-form-item>

          <el-form-item label="SQL语句"
                        label-position="top">
            <monaco-editor v-model="form.dbSql"
                           language="sql"
                           height="100"></monaco-editor>
          </el-form-item>
        </template>
        <template v-else-if="isApi || isNode">
          <el-form-item label="请求方式"
                        v-if="isApi">
            <avue-select v-model="form.dataMethod"
                         :dic="DIC.method"></avue-select>
          </el-form-item>
          <el-form-item label="地址"
                        v-if="isApi">
            <avue-input v-model="form.url"
                        placeholder="请输入地址"></avue-input>
          </el-form-item>
          <el-form-item label="开启跨域"
                        v-if="isApi">
            <avue-switch v-model="form.proxy"
                         @click="handleRes"></avue-switch>
          </el-form-item>
        </template>
        <template v-else-if="isWs">
          <el-form-item label="WS地址">
            <el-input v-model="form.wsUrl">
            </el-input>
          </el-form-item>
        </template>
        <template v-else-if="isMqtt">
          <el-form-item label="MQTT地址">
            <el-input v-model="form.mqttUrl">
            </el-input>
          </el-form-item>
          <el-form-item label="MQTT配置">
            <monaco-editor v-model="form.mqttConfig"
                           height="250"></monaco-editor>
          </el-form-item>
        </template>
        <el-form-item label="请求配置"
                      v-if="isWs || isApi">
          <el-tabs class="menu__tabs"
                   v-model="tabs">
            <el-tab-pane label="请求参数（Body）"
                         name="0">
              <template v-if="tabs==0">
                <el-radio-group size="small"
                                v-if="['post','put'].includes(form.dataMethod)"
                                v-model="form.dataQueryType">
                  <el-radio label="json">JSON数据</el-radio>
                  <el-radio label="form">FORM表单</el-radio>
                </el-radio-group>
                <monaco-editor v-model="form.dataQuery"
                               language="javascript"
                               height="100"></monaco-editor>
              </template>
            </el-tab-pane>
            <el-tab-pane label="请求头（Headers）"
                         v-if="isApi"
                         name="1">
              <template v-if="tabs==1">
                <monaco-editor v-model="form.dataHeader"
                               language="javascript"
                               height="100"></monaco-editor>
              </template>
            </el-tab-pane>

          </el-tabs>
        </el-form-item>
        <el-form-item label="过滤器">
          <monaco-editor v-model="form.dataFormatter"
                         height="100"></monaco-editor>
        </el-form-item>
        <el-form-item label="响应数据">
          <json-viewer :value="result"
                       v-loading="boxLoading"
                       v-bind="$loadingParams"
                       copyable
                       theme="avue-json-theme"
                       boxed></json-viewer>
        </el-form-item>
      </template>
      <template #menu-form>
        <el-button @click="handleRes"
                   icon="el-icon-upload"
                   type="primary">刷新数据</el-button>
        <el-button @click="handleTest"
                   v-if="isSql&&form.dbId"
                   icon="el-icon-connection"
                   type="success">测试连接</el-button>
      </template>
    </avue-crud>
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="$refs.crud.rowAdd()">
          <img :src="`${$router.options.base}img/db.png`"
               height="40px"
               alt="">
          <div>
            <p>创建数据集</p>
            <span>多种查询方式 自由组合</span>
          </div>
        </div>
        <div class="content__page">
          <div class="list-search">
            <el-input v-model="search.name"
                      @keyup.enter="onLoad"
                      placeholder="请输入名称">
              <template #suffix>

                <el-icon @click="onLoad"
                         class="el-input__icon">
                  <el-icon-search></el-icon-search>
                </el-icon>
              </template>
            </el-input>
          </div>
          <el-pagination v-if="page.total>0"
                         layout="total, prev, pager, next,jumper"
                         background
                         size="small"
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :page-size="page.size"
                         v-model:current-page="page.page"
                         :total="page.total">
          </el-pagination>
        </div>
      </div>
    </el-header>
    <el-main class="content"
             v-loading="loading"
             v-bind="$loadingParams">
      <div class="content__box">
        <template v-if="data.length>0">
          <div class="content__item"
               v-for="(item,index) in data"
               @mouseenter="item._menu=true"
               @mouseleave="item._menu=false"
               :key="index">
            <div class="content__info">
              <div class="content__logo">
                <p>{{ getTypeLabel(item.dataType) }}</p>
              </div>

              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                  <el-tooltip content="测试">
                    <el-icon @click="handleResult(item)">
                      <el-icon-connection></el-icon-connection>
                    </el-icon>
                  </el-tooltip>
                </div>
                <div class="content__start">
                  <div class="content__btn"
                       @click="handleEdit(item,index)">
                    <el-icon>
                      <el-icon-edit></el-icon-edit>
                    </el-icon>
                  </div>
                  <div class="content__btn"
                       @click="rowDel(item,index)">
                    <el-icon>
                      <el-icon-delete></el-icon-delete>
                    </el-icon>
                  </div>
                </div>

              </div>
            </div>
            <div class="content__main">
              <span class="content__name">{{item.name}}</span>
              <span class="content__status"> {{   item.username }}</span>
            </div>
          </div>
        </template>
        <el-empty v-else
                  class="content__empty"
                  description="暂无数据">
          <template #image>
            <svg-icon icon-class="empty" />
          </template>
        </el-empty>
      </div>
    </el-main>
    <db ref="db"></db>
    <el-dialog :title="form.name"
               class="avue-dialog"
               v-model="box"
               width="50%">
      <json-viewer :value="result"
                   copyable
                   v-loading="boxLoading"
                   v-bind="$loadingParams"
                   theme="avue-json-theme"
                   boxed></json-viewer>
    </el-dialog>
  </el-container>
</template>

<script>
import db from '@/page/components/db.vue'
import * as mqtt from 'mqtt/dist/mqtt.min';
import crypto from '@/utils/crypto'
import { addParam } from '@/echart/util';
import { getFunction, funEval } from '@/utils/utils'
import MonacoEditor from '@/page/components/monaco-editor'
import { getList, getObj, addObj, delObj, updateObj } from '@/api/record'
import { getList as getDbList, dynamicSql } from "@/api/db";
import { dicOption } from '@/option/config'
dicOption.dataType.splice(4, 1)
const dataType = dicOption.dataType
export default {
  components: {
    MonacoEditor,
    db
  },
  data () {
    return {
      box: false,
      search: {},
      loading: false,
      tabs: 0,
      box: false,
      client: {},
      mqClient: {},
      DIC: {
        method: dicOption.dataMethod,
        sql: []
      },
      result: {},
      form: {},
      data: [],
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        height: 'auto',
        calcHeight: 140,
        header: false,
        labelWidth: 130,
        index: true,
        align: 'center',
        headerAlign: 'center',
        column: [
          {
            label: '名称',
            prop: 'name',
            rules: [{
              required: true,
              message: "请输入名称",
              trigger: "blur"
            }]
          },
          {
            label: '类型',
            prop: 'dataType',
            type: 'select',
            dicData: dataType,
            rules: [{
              required: true,
              message: "请选择类型",
              trigger: "blur"
            }]
          },
          {
            label: '',
            prop: 'content',
            hide: true,
            labelWidth: 0,
            span: 24,
          }
        ]
      }
    }
  },
  computed: {
    isStatic () {
      return this.form.dataType === 0
    },
    isApi () {
      return this.form.dataType == 1
    },
    isSql () {
      return this.form.dataType == 2
    },
    isWs () {
      return this.form.dataType === 3
    },
    isMqtt () {
      return this.form.dataType == 6
    },
    isNode () {
      return this.form.dataType == 7
    }
  },
  watch: {
    'form.dataType' () {
      this.result = ''
      this.tabs = '0'
    }
  },
  created () {
    this.init()
  },
  methods: {
    init () {
      getDbList({
        current: 1,
        size: 100,
      }).then(res => {
        const data = res.data.data;
        this.DIC.sql = data.records.map(ele => {
          return {
            label: ele.name,
            value: ele.id
          }
        })
      });
    },
    getTypeLabel (type) {
      return dataType.find(ele => ele.value == type).label
    },
    getSql (row) {
      return crypto.encrypt(JSON.stringify({
        id: row.dbId,
        sql: row.dbSql
      }))
    },
    handleClose () {
      this.mqClient.end && this.mqClient.end()
      this.mqClient.close && this.mqClient.close()
      this.client.close && this.client.close()
    },
    handleResult (row) {
      this.form = row;
      this.box = true;
      this.beforeOpen(() => { }, 'edit')
    },
    handleTest () {
      this.$refs.db.handleOpen(this.form.dbId)
    },
    handleRes (tip = true) {
      this.boxLoading = true;
      const formatter = (data) => {
        this.boxLoading = false;
        const dataFormatter = getFunction(this.form.dataFormatter)
        if (tip) this.$message.success('数据刷新成功')
        if (typeof dataFormatter == 'function') {
          return dataFormatter(data)
        }

        return data
      }
      if (this.isStatic) {
        let result = funEval(this.form.data)
        this.result = formatter(result);
      } else if (this.isApi) {
        let dataQuery = getFunction(this.form.dataQuery)
        dataQuery = typeof (dataQuery) === 'function' && dataQuery(this.form.url) || {}
        let dataHeader = getFunction(this.form.dataHeader)
        dataHeader = typeof (dataHeader) === 'function' && dataHeader(this.form.url) || {}
        let params = dataQuery;
        let axiosParams = {}, method = this.form.dataMethod
        if (this.form.proxy) dataHeader.proxy = true;
        if (['post', 'put'].includes(method)) {
          axiosParams.data = dataQuery
          if (this.form.dataQueryType == 'form') {
            if (this.form.proxy) dataHeader.form = true
            let formData = []
            Object.keys(params).forEach(ele => {
              formData.push(`${ele}=${params[ele]}`)
            })
            axiosParams.data = formData.join('&')
          }
        } else if (['get', 'delete'].includes(method)) {
          axiosParams.params = dataQuery
        }
        this.$axios({
          ...{
            method: this.form.dataMethod,
            url: this.form.url,
            headers: dataHeader
          },
          ...axiosParams
        }).then(res => {
          this.result = formatter(res.data)
        })
      } else if (this.isSql) {
        dynamicSql(this.getSql(this.form)).then(res => {
          this.result = formatter(res.data.data)
        })
      } else if (this.isWs) {
        this.handleClose()
        let dataQuery = this.form.dataQuery ? getFunction(this.form.dataQuery)() : {}
        let url = this.form.wsUrl + addParam(dataQuery)
        this.client = new WebSocket(url)
        this.client.onmessage = (msgEvent = {}) => {
          const data = JSON.parse(msgEvent.data)
          this.result = formatter(data)
        }
      } else if (this.isMqtt) {
        this.handleClose()
        console.log(this.form)
        let url = this.form.mqttUrl
        let dataMqttConfig = JSON.parse(this.form.mqttConfig)
        dataMqttConfig.clientId = 'mqttjs_' + new Date().getTime()
        this.mqClient = mqtt.connect(url, dataMqttConfig)
        this.mqClient.on('connect', () => {
          this.mqClient.subscribe(dataMqttConfig.topic.name, { qos: dataMqttConfig.topic.qos || 0 }, (error, res) => {
            console.log('Subscribe to topics res', res, error)
          })
        })
        this.mqClient.on('message', (topic, message) => {
          let defaultTopic = dataMqttConfig.topic
          if (topic === defaultTopic.name) {
            let data = JSON.parse(message)
            this.result = formatter(data)
          }
        })

      }
    },
    handleImport (file, fileLis) {
      this.$Export.xlsx(file.raw)
        .then(data => {
          this.form.data = data.results;
          this.$message.success('导入成功')
        })
    },
    validData (id) {
      return [0, 1, 2].includes(id)
    },
    beforeClose (done) {
      this.handleClose()
      done()
    },
    beforeOpen (done, type) {
      this.result = '';
      if (type == 'edit') {
        getObj(this.form.id).then(res => {
          const data = res.data.data;
          this.form = data
          if (this.isSql) {
            let mode = JSON.parse(crypto.decrypt(this.form.data));
            this.form.dbId = mode.id;
            this.form.dbSql = mode.sql;
          }
          this.handleRes(false)
          done && done()
        })
      } else {
        done && done()
      }
      this.form.dataFormatter = this.form.dataFormatter || "(data)=>{\n    return {\n        data\n    }\n}"
      this.form.dataQuery = this.form.dataQuery || "()=>{\n    return {\n        \n    }\n}"
      this.form.dataHeader = this.form.dataHeader || "()=>{\n    return {\n        \n    }\n}"
    },
    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    rowDel (row, index) {
      if (this.validData(index) && this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delObj(row.id).then(() => {
          this.$message.success('删除成功');
          this.onLoad()
        })
      }).catch(() => {

      });

    },
    rowUpdate (row, index, done, loading) {
      if (this.validData(index) && this.$website.isDemo) {
        done();
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      if (this.isSql) row.data = this.getSql(row)
      updateObj(row).then(() => {
        done();
        this.$message.success('修改成功');
        this.onLoad()
      }).catch(err => {
        loading()
      })
    },
    rowSave (row, done, loading) {
      if (this.isSql) row.data = this.getSql(row)
      addObj(row).then(() => {
        this.$message.success('新增成功');
        this.onLoad()
        done();
      }).catch(err => {
        loading()
      })
    },
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.onLoad();
    },
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.onLoad();
    },
    onLoad () {
      this.loading = true
      getList({
        name: this.search.name,
        current: this.page.currentPage,
        size: this.page.pageSize,
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        let records = data.records
        records.forEach(ele => ele._menu = false);
        this.page.total = data.total;
        this.data = records;
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>