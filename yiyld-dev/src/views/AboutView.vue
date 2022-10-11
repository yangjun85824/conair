<template>
    <div class="about">
        <h1>This is an about page</h1>
      <DSTree msg="1234567890-"></DSTree>
    </div>
</template>
<script>

    import DSTree from '@/components/DSTree.vue'

    export default {
        name: 'DSTree',
        components: {
            DSTree
        },
        methods: {},
        // `mounted` 是生命周期钩子，之后我们会讲到
        setup() {
            // `this` 指向当前组件实例
            // console.log(this.count) // => 1
            console.log("11111111111111")
            // 数据属性也可以被更改
            // this.count = 2

            // alert("qqq");
        }
    }
</script>
