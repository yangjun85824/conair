const { defineConfig } = require('@vue/cli-service')
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
      '/api': {
        target: 'http://localhost:4567',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
      //
      '/eapi': {
        target: 'http://localhost:8003',
        changeOrigin: true,
        pathRewrite: { '^/eapi': '' },
      }
    }
  },
})


