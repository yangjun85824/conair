<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       ref="main">
    <i class="iconfont icon-message"></i>
  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "notice",
  props: {
    option: {
      type: Object,
      default: () => {
        return {};
      }
    },
    debugFormatter: Function,
  },
  data () {
    return {

    };
  },
  computed: {
    templateCode () {
      return this.option.templateCode
    }
  },
  watch: {

  },
  created () { },
  mounted () {

  },
  methods: {
    send (content) {
      if (typeof (content) != 'string') content = JSON.stringify(content);
      return this.debugFormatter(this.templateCode, content)
    }
  }
});
</script>


