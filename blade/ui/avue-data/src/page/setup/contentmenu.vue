<template>
  <div class="contentmenu"
       id="avue-data-menu">
    <div class="contentmenu__item"
         @click="contain.handleParams('lock')">
      <el-icon>
        <el-icon-lock />
      </el-icon>
      {{contain.activeObj.lock?'解锁':'锁定'}}
    </div>
    <div class="contentmenu__item"
         @click="contain.handleParams('display')">
      <el-icon>
        <el-icon-view />
      </el-icon>
      {{contain.activeObj.display?'显示':'隐藏'}}
    </div>
    <div class="contentmenu__item"
         @click="contain.isFolder?handleLogout():handleCompose()">
      <el-icon>
        <el-icon-document-copy />
      </el-icon>
      {{contain.isFolder?'解散':'组合'}}
    </div>
    <template v-if="false">
      <div class="contentmenu__item"
           @click="handlePostionSelect('left')">
        <svg-icon icon-class="pos1" />
        左对齐
      </div>
      <div class="contentmenu__item"
           @click="handlePostionSelect('center')">
        <svg-icon icon-class="pos5" />
        水平对齐
      </div>
      <div class="contentmenu__item"
           @click="handlePostionSelect('right')">
        <svg-icon icon-class="pos3" />
        右对齐
      </div>
      <div class="contentmenu__item"
           @click="handlePostionSelect('top')">
        <svg-icon icon-class="pos4" />
        顶部对齐
      </div>
      <div class="contentmenu__item"
           @click="handlePostionSelect('middle')">
        <svg-icon icon-class="pos2" />
        垂直对齐
      </div>
      <div class="contentmenu__item"
           @click="handlePostionSelect('bottom')">
        <svg-icon icon-class="pos6" />
        底部对齐
      </div>
    </template>
    <div class="contentmenu__item"
         @click="handleDel()">
      <el-icon>
        <el-icon-delete />
      </el-icon>
      删除
    </div>
    <div class="contentmenu__item"
         v-if="!contain.isFolder"
         @click="hover2=false"
         @mouseover="hover2=true"
         @mouseleave="hover2=false">
      <el-icon>
        <el-icon-pointer />
      </el-icon>
      交互
      <el-icon class="contentmenu__list--icon">
        <el-icon-caret-right />
      </el-icon>
      <div class="contentmenu contentmenu__list"
           @click="hover2=false"
           v-if="hover2">
        <div class="contentmenu__item"
             v-if="contain.validProp('dataList')"
             @click="handleOpen('data')">
          <el-icon>
            <el-icon-document-copy />
          </el-icon>
          添加数据
        </div>
        <div class="contentmenu__item"
             @click="handleOpen('transfer')">
          <el-icon>
            <el-icon-edit />
          </el-icon>
          添加交互
        </div>
        <div class="contentmenu__item"
             @click="handleOpen('mouse')">
          <el-icon>
            <el-icon-mouse />
          </el-icon>
          添加事件
        </div>
        <div class="contentmenu__item"
             v-if="contain.validProp('dataList')"
             @click="handleCopyData()"><i class="iconfont icon-copy"></i>
          复制数据配置
        </div>
        <div class="contentmenu__item"
             v-if="contain.validProp('dataList')&&contain.cacheList.data"
             @click="handlePasteData()"><i class="iconfont icon-paste"></i>
          粘贴数据配置
        </div>
      </div>
    </div>
    <div class="contentmenu__item"
         @click="hover=false"
         @mouseover="hover=true"
         @mouseleave="hover=false">
      <el-icon>
        <el-icon-tools />
      </el-icon>
      操作
      <el-icon class="contentmenu__list--icon">
        <el-icon-caret-right />
      </el-icon>
      <div class="contentmenu contentmenu__list"
           @click="hover=false"
           v-if="hover">
        <div class="contentmenu__item"
             @click="handleCopy()"><i class="iconfont icon-copy"></i>
          复制
        </div>
        <div class="contentmenu__item"
             @click="handleShear()"><i class="iconfont icon-shear"></i>
          剪切
        </div>
        <div class="contentmenu__item"
             @click="handleCopy1()"><i class="iconfont icon-copy1"></i>
          拷贝
        </div>
        <div class="contentmenu__item"
             v-if="(contain.cacheList.copy || []).length!=false"
             @click="handlePaste()"><i class="iconfont icon-paste"></i>
          粘贴
        </div>
      </div>
    </div>
    <div class="contentmenu__item"
         @click="hover1=false"
         @mouseover="hover1=true"
         @mouseleave="hover1=false">
      <el-icon>
        <el-icon-position />
      </el-icon>
      位置
      <el-icon class="contentmenu__list--icon">
        <el-icon-caret-right />
      </el-icon>
      <div class="contentmenu contentmenu__list"
           @click="hover1=false"
           v-if="hover1">
        <div class="contentmenu__item"
             @click="handleTop()">
          <el-icon>
            <el-icon-arrow-up />
          </el-icon>
          置顶
        </div>
        <div class="contentmenu__item"
             @click="handleBottom()">
          <el-icon>
            <el-icon-arrow-down />
          </el-icon>
          置底
        </div>
        <div class="contentmenu__item"
             @click="handleStepTop()">
          <el-icon>
            <el-icon-arrow-up />
          </el-icon>
          上移
        </div>
        <div class="contentmenu__item"
             @click="handleStepBottom()">
          <el-icon>
            <el-icon-arrow-down />
          </el-icon>
          下移
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { createFile, uuid } from '@/utils/utils'
export default {
  name: 'contentmenu',
  inject: ["contain"],
  data () {
    return {
      hover: false,
      hover1: false,
      hover2: false,
      selectCount: {
        x1: null,
        x2: null,
        y1: null,
        y2: null
      },
    }
  },
  methods: {
    handleOpen (type) {
      if (type == 'data') {
        this.contain.menuTabs = '1'
        this.contain.handleSetting()
      } else if (type == 'transfer') {
        this.contain.menuTabs = '2'
        this.$nextTick(() => {
          this.contain.$refs.transfer.open()
        })
      } else if (type == 'mouse') {
        this.contain.menuTabs = '3'
        this.$nextTick(() => {
          this.contain.$refs.event.open()
        })
      }
    },
    handlePostionSelect (postion) {
      if (this.contain.isSelectActive) {
        this.contain.activeIndex = null
        this.handleCalcPostionSelect();
      } else {
        this.selectCount = {
          x1: 0,
          x2: this.contain.config.width - 10,
          y1: 0,
          y2: this.contain.config.height - 10
        }
      }
      const x1 = this.selectCount.x1;
      const x2 = this.selectCount.x2;
      const y1 = this.selectCount.y1;
      const y2 = this.selectCount.y2;
      if (postion === 'left') {
        this.handleMoveSelectList(x1, undefined, true, postion);
      } else if (postion === 'center') {
        this.handleMoveSelectList(undefined, y1 + (y2 - y1) / 2, true, postion);
      } else if (postion === 'right') {
        this.handleMoveSelectList(x2, undefined, true, postion);
      } else if (postion === 'top') {
        this.handleMoveSelectList(undefined, y1, true, postion);
      } else if (postion === 'middle') {
        this.handleMoveSelectList(x1 + (x2 - x1) / 2, undefined, true, postion);
      } else if (postion === 'bottom') {
        this.handleMoveSelectList(undefined, y2, true, postion);
      }
    },
    handleMoveSelectList (left, top, type, postion) {
      this.contain.active.forEach(ele => {
        let item = this.contain.findList(ele)
        const component = item.component;
        //水平情况
        if (left) {
          let baseLeft = Number(type ? left : (item.left + left).toFixed(2));
          if (postion === 'right') {
            baseLeft = baseLeft - component.width
          }
          else if (postion === 'center') {
            const obj_center = item.left + component.width / 2;
            baseLeft = item.left + (left - obj_center)
          }
          item.left = baseLeft
        }
        //垂直情况
        if (top) {
          let baseTop = Number(type ? top : (item.top + top).toFixed(2));
          if (postion === 'bottom') {
            baseTop = baseTop - component.height
          }
          else if (postion === 'middle') {
            const obj_middle = item.top + component.height / 2;
            baseTop = item.top + (top - obj_middle)
          }
          item.top = baseTop
        }
      })
    },
    //计算多选状态下的最大边界值
    handleCalcPostionSelect () {
      this.selectCount = {
        x1: null,
        x2: null,
        y1: null,
        y2: null
      }
      this.contain.active.forEach(ele => {
        ele = this.contain.findList(ele)
        const left = ele.left;
        const top = ele.top;
        const width = ele.component.width;
        const height = ele.component.height;
        if (!this.selectCount.x1) {
          this.selectCount = {
            x1: left,
            x2: left + width,
            y1: top,
            y2: top + height
          }
        }
        if (this.selectCount.x1 > left) this.selectCount.x1 = left;
        if (this.selectCount.x2 < left + width) this.selectCount.x2 = left + width;
        if (this.selectCount.y1 > top) this.selectCount.y1 = top;
        if (this.selectCount.y2 < top + height) this.selectCount.y2 = top + height;
      })
    },
    handleStepBottom () {
      this.handleCommon(false, true);
    },
    handleStepTop () {
      this.handleCommon(true, true);
    },
    //文件夹成组逻辑
    handleCompose () {
      this.$confirm(`是否组合所选择的图层?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let folder = createFile()
        folder.group = this.contain.group
        let navList;
        this.contain.active.forEach(ele => {
          let { itemList, itemIndex } = this.contain.findnav(ele);
          let obj = itemList.splice(itemIndex, 1)[0];
          folder.children.push(obj);
          navList = itemList;
        });
        navList.unshift(folder);
        this.contain.handleInitActive();
      }).catch(() => { })
    },
    //文件夹解散逻辑
    handleLogout () {
      let ele = this.contain.activeObj
      this.$confirm(`是否解散【${ele.name}】图层?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let { itemList, itemIndex } = this.contain.findnav(ele.index);
        const list = this.deepClone(ele.children)
        itemList.splice(itemIndex, 1);
        list.forEach(item => itemList.push(item));
        this.contain.handleInitActive();
      }).catch(() => { })
    },
    //删除
    handleDel (tip = true) {
      const callback = () => {
        this.contain.active.forEach(ele => {
          const { itemList, itemIndex } = this.contain.findnav(ele);
          itemList.splice(itemIndex, 1);
        });
        this.contain.handleInitActive();
      }
      if (tip) {
        this.$confirm(`是否删除所选图层?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          callback()
        }).catch(() => { })
      } else {
        callback()
      }

    },
    handleCopyData () {
      let obj = this.contain.activeObj
      let list = ['sql', 'record', 'data', 'dataMethod', 'dataType', 'dataFormatter', 'dataHeader', 'dataQuery', 'wsUrl', 'mqttUrl', 'mqttConfig', 'dataQueryType', 'url']
      this.contain.cacheList.data = {}
      list.forEach(key => {
        this.contain.cacheList.data[key] = obj[key]
      })
      this.$message.success('复制数据配置成功')
    },
    handlePasteData () {
      let obj = this.contain.cacheList.data
      let option = this.contain.cacheList.data
      Object.keys(obj).forEach(key => {
        this.contain.activeObj[key] = option[key]
      })
      this.$message.success('粘贴数据配置成功')
    },
    //剪切
    handleShear (tip = true) {
      this.handleCopy1(false, () => {
        this.handleDel(false)
        if (tip) this.$message.success('剪切组件成功')
      }, false);
    },
    //粘贴
    handlePaste (tip = true) {
      if (!this.contain.cacheList.copy) return
      let active = []
      let { itemList } = this.contain.findnav(this.contain.activeIndex)
      this.contain.cacheList.copy.forEach(ele => {
        active.push(ele(itemList))
      })
      this.contain.cacheList.copy = []
      setTimeout(() => this.contain.selectNav(active))
      if (tip) this.$message.success('粘贴组件成功')
    },
    //拷贝
    handleCopy1 (tip = true, callback) {
      this.handleCopy(fn => {
        this.contain.cacheList.copy = fn
        callback && callback()
        if (tip) this.$message.success('拷贝组件成功')
      })
    },
    //复制
    handleCopy (fn, add = true) {
      let active = []
      let fnList = []
      const setIndex = (list = []) => {
        list.forEach(ele => {
          if (add) {
            let index = uuid();
            ele.index = index;
          }
          ele.group = this.contain.group
          if (ele.children) {
            ele.menu = false
            setIndex(ele.children)
          }
        })
      }
      this.contain.active.forEach(ele => {
        const { item, itemList } = this.contain.findnav(ele);
        let obj;
        if (fn) {
          fnList.push((parentList) => {
            obj = this.deepClone(item);
            obj.index = uuid();
            active.push(obj.index)
            setIndex([obj])
            if (!parentList) parentList = itemList
            parentList.unshift(obj)
            return obj.index
          })
        } else {
          obj = this.deepClone(item);
          obj.index = uuid();
          active.push(obj.index)
          setIndex([obj])
          itemList.unshift(obj)
          this.$message.success('复制组件成功')
        }
      });
      if (fn) {
        fn(fnList)
        return
      } else {
        setTimeout(() => this.contain.selectNav(active))
      }

    },
    // 图层的上下移动方法 
    handleCommon (top = false, step = false) {
      this.contain.active.forEach(ele => {
        let { itemList, itemIndex } = this.contain.findnav(ele);
        let obj = itemList.splice(itemIndex, 1)[0];
        if (step) {
          itemList.splice(top ? (itemIndex - 1) : (itemIndex + 1), 0, obj)
        } else {
          itemList[top ? 'unshift' : 'push'](obj)
        }
      })
    },
    handleTop () {
      this.handleCommon(true);
    },
    handleBottom () {
      this.handleCommon();
    }
  }
}
</script>

<style lang="scss">
.contentmenu {
  width: 180px;
  display: none;
  z-index: 99999;
  list-style: none;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 0;
  background: #232324;
  color: rgba(255, 255, 255, 0.82);
  border-radius: 5px;
}
.contentmenu__list {
  display: block;
  position: absolute;
  top: 0;
  left: 180px;
  &--icon {
    position: absolute;
    right: 2px;
  }
}
.contentmenu__item {
  display: flex;
  align-items: center;
  position: relative;
  z-index: 10000;
  list-style: none;
  padding: 6px 12px;
  cursor: pointer;
  position: relative;
  font-size: 14px;
}
.contentmenu__item:hover {
  background-color: rgba(0, 192, 222, 0.1);
}
.contentmenu__item i {
  margin-right: 5px;
}
</style>