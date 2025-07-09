<template>
  <div class="document">
    <div class="item">
      <p class="avue-tip-title">功能</p>
      <el-button type="primary"
                 icon="el-icon-eleme"
                 @click="$refs.control.box=true">屏幕控制器</el-button>
      <el-button type="primary"
                 icon="el-icon-menu"
                 @click="$refs.swiper.box=true">屏幕轮播</el-button>
    </div>
    <div class="item">
      <p class="avue-tip-title">文档</p>
      <el-button type="success"
                 @click="goList">功能清单</el-button>
      <el-button type="success"
                 @click="goDoc1">原理图</el-button>
      <el-button type="success"
                 @click="goApi">接口文档</el-button>
      <el-button type="success"
                 @click="goDoc">操作文档</el-button>
      <!-- <el-button 
                 @click="goVip"
                 type="danger">购买源码</el-button> -->
    </div>
    <div class="item">
      <p class="avue-tip-title">视频</p>
      <div class="list">
        <el-main class="content">
          <div class="content__box">
            <div class="content__item"
                 v-for="(item,index) in list"
                 :key="index"
                 @click="goOpen(item)">
              <div class="content__main">
                <span class="content__name"
                      style="width:100%;cursor: pointer;">{{item.label}}</span>
              </div>
            </div>
          </div>
        </el-main>
      </div>
    </div>
    <swiper ref="swiper"></swiper>
    <control ref="control"></control>
  </div>
</template>

<script>
import control from '@/page/list/control.vue'
import swiper from '@/page/list/swiper.vue'
export default {
  data () {
    return {
      list: []
    }
  },
  components: {
    control,
    swiper
  },
  created () {
    this.$axios.get('/video.json').then(res => {
      this.list = res.data;
    })
  },
  methods: {
    goOpen (item) {
      window.open(item.src)
    },
    goApi () {
      window.open('https://api.data.bladex.cn/doc.html')
    },
    goList () {
      window.open('https://docs.qq.com/sheet/DZmdKak9vY0lnRWRy')
    },
    goDoc () {
      window.open('https://www.kancloud.cn/smallwei/avue-doc')
    },
    goDoc1 () {
      window.open('https://kdocs.cn/l/cuOoWqq98ljO')
    },
    goVip () {
      window.open('https://avuejs.com/views/vip.html')
    }
  }
}
</script>

<style lang="scss">
.document {
  height: 100%;
  overflow: auto;
  padding: 0 30px;
  .item {
    margin-bottom: 20px;
  }
  .avue-tip-title {
    font-size: 18px;
    margin-bottom: 5px;
  }
}
</style>