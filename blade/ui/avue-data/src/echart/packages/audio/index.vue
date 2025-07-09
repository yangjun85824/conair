<template>
  <div :class="[b(),className]"
       :style="styleSizeName"
       ref="main">
    <i class="iconfont icon-audio"
       @click="play()"></i>
  </div>
</template>

<script>
import create from "../../create";
export default create({
  name: "audio",
  props: {
    option: {
      type: Object,
      default: () => {
        return {};
      }
    },
  },
  data () {
    return {

    };
  },
  created () { },
  mounted () {

  },
  methods: {
    play (content) {
      const speech = new SpeechSynthesisUtterance();
      speech.lang = this.option.lang
      speech.pitch = this.option.pitch
      speech.rate = this.option.rate
      speech.text = content || this.dataChart.value
      speech.volume = this.option.volume
      window.speechSynthesis.speak(speech);
    },
    pause () {
      window.speechSynthesis.pause()
    },
    resume () {
      window.speechSynthesis.resume()
    },
    cancel () {
      window.speechSynthesis.cancel()
    },
  }
});
</script>

