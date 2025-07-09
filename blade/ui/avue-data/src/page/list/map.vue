<template>
  <el-container class="list">

    <el-header class="content__header">
      <div class="content__box content__nav">
        <div class="avue-flex">
          <div class="content__add"
               @click="$refs.crud.rowAdd()">
            <img :src="`${$router.options.base}img/map.png`"
                 height="40px"
                 alt="">
            <div>
              <p>创建地图</p>
              <span>自定义地图 随心所欲</span>
            </div>
          </div>
          <div class="content__add"
               @click="goDraw">
            <img :src="`${$router.options.base}img/download.png`"
                 height="40px"
                 alt="">
            <div>
              <p>地图下载</p>
              <span>全国任意地图下载</span>
            </div>
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
        </div>
      </div>
    </el-header>
    <el-main class="content"
             v-loading="loading"
             v-bind="$loadingParams">
      <div class="content__box">
        <avue-crud ref="crud"
                   :option="option"
                   v-model="form"
                   @row-save="rowSave"
                   @row-update="rowUpdate"
                   @row-del="rowDel"
                   @tree-load="treeLoad"
                   @current-row-change="handleCurrentRowChange"
                   v-loading="loading"
                   v-bind="$loadingParams"
                   :before-open="beforeOpen"
                   :data="data">
          <template #menu-form>
            <el-button type="primary"
                       v-if="isEdit"
                       @click="handleView()"
                       icon="el-icon-view">
              预览
            </el-button>
          </template>
          <template #code-form>
            <el-input placeholder="请输入内容"
                      v-model="form.code">
              <template #prepend
                        v-if="form.parentCode">
                <span>{{ form.parentCode }}</span>
              </template>
            </el-input>
          </template>
          <template #data-form>
            <json-viewer :value="form.data"
                         v-loading="loading"
                         v-bind="$loadingParams"
                         copyable
                         :expand-depth="2"
                         theme="avue-json-theme"
                         boxed></json-viewer>
            <el-upload action="#"
                       style="margin-top:20px;"
                       :show-file-list="false"
                       :auto-upload="false"
                       :on-change="uploadFile">
              <el-button :loading="loading1"
                         icon="el-icon-upload"
                         type="primary">点击上传</el-button>
            </el-upload>
          </template>
          <template #menu="{row,index,size}">
            <el-button type="primary"
                       text
                       :size="size"
                       @click="handleLefAdd(row,index)"
                       icon="el-icon-plus">
              增加子级
            </el-button>
            <el-button type="primary"
                       text
                       :size="size"
                       @click="handleView(row,index)"
                       icon="el-icon-view">
              预览
            </el-button>
          </template>
        </avue-crud>
      </div>
    </el-main>
    <el-dialog :title="viewObj.form.name+' 预览'"
               class="avue-dialog"
               width="50%"
               v-model="viewObj.box">
      <avue-echart-map v-if="viewObj.box"
                       v-bind="viewObj.option"
                       :map-formatter="mapFormatter"></avue-echart-map>
    </el-dialog>
  </el-container>
</template>

<script>
import { getList, getObj, addObj, delObj, updateObj, getMapData } from '@/api/map'
import AvueEchartMap from '@/echart/packages/map/index.vue';
export default {
  props: {
    menu: {
      type: Boolean,
      default: true
    }
  },
  components: {
    AvueEchartMap
  },
  computed: {
    isEdit () {
      return this.boxType == 'edit'
    }
  },
  data () {
    return {
      boxType: null,
      mapFormatter: getMapData,
      search: {},
      loading: false,
      form: {},
      data: [],
      option: {
        lazy: true,
        dialogWidth: '600',
        height: 'auto',
        calcHeight: 140,
        header: false,
        labelWidth: 100,
        menuWidth: 350,
        column: [{
          label: '上一级别',
          hide: true,
          span: 24,
          disabled: true,
          prop: 'parentName',
        },
        {
          label: '地图名称',
          prop: 'name',
          span: 24,
          row: true,
          rules: [{
            required: true,
            message: "请输入地图名称",
            trigger: "blur"
          }]
        }, {
          label: '地图级别',
          prop: 'level',
          width: 100,
          align: 'center',
          headerAlign: 'center',
          type: "select",
          dicData: [{
            label: '国家',
            value: 0
          }, {
            label: '省份',
            value: 1
          }, {
            label: '城市',
            value: 2
          }, {
            label: '区县',
            value: 3
          }],
          rules: [{
            required: true,
            message: "请选择地图级别",
            trigger: "blur"
          }],
          span: 24,
        }, {
          label: '地图编号',
          prop: 'code',
          width: 100,
          align: 'center',
          headerAlign: 'center',
          span: 24,
          row: true,
          rules: [{
            required: true,
            message: "请输入地图编号",
            trigger: "blur"
          }]
        },
        {
          span: 24,
          label: '地图数据',
          prop: 'data',
          hide: true,
          formslot: true,
          type: 'textarea',
          minRows: 20
        }
        ]
      },
      viewObj: {
        option: {
          width: 600,
          height: 700,
          option: {
            mapData: 1,
            tipBackgroundColor: "rgba(13, 255, 255, .5)",
            tipColor: "rgba(217, 38, 10, 1)",
            tipFontSize: "20",
            borderWidth: 1,
            scale: 100,
            type: 0,
            borderColor: "#0dffff",
            areaColor: "rgba(6, 29, 51, 0.59)",
            banner: true,
            bannerTime: 3000,
            fontSize: 12,
            zoom: 0.5,
            roam: true,
            empAreaColor: "rgba(35, 183, 229, 0.42)",
            empColor: "rgba(217, 38, 10, 1)",
            color: "rgba(13, 255, 255, 1)",
          }
        },
        box: false,
        form: {}
      }
    }
  },
  created () {
    this.option.menu = this.menu
    this.onLoad()
  },
  mounted () {

  },
  methods: {
    expanded () {
      const els = document.getElementsByClassName('el-table__expand-icon')
      this.$nextTick(() => {
        els[0].click()
      })
    },
    handleCurrentRowChange (item) {
      this.$emit('submit', item)
    },
    validData (id) {
      return true
    },
    goDraw () {
      window.open("https://datav.aliyun.com/tools/atlas/#&lat=33.521903996156105&lng=104.29849999999999&zoom=4")
    },
    handleView (row) {
      row = row || this.form
      this.viewObj.form = row;
      this.viewObj.box = true;
      this.viewObj.option.option.mapData = row.id;
    },
    handleLefAdd (row, index) {
      this.form.parentId = row.id;
      this.form.parentName = row.name;
      this.form.parentCode = row.code;
      this.$refs.crud.rowAdd()
    },
    uploadFile (file) {
      this.loading = true
      const reader = new FileReader();
      // 异步处理文件数据
      reader.readAsText(file.raw, "UTF-8");
      // 处理完成后立马触发 onload
      reader.onload = (fileReader) => {
        const fileData = fileReader.target.result;
        let result = JSON.parse(fileData) || {};
        this.form.data = result;
        this.loading = false
      };
    },
    beforeOpen (done, type) {
      this.boxType = type;
      if (type == 'edit') {
        done()
        this.loading = true
        getObj(this.form.id).then(res => {
          this.loading = false
          const data = res.data.data;
          this.form = data
          this.form.code = this.form.code.replace(this.form.parentCode, '')
          this.form.data = JSON.parse(this.form.data)
        }).catch(_ => {
          this.loading = false
        })
      } else {
        this.form.parentCode = this.form.parentCode || 0
        done()
      }
    },
    rowDel (row, index, done) {
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
          done(row)
        })
      }).catch(() => {

      });

    },
    isValidJSON (json) {
      try {
        JSON.stringify(json);
        return true;
      } catch (error) {
        return false;
      }
    },
    rowUpdate (row, index, done) {
      if (this.validData(index) && this.$website.isDemo) {
        done();
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      if (this.isValidJSON(row.data)) {
        row.data = JSON.stringify(row.data)
      }
      row.code = row.parentCode + row.code
      updateObj(row).then(() => {
        done(row);
        this.$message.success('修改成功');

      })
    },

    handleEdit (row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    rowSave (row, done, loading) {
      if (this.isValidJSON(row.data)) {
        row.data = JSON.stringify(row.data)
      }
      row.parentId = row.parentId || 0
      row.parentCode = row.parentCode || row.code
      row.code = row.parentCode + row.code
      addObj(row).then(() => {
        this.$message.success('新增成功');
        done(row);
      }).catch(err => {
        loading()
      })
    },
    treeLoad (tree, treeNode, resolve) {
      getList({
        parentId: tree.id
      }).then(res => {
        const data = res.data.data;
        resolve(data);
      })
    },
    onLoad () {
      this.loading = true;
      getList({
        name: this.search.name,
        parentId: 0
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        this.data = data;
        this.$nextTick(() => {
          this.expanded()
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>