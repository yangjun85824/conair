<template>
  <el-dialog title="代码编辑"
             fullscreen
             modal-append-to-body
             v-model="box"
             append-to-body
             :close-on-click-modal="false"
             width="100%">
    <div class="code-edit">
      <div class="left">
        <monaco-editor v-model="form.content"
                       :language="options.language"
                       :height="options.height"
                       :options="options"></monaco-editor>
      </div>
      <div class="right"
           v-if="reload">
        <div class="tip"
             v-if="error">{{error}}</div>
        <avue-echart-vue v-bind="contain&&contain.activeObj"
                         :option="vueOption"
                         v-if="form.type==0"
                         @error-change="errorChange"
                         width="100%"
                         height="100%"></avue-echart-vue>
        <avue-echart-common v-bind="contain&&contain.activeObj"
                            :echart-formatter-str="vueOption.content"
                            @error-change="errorChange"
                            v-else-if="form.type==1"
                            width="100%"></avue-echart-common>
        <avue-echart-html v-bind="contain&&contain.activeObj"
                          :option="vueOption"
                          @error-change="errorChange"
                          v-else-if="form.type==2"
                          width="100%"></avue-echart-html>
      </div>
    </div>
  </el-dialog>
</template>
<script>
import common from '@/config'
import AvueEchartVue from '@/echart/packages/vue/index.vue';
import AvueEchartCommon from '@/echart/packages/common/index.vue';
import AvueEchartHtml from '@/echart/packages/html/index.vue';
import MonacoEditor from '@/page/components/monaco-editor'
import { getFunction, dataURLtoFile } from '@/utils/utils'
export default {
  inject: ["contain"],
  components: {
    AvueEchartCommon,
    AvueEchartVue,
    AvueEchartHtml,
    MonacoEditor
  },
  watch: {
    'form.content' (val) {
      this.handleOption()
    },
    modelValue: {
      handler (val) {
        this.form = val;
      },
      deep: true

    },
    form: {
      handler (val) {
        this.$emit('update:modelValue', val);
      },
      deep: true
    }
  },
  props: {
    modelValue: {}
  },
  data () {
    return {
      common,
      error: "",
      vueOption: '',
      box: false,
      reload: false,
      options: {
        height: 600,
        language: 'javascript',
      },
      form: {}
    }
  },
  methods: {
    errorChange (val) {
      if (val) console.log(val)
      this.error = val
    },
    handleOpen () {
      this.form = this.modelValue
      this.box = true;
      this.$nextTick(() => {
        this.handleOption()
      })
    },
    handleClose () {
      this.$emit('update:modelValue', this.form)
      this.box = false;
    },
    handleOption () {
      this.reload = false;
      this.vueOption = this.deepClone(this.form)
      this.$nextTick(() => {
        this.reload = true;
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.code-edit {
  width: 100%;
  height: 100%;
  overflow: scroll;
  display: flex;
  box-sizing: border-box;
  .left {
    width: 50%;
  }
  .right {
    width: 50%;
  }
  .right {
    padding: 0 20px;
    box-sizing: border-box;
  }
  .tip {
    color: #fff;
    font-size: 14px;
    margin-top: 20px;
    margin-bottom: 40px;
  }
}
</style>