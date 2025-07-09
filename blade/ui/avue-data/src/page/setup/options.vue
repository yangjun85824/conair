<template>
  <div>
    <el-form-item label="水平对齐">
      <ul class="menu__button">
        <li @click="contain.$refs.contentmenu.handlePostionSelect('left')">
          <el-tooltip content="左对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos1" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.$refs.contentmenu.handlePostionSelect('middle')">
          <el-tooltip content="水平对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos2" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.$refs.contentmenu.handlePostionSelect('right')">
          <el-tooltip content="右对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos3" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.activeComponent.rotateX=contain.activeComponent.rotateX==180?0:180">
          <el-tooltip content="水平翻转"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos8" />
            </el-button>
          </el-tooltip>
        </li>
      </ul>
    </el-form-item>
    <el-form-item label="垂直对齐">
      <ul class="menu__button">
        <li @click="contain.$refs.contentmenu.handlePostionSelect('top')">
          <el-tooltip content="顶部对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos4" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.$refs.contentmenu.handlePostionSelect('center')">
          <el-tooltip content="垂直对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos5" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.$refs.contentmenu.handlePostionSelect('bottom')">
          <el-tooltip content="底部对齐"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos6" />
            </el-button>
          </el-tooltip>
        </li>
        <li @click="contain.activeComponent.rotateY=contain.activeComponent.rotateY==180?0:180">
          <el-tooltip content="垂直翻转"
                      placement="top">
            <el-button>
              <svg-icon icon-class="pos7" />
            </el-button>
          </el-tooltip>
        </li>
      </ul>
    </el-form-item>
    <el-form-item label="基础">
      <div class="flex">
        <avue-input-number style="width:130px"
                           v-model="contain.activeObj.left"></avue-input-number>
        &nbsp;x&nbsp;
        <avue-input-number style="width:130px"
                           v-model="contain.activeObj.top"></avue-input-number>
      </div>
    </el-form-item>
    <el-form-item label="尺寸">
      <div class="flex">
        <avue-input-number style="width:130px"
                           v-model="contain.activeComponent.width"></avue-input-number>
        &nbsp;x&nbsp;
        <avue-input-number style="width:130px"
                           v-model="contain.activeComponent.height"></avue-input-number>
      </div>
    </el-form-item>
    <el-form-item label="字体">
      <avue-input v-model="contain.activeComponent.fontFamily"
                  placeholder="输入字体库的url">
      </avue-input>
    </el-form-item>
    <el-collapse accordion>
      <el-collapse-item title="比例">
        <el-form-item label="缩放">
          <avue-slider v-model="contain.activeComponent.scale"
                       :min="0"
                       :max="10"
                       :step="0.1"></avue-slider>
        </el-form-item>
        <el-form-item label="透视">
          <avue-slider v-model="contain.activeComponent.perspective"
                       :max="1000"></avue-slider>
        </el-form-item>
        <el-form-item label="X旋转度">
          <avue-slider v-model="contain.activeComponent.rotateX"
                       :min="-360"
                       :max="360"></avue-slider>
        </el-form-item>
        <el-form-item label="Y旋转度">
          <avue-slider v-model="contain.activeComponent.rotateY"
                       :min="-360"
                       :max="360"></avue-slider>
        </el-form-item>
        <el-form-item label="Z旋转度">
          <avue-slider v-model="contain.activeComponent.rotateZ"
                       :min="-360"
                       :max="360"></avue-slider>
        </el-form-item>
      </el-collapse-item>
      <el-collapse-item title="滤镜">
        <el-form-item label="对比度">
          <avue-slider v-model="contain.activeComponent.contrast"
                       :max="400"></avue-slider>
        </el-form-item>
        <el-form-item label="饱和度">
          <avue-slider v-model="contain.activeComponent.saturate"
                       :max="400"></avue-slider>
        </el-form-item>
        <el-form-item label="亮度">
          <avue-slider v-model="contain.activeComponent.brightness"
                       :max="400"></avue-slider>
        </el-form-item>
        <el-form-item label="透明度">
          <avue-slider v-model="contain.activeComponent.opacity"
                       :max="100"></avue-slider>
        </el-form-item>
        <el-form-item label="灰度">
          <avue-slider v-model="contain.activeComponent.grayscale"
                       :max="400"></avue-slider>
        </el-form-item>
        <el-form-item label="色相">
          <avue-slider v-model="contain.activeComponent.hueRotate"
                       :max="1000"></avue-slider>
        </el-form-item>
        <el-form-item label="反转">
          <avue-slider v-model="contain.activeComponent.invert"
                       :max="400"></avue-slider>
        </el-form-item>
        <el-form-item label="模糊">
          <avue-slider v-model="contain.activeComponent.blur"
                       :min="0"
                       :max="100"></avue-slider>
        </el-form-item>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  inject: ["contain"],
}
</script>

<style>
</style>