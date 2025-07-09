<template>
  <div class="wrapper"
       :style="wrapperStyle">
    <div class="container"
         :style="containerStyle"
         id="container"
         @mousedown="dragMousedown"
         @mouseup="dragMouseup"
         @mousemove="dragMousemove"
         ref="container">
      <div class="refer-select"
           :style="selectStyle"></div>
      <div class="grade"
           v-if="gradeFlag || contain.config.gradeShow"
           :style="gradeLenStyle"></div>
      <subgroup ref="subgroup"
                :key="reload"
                :nav="contain.list"></subgroup>
    </div>
    <loading ref="loading"></loading>
  </div>
</template>

<script>
import * as mqtt from 'mqtt/dist/mqtt.min';
import { mqttUrl } from '@/config'
import loading from '@/page/group/loading.vue'
import subgroup from '@/page/block/subgroup.vue'
import { loadScript, uuid, getFunction } from '@/utils/utils'
import { config as defaultConfig } from '@/option/config'
import { getObj } from '@/api/visual'
import { getList } from '@/api/glob'
import { getObj as getVersionObj } from '@/api/version';
import components from '@/components/';
import echartComponents from '@/echart/'
window.$loadScript = loadScript;
export default {
  name: 'contents',
  inject: ["contain"],
  props: {
    target: String,
    option: Object,
    id: [String, Number]
  },
  provide () {
    return {
      contain: this.contain,
      container: this
    };
  },
  components: {
    subgroup,
    loading
  },
  data () {
    return {
      reload: Math.random(),
      select: {
        startX: '',
        startY: '',
        endX: '',
        endY: '',
        show: false
      },
      wrapperStyle: {},
      gradeFlag: false,
    }
  },
  computed: {
    selectStyle () {
      let x = this.select.endX - this.select.startX;
      let y = this.select.endY - this.select.startY;
      return {
        top: this.setPx(y > 0 ? this.select.startY : this.select.endY),
        left: this.setPx(x > 0 ? this.select.startX : this.select.endX),
        width: this.setPx(Math.abs(x)),
        height: this.setPx(Math.abs(y)),
        display: this.select.show ? 'block' : 'none'
      }
    },
    stepScale () {
      let result = Number(100 / (this.contain.scale * 100))
      return result
    },
    //计算中央可视化大屏比例
    containerStyle () {
      const widthVal = (this.contain.width / this.contain.config.width)
      const heightVal = (this.contain.height / this.contain.config.height)
      let scaleX = widthVal, scaleY = widthVal;
      if (!this.isBuild) {
        let screen = this.contain.config.screen;
        if (screen == 'x') {
          this.contain.viewStyle = {
            'overflow-y': 'auto'
          }
        } else if (screen == 'y') {
          scaleX = heightVal;
          scaleY = heightVal;
          this.contain.viewStyle = {
            'overflow-x': 'auto'
          }
        } else if (screen == 'xy') {
          scaleX = widthVal;
          scaleY = heightVal;
        }
      } else {
        scaleX = 1;
        scaleY = 1;
      }
      const styles = this.contain.config.styles;
      this.wrapperStyle = {
        filter: styles.show ? `contrast(${styles.contrast || 100}%) saturate(${styles.saturate || 100}%) brightness(${styles.brightness || 100}%) opacity(${styles.opacity || 100}%) grayscale(${styles.grayscale || 0}%) hue-rotate(${styles.hueRotate || 0}deg) invert(${styles.invert || 0}%) blur(${styles.blur}px)` : '',
        width: this.setPx(this.contain.config.width * scaleX),
        height: this.setPx(this.contain.config.height * scaleY),
      }
      return Object.assign({
        transform: `scale(${scaleX}, ${scaleY})`,
        width: this.setPx(this.contain.config.width),
        height: this.setPx(this.contain.config.height),
        backgroundColor: this.contain.config.backgroundColor
      }, (() => {
        if (this.contain.config.backgroundImage) {
          return {
            background: `url(${this.contain.config.backgroundImage}) 0% 0% / 100% 100% rgb(3, 12, 59)`,
          }
        }
        return
      })())
    },
    gradeLenStyle () {
      return {
        backgroundSize: `${this.setPx(this.contain.config.gradeLen)} ${this.setPx(this.contain.config.gradeLen)},${this.setPx(this.contain.config.gradeLen)} ${this.setPx(this.contain.config.gradeLen)}`
      }
    },
    isBuild () {
      return this.$route ? this.$route.name === 'build' : false;
    }
  },
  created () {
    this.initGlob();
  },
  mounted () {
    this.init()
    this.initData();
    this.initFun();
    this.initControl()
    window.onresize = () => {
      this.setScale()
    }
  },
  methods: {
    init () {
      let list = { ...echartComponents, ...components }
      Object.keys(list).map(ele => {
        let component = list[ele];
        this.$component(component.name, component);
      });
    },
    transfer (list, refList, params) {
      list.forEach(ele => {
        if (ele.type == 'params' || ele.type == 'data') {
          let p = {};
          ele.child.forEach(item => {
            p[item.name] = params[item.value]
          })
          ele.index.forEach(index => {
            let { item } = this.contain.findnav(index)
            if (!item) return
            if (item.children) {
              item.children.forEach(child => {
                refList[child.index].updateData(p);
              })
            } else {
              refList[index].updateData(p);
            }

          })
        } else if (ele.type == 'group') {
          window.$glob.group = ele.group
        } else if (ele.type == 'href') {
          if (ele.target) {
            window.open(ele.src)
          } else {
            location.href = ele.src
          }
        } else if (ele.type == 'dialog') {
          let dialogList = document.getElementsByClassName('dialog')
          let dialog = document.createElement('div');
          const zIndex = 10000 + dialogList.length;
          dialog.className = 'dialog'
          dialog.style.zIndex = zIndex
          dialog.innerHTML = `<div class="dialog__title">${ele.title}</div>`
          document.getElementById('container').append(dialog)
          dialog.addEventListener('click', () => {
            ele.index.forEach(index => {
              let dom = this.getListRef(index)
              if (dom) dom.$el.style.display = 'none'
            })
            dialog.remove()
          })
          ele.index.forEach(index => {
            let { item } = this.contain.findnav(index)
            if (!item) return
            const callback = (child) => {
              let styleObj = this.getListRef(index)
              if (styleObj) {
                styleObj = styleObj.$el.style
                styleObj.display = 'block'
                styleObj.zIndex = zIndex + 1;
              }
            }
            if (item.children) {
              item.children.forEach(child => {
                callback(child.index)
              })
            } else {
              callback(index)
            }
          })
        } else if (ele.type == 'display') {
          ele.index.forEach(index => {
            let { item } = this.contain.findnav(index)
            if (!item) return
            const callback = (child) => {
              let styleObj = this.getListRef(index)
              if (styleObj) {
                styleObj = styleObj.$el.style
                if (ele.displayType == '') {
                  if (styleObj.display == 'block' || styleObj.display == '') {
                    styleObj.display = 'none'
                  } else {
                    styleObj.display = 'block'
                  }
                } else {
                  styleObj.display = ele.displayType
                }
              }
            }
            if (item.children) {
              item.children.forEach(child => {
                callback(child.index)
              })
            } else {
              callback(index)
            }
          })
        }
      })
    },
    dragMousedown (e) {
      if (this.contain.isKeysCtrl) {
        this.contain.handleInitActive()
        let rect = e.currentTarget.getBoundingClientRect();
        let offsetX = (e.clientX - rect.left) * this.stepScale;
        let offsetY = (e.clientY - rect.top) * this.stepScale;
        this.select.startX = offsetX
        this.select.startY = offsetY
        this.select.endX = this.select.startX
        this.select.endY = this.select.startY
        this.select.show = true
        e.stopPropagation()
      }
    },
    dragMousemove (e) {
      if (!this.select.show) return
      let rect = e.currentTarget.getBoundingClientRect();
      let offsetX = (e.clientX - rect.left) * this.stepScale;
      let offsetY = (e.clientY - rect.top) * this.stepScale;
      this.select.endX = offsetX
      this.select.endY = offsetY
    },
    dragMouseup (e) {
      if (this.select.show) {
        let selectIndex = []
        this.contain.list.forEach(ele => {
          let x = ele.left >= this.select.startX && ele.left <= this.select.endX
          let y = ele.top >= this.select.startY && ele.top <= this.select.endY
          if (x && y) selectIndex.push(ele.index)
        })
        this.contain.selectNav(selectIndex)
      }
      this.select.show = false
    },
    getTargetDom () {
      let target = this.target || (this.isBuild ? '#section' : 'body')
      return document.querySelector(target)
    },
    initFun () {
      ['handleRefresh', 'getListRef', 'getItemRef'].forEach(ele => {
        this[ele] = this.$refs.subgroup[ele]
      });
    },
    initControl () {
      const client = mqtt.connect(mqttUrl, {
        clientId: uuid() + '_' + this.contain.id
      })
      client.on("connect", () => {
        const control_keys = 'control_' + this.contain.id
        client.subscribe(control_keys, () => {
          console.log(`Subscribe to topic '${control_keys}'`)
        })
        client.on('message', (topic, message) => {
          let data = JSON.parse(message)
          if (data.type == 'group') {
            window.$glob.group = data.id;
          }
        })
      })
    },
    initGlob () {
      getList({
        current: 1,
        size: 100,
      }).then(res => {
        let list = res.data.data.records
        list.forEach(ele => {
          window.$glob[ele.globalKey] = ele.globalValue
        })
      })
    },
    refresh (themeId) {
      if (themeId) {
        let theme = this.contain.config.theme[themeId];
        theme.data.forEach(ele => {
          theme[ele.key] = ele.value
        })
        window.$glob.theme = theme
        this.reload = Math.random();
        this.$nextTick(() => {
          this.initFun()
        })
      }
    },
    //初始化数据
    initData () {
      this.setScale();
      this.$refs.loading.show()
      const query = (this.$route && this.$route.query) || {}
      const id = this.id || query.id || (this.$route && this.$route.params.id)
      const version_query = query.v
      this.contain.id = id;
      const src = query.src
      let contain;
      let config;
      //大屏绘制逻辑
      const draw = () => {
        const next = () => {
          this.contain.setGlobParams()
          this.contain.nav = contain.component
          this.$refs.loading.hide()
        }
        if (this.validatenull(this.contain.config.group)) {
          this.contain.config.group = [{
            name: '主屏幕',
            id: '',
            isname: false
          }]
        }
        if (!this.validatenull(this.contain.config.glob)) {
          let list = this.contain.config.glob;
          list.forEach(ele => {
            window.$glob[ele.key] = ele.value
          })
        }
        let themeId = this.contain.config.themeId
        if (!this.validatenull(themeId)) {
          window.$glob.themeId = themeId
        }
        let mark = this.contain.config.mark;
        if (mark.show && !this.isBuild) {
          this.watermark(Object.assign(mark, {
            fontSize: mark.fontSize + 'px'
          }));
        }
        this.calcData();
        this.setScale();
        //赋值属性
        let before = this.contain.config.before
        if (before) {
          let beforeFun = getFunction(before)(this.contain.config)
          beforeFun.then(() => {
            next()
          }).catch(err => {
            console.log(err)
            next()
          })
        } else {
          next()
        }
      }
      const callback = (config) => {
        contain = {
          config: JSON.parse(config.detail) || {},
          component: JSON.parse(config.component) || []
        }
        this.contain.config = Object.assign({}, defaultConfig, contain.config);
        if (!this.isBuild) {
          const password = this.contain.visual.password
          const status = this.contain.visual.status
          if (status == 0) {
            this.$alert('大屏还没有发布，晚一点再来吧！', '提示', {
              showClose: false,
              center: true,
              showConfirmButton: false,

            });
          } else if (!this.validatenull(password)) {
            this.$prompt('请输入密码', '提示', {
              confirmButtonText: '确定',
              showCancelButton: false,
              showClose: false,
              closeOnClickModal: false,
              inputPattern: new RegExp(password),
              inputErrorMessage: '密码不正确，请重新输入'
            }).then(() => {
              draw();
            })
          } else {
            draw();
          }
        } else {
          draw();
        }
      }
      if (src) {
        axios({
          url: src,
          method: 'get',
        }).then(res => {
          let data = res.data.replace('const option = ', '');
          let config = JSON.parse(data)
          contain = {
            config: config.detail || {},
            component: config.component || []
          }
          document.title = this.$website.title + '-' + contain.config.name;
          this.contain.config = Object.assign({}, defaultConfig, config.detail);
          draw();
        })
      } else if (id) {
        getObj(id).then(res => {
          const data = res.data.data;
          this.contain.obj = data;
          config = data.config;
          this.contain.visual = data.visual;
          document.title = this.$website.title + '-' + data.visual.title
          const version = version_query || data.visual.version
          if (!this.validatenull(version) && !this.isBuild) {
            getVersionObj(version).then(res => {
              const data = res.data.data;
              if (data) {
                let content = data.data
                content = JSON.parse(content)
                callback({
                  detail: JSON.stringify(content.detail),
                  component: JSON.stringify(content.component),
                })
              } else {
                callback(config)
              }
            })
          } else {
            callback(config)
          }
        }).catch((err) => {
          console.log(err)
          contain = {
            config: defaultConfig,
            component: []
          }
          this.contain.config = Object.assign({}, defaultConfig, contain.config);
          draw();
        })
      } else if (this.option) {
        config = this.option;
        contain = {
          config: config.detail || {},
          component: config.component || []
        }
        document.title = this.$website.title + '-' + contain.config.name;
        this.contain.config = Object.assign({}, defaultConfig, config.detail);
        draw();
      } else {
        this.setScale();
      }
    },
    //计算比例
    setScale (width) {
      const thick = this.contain.thick || 0
      this.contain.canvasWidth = this.contain.config.width;
      this.contain.canvasHeight = this.contain.config.height;
      this.contain.width = this.getTargetDom().offsetWidth - thick
      this.contain.height = this.getTargetDom().offsetHeight - thick
      this.contain.scale = this.contain.width / this.contain.config.width - 0.02
      this.$nextTick(() => {
        this.contain.initSize && this.contain.initSize()
      })
    },
    calcData () {
      if (!this.contain.config.mark) this.contain.config.mark = {}
      if (!this.contain.config.query) this.contain.config.query = {}
    }
  }
}
</script>

<style lang="scss">
@import "@/styles/echart.scss";
@import "@/styles/style.scss";
</style>