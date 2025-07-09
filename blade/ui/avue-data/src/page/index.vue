<template>
  <el-container class="index">
    <el-aside class="left">
      <logo></logo>
      <navs @change="handleChange"></navs>
      <help></help>
    </el-aside>
    <el-main class="main">
      <router-view />
    </el-main>
  </el-container>
</template>
<script>
import { getList } from '@/api/glob'
import navs from './nav.vue'
import logo from './logo.vue'
import help from './help.vue'
export default {
  name: "index",
  components: {
    navs,
    logo,
    help
  },
  data () {
    return {
      publicPath: import.meta.env.VITE_APP_BASE,
    }
  },
  created () {
    this.initGlob();
  },
  methods: {
    initGlob () {
      getList({
        current: 1,
        size: 100,
      }).then(res => {
        let list = res.data.data.records
        list.forEach(ele => {
          window.$glob[ele.globalKey] = ele.globalValue
        })
      })
    },
    handleChange (item, index) {
      this.$router.push({ path: item.path })
    }
  }
}
</script>
<style lang="scss">
@import "@/styles/list.scss";
.index {
  height: 100%;
  & > .left {
    position: relative;
    width: 220px !important;
    height: 100%;
    border-right: 1px solid #2d2d2d;
    background: #18181c;
  }
  & > .main {
    padding: 0;
  }
}
</style>