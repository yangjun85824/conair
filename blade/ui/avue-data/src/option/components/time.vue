<!-- 通用形配置 -->
<template>
  <div>
    <el-form-item label="执行间隔">
      <el-input v-model="main.activeOption.time">
        <template #append>
          <span>毫秒</span>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item label="执行逻辑">
      <el-button type="primary"
                 @click="openCode">编辑</el-button>
    </el-form-item>
    <el-form-item label-width="0">
      <avue-highlight :height="500"
                      v-model="main.activeOption.content"></avue-highlight>
    </el-form-item>
    <codeedit @submit="codeClose"
              title="Time执行逻辑"
              v-model="code.obj"
              v-if="code.box"
              :type="code.type"
              v-model:visible="code.box"></codeedit>
  </div>
</template>

<script>
import codeedit from '../../page/group/code.vue';
export default {
  name: 'time',
  inject: ["main"],
  data () {
    return {
      code: {
        box: false,
        obj: {},
      }
    }
  },
  components: {
    codeedit
  },
  methods: {
    codeClose (value) {
      this.main.activeObj[this.code.type] = value;
    },
    openCode () {
      this.code.type = 'echartFormatter';
      this.code.obj = this.main.activeObj[this.code.type];
      this.code.box = true;
    },
  }
}
</script>

<style>
</style>