<template>
  <div class="build views"
       :style="viewStyle">
    <container :id="id"
               :target="target"
               :option="option"
               ref="container"></container>
  </div>
</template>
<script>
import init from '@/mixins/'
export default {
  props: {
    target: String,
    option: Object,
    id: [String, Number]
  },
  mixins: [init],
  data () {
    return {
      viewStyle: {}
    }
  }
}
</script>