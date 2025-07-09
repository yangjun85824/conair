<template>
  <div style="padding:0 5px;">
    <el-button type="primary"
               style="margin:10px 0 10px 0;"
               @click="handleAddGroup()">新增过滤器</el-button>
    <ul class="menu_ul"
        v-if="list.length!=0">
      <li v-contextmenu="{id:menuId,event:handleContextMenu,value:item}"
          @dblclick="handleChangeName(item,index)"
          class="menu__item"
          v-for="(item,index) in list"
          :key="index">
        <span class="menu__icon">
          <svg-icon icon-class="filters" />
        </span>
        <span class="menu__label">
          <input type="text"
                 @keyup.enter="item.isname=false"
                 v-if="item.isname"
                 v-model="item.name">
          <span v-else
                class="menu__name">{{item.name}}</span>
        </span>
        <span class="menu__menu">
          <el-icon @click.stop="handleEditGroup(item,index)">
            <el-icon-edit></el-icon-edit>
          </el-icon>
          <el-icon @click.stop="handleDelGroup(item,index)">
            <el-icon-delete></el-icon-delete>
          </el-icon>
        </span>
      </li>
    </ul>
    <el-empty v-else
              description="暂无过滤器">
      <template #image>
        <svg-icon icon-class="empty" />
      </template>
    </el-empty>
    <div class="contentmenu"
         :id="menuId">
      <template v-if="index!=0">
        <div class="contentmenu__item"
             @click="handleChangeName(item,index)">{{item.isname?'完成':'重命名'}} </div>
        <div class="contentmenu__item"
             @click="handleDelGroup(item,index)">删除 </div>
      </template>

    </div>
    <codeedit @submit="codeClose"
              :title="form.name+'过滤器'"
              :rules="false"
              v-model="code.obj"
              v-if="code.box"
              :type="code.type"
              language="javascript"
              v-model:visible="code.box"></codeedit>

  </div>
</template>

<script>
import codeedit from '@/page/group/code.vue';
import { uuid } from '@/utils/utils'
export default {
  inject: ["contain"],
  components: {
    codeedit
  },
  data () {
    return {
      code: {
        type: 'dataFormatter',
        box: false,
        obj: {},
      },
      item: {},
      form: {},
      index: null,
      obj: {},
      menuId: 'avue-filter-menu'
    }
  },
  computed: {
    list () {
      let list = this.contain.config.filters
      return Object.keys(list).map(key => list[key])
    }
  },
  methods: {
    handleContextMenu (item = {}, done) {
      this.item = item;
      done()
    },
    handleChangeName (item) {
      item.isname = !item.isname
    },
    handleAddGroup () {
      if (!this.contain.config.filters) {
        this.contain.config.filters = {}
      }
      let id = uuid();
      this.contain.config.filters[id] = {
        id,
        name: '新增过滤器',
        isname: true,
        dataFormatter: ''
      }
    },
    handleEditGroup (item) {
      this.form = item;
      this.openCode()
    },
    codeClose (value) {
      this.contain.config.filters[this.form.id][this.code.type] = value;
    },
    openCode () {
      this.code.obj = this.form[this.code.type];
      this.code.box = true;
    },
    handleDelGroup (item) {
      this.$confirm(`是否删除【${item.name}】过滤器?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delete this.contain.config.filters[item.id]
      })
    }
  }
}
</script>

<style>
</style>