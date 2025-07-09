import {
  defineConfig,
  loadEnv
} from 'vite'
import {
  resolve
} from 'path'
import libConfig from './lib.config';
import createVitePlugins from './vite/plugins'
// https://vitejs.dev/config/
export default ({
  mode,
  command
}) => {
  const env = loadEnv(mode, process.cwd())
  const {
    VITE_APP_BASE,
    VITE_APP_ENV
  } = env
  return defineConfig({
    ...(() => {
      if (VITE_APP_ENV == 'lib') {
        return libConfig
      }
      return {}
    })(),
    base: VITE_APP_BASE,
    server: {
      port: 8080,
      proxy: {
        '/api': {
          target: 'http://localhost:5100/sip/blade/v3/',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    },
    resolve: {
      alias: {
        'vue': 'vue/dist/vue.esm-bundler.js',
        '~': resolve(__dirname, './'),
        "@": resolve(__dirname, "./src"),
        "components": resolve(__dirname, "./src/components"),
        "styles": resolve(__dirname, "./src/styles"),
        "utils": resolve(__dirname, "./src/utils"),
      }
    },
    plugins: createVitePlugins(env, command === 'build')
  })
}