import {
  resolve
} from 'path'
export default {
  //打包index.umd.js的配置
  publicDir: 'access', // 指定public目录的位置
  build: {
    cssCodeSplit: true,
    lib: {
      entry: resolve(__dirname, 'src/page/index.js'),
      name: 'AvueData',
      fileName: 'index',
      formats: ['umd']
    },
    outDir: resolve(__dirname, 'public/lib'),
    assetsDir: "assets",
    rollupOptions: {
      external: ['vue', 'axios', 'AVUE'],
      output: {
        compact: true,
        globals: {
          vue: 'Vue',
          AVUE: 'AVUE',
          axios: 'axios'
        },
      },
    },
  },
}