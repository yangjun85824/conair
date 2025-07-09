<template>
  <el-container class="list">
    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="avue-flex">
          <div class="content__add"
               @click="handleAdd">
            <img :src="`${$router.options.base}img/project.png`"
                 height="40px"
                 alt="">
            <div>
              <p>创建大屏</p>
              <span>多平台展示部署 打包部署</span>
            </div>
          </div>
          <div class="content__add"
               @click="handleImport">
            <img :src="`${$router.options.base}img/export.png`"
                 height="40px"
                 alt="">
            <div>
              <p>导入大屏</p>
              <span>解析文件 生成新项目</span>
            </div>
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
               v-model="activeName"
               @tab-click="handleTabClick">
        <el-tab-pane v-for="(item,index) in typeList"
                     :key="index"
                     :label="item.categoryKey"
                     :name="item.categoryValue"></el-tab-pane>
      </el-tabs>
    </el-header>
    <el-main class="content"
             v-loading="loading"
             v-bind="$loadingParams">
      <div class="content__box">
        <template v-if="list.length>0">
          <div class="content__item"
               v-for="(item,index) in list"
               :key="index"
               @mouseenter="item._menu=true"
               @mouseleave="item._menu=false">
            <div class="content__info">
              <img :src="item.backgroundUrl ||`${$router.options.base}img/bg-default.png`"
                   alt="" />
              <div class="content__menu"
                   v-if="item._menu">
                <div class="content__right">
                  <el-tooltip content="部署">
                    <el-icon @click="handleExport(item,index)">
                      <el-icon-monitor />
                    </el-icon>
                  </el-tooltip>
                  <el-tooltip content="修改">
                    <el-icon @click="handleUpdate(item,index)">
                      <el-icon-edit />
                    </el-icon>
                  </el-tooltip>
                  <el-tooltip content="删除">
                    <el-icon @click="handleDel(item,index)">
                      <el-icon-delete />
                    </el-icon>
                  </el-tooltip>
                  <el-tooltip content="复制">
                    <el-icon @click="handleCopy(item,index)">
                      <el-icon-copy-document />
                    </el-icon>
                  </el-tooltip>
                </div>
                <div class="content__start">
                  <div class="content__btn"
                       @click="handleViews(item,index)">
                    <el-icon>
                      <el-icon-view />
                    </el-icon>
                  </div>
                  <div class="content__btn"
                       @click="handleEdit(item)">
                    <el-icon>
                      <el-icon-edit />
                    </el-icon>
                  </div>
                </div>
              </div>
            </div>
            <div class="content__main">
              <span class="content__name">{{item.title}}</span>
              <div class="content__menulist">
                <span class="content__status"
                      :class="{'is-active':item.status}">
                  <span class="icon"></span>
                  {{item.status==1?'已发布':'未发布'}}
                </span>
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
    <el-dialog :title="isEdit?'编辑大屏':'创建大屏'"
               :width="isEdit?'50%':'70%'"
               class="avue-dialog"
               :close-on-click-modal="false"
               v-model="box">
      <avue-form ref="form"
                 v-if="box"
                 :option="option"
                 v-model="form"
                 @submit="handleSave">
        <template #backgroundUrl>
          <img :src="form.backgroundUrl"
               v-if="form.backgroundUrl"
               style="width:200px;"
               alt="">
          <el-upload :on-success="onSuccess"
                     :on-progress="onProgress"
                     :show-file-list="false"
                     :action="url+'/visual/put-file'"
                     multiple
                     list-type="picture">
            <el-button :loading="loading1"
                       icon="el-icon-upload"
                       type="primary">点击上传</el-button>
          </el-upload>
        </template>
        <template #screen>
          <div class="create-screen">
            <div class="item"
                 :class="{'is-active':index===screenActive}"
                 v-for="(item,index) in screenList"
                 @click="handleSetScreen(item)"
                 :key="index">
              <svg-icon class="item-icon"
                        :icon-class="item.icon" />
              <div class="item-title">{{ item.label }}</div>
              <div class="item-size">{{ item.width }} x {{ item.height }}</div>
            </div>
          </div>
        </template>
      </avue-form>
      <span class="avue-dialog__footer avue-dialog__footer--center">

        <el-button type="primary"
                   @click="$refs.form.submit()"
                   icon="el-icon-check">{{ isEdit?'更新大屏':'创建大屏'}}</el-button>
        <el-button icon="el-icon-close"
                   @click="box=false">关 闭</el-button>
      </span>
    </el-dialog>
    <export-tip ref="exportTip"></export-tip>
    <import-tip ref="importTip"
                :category="activeName"></import-tip>
  </el-container>
</template>
<script>
import { getList, addObj, updateObj, delObj, getCategory, copyObj } from '@/api/visual';
import { url } from '@/config';
import exportTip from '@/page/components/export-tip.vue';
import importTip from '@/page/components/import-tip.vue';
export default {
  name: "list",
  components: {
    exportTip,
    importTip
  },
  data () {
    return {
      search: {},
      screenList: [{
        label: 'Desktop(默认)',
        width: 1920,
        height: 1080,
        icon: 'desktop'
      }, {
        label: 'MacBook pro',
        width: 1440,
        height: 900,
        icon: 'mac'
      }, {
        label: 'iPad pro',
        width: 1366,
        height: 1024,
        icon: 'pad'
      }, {
        label: '手机 Max',
        width: 430,
        height: 932,
        icon: 'phone'
      }, {
        label: '自定义',
        width: '-',
        height: '-',
        icon: 'slot'
      }],
      loading1: false,
      loading: false,
      index: -1,
      typeList: [],
      box: false,
      url,
      addColumn: [{
        label: '分组',
        prop: 'category',
        span: 24,
        type: 'select',
        dicUrl: url + '/category/list',
        props: {
          label: 'categoryKey',
          value: 'categoryValue',
        },
        rules: [{
          required: true,
          message: "请选择分组",
          trigger: "blur"
        }]
      }, {
        label: '大屏名称',
        span: 24,
        prop: 'title',
        rules: [{
          required: true,
          message: "请输入大屏名称",
          trigger: "blur"
        }]
      }, {
        label: '密码',
        span: 24,
        type: 'password',
        labelWidth: 100,
        prop: 'password',
      }, {
        label: '大屏尺寸',
        span: 24,
        prop: 'screen'
      }, {
        label: '',
        span: 14,
        prop: 'width',
        display: false,
        placeholder: '请输入宽度',
        rules: [{
          required: true,
          message: "请输入宽度",
          trigger: "blur"
        }]
      }, {
        label: '',
        span: 10,
        labelWidth: 1,
        display: false,
        prop: 'height',
        placeholder: '请输入高度',
        rules: [{
          required: true,
          message: "请输入高度",
          trigger: "blur"
        }]
      }, {
        label: '缩略图',
        span: 24,
        prop: 'backgroundUrl'
      }],
      editColumn: [{
        label: '分组',
        prop: 'category',
        span: 24,
        type: 'select',
        dicUrl: url + '/category/list',
        props: {
          label: 'categoryKey',
          value: 'categoryValue',
        },
        rules: [{
          required: true,
          message: "请选择分组",
          trigger: "blur"
        }]
      }, {
        label: '大屏名称',
        span: 24,
        prop: 'title',
        rules: [{
          required: true,
          message: "请输入大屏名称",
          trigger: "blur"
        }]
      }, {
        label: '密码',
        span: 24,
        type: 'password',
        labelWidth: 100,
        prop: 'password',
      }, {
        label: '发布状态',
        prop: 'status',
        span: 24,
        type: 'select',
        dicData: [{
          label: '未发布',
          value: 0
        }, {
          label: '已发布',
          value: 1
        }]
      }, {
        label: '缩略图',
        span: 24,
        prop: 'backgroundUrl'
      }],
      option: {
        emptyBtn: false,
        submitBtn: false,
        labelWidth: 100,
        column: []
      },
      page: {
        page: 1,
        size: 50,
        total: 0,
      },
      form: {},
      activeName: '',
      list: [],
      importBox: true
    }
  },
  created () {
    this.getCategory()
  },
  computed: {
    isEdit () {
      return !!this.form.id
    },
    screenActive () {
      let len = this.screenList.length
      let index = this.screenList.findIndex(ele => ele.width == this.form.width && ele.height == this.form.height);
      return index == -1 ? len - 1 : index;
    },
  },
  methods: {
    handleImport () {
      this.$refs.importTip.box = true;
    },
    handleTabClick (e) {
      this.activeName = e.props.name
      this.page.page = 1
      this.getList()
    },
    onProgress () {
      this.loading1 = true;
    },
    onSuccess (res) {
      const url = res.data.link;
      this.loading1 = false;
      this.form.backgroundUrl = url
    },
    validData (id) {
      const list = [];
      for (var i = 0; i < 20; i++) {
        list.push(i)
      }
      return list.includes(id)
    },
    getCategory () {
      getCategory().then(res => {
        const data = res.data.data;
        this.typeList = data;
        this.activeName = (data[0] || {}).categoryValue;
        this.getList();
      })
    },
    handleExport (item) {
      this.$refs.exportTip.handleOpen(item)
    },
    handleCopy (item) {
      if (this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        return
      }
      this.$confirm('确认复制当前大屏', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        copyObj(item.id).then((res) => {
          this.$message.success('复制成功');
          const id = res.data.data;
          this.handleEdit({ id })
        })
      }).catch(() => {

      });
    },
    handleDel (item, index) {
      this.$confirm('是否确认永久删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.validData(index) && this.$website.isDemo) {
          this.$message.error(this.$website.isDemoTip)
          return false;
        }
        delObj(item.id).then(() => {
          this.list.splice(index, 1)
          this.$message.success('删除成功')
        })
      }).catch(() => {

      });
    },
    handleAdd () {
      this.form = {
        id: '',
        name: '',
        backgroundUrl: '',
        width: '1920',
        height: '1080',
        category: this.activeName
      };
      this.option.column = this.addColumn
      this.box = true;
    },
    handleUpdate (item, index) {
      this.form = item
      this.index = index;
      this.form.category = this.form.category + '';
      this.option.column = this.editColumn
      this.box = true;
    },
    handleSetScreen (item) {
      let column = this.option.column
      let widthColumn = this.findObject(column, 'width');
      let heightColumn = this.findObject(column, 'height');
      widthColumn.display = false;
      heightColumn.display = false;
      if (item.width == '-' && item.height == '-') {
        widthColumn.display = true;
        heightColumn.display = true;
        this.form.width = ''
        this.form.height = ''
      } else {
        this.form.width = item.width;
        this.form.height = item.height;
      }
    },
    handleEdit (item) {
      let routeUrl = this.$router.resolve({
        path: '/build/' + item.id
      })
      window.open(routeUrl.href, '_blank');
    },
    handleViews (item) {
      let routeUrl = this.$router.resolve({
        path: '/view/' + item.id
      })
      window.open(routeUrl.href, '_blank');
    },
    handleSave (form, done) {
      if (this.isEdit) {
        if (this.validData(Number(this.index)) && this.$website.isDemo) {
          this.$message.error(this.$website.isDemoTip)
          done()
          this.box = false;
          return false;
        }
        updateObj(this.form).then(() => {
          done();
          this.box = false;
          this.$message.success('修改成功');
          this.getList();
        })
      } else {
        addObj(this.form).then(res => {
          const id = res.data.data.id;
          this.box = false;
          setTimeout(() => {
            this.handleEdit({ id })
          })
        })
      }
    },
    handleCurrentChange (val) {
      this.page.page = val;
      this.getList();
    },
    handleSizeChange (val) {
      this.page.size = val;
      this.getList();
    },
    getList (category) {
      this.loading = true
      this.list = []
      getList({
        title: this.search.name,
        category: this.activeName,
        current: this.page.page,
        size: this.page.size,
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        this.page.total = data.total;
        let records = data.records
        records.forEach(ele => ele._menu = false);
        this.list = records
      })
    }
  }
}
</script>