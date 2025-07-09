<template>
  <el-form class="anima"
           label-width="90px"
           label-position="left">

    <el-form-item label="动画名称">
      <avue-input v-model="contain.activeComponent.animated"></avue-input>
      <div>
        <a href="https://www.dowebok.com/demo/2014/98/"
           target="_blank">animated库</a>
      </div>
    </el-form-item>
    <el-form-item label="开启动画">
      <avue-switch v-model="contain.activeComponent.animatedSwitch"></avue-switch>
    </el-form-item>
    <el-form-item label="开启循环">
      <avue-switch v-model="contain.activeComponent.animatedInfinite"></avue-switch>
    </el-form-item>
    <el-form-item label="播放速度">
      <avue-select v-model="contain.activeComponent.animateSpeed"
                   placeholder="请选择播放速度"
                   :dic="dicOption.animateSpeed"></avue-select>
    </el-form-item>
    <el-form-item label="交替方向">
      <avue-select placeholder="请选择交替方向"
                   v-model="contain.activeComponent.animateDirection"
                   :dic="dicOption.animateDirection"></avue-select>
    </el-form-item>
    <el-form-item label="动画时长">
      <el-input placeholder="2"
                v-model="contain.activeComponent.animateDuration">
        <template #append>
          <span>秒</span>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item label="动画延时">
      <el-input placeholder="0"
                v-model="contain.activeComponent.animateDelay">
        <template #append>
          <span>秒</span>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item label-width="0">
      <el-tabs class="menu__tabs"
               stretch
               v-model="tabs">
        <el-tab-pane :name="index"
                     :key="index"
                     v-for="(item,index) in list">
          <template #label>
            <span>{{ item.label }}</span>
          </template>

        </el-tab-pane>
      </el-tabs>
      <div class="anima__list">
        <div class="anima__item"
             @mouseenter="item.hover=true"
             @mouseleave="item.hover=false"
             v-for="(item,index) in list[tabs].list"
             :key="index">
          <el-button :type="contain.activeComponent.animated==item.value?'primary':'info'"
                     @click="contain.activeComponent.animated=item.value">
            <div :class="getClass(item)">
              {{ item.label }}</div>
          </el-button>
        </div>
      </div>
    </el-form-item>
  </el-form>
</template>

<script>
import { dicOption } from '@/option/config'
export default {
  inject: ["contain"],
  data () {
    return {
      dicOption,
      tabs: 0,
      list: [{
        label: '移入',
        list: [{
          label: '渐显',
          value: 'fadeIn',
          hover: false
        }, {
          label: '向右进入',
          value: 'fadeInRight',
          hover: false
        }, {
          label: '向左进入',
          value: 'fadeInLeft',
          hover: false
        }, {
          label: '向上进入',
          value: 'fadeInUp',
          hover: false
        }, {
          label: '向下进入',
          value: 'fadeInDown',
          hover: false
        }, {
          label: '向右长距进入',
          value: 'fadeInLeftBig',
          hover: false
        }, {
          label: '向左长距进入',
          value: 'fadeInRightBig',
          hover: false
        }, {
          label: '向上长距进入',
          value: 'fadeInUpBig',
          hover: false
        }, {
          label: '向下长距进入',
          value: 'fadeInDownBig',
          hover: false
        }, {
          label: '旋转进入',
          value: 'rotateIn',
          hover: false
        }, {
          label: '左顺时针旋转',
          value: 'rotateInDownLeft',
          hover: false
        }, {
          label: '右逆时针旋转',
          value: 'rotateInDownRight',
          hover: false
        }, {
          label: '左逆时针旋转',
          value: 'rotateInUpLeft',
          hover: false
        }, {
          label: '右逆时针旋转',
          value: 'rotateInUpRight',
          hover: false
        }, {
          label: '弹入',
          value: 'bounceIn',
          hover: false
        }, {
          label: '向右弹入',
          value: 'bounceInRight',
          hover: false
        }, {
          label: '向左弹入',
          value: 'bounceInLeft',
          hover: false
        }, {
          label: '向上弹入',
          value: 'bounceInUp',
          hover: false
        }, {
          label: '向下弹入',
          value: 'bounceInDown',
          hover: false
        }, {
          label: '光速从右进入',
          value: 'lightSpeedInRight',
          hover: false
        }, {
          label: '光速从左进入',
          value: 'lightSpeedInLeft',
          hover: false
        }, {
          label: '光速从右进入',
          value: 'lightSpeedOutRight',
          hover: false
        }, {
          label: '光速从左进入',
          value: 'lightSpeedOutRight',
          hover: false
        }, {
          label: 'Y轴旋转',
          value: 'flip',
          hover: false
        }, {
          label: '中心X轴旋转',
          value: 'flipInX',
          hover: false
        }, {
          label: '中心Y轴旋转',
          value: 'flipInY',
          hover: false
        }, {
          label: '左长半径旋转',
          value: 'rollIn',
          hover: false
        }, {
          label: '由小变大进入',
          value: 'rollIn',
          hover: false
        }, {
          label: '左变大进入',
          value: 'zoomInLeft',
          hover: false
        }, {
          label: '右变大进入',
          value: 'zoomInRight',
          hover: false
        }, {
          label: '向上变大进入',
          value: 'zoomInUp',
          hover: false
        }, {
          label: '向下变大进入',
          value: 'zoomInDown',
          hover: false
        }, {
          label: '向右滑动展开',
          value: 'slideInRight',
          hover: false
        }, {
          label: '向左滑动展开',
          value: 'slideInLeft',
          hover: false
        }, {
          label: '向上滑动展开',
          value: 'slideInUp',
          hover: false
        }, {
          label: '向下滑动展开',
          value: 'slideInDown',
          hover: false
        }]
      }, {
        label: '强调',
        list: [{
          label: '弹跳',
          value: 'bounce',
          hover: false
        }, {
          label: '闪烁',
          value: 'flash',
          hover: false
        }, {
          label: '放大缩小',
          value: 'pulse',
          hover: false
        }, {
          label: '快速放大缩小',
          value: 'heartBeat',
          hover: false
        }, {
          label: '放大缩小弹簧',
          value: 'rubberBand',
          hover: false
        }, {
          label: '左右晃动',
          value: 'headShake',
          hover: false
        }, {
          label: '左右扇形摇摆',
          value: 'swing',
          hover: false
        }, {
          label: '放大晃动缩小',
          value: 'tada',
          hover: false
        }, {
          label: '扇形摇摆',
          value: 'wobble',
          hover: false
        }, {
          label: '左右上下晃动',
          value: 'jello',
          hover: false
        }]
      }, {
        label: '退出',
        list: [{
          label: '渐隐',
          value: 'fadeOut',
          hover: false
        }, {
          label: '向左退出',
          value: 'fadeOutLeft',
          hover: false
        }, {
          label: '向右退出',
          value: 'fadeOutRight',
          hover: false
        }, {
          label: '向上退出',
          value: 'fadeOutUp',
          hover: false
        }, {
          label: '向下退出',
          value: 'fadeOutDown',
          hover: false
        }, {
          label: '向左长距退出',
          value: 'fadeOutLeftBig',
          hover: false
        }, {
          label: '向右长距退出',
          value: 'fadeOutRightBig',
          hover: false
        }, {
          label: '向上长距退出',
          value: 'fadeOutUpBig',
          hover: false
        }, {
          label: '向下长距退出',
          value: 'fadeOutDownBig',
          hover: false
        }, {
          label: '旋转退出',
          value: 'rotateOut',
          hover: false
        }, {
          label: '左顺时针旋转',
          value: 'rotateOutDownLeft',
          hover: false
        }, {
          label: '右逆时针旋转',
          value: 'rotateOutDownRight',
          hover: false
        }, {
          label: '左逆时针旋转',
          value: 'rotateOutUpLeft',
          hover: false
        }, {
          label: '右逆时针旋转',
          value: 'rotateOutUpRight',
          hover: false
        }, {
          label: '弹出',
          value: 'bounceOut',
          hover: false
        }, {
          label: '向左弹出',
          value: 'bounceOutLeft',
          hover: false
        }, {
          label: '向右弹出',
          value: 'bounceOutRight',
          hover: false
        }, {
          label: '向上弹出',
          value: 'bounceOutUp',
          hover: false
        }, {
          label: '向下弹出',
          value: 'bounceOutDown',
          hover: false
        }, {
          label: '中心X轴旋转',
          value: 'flipOutX',
          hover: false
        }, {
          label: '中心Y轴旋转',
          value: 'flipOutY',
          hover: false
        }, {
          label: '左长半径旋转',
          value: 'rollOut',
          hover: false
        }, {
          label: '由小变大退出',
          value: 'zoomOut',
          hover: false
        }, {
          label: '左变大退出',
          value: 'zoomOutLeft',
          hover: false
        }, {
          label: '右变大退出',
          value: 'zoomOutRight',
          hover: false
        }, {
          label: '向上变大退出',
          value: 'zoomOutUp',
          hover: false
        }, {
          label: '向下变大退出',
          value: 'zoomOutDown',
          hover: false
        }, {
          label: '向左滑动收起',
          value: 'slideOutLeft',
          hover: false
        }, {
          label: '向右滑动收起',
          value: 'slideOutRight',
          hover: false
        }, {
          label: '向上滑动收起',
          value: 'slideOutUp',
          hover: false
        }, {
          label: '向下滑动收起',
          value: 'slideOutDown',
          hover: false
        }]
      }]
    }
  },
  methods: {
    getClass (item) {
      return 'animated ' + (item.hover ? item.value : '')
    }
  }
}
</script>

<style lang="scss">
.anima {
  &__list {
    display: flex;
    flex-wrap: wrap;
  }
  &__item {
    box-sizing: border-box;
    padding: 5px;
    .el-button {
      padding-left: 0;
      padding-right: 0;
      text-align: center;
      width: 90px;
    }
  }
}
</style>