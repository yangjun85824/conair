const { defineConfig } = require('@vue/cli-service')

const BASE_API = "http://localhost:9999"
module.exports = {
  pages: {
    index:{
      entry: "./src/router/index.js",
      template: "./src/views/index.vue",
      filename: "index.html",
      title: "首页"
    },
    dynjava:{
      entry: "./src/router/dyn-java.js",
      template: "./src/views/dyn-java.vue",
      filename: "dyn-java.html",
      title: "动态java"
    }
  }
}
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, // 关闭eslint校验
  // 配置前端服务地址和端口
  devServer: {
    open: true,
		// disableHostCheck: false,
		host: "localhost",
		port: 9527,
		https: false,
    proxy: {
      //和上面自定义的 baseURL 保持一致
      '/ds': {
        target: BASE_API,
        changeOrigin: true,
        pathRewrite: { '^/': '' },
      },
      //
      '/eapi': {
        target: BASE_API,
        changeOrigin: true,
        pathRewrite: { '^/': '' },
      },
      //
      '/dyn-java': {
        target: BASE_API,
        changeOrigin: true,
        pathRewrite: { '^/': '' },
      }
    }
  }
})


