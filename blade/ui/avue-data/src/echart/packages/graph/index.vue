<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       ref="main"
       @mouseenter="handleMouseEnter"
       @mouseleave="handleMouseLeave"
       @dblclick="handleDblClick"
       @click="handleClick">
    <div :style="styleChartName">
      <svg v-if="option.type=='horizontal' || option.type=='vertical'"
           preserveAspectRatio="none meet"
           version="1.1"
           xmlns="http://www.w3.org/2000/svg"
           style="height: 100%; width: 100%;">
        <g fill="none"
           :stroke="option.color"
           :stroke-width="option.size">
          <path :class="pathClassName"
                :stroke-dasharray="dasharray"
                :d="type"></path>
        </g>
      </svg>
      <svg v-else-if="option.type=='rectangle'"
           xmlns="http://www.w3.org/2000/svg"
           preserveAspectRatio="none meet"
           version="1.1"
           style="height: 100%; width: 100%;">
        <circle :cx="width/2"
                :cy="height/2"
                :r="width/2-option.size*2"
                :stroke="option.color"
                :stroke-width="option.size"
                :fill="fill"
                :class="pathClassName"
                :stroke-dasharray="dasharray"></circle>
      </svg>
      <svg v-else-if="option.type=='rotundity'"
           xmlns="http://www.w3.org/2000/svg"
           version="1.1"
           preserveAspectRatio="none meet"
           style="height: 100%; width: 100%;">
        <rect :x="option.size/2"
              :y="option.size/2"
              :width="width-option.size"
              :height="height-option.size"
              :fill="fill"
              :class="pathClassName"
              :style="pathStyleName"></rect>
      </svg>
    </div>

  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "graph",
  data () {
    return {

    };
  },
  computed: {
    fill () {
      return this.option.fill || 'transparent'
    },
    type () {
      let result = {
        horizontal: `M0 ${this.height / 2} l${this.width} 0`,
        vertical: `M${this.width / 2} 0 l0 ${this.height}`,
      }
      return result[this.option.type]
    },
    dasharray () {
      let result = {
        dotted: '5 5',
        dashed: '10 10',
        blend: '10 10 2 10'
      }
      return result[this.option.lineType]
    },
    pathClassName () {
      if (!this.option.animation) return ''
      return `${this.option.animation}-water-run-${this.option.lineType}`
    },
    pathStyleName () {
      if (this.option.type == 'rotundity') {
        return {
          fill: this.fill,
          strokeWidth: this.option.size,
          strokeDasharray: this.dasharray,
          stroke: this.option.color
        }
      }
    }
  },
  watch: {

  },
  created () {

  },
  mounted () {

  },
  methods: {

  },
  props: {
    option: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});
</script>


