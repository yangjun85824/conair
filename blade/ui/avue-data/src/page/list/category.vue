<template>
  <el-container class="list category">
    <avue-crud ref="crud"
               style="display:none"
               :option="option"
               v-model="form"
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
               v-loading="loading"
               v-bind="$loadingParams"
               :before-open="beforeOpen"
               :data="data"></avue-crud>
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="$refs.crud.rowAdd()">
          <img :src="`${$router.options.base}img/category.png`"
               height="40px"
               alt="">
          <div>
            <p>创建分类</p>
            <span>多种分类 更好管理</span>
          </div>
        </div>
        <div class="list-search">
          <el-input v-model="search.name"
                    @keyup.enter="getList"
                    placeholder="请输入名称">
            <template #suffix>

              <el-icon @click="getList"
                       class="el-input__icon">
                <el-icon-search></el-icon-search>
              </el-icon>
            </template>
          </el-input>
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
            <div class="content__main">
              <div class="content__logo">
                <img :src="`${$router.options.base+'img/categorys.png'}`"
                     alt="">
              </div>
              <span class="content__name">{{item.categoryKey}}</span>
            </div>
            <div class="content__menu">
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
import { getList, getObj, addObj, delObj, updateObj } from '@/api/category'
export default {
  data () {
    return {
      search: {},
      loading: false,
      form: {},
      data: [],
      option: {
        dialogWidth: '400',
        dialogMenuPosition: 'center',
        height: 'auto',
        calcHeight: 330,
        header: false,
        index: true,
        align: 'center',
        headerAlign: 'center',
        column: [
          {
            label: '模块名',
            prop: 'categoryKey',
            span: 24,
            rules: [{
              required: true,
              message: "请输入模块名",
              trigger: "blur"
            }]
          },
          {
            label: '模块值',
            prop: 'categoryValue',
            span: 24,
            type: 'number',
            rules: [{
              required: true,
              message: "请输入模块值",
              trigger: "blur"
            }]
          }
        ]
      }
    }
  },
  created () {
    this.getList()
  },
  methods: {
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
          this.getList()
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
        this.getList()
      }).catch(err => {
        loading()
      })
    },

    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    rowSave (row, done, loading) {
      addObj(row).then(() => {
        this.$message.success('新增成功');
        this.getList()
        done();
      }).catch(err => {
        loading()
      })
    },
    getList () {
      this.loading = true
      getList({
        categoryValue: this.search.name,
        current: 1,
        size: 100,
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        let records = data
        records.forEach(ele => ele._menu = false);
        this.data = records;
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.category {
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
      width: 100px;
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
  }
}
</style>