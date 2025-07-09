<template>
  <component :id="common.NAME+item.index"
             ref="temp"
             :is="common.COMPNAME+item.component.name"
             v-bind="item"
             :style="parent&&getStyle(parent,item)"
             :component="item.component"
             :transfer="container.transfer"
             :initialize="container.isBuild"
             :filter-list="contain.config.filters"
             :data-formatter-str="item.dataFormatter"
             :click-formatter-str="item.clickFormatter"
             :dbl-click-formatter-str="item.dblClickFormatter"
             :mouse-enter-formatter-str="item.mouseEnterFormatter"
             :mouse-leave-formatter-str="item.mouseLeaveFormatter"
             :data-before-formatter-str="item.dataBeforeFormatter"
             :data-after-formatter-str="item.dataAfterFormatter"
             :echart-formatter-str="item.echartFormatter"
             :label-formatter-str="item.labelFormatter"
             :styles-formatter-str="item.stylesFormatter"
             :formatter-str="item.formatter"
             :sql-formatter="sqlFormatter"
             :debug-formatter="debugFormatter"
             :map-formatter="mapFormatter"
             :record-formatter="recordFormatter"
             :width="item.component.width"
             :height="item.component.height"
             :disabled="!contain.menuFlag"
             :scale="container.stepScale"
             @remote-change="remoteChange"
             title="" />
</template>
<script>
import { debug } from '@/api/template'
import { dynamicSql } from '@/api/db'
import { getMapData } from '@/api/map'
import { getObj as getRecordObj } from '@/api/record'
import common from '@/config'
export default {
  inject: ["contain", 'container'],
  props: {
    item: Object,
    parent: Object
  },
  data () {
    return {
      mapFormatter: getMapData,
      debugFormatter: debug,
      sqlFormatter: dynamicSql,
      recordFormatter: getRecordObj,
      common: common,
    }
  },
  methods: {
    getStyle () {
      let leftMinList = [], topMinList = []
      let key = 10;
      const deepList = (list) => {
        list.forEach(ele => {
          if (ele.children) deepList(ele.children)
          else {
            leftMinList.push(ele.left - key)
            topMinList.push(ele.top - key)
          }
        })
      }
      deepList([this.parent])
      const left = Math.min.apply(null, leftMinList);
      const top = Math.min.apply(null, topMinList);
      return {
        position: 'absolute',
        top: this.setPx(this.item.top - top),
        left: this.setPx(this.item.left - left),
      }
    },
    remoteChange (params) {
      params.id.remoteContent = params.content
    }
  }
}
</script>