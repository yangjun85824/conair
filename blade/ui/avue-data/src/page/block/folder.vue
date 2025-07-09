<template>
  <avue-draggable @move="handleMove"
                  @over="handleOver"
                  @out="handleOut"
                  @focus="handleFocus"
                  @blur="handleBlur"
                  folder
                  v-bind="item"
                  :z-index="disabled ?-9999:item.zIndex"
                  :id="index"
                  ref="main"
                  :tool="tool"
                  :line="line"
                  :range="false"
                  :width="width"
                  :left="left"
                  :top="top"
                  :scale="scale"
                  :step="step"
                  :disabled="disabled"
                  :height="height">
    <slot></slot>
  </avue-draggable>
</template>

<script>
export default {
  props: {
    step: Number,
    index: [Number, String],
    zIndex: [Number, String],
    id: String,
    scale: Number,
    disabled: Boolean,
    line: Boolean,
    tool: Boolean,
    item: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      type: 'folder',
      active: false,
      list: [],
      width: 0,
      height: 0,
      left: 0,
      top: 0
    }
  },
  watch: {
    'item.left' (val) {
      this.left = val;
    },
    'item.top' (val) {
      this.top = val;
    },
  },
  mounted () {
    this.setPosition()
  },
  methods: {
    setActive (val, flag) {
      this.setPosition()
      this.active = val;
      this.$refs.main.setActive(val, flag)
    },
    setOverActive (val) {
      this.setPosition()
      this.$refs.main.setOverActive(val)
    },
    handleMove (params) {
      let { index, left, top } = params
      if (this.active) {
        this.list.forEach(item => {
          if (index === item.index) return
          item.left = item.left + left;
          item.top = item.top + top
        })
      }
      this.$emit('move', params)
    },
    handleOut (params) {
      this.placement = false
      this.$emit('out', params)
    },
    handleOver (params) {
      this.setPosition()
      this.$emit('over', params)
    },
    handleFocus (params) {
      this.$emit('focus', params)
    },
    handleBlur (params) {
      params.type = 'folder'
      this.$emit('blur', params)
    },
    setPosition () {
      this.list = []
      let leftMinList = [], topMinList = []
      let leftMaxList = [], topMaxList = []
      let key = 10;
      const deepList = (list) => {
        list.forEach(ele => {
          if (ele.children) deepList(ele.children)
          else {
            this.list.push(ele)
            leftMinList.push(ele.left - key)
            topMinList.push(ele.top - key)
            leftMaxList.push(ele.left + ele.component.width + key)
            topMaxList.push(ele.top + ele.component.height + key)
          }
        })
      }
      deepList(this.item.children)
      const startX = Math.min.apply(null, leftMinList);
      const endX = Math.max.apply(null, leftMaxList);
      const startY = Math.min.apply(null, topMinList);
      const endY = Math.max.apply(null, topMaxList);
      this.width = endX - startX;
      this.height = endY - startY
      this.left = startX
      this.top = startY
      this.item.top = this.top
      this.item.left = this.left
      this.item.component = {
        width: this.width,
        height: this.height
      }
    },
    deepItem (fn) {
      const deepList = (list) => {
        list.forEach(ele => {
          fn & fn(ele)
          if (ele.children) deepList(ele.children)
        })
      }
      deepList([this.item])
    },
    setLock (flag) {
      this.deepItem((ele) => {
        ele.lock = flag
      })
    },
    setDisplay (flag) {
      this.deepItem((ele) => {
        console.log(ele, flag)
        ele.display = flag
      })
    },
  }
}
</script>

<style lang="scss">
</style>