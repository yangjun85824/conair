<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       ref="main"
       @mouseenter="handleMouseEnter"
       @mouseleave="handleMouseLeave"
       @dblclick="handleDblClick"
       @click="handleClick">
    <iframe :style="styleChartName"
            :src="mappingValue"
            draggable="false"></iframe>
  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "iframe",
  props: {
    option: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data () {
    return {};
  },
  computed: {},
  created () { },
  mounted () { },
  methods: {

  }
});
</script>


