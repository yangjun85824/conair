<template>
  <div class="swiper">
    <el-drawer title="屏幕轮播"
               append-to-body
               class="avue-dialog"
               v-model="box"
               direction="rtl">
      <div class="swiper__content">
        <el-input placeholder="请输入大屏ID多个大屏用','间隔"
                  v-model="value1">
          <template #append>
            <span @click="goMenu2">预览轮播大屏</span>
          </template>

        </el-input>
        <br /> <br />
        <el-input placeholder="请输入大屏ID"
                  v-model="value">
          <template #append>
            <span @click="goMenu1">预览单html大屏</span>
          </template>
        </el-input>
      </div>
    </el-drawer>
  </div>

</template>
<script>
import { getObj } from '@/api/visual'
import { uuid } from '@/utils/utils'
export default {
  data () {
    return {
      box: false,
      value: '1',
      value1: '1,2,5,6'
    }
  },
  methods: {
    goMenu1 () {
      window.open('/view.html?id=' + this.value)
    },
    goMenu2 () {
      window.open('/swiper.html?id=' + this.value1)
    }
  }
}
</script>
<style lang="scss" scoped>
.swiper {
  &__content {
    padding: 20px;
  }
}
</style>