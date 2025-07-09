<template>
  <div class="version">
    <div class="version__title">版本列表</div>
    <ul class="menu_ul"
        v-if="list.length>0">
      <li class="menu__item"
          :class="{'is-active':item.id===contain.visual.version}"
          @click="handleSelect(item)"
          v-for="(item,index) in list"
          :key="index">
        <span class="menu__label">
          <el-tag type="info"
                  size="small"
                  class="menu__name">v{{item.version}}</el-tag>
          <el-button type="info"
                     size="small"
                     v-if="item.createTime"
                     class="version__datetime">{{ item.createTime }}</el-button>
        </span>
        <span class="menu__menu">
          <el-button type="primary"
                     @click.stop="handleView(item)"
                     size="small"
                     icon="el-icon-view"></el-button>
          <el-button type="success"
                     @click.stop="handleImport(item)"
                     size="small"
                     icon="el-icon-download"></el-button>
        </span>
      </li>
    </ul>
    <el-empty v-else
              class="content__empty"
              description="暂无数据">
      <template #image>
        <svg-icon icon-class="empty" />
      </template>
    </el-empty>
  </div>
</template>

<script>
import { updateObj } from '@/api/visual';
import { getList, addObj, getObj } from '@/api/version';
export default {
  inject: ["contain", "parent"],
  data () {
    return {
      list: []
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    getList () {
      getList({
        current: 1,
        size: 99,
        visualId: this.contain.id
      }).then(res => {
        const data = res.data.data.records;
        this.list = data;
      })
    },
    handleSelect (item) {
      this.$confirm(`是否选中当前【v${item.version}】版本?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contain.visual.version = item.id;
        return updateObj({
          id: this.contain.visual.id,
          version: item.id
        })
      }).then(() => {
        this.$message.success(`【v${item.version}】版本发布成功`)
      })
    },
    handleView (item) {
      let routeUrl = this.$router.resolve({
        path: '/view/' + this.contain.id,
        query: {
          v: item.id
        }
      })
      window.open(routeUrl.href, '_blank');
    },
    handleImport (item) {
      this.$confirm(`是加载当前【v${item.version}】版本?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        getObj(item.id).then((res) => {
          const data = JSON.parse(res.data.data.data);
          this.contain.config = data.detail
          this.contain.nav = data.component
          this.$message.success(`【v${item.version}】版本导入成功`)
          this.parent.box = false
        })
      })
    },
    handleAdd () {
      let content = {
        detail: this.contain.config,
        component: this.contain.nav,
      }
      addObj({
        version: this.list.length + 1,
        visualId: this.contain.id,
        data: JSON.stringify(content)
      }).then(() => {
        this.$message.success('版本发布成功')
        this.getList()
      })

    }
  }
}
</script>

<style lang="scss">
.version {
  padding: 8px 0 0 20px;
  width: 550px;
  height: 300px;
  overflow-y: scroll;
  border-left: 1px solid #333;
  &__title {
    color: #fff;
    margin-bottom: 10px;
  }
  &__datetime {
    width: 140px;
    margin-left: 8px;
    font-size: 12px;
  }
  .menu {
    &__item {
      padding: 5px 10px;
      list-style: none;
      margin-bottom: 10px;
      cursor: pointer;
      &.is-active {
        border: 1px solid #409eff;
        background-color: #2f3f62;
        border-radius: 3px;
      }
    }
    &__name {
      width: 40px;
      text-align: center;
    }
    &__menu {
      margin-left: 10px;
    }
  }
}
</style>