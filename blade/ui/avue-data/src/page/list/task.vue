<template>
  <el-container class="list">
    <avue-crud ref="crud"
               style="display: none;"
               :option="option"
               :data="data"
               v-model:page="page"
               :before-open="beforeOpen"
               v-model="form"
               v-loading="loading"
               v-bind="$loadingParams"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @row-del="rowDel"
               @refresh-change="refreshChange"
               @on-load="onLoad">
      <template #pushParam="{ row }">
        <el-text size="small"
                 type="info">{{ row.pushParam }}</el-text>
      </template>
    </avue-crud>
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="$refs.crud.rowAdd()">
          <img :src="`${$router.options.base}img/task.png`"
               height="40px"
               alt="">
          <div>
            <p>创建消息渠道</p>
            <span>多种渠道 随心推送</span>
          </div>
        </div>
        <div class="content__page">
          <div class="list-search">
            <el-input v-model="search.name"
                      @keyup.enter="onLoad"
                      placeholder="请输入名称">
              <template #suffix>
                <el-icon click="onLoad">
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
                         :page-size="page.pageSize"
                         v-model:current-page="page.currentPage"
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
                <img :src="'/img/task/'+ item.pushType+'.png'"
                     alt="" />
              </div>

              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                </div>
                <div class="content__start">
                  <el-tooltip effect="dark"
                              content="消息模板"
                              placement="top">
                    <div class="content__btn"
                         @click="rowTemplate(item,index)">
                      <el-icon>
                        <el-icon-message></el-icon-message>
                      </el-icon>
                    </div>
                  </el-tooltip>

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
              <span class="content__name">{{item.pushName}}</span>
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
    <el-dialog :title="form.pushName+'[消息模板]'"
               append-to-body
               :close-on-click-modal="false"
               v-model="box"
               width="80%"
               class="avue-dialog">
      <temp :id="form.id"
            :menu="menu"
            @submit="select"
            v-if="box"></temp>
    </el-dialog>
  </el-container>
</template>

<script>
import temp from './template.vue'
import { getList, getObj, addObj, delObj, updateObj } from '@/api/task'

export default {
  props: {
    menu: {
      type: Boolean,
      default: false
    }
  },
  components: {
    temp
  },
  data () {
    return {
      search: {},
      box: false,
      form: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        tip: false,
        border: true,
        index: true,
        labelWidth: 150,
        dialogWidth: 650,
        column: [
          {
            label: "推送名称",
            prop: "pushName",
            search: true,
            span: 24,
            rules: [{
              required: true,
              message: "请输入推送名称",
              trigger: "blur"
            }]
          },
          {
            label: "推送类型",
            prop: "pushType",
            type: "radio",
            dataType: "number",
            width: 120,
            value: 1,
            dicData: [{
              label: '企业微信',
              value: 1
            }, {
              label: '钉钉',
              value: 2
            }, {
              label: '邮件',
              value: 3
            }, {
              label: '阿里云短信',
              value: 4
            }, {
              label: '腾讯云短信',
              value: 5
            }],
            slot: true,
            search: true,
            span: 24,
            rules: [{
              required: true,
              message: "请选择推送类型",
              trigger: "blur"
            }]
          },
          {
            label: "webhook",
            prop: "webhook",
            type: "textarea",
            minRows: 3,
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "机器人签名",
            prop: "robotSign",
            type: "textarea",
            minRows: 2,
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "发件邮箱地址",
            prop: "senderEmail",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "发件邮箱服务器",
            prop: "emailHost",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "发件邮箱服务器端口",
            prop: "emailPort",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "用户名",
            prop: "username",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "邮箱密码",
            prop: "password",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "收件邮箱地址",
            prop: "recipientEmail",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "accessKey",
            prop: "accessKey",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "secretKey",
            prop: "secretKey",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "regionId",
            prop: "regionId",
            value: "cn-hangzhou",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "appId",
            prop: "appId",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "appKey",
            prop: "appKey",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "短信签名",
            prop: "smsSign",
            hide: true,
            display: false,
            span: 24,
          },
          {
            label: "推送参数",
            prop: "pushParam",
            display: false,
          },
        ]
      },
      data: []
    };
  },
  watch: {
    'form.pushType' () {
      const pushType = this.form.pushType;
      if (!this.validatenull(pushType)) {
        this.displayColumn('webhook', this.isDing() || this.isWechat());
        this.displayColumn('robotSign', this.isDing());

        this.displayColumn('senderEmail', this.isEmail());
        this.displayColumn('emailHost', this.isEmail());
        this.displayColumn('emailPort', this.isEmail());
        this.displayColumn('username', this.isEmail());
        this.displayColumn('password', this.isEmail());
        this.displayColumn('recipientEmail', this.isEmail());

        this.displayColumn('accessKey', this.isAliSms());
        this.displayColumn('secretKey', this.isAliSms());
        this.displayColumn('regionId', this.isAliSms());

        this.displayColumn('appId', this.isTencentSms());
        this.displayColumn('appKey', this.isTencentSms());

        this.displayColumn('smsSign', this.isAliSms() || this.isTencentSms());
      }
    }
  },
  methods: {
    select (item) {
      this.$emit('submit', item)
    },
    isWechat () {
      return this.form.pushType === 1;
    },
    isDing () {
      return this.form.pushType === 2;
    },
    isEmail () {
      return this.form.pushType === 3;
    },
    isAliSms () {
      return this.form.pushType === 4;
    },
    isTencentSms () {
      return this.form.pushType === 5;
    },
    displayColumn (prop, value) {
      const column = this.findColumn(prop)
      column.display = value;
    },
    findColumn (prop) {
      return this.$refs.crud.option.column.find(item => item.prop === prop);
    },
    validData (id) {
      return [0, 1, 2].includes(id)
    },
    beforeOpen (done, type) {
      if (type == 'edit') {
        getObj(this.form.id).then(res => {
          const data = res.data.data;
          this.form = data
          done()
        })
      } else {
        done()
      }
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
      updateObj(row).then(() => {
        done();
        this.$message.success('修改成功');
        this.onLoad()
      }).catch(err => {
        loading()
      })
    },
    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    rowTemplate (row) {
      this.form = row;
      this.box = true
    },
    rowSave (row, done, loading) {
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
};
</script>

<style>
</style>