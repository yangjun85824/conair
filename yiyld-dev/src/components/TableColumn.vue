<template>
  <div class="table">
    <div>表名：{{tableName}}</div>
    <p v-for="(value,key) in columns" :key = key>{{key+1}} - {{value.columnsName}} - {{value.dataType}}</p>
  </div>
<!--  <a href="http://localhost:8787/pay/test/alipay/url">alipay</a>-->
<!--  <a href="http://localhost:8787/pay/test/alipay/notify_url">wx</a>-->
</template>

<script setup>

  // import {watch} from 'vue';

  const props = defineProps({
    columns: Object,
    tableName: String,
    modelValue: Object
  })
  const emit = defineEmits(['columnList', 'tableName'])

  import {ref, getCurrentInstance} from 'vue'

  const {proxy} = getCurrentInstance();
  console.log(proxy.$api)
  const api = proxy.$api;

  // let childDBList = ref({});
  // const formData = ref({ ...props.modelValue })
  // watch(formData, (newVal) => emit('update:modelValue', newVal), {
  //   deep: true
  // })

  function alldsliblist(key, val) {

    console.log(val, val.type)

    let params = {dsType: val.type}

    api.dsapi.alldsliblist(params).then(function (response) {

      console.log("response", response.data)
      if (response.data.code == '200') {

        val.childList = response.data.data;

      }
    });
  }

</script>
<style lang="scss">
.table{
  width:100%;
}
</style>