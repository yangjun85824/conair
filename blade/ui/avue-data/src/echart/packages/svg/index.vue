<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       ref="main"
       @mouseenter="handleMouseEnter"
       @mouseleave="handleMouseLeave"
       @dblclick="handleDblClick"
       @click="handleClick">
    <component :is="componentName"
               :style="[styleChartName,styleName]"> </component>
  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "svg",
  data () {
    return {

    };
  },
  computed: {
    componentName () {
      return {
        template: this.option.content
      }
    },
    color () {
      return this.option.color
    },
    styleName () {
      return Object.assign({
        fill: this.color,
        width: this.setPx(this.width),
        height: this.setPx(this.height),
        color: this.color
      }, this.styles);
    }
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

