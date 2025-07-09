<template>
  <div class="nav-main">
    <div class="nav-item"
         v-for="(item,index) in navs"
         :key="index"
         :class="{'is-active':index===activeIndex}"
         @click="toggleNav(item,index)">
      <el-icon class="nav-icon">
        <component :is="item.icon"></component>
      </el-icon>
      <div class="nav-title">{{ item.name }}</div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      navs: [{
        icon: 'el-icon-menu',
        name: '大屏管理',
        path: '/'
      }, {
        icon: 'el-icon-folder-opened',
        name: '大屏分类',
        path: '/category'
      }, {
        icon: 'el-icon-set-up',
        name: '数据源管理',
        path: '/db'
      }, {
        icon: 'el-icon-eleme',
        name: '组件库',
        path: '/components'
      }, {
        icon: 'el-icon-opportunity',
        name: '全局变量',
        path: '/glob'
      }, {
        icon: 'el-icon-suitcase',
        name: '数据集管理',
        path: '/record'
      }, {
        icon: 'el-icon-message',
        name: '消息渠道',
        path: '/task'
      }, {
        icon: 'el-icon-files',
        name: '静态资源',
        path: '/file'
      }, {
        icon: 'el-icon-location-information',
        name: '地图管理',
        path: '/map'
      }, {
        icon: 'el-icon-document',
        name: '在线教程',
        path: '/document'
      }, {
        icon: 'el-icon-user',
        name: 'AI智能',
        path: '/chatgpt'
      }],
      activeIndex: 0,
    }
  },
  mounted () {
    let index = this.navs.findIndex(ele => ele.path === this.$route.path)
    this.activeIndex = index
  },
  methods: {
    toggleNav (nav, index) {
      this.activeIndex = index;
      this.$emit('change', nav, index)
    }
  }
}
</script>

<style lang="scss" scoped>
.nav-main {
  padding: 0 8px;
  display: flex;
  flex-direction: column;
  flex: 1;
  user-select: none;
  white-space: nowrap;
  overflow: hidden;
  cursor: pointer;
  box-sizing: border-box;
}
.nav-item {
  position: relative;
  height: 40px;
  display: flex;
  flex-shrink: 0;
  margin: 3px 0;
  line-height: 40px;
  align-items: center;
  cursor: pointer;
  border-radius: 3px;
  color: #dadadb;
  font-size: 14px;
  &:hover {
    color: var(--primary-color);
    background-color: #223442;
  }
  &.is-active {
    color: var(--primary-color);
    background-color: #223442;
    font-size: 15px;
    &:after {
      content: "";
      position: absolute;
      top: 50%;
      left: 0;
      width: 3px;
      height: 50%;
      border-radius: 6px;
      transform: translateY(-50%);
      background-color: var(--primary-color);
    }
  }
}
.nav-icon {
  margin-left: 15px;
  margin-right: 4px;
}
</style>