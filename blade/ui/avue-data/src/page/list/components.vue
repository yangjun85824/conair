<template>
  <el-container class="list">
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="content__add"
             @click="handleAdd"
             v-if="menu">
          <img :src="`${$router.options.base}img/components.png`"
               height="40px"
               alt="">
          <div>
            <p>创建组件</p>
            <span>丰富组件，按需引入项目</span>
          </div>
        </div>
        <div class="content__page">
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
      <el-tabs class="list-tabs"
               v-model="active"
               v-if="menu"
               @tab-click="handleTabClick">
        <el-tab-pane v-for="(item,index) in dicData"
                     :key="index"
                     :label="item.label"
                     :name="item.value"></el-tab-pane>
      </el-tabs>
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
              <img :src="item.img || `${$router.options.base}img/components-default.png`"
                   alt="" />
              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                  <el-tooltip content="预览">
                    <el-icon @click="handleView(item,index)">
                      <el-icon-view />
                    </el-icon>
                  </el-tooltip>
                </div>
                <div class="content__start">
                  <template v-if="menu">
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
                  </template>
                  <div v-else
                       class="content__btn"
                       @click="handleRow(item,index)">
                    <el-icon>
                      <el-icon-check></el-icon-check>
                    </el-icon>
                  </div>
                </div>
              </div>
            </div>
            <div class="content__main">
              <span class="content__name">{{item.name}}</span>
              <span class="content__status">
                {{getTypeLabel(item.type)}}
              </span>
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
    <el-dialog title="编辑组件"
               width="70%"
               destroy-on-close
               append-to-body
               :close-on-click-modal="false"
               v-model="dialog">
      <avue-form :option="option"
                 v-model="form"
                 @submit="handleSubmit">
        <template #content>
          <monaco-editor v-model="form.content"
                         :language="options.language"
                         :height="options.height"
                         :options="options"></monaco-editor>
          <br />
          <div v-if="form.type==1">
            <span>
              <a href="https://echarts.apache.org/examples/zh/index.html"
                 target="_blank">官方组件库</a>
            </span>&nbsp;&nbsp;
            <span>
              <a href="http://chart.majh.top"
                 target="_blank">三方组件库1</a>
            </span>&nbsp;&nbsp;
            <span>
              <a href="http://echarts.zhangmuchen.top"
                 target="_blank">三方组件库2</a>
            </span>&nbsp;&nbsp;
            <span>
              <a href="https://madeapie.com"
                 target="_blank">三方组件库3</a>
            </span>&nbsp;&nbsp;
            <span>
              <a href="http://ppchart.com"
                 target="_blank">三方组件库4</a>
            </span>
          </div>
          <el-button icon="el-icon-edit"
                     type="primary"
                     @click="$refs.code.handleOpen()">放大编辑器</el-button>
        </template>
        <template #view>
          <div class="view"
               v-if="reload">
            <div class="components__tip"
                 v-if="error">{{error}}</div>
            <avue-echart-vue :option="vueOption"
                             v-if="form.type==0"
                             @error-change="errorChange"
                             width="100%"
                             height="100%"></avue-echart-vue>
            <avue-echart-common :echart-formatter-str="vueOption.content"
                                v-else-if="form.type==1"
                                @error-change="errorChange"
                                width="100%"></avue-echart-common>
            <avue-echart-html :option="vueOption"
                              @error-change="errorChange"
                              v-else-if="form.type==2"
                              width="100%"
                              height="100%"></avue-echart-html>
          </div>
        </template>

      </avue-form>
    </el-dialog>

    <code-edit ref="code"
               v-model="form"></code-edit>
  </el-container>
</template>

<script>
import codeEdit from '@/page/components/code-edit.vue'
import AvueEchartVue from '@/echart/packages/vue/index.vue';
import AvueEchartCommon from '@/echart/packages/common/index.vue';
import AvueEchartHtml from '@/echart/packages/html/index.vue';
import MonacoEditor from '@/page/components/monaco-editor'
import { dataURLtoFile } from '@/utils/utils'
import { uploadImg } from '@/api/visual'
import { getList, getObj, addObj, delObj, updateObj } from '@/api/components'
const dicData = [
  {
    label: 'vue组件',
    value: 0,
    content: "<template>\n    <div>\n        \n    </div>\n</template>\n<script>\n    export default{\n        data(){\n            return {}\n        },\n        created(){\n\n        },\n        methods:{\n\n        }\n    }\n<\/script>\n\n<style>\n\n<\/style>"
  },
  {
    label: 'echart组件',
    value: 1,
    content: "function (data,params){\n    const myChart = this.myChart;\n    return option;\n}"
  },
  {
    label: 'html组件',
    value: 2,
    content: "<div style=''>html</div>"
  }
]
export default {
  components: {
    codeEdit,
    AvueEchartCommon,
    AvueEchartVue,
    AvueEchartHtml,
    MonacoEditor
  },
  props: {
    menu: {
      type: Boolean,
      default: true
    },
    activeName: {
      type: String,
      default: ""
    }
  },
  data () {
    return {
      search: {},
      error: '',
      loading: false,
      reload: false,
      active: 0,
      dicData,
      options: {
        height: 300,
        language: 'javascript',
      },
      content: '',
      index: -1,
      isEdit: false,
      form: {},
      vueOption: {},
      data: [],
      type: '',
      page: {
        size: 50,
        page: 1,
        total: 0
      },
      dialog: false,
      option: {
        labelWidth: 120,
        column: [
          {
            label: '组件名称',
            prop: 'name',
            row: true,
            rules: [{
              required: true,
              message: "请输入组件名称",
              trigger: "blur"
            }]
          },
          {
            label: '组件类型',
            prop: 'type',
            type: 'select',
            dicData: dicData,
            rules: [{
              required: true,
              message: "请选择组件类型",
              trigger: "blur"
            }]
          },
          {
            label: '组件数据',
            prop: 'content',
            span: 24
          },
          {
            label: '组件预览',
            prop: 'view',
            span: 24
          }]
      }
    }
  },
  created () {
    if (!this.menu) {
      this.active = this.activeName
    }
    this.getList()
  },
  watch: {
    'form.content' (val) {
      this.handleOption()
    },
    'form.type' (val) {
      if (this.isEdit) {
        this.isEdit = false;
        return
      }
      let obj = dicData.find(ele => ele.value == val) || {}
      this.form.content = obj.content
    }
  },
  methods: {
    errorChange (val) {
      if (val) console.log(val)
      this.error = val
    },
    handleTabClick (e) {
      this.active = e.props.name
      this.page.page = 1
      this.getList()
    },
    getTypeLabel (type) {
      return dicData.find(ele => ele.value == type).label
    },
    handleRow (row, index) {
      this.$emit('change', row)
    },
    validData (id) {
      return [0, 1, 2, 3, 4].includes(id)
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
      })
    },
    handleOption () {
      this.reload = false;
      this.vueOption = this.deepClone(this.form)
      this.$nextTick(() => {
        this.reload = true;
      })
    },
    getImg () {
      return new Promise(resolve => {
        html2canvas(document.querySelector('.view'), {
          useCORS: true,
          backgroundColor: null,
          logging: false,
          allowTaint: true
        }).then(canvas => {
          let result = canvas.toDataURL('image/jpeg', 0.1);
          if (result.length < 10) resolve('')
          var file = dataURLtoFile(result, new Date().getTime() + '.jpg');
          var formdata = new FormData();
          formdata.append('file', file)
          uploadImg(formdata).then(res => {
            const data = res.data.data;
            const url = data.link;
            resolve(url)
          })
        })
      })
    },
    handleAdd () {
      this.form = {
        name: '',
        type: '',
        content: ''
      };
      this.type = 'add'
      this.isEdit = true;
      this.handleOption()
      this.dialog = true;
    },
    handleEdit (row, index) {
      this.index = index;
      this.type = 'edit'
      this.isEdit = true;
      getObj(row.id).then(res => {
        const data = res.data.data;
        this.form = data
        this.handleOption()
        this.dialog = true;
      })
    },
    handleSubmit (form, done) {
      if (this.type == 'add') {
        this.rowSave(this.from, done)
      } else if (this.type == 'edit') {
        this.rowUpdate(this.from, this.index, done)
      }
    },
    rowUpdate (row, index, done, loading) {
      if (this.validData(index) && this.$website.isDemo) {
        done();
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      this.getImg().then(img => {
        this.form.img = img;
        return updateObj(this.form)
      }).then(() => {
        done();
        this.dialog = false;
        this.$message.success('修改成功');
        this.getList()
      }).catch(err => {
        loading()
      })
    },
    rowSave (row, done, loading) {
      this.getImg().then(img => {
        this.form.img = img;
        return addObj(this.form)
      }).then(() => {
        done();
        this.dialog = false;
        this.$message.success('新增成功');
        this.getList()
      }).catch(err => {
        loading()
      })
    },
    handleView (row, index) {
      getObj(row.id).then(res => {
        this.type = 'edit'
        this.isEdit = true
        const data = res.data.data;
        this.form = data;
        this.vueOption = this.deepClone({
          content: data.content
        })
        this.$refs.code.handleOpen()
      })
    },
    handleCurrentChange (val) {
      this.page.page = val;
      this.getList();
    },
    handleSizeChange (val) {
      this.page.size = val;
      this.getList();
    },
    getList () {
      this.loading = true
      this.data = []
      getList({
        name: this.search.name,
        current: this.page.page,
        size: this.page.size,
        type: this.active
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.data.forEach((ele, index) => {
          this.data[index]['_menu'] = false
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.components {
  &_content {
    display: flex;
    align-items: flex-end;
    flex-direction: column;
  }
  &_left,
  &_right {
    width: 100%;
  }
  &__tip {
    color: #fff;
    font-size: 14px;
    margin-bottom: 40px;
  }
}
</style>