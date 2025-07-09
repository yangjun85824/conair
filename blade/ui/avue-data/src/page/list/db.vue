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
      <template #menu-form>
        <el-button type="primary"
                   icon="el-icon-connection"
                   @click="handleTest()">测试连接</el-button>
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
            <p>创建数据源</p>
            <span>多库查询 自由组合</span>
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
                <img :src="'/img/'+getTypeLabel(item.driverClass)+'.png'"
                     alt="" />
                <span>{{ item.url }}</span>
              </div>

              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                  <el-tooltip content="测试连接">
                    <el-icon @click="handleTest(item)">
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
  </el-container>
</template>

<script>
import { getList, getDetail, add, update, remove, dbTest } from "@/api/db";
import db from '@/page/components/db.vue'
const dicData = [
  {
    label: 'mysql',
    value: 'com.mysql.cj.jdbc.Driver',
    url: 'jdbc:mysql://localhost:3306/bladex_report'
  }, {
    label: 'sqlServer',
    value: 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
    url: 'jdbc:sqlserver://127.0.0.1:1433;DatabaseName=bladex'
  }, {
    label: 'postgreSql',
    value: 'org.postgresql.Driver',
    url: 'jdbc:postgresql://127.0.0.1:5432/bladex'
  }, {
    label: 'oracle',
    value: 'oracle.jdbc.OracleDriver',
    url: 'jdbc:oracle:thin:@127.0.0.1:1521:orcl'
  }
]
export default {
  data () {
    return {
      search: {},
      loading: false,
      isEdit: false,
      form: {},
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        height: 'auto',
        calcHeight: 360,
        header: false,
        index: true,
        copyBtn: true,
        labelWidth: 110,
        menuWidth: 280,
        column: [
          {
            label: "名称",
            prop: "name",
            width: 120,
            rules: [{
              required: true,
              message: "请输入数据源名称",
              trigger: "blur"
            }]
          },
          {
            label: "类型",
            prop: "driverClass",
            type: 'select',
            dicData: dicData,
            width: 200,
            rules: [{
              required: true,
              message: "请选择类型",
              trigger: "blur"
            }]
          },
          {
            label: "用户名",
            prop: "username",
            width: 120,
            rules: [{
              required: true,
              message: "请输入用户名",
              trigger: "blur"
            }]
          },
          {
            label: "密码",
            prop: "password",
            type: 'password',
            hide: true,
            rules: [{
              required: true,
              message: "请输入密码",
              trigger: "blur"
            }]
          },
          {
            label: "连接地址",
            prop: "url",
            type: 'textarea',
            overHidden: true,
            span: 24,
            rules: [{
              required: true,
              message: "请输入连接地址",
              trigger: "blur"
            }]
          },
          {
            label: "备注",
            prop: "remark",
            span: 24,
            minRows: 3,
            hide: true,
            type: "textarea"
          },
        ]
      },
      data: []
    };
  },
  components: {
    db
  },
  watch: {
    'form.driverClass' (val) {
      if (this.isEdit) {
        this.isEdit = false;
        return
      }
      let obj = dicData.find(ele => ele.value == val) || {}
      this.form.url = obj.url
    }
  },
  methods: {
    validData (id) {
      return [0, 1, 2, 3].includes(id)
    },
    getTypeLabel (type) {
      return dicData.find(ele => ele.value == type).label
    },
    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    rowSave (row, done, loading) {
      add(row).then(() => {
        this.onLoad();
        this.$message({
          type: "success",
          message: "操作成功!"
        });
        done();
      }).catch(err => {
        loading()
      });
    },
    rowUpdate (row, index, done, loading) {
      if (this.validData(index) && this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        done();
        return false;
      }
      update(row).then(() => {
        done();
        this.onLoad();
        this.$message({
          type: "success",
          message: "操作成功!"
        });
      }).catch(err => {
        loading()
      })
    },
    rowDel (row, index) {
      if (this.validData(index) && this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          return remove(row.id);
        })
        .then(() => {
          this.onLoad();
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        });

    },
    beforeOpen (done, type) {
      if (["edit", "view"].includes(type)) {
        getDetail(this.form.id).then(res => {
          this.form = res.data.data;
        });
      }
      this.isEdit = true;
      done();
    },
    refreshChange () {
      this.onLoad();
    },
    handleTest (form) {
      let concent = false
      if (form) concent = true
      form = form || this.form
      const callback = () => {
        dbTest({
          driverClass: form.driverClass,
          url: form.url,
          username: form.username,
          password: form.password
        }).then(res => {
          const data = res.data;
          if (data.success) {
            this.$message.success('连接成功')
            if (concent) {
              this.$refs.db.handleOpen(form.id)
            }
          } else {
            this.$message.error(data.data || data.msg || '连接失败，请检查相关配置参数')
          }
        })
      }
      if (this.$refs.crud.validate) {
        this.$refs.crud.validate((valid, done, msg) => {
          if (valid) {
            done()
            callback()
          } else {
            return false;
          }
        })
      } else {
        callback()
      }
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
      });
    },
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.onLoad();
    },
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.onLoad();
    },
  }
};
</script>

<style>
</style>