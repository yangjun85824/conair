<template>
  <div style="padding:0 5px;">
    <div style="margin:10px 0 10px 0;">
      <el-button type="primary"
                 @click="$refs.crud.rowAdd()">新增变量</el-button>
      <el-button type="success"
                 @click="handleGlob">系统变量</el-button>
    </div>
    <avue-crud ref="crud"
               style="display:none"
               :option="option"
               @row-save="rowSave"
               @row-update="rowUpdate"
               @row-del="rowDel"
               :data="contain.config.glob">
    </avue-crud>
    <ul class="menu_ul"
        v-if="contain.config.glob.length!==0">
      <li class="menu__item"
          v-for="(item,index) in contain.config.glob"
          :key="index">
        <span class="menu__icon">
          <svg-icon icon-class="variable" />
        </span>
        <span class="menu__label">
          <span class="menu__name">{{item.name}} ${{ item.key }}</span>
        </span>
        <span class="menu__menu">
          <el-icon @click.stop="$refs.crud.rowEdit(item,index)">
            <el-icon-edit></el-icon-edit>
          </el-icon>
          <el-icon @click.stop="$refs.crud.rowDel(item,index)">
            <el-icon-delete></el-icon-delete>
          </el-icon>
        </span>
      </li>
    </ul>
    <el-empty v-else
              description="暂无变量">
      <template #image>
        <svg-icon icon-class="empty" />
      </template>
    </el-empty>
    <glob ref="glob"></glob>
  </div>
</template>

<script>
import glob from '@/page/setup/glob-params.vue'
export default {
  inject: ["contain"],
  components: {
    glob
  },
  data () {
    return {
      option: {
        dialogWidth: '400',
        dialogMenuPosition: 'center',
        refreshBtn: false,
        columnBtn: false,
        height: 'auto',
        calcHeight: 50,
        align: 'center',
        headerAlign: 'center',
        menuType: 'icon',
        column: [
          {
            label: '名称',
            prop: 'name',
            span: 24,
            formatter: (row) => {
              return `${row.name}-${row.key}`
            },
            rules: [{
              required: true,
              message: "请输入名称",
              trigger: "blur"
            }]
          },
          {
            label: '变量名',
            prop: 'key',
            hide: true,
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
            prop: 'value',
            type: 'textarea',
            span: 24,
            hide: true,
            overHidden: true,
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
  computed: {
  },
  methods: {
    handleGlob () {
      this.$refs.glob.box = true
    },
    initData () {
      let list = this.contain.config.glob;
      list.forEach(ele => {
        window.$glob[ele.key] = ele.value
      })
    },
    rowDel (row, index) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contain.config.glob.splice(index, 1)
        this.initData()
      })
    },
    rowUpdate (row, index, done) {
      this.contain.config.glob[index] = row
      this.initData()
      done();
    },
    rowSave (row, done) {
      this.contain.config.glob.push(row)
      this.initData()
      done()
    },
  }
}
</script>

<style>
</style>