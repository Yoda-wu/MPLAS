const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port:7000,
    proxy:{
      '/api': {
          target: 'http://43.139.104.217:8080/',// 后端接口
          changeOrigin: true, // 是否跨域
          pathRewrite: {
            '/api': ''
          }
        }
      }
  }
})

 
