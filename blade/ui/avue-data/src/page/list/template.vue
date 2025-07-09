<template>
  <el-container class="list template">
    <avue-crud :option="option"
               style="display:none"
               :data="data"
               page.sync="page"
               :before-open="beforeOpen"
               v-model="form"
               ref="crud"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @row-del="rowDel"
               @on-load="onLoad">
    </avue-crud>
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="$refs.crud.rowAdd()">
          <img :src="`${$router.options.base}img/template.png`"
               height="40px"
               alt="">
          <div>
            <p>创建消息模板</p>
            <span>消息模板 定时推送</span>
          </div>
        </div>
        <div class="content__page">
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
               @click="select(item,index)"
               :key="index">
            <div class="content__main">
              <div class="content__logo">
              </div>
              <span class="content__name">{{item.templateName}}</span>
              <span class="content__size">{{ item.templateCode }}</span>
            </div>
            <div class="content__menu">
              <div class="content__start">
                <div class="content__btn"
                     @click.stop="handleEdit(item,index)">
                  <el-icon>
                    <el-icon-edit></el-icon-edit>
                  </el-icon>
                </div>
                <div class="content__btn"
                     @click.stop="rowDel(item,index)">
                  <el-icon>
                    <el-icon-delete></el-icon-delete>
                  </el-icon>
                </div>
                <el-tooltip content="调试">
                  <div class="content__btn"
                       @click.stop="rowDebug(item,index)">
                    <el-icon>
                      <el-icon-connection></el-icon-connection>
                    </el-icon>
                  </div>
                </el-tooltip>
              </div>

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
    <el-dialog append-to-body
               class="avue-dialog"
               v-model="visibleDebug"
               title="消息调试"
               width="600">
      <el-alert title="消息调试功能，用于调试消息模版，请输入 Json格式 参数调试，与模版参数对应。"
                type="info"
                :closable="false" />
      <avue-form ref="form"
                 v-model="debugForm"
                 :option="debugOption"
                 @submit="handleDebug" />
      <span class="avue-dialog__footer avue-dialog__footer--center">
        <el-button type="primary"
                   @click="$refs.form.submit()"
                   icon="el-icon-connection">发送</el-button>
        <el-button @click="visibleDebug=false"
                   icon="el-icon-close">关闭</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
import { getList, getObj, addObj, delObj, updateObj, debug } from '@/api/template'
export default {
  props: {
    id: String,
    menu: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      debugForm: {},
      form: {},
      query: {},
      loading: true,
      visibleDebug: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        header: false,
        index: true,
        menuWidth: 280,
        labelWidth: 120,
        dialogWidth: 600,
        column: [
          {
            label: "模版名称",
            prop: "templateName",
            width: 280,
            span: 24,
            rules: [{
              required: true,
              message: "请输入模版名称",
              trigger: "blur"
            }]
          },
          {
            label: "模版编号",
            labelTip: "用于快速自动调用推送服务的唯一编号",
            prop: "templateCode",
            width: 100,
            span: 24,
            rules: [{
              required: true,
              message: "请输入模版编号",
              trigger: "blur"
            }]
          },
          {
            label: "模版内容",
            labelTip: "模版内容支持${}与#{}占位符接受参数,短信则输入平台发放的模版ID",
            prop: "templateParam",
            type: "textarea",
            minRows: 6,
            span: 24,
            rules: [{
              required: true,
              message: "请输入模版内容,短信则输入模版ID",
              trigger: "blur"
            }]
          },
          {
            label: "模版备注",
            prop: "templateDesc",
            type: "textarea",
            width: 150,
            minRows: 2,
            span: 24
          },
        ]
      },
      debugOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [
          {
            label: "模版名称",
            prop: "templateName",
            disabled: true,
            span: 24,
          },
          {
            label: "模版编号",
            prop: "templateCode",
            disabled: true,
            span: 24,
          },
          {
            label: "模版内容",
            prop: "templateParam",
            type: "textarea",
            disabled: true,
            minRows: 6,
            span: 24,
          },
          {
            label: "调试内容",
            prop: "debugParam",
            type: "textarea",
            minRows: 6,
            span: 24,
          },
        ]
      },
      data: []
    };
  },
  methods: {
    select (item) {
      this.$emit('submit', item)
    },
    rowDebug (row) {
      this.debugForm = row;
      this.visibleDebug = true;
    },
    handleDebug (form, done) {
      debug(form.templateCode, form.debugParam).then(() => {
        this.$message({
          type: "success",
          message: "调试完毕,请查看推送消息!"
        });
        this.visibleDebug = false;
      }, error => {
        window.console.log(error);
      });
      done();
    },
    validData (id) {
      return [0, 1, 2].includes(id)
    },
    beforeOpen (done, type) {
      if (type == 'edit') {
        getObj(this.form.id).then(res => {
          const data = res.data.data;
          this.form = data
          this.form.channelId = this.id
          done()
        })
      } else {
        this.form.channelId = this.id
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
      row.channelId = this.id
      updateObj(row).then(() => {
        done();
        this.$message.success('修改成功');
        this.onLoad()
      }).catch(err => {
        loading()
      })
    },
    rowSave (row, done, loading) {
      row.channelId = this.id
      addObj(row).then(() => {
        this.$message.success('新增成功');
        this.onLoad()
        done();
      }).catch(err => {
        loading()
      })
    },
    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
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
        channelId: this.id,
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


<style lang="scss" scoped>
.template {
  .content {
    &__info {
      height: auto;
    }
    &__item {
      margin-right: 0;
      margin-bottom: 10px;
      display: flex;
      align-items: center;
      justify-content: space-around;
      width: 100%;
    }
    &__logo {
      margin-right: 10px;
      img {
        object-fit: cover;
        width: 40px !important;
      }
    }
    &__main {
      flex: 1;
      justify-content: flex-start;
    }
    &__menu {
      margin-right: 10px;
      position: relative;
      width: 120px;
      height: inherit;
      right: 0;
      display: inline-block;
    }
    &__name {
      width: 300px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
    &__size {
      min-width: 80px;
      margin-right: 100px;
    }
  }
}
</style>