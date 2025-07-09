<template>
  <div ref="main">
    <canvas id="qr"></canvas>
  </div>
</template>

<script>
export default {
  name: "code",
  data () {
    return {

    };
  },
  computed: {

  },
  created () {

  },
  mounted () {

  },
  methods: {
    updateChart () {
      const qr = new QRious({
        element: document.querySelector('#qr'),
        background: this.option.background || '#fff',
        foreground: this.option.color || '#000',
        level: this.option.level || 'H',
        size: this.width,
        value: this.mappingValue
      })
    }
  },
  props: {
    width: Number,
    option: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
};
</script>
