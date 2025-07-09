<template>
<div class="head">
    <p class="title">在线JAVA解析器</p>
</div>
<iframe src="/ds/print" frameborder="1" width="400px" height="400px"></iframe>
</template>

<script setup>
import {
    ref,
    getCurrentInstance,
    reactive
} from 'vue'
const {
    proxy
} = getCurrentInstance();
const api = proxy.$api;

</script>

<style lang="scss">
.head {
    padding: 5px;

    .title {
        text-align: center;
        font-weight: bold;
        font-size: 18px;
        color: #42b983;
    }
}

.form-item-class {
    margin-top: 20px;
}

.tac {
    margin-left: 20px;
}
</style>
<style scoped>
    
</style>
