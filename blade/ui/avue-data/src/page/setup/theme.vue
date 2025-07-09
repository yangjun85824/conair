<template>
  <div class="theme">
    <el-button type="primary"
               style="margin:10px 0 10px 0;"
               @click="handleAdd()">新增主题色</el-button>
    <el-button type="danger"
               style="margin:10px 0 10px 10px;"
               @click="contain.config.themeId=''">清空主题</el-button>
    <div class="theme__list">
      <div class="theme__item"
           @click="handleSetTheme(item)"
           :class="{'is-active':item.id==contain.config.themeId}"
           v-for="(item,index) in list"
           :key="index">
        <span class="theme__name">{{ item.name }}</span>
        <template v-for="(color,num) in item.color">

          <span class="theme__color"
                v-if="num<6"
                :key="num"
                :style="{backgroundColor:color}"></span>
        </template>
        <span class="theme__menu">
          <el-icon @click.stop="handleEdit(item,index)">
            <el-icon-edit></el-icon-edit>
          </el-icon>
          <el-icon @click.stop="handleDel(item,index)">
            <el-icon-delete></el-icon-delete>
          </el-icon>
        </span>
        <span class="theme__bottom"
              :style="getBottomStyle(item.color)"></span>
      </div>
    </div>
    <el-dialog append-to-body
               class="theme__dialog"
               :close-on-click-modal="false"
               :title="form.name || '未命名'"
               v-model="box"
               width="50%">
      <div style="padding-bottom:40px">
        <el-input v-model="form.name"
                  placeholder="请输入主题名称"></el-input>
        <div class="dialog__list">
          <div v-for="(item,index) in form.color"
               :key="index"
               class="dialog__item">
            <span class="dialog__color"
                  :style="{background:item}">
              <span> {{ item }}</span>
              <el-color-picker v-model="form.color[index]"
                               show-alpha
                               size="small"></el-color-picker>
              <el-icon @click.stop="colorDel(index)">
                <el-icon-delete></el-icon-delete>
              </el-icon>

            </span>

          </div>
          <div class="dialog__add">
            <el-button type="primary"
                       @click.stop="colorAdd()"
                       icon="el-icon-plus"></el-button>

          </div>
        </div>
        <avue-crud :option="option"
                   @row-save="rowSave"
                   @row-update="rowUpdate"
                   @row-del="rowDel"
                   :data="form.data"></avue-crud>
      </div>
      <span class="avue-dialog__footer avue-dialog__footer--right">
        <el-button @click="box=false">取 消</el-button>
        <el-button type="primary"
                   @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { uuid } from '@/utils/utils'
export default {
  inject: ["contain"],
  computed: {
    list () {
      let list = this.contain.config.theme
      return Object.keys(list).map(key => list[key])
    }
  },
  data () {
    return {
      data: [],
      box: false,
      form: {},
      option: {
        dialogWidth: '400',
        dialogMenuPosition: 'center',
        refreshBtn: false,
        columnBtn: false,
        align: 'center',
        headerAlign: 'center',
        menuType: 'icon',
        size: 'mini',
        menuWidth: 130,
        addBtnText: '新增主题变量',
        column: [
          {
            label: '名称',
            prop: 'name',
            span: 24,
            rules: [{
              required: true,
              message: "请输入名称",
              trigger: "blur"
            }]
          },
          {
            label: '变量名',
            prop: 'key',
            span: 24,
            tip: "提示:使用window.$glob.theme.xxx获取变量值",
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
  methods: {
    rowSave (row, done) {
      this.form.data.push(row)
      done()
    },
    rowUpdate (row, index, done) {
      this.form.data[index] = row;
      done()
    },
    rowDel (row, index, done) {
      this.form.data.splice(index, 1)
      done()
    },
    colorAdd () {
      this.form.color.push('#2681ff')
    },
    colorDel (index) {
      this.form.color.splice(index, 1)
    },
    handleSetTheme (item) {
      this.contain.config.themeId = item.id;
      window.$glob.themeId = item.id
    },
    handleSubmit () {
      if (!this.form.name) {
        this.$message.error('请输入主题名称')
        return
      }
      if (this.form.id) {
        this.handleUpdate()
      } else {
        this.handleSave()
      }
    },
    handleAdd () {
      this.form = {
        color: [],
        data: []
      }
      this.box = true;
    },
    handleSave () {
      if (!this.contain.config.theme) {
        this.contain.config.theme = {}
      }
      let id = uuid();
      this.form.id = id;
      this.$set(this.contain.config.theme, id, this.form)
      this.box = false;
    },
    handleUpdate () {
      this.$set(this.contain.config.theme, this.form.id, this.form)
      this.box = false;
    },
    handleEdit (item) {
      item.data = item.data || []
      this.form = this.deepClone(item)
      this.box = true;
    },
    handleDel (item) {
      this.$confirm(`是否删除【${item.name}】主题?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delete this.contain.config.theme[item.id]
      })
    },
    getBottomStyle (list) {
      let colors = list.join(',')
      return {
        'backgroundImage': `linear-gradient(to right,${colors})`
      }
    }
  }
}
</script>

<style lang="scss">
.theme {
  padding: 0 10px;
  &__item {
    position: relative;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: var(--bg-color);
    margin-bottom: 15px;
    padding: 0;
    overflow: hidden;
    padding: 12px;
    border: 1px solid var(--border-color);
    border-radius: 5px;
    &.is-active {
      background-color: rgba(38, 129, 255, 0.5);
    }
  }
  &__name {
    text-align: left;
    width: 60px;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
    vertical-align: bottom;
    max-width: 100%;
    overflow: hidden;
    color: #fff;
    font-size: 12px;
  }
  &__color {
    display: inline-block;
    width: 20px;
    height: 20px;
    border-radius: 6px;
  }
  &__menu {
    cursor: pointer;
    i {
      margin-right: 3px;
      font-size: 13px;
    }
  }
  &__bottom {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 3px;
  }
  &__dialog {
    .dialog {
      &__add {
        cursor: pointer;
        margin: 5px;
        display: block;
        height: 30px;
        line-height: 30px;
        text-align: center;
        .dialog__color {
          padding: 0 10px;
          display: block;
          font-weight: bold;
          color: var(--primary-color);
          border: 1px solid var(--primary-color);
        }
      }
      &__color {
        box-sizing: border-box;
        padding: 0 10px 0 10px;
        display: inline-block;
        color: #fff;
        font-weight: bold;
        display: flex;
        align-items: center;
        border-radius: 5px;
        .el-color-picker__trigger {
          border: none;
        }
        .el-color-picker__color {
          border-color: #fff;
        }
        .el-color-picker {
          margin-left: 10px;
        }
        & > span {
          display: block;
          width: 120px;
          font-size: 12px;
        }
      }
      &__list {
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
      }
      &__item {
        margin: 5px;
      }
    }
  }
}
</style>