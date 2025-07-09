<template>
  <el-container class="list">
    <avue-crud ref="crud"
               style="display: none;"
               :option="option"
               v-model="form"
               v-model:page="page"
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
               @on-load="onLoad"
               v-loading="loading"
               v-bind="$loadingParams"
               :data="data">
    </avue-crud>
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="$refs.crud.rowAdd()">
          <img :src="`${$router.options.base}img/glob.png`"
               height="40px"
               alt="">
          <div>
            <p>创建变量</p>
            <span>全局变量 任意调用</span>
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
                <p>${{ item.globalKey }}</p>
                <span>{{ item.globalValue }}</span>
              </div>

              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                  <el-tooltip content="复制变量">
                    <el-icon @click="handleCopy(item)">
                      <el-icon-copy-document></el-icon-copy-document>
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
              <span class="content__name">{{item.globalName}}</span>
              <span class="content__status"> {{ item.username }}</span>
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
  </el-container>
</template>

<script>
import { getList, addObj, delObj, updateObj } from '@/api/glob'
export default {
  props: {
    menu: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      search: {},
      loading: false,
      form: {},
      data: [],
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        dialogWidth: '400',
        dialogMenuPosition: 'center',
        height: 'auto',
        calcHeight: 140,
        header: false,
        index: true,
        align: 'center',
        headerAlign: 'center',
        menu: this.menu,
        column: [
          {
            label: '名称',
            prop: 'globalName',
            span: 24,
            rules: [{
              required: true,
              message: "请输入名称",
              trigger: "blur"
            }]
          },
          {
            label: '变量名',
            prop: 'globalKey',
            span: 24,
            tip: "提示:url中使用${xxx}，js语法中使用window.$glob['xxx']",
            rules: [{
              required: true,
              message: "请输入变量名",
              trigger: "blur"
            }]
          },
          {
            label: '变量值',
            prop: 'globalValue',
            type: 'textarea',
            overHidden: true,
            span: 24,
            rules: [{
              required: true,
              message: "请输入变量值",
              trigger: "blur"
            }]
          }
        ]
      }
    }
  },
  methods: {
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.onLoad();
    },
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.onLoad();
    },
    handleCopy (row) {
      const data = '${' + row.globalKey + '}'
      this.$Clipboard({
        text: data
      }).then(() => {
        this.$message.success(data + '变量复制成功')
      })
    },
    validData (id) {
      return [0, 1, 2].includes(id)
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
      updateObj(row).then(() => {
        done();
        this.$message.success('修改成功');
        this.onLoad()
      }).catch(err => {
        loading()
      })
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
    onLoad () {
      this.loading = true
      getList({
        globalName: this.search.name,
        current: 1,
        size: 100,
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