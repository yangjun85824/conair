<template>
  <div v-for="item in nav"
       :key="item.index"
       v-contextmenu="{id:contain.menuId,event:contain.handleContextMenu,value:item}">
    <template v-if="!item.children">
      <avue-draggable v-bind="item"
                      :range="!contain.isSelectActive"
                      :line="!contain.isSelectActive"
                      :tool="contain.config.toolShow"
                      :scale="container.stepScale"
                      :disabled="!contain.menuFlag"
                      :step="container.stepScale"
                      :width="item.component.width"
                      :height="item.component.height"
                      :ref="common.DEAFNAME+item.index"
                      :id="common.DEAFNAME+item.index"
                      :active-flag="contain.active.includes(item.index)"
                      v-show="getShow(item)&&getDisplay(item)&&!item.auto"
                      @move="handleMove"
                      @out="handleOut"
                      @over="handleOver"
                      @focus="handleFocus"
                      @blur="handleBlur">
        <temp :item="item"
              :ref="common.NAME+item.index"></temp>
      </avue-draggable>
      <subgroup :nav="item.children"></subgroup>
    </template>
    <folder v-else
            @move="handleMove"
            @out="handleOut"
            @over="handleOver"
            @focus="handleFocus"
            @blur="handleBlur"
            :key="item.index"
            :item="item"
            v-bind="item"
            :step="container.stepScale"
            :scale="container.stepScale"
            :disabled="!contain.menuFlag"
            :id="common.DEAFNAME+item.index"
            :ref="common.DEAFNAME+item.index">
      <el-carousel class="carousel"
                   :interval="item.interval"
                   indicator-position="none"
                   arrow="never"
                   v-if="item.auto"
                   style="height:100%">
        <template v-for="citem in item.children">
          <el-carousel-item :key="citem.index"
                            v-if="citem.auto&&getDisplay(citem)">
            <temp v-if="!citem.children"
                  :parent="item"
                  :ref="common.NAME+citem.index"
                  :item="citem"></temp>
            <subgroup v-else
                      :nav="citem.children"></subgroup>
          </el-carousel-item>
        </template>
      </el-carousel>
    </folder>
  </div>
</template>

<script>
import folder from './folder.vue'
import temp from './temp.vue'
import crypto from '@/utils/crypto';
import common from '@/config'
export default {
  name: 'subgroup',
  inject: ["contain", 'container'],
  provide () {
    return {
      contain: this.contain,
      container: this.container
    };
  },
  components: {
    folder,
    temp
  },
  props: {
    nav: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      common: common,
    }
  },
  methods: {
    //刷新数据
    handleRefresh () {
      let result = this.getItemRef()
      if (result) {
        return result.updateData()
      }
      return Promise.resolve()
    },
    getItemRef (index) {
      index = index || this.contain.activeIndex
      let ref = this.$refs[`${this.common.NAME}${index}`] || []
      if (ref[0]) {
        return ref[0].$refs.temp
      } else {
        return {}
      }
    },
    getListRef (index) {
      let ref = this.$refs[`${this.common.DEAFNAME}${index}`] || []
      return ref[0]
    },
    getDisplay (item) {
      return !item.display
    },
    getShow (item) {
      if (!this.container.isBuild && ['time', 'data', 'notice'].includes(item.component.prop)) {
        return false
      }
      return true
    },
    handleMove ({ index, left, top }) {
      if (this.contain.activeIndex !== index) return
      this.contain.activeList.forEach(item => {
        if (this.contain.activeIndex === item.index) return
        item.left = item.left + left;
        item.top = item.top + top
      })
    },
    handleOut () {
      this.contain.activeOverIndex = null;
    },
    handleOver ({ index }) {
      this.contain.activeOverIndex = index;
    },
    handleFocus ({ index }) {
      this.container.gradeFlag = true;
      this.contain.selectNav(index);
    },
    handleBlur ({ index, left, top, width, height, type }) {
      this.container.gradeFlag = false;
      if (index !== this.contain.activeIndex || type === 'folder') return
      this.contain.activeObj.component.width = width
      this.contain.activeObj.component.height = height
      this.contain.activeObj.left = left
      this.contain.activeObj.top = top
    },
  }
}
</script>
<style  >
.carousel .el-carousel__container {
  height: 100%;
}
</style>