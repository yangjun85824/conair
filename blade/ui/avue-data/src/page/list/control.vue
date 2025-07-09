<template>
  <div class="control">
    <el-drawer title="屏幕控制器"
               append-to-body
               class="avue-dialog"
               v-model="box"
               direction="rtl">
      <div class="control__content"
           element-loading-background="rgba(0, 0, 0, 0)"
           v-loading="loading">
        <el-input placeholder="请输入大屏ID"
                  v-model="id"
                  class="input-with-select">
          <template #append>
            <el-button @click="handleSearch"
                       icon="el-icon-search"></el-button>
          </template>

        </el-input>
        <div class="control__list"
             v-if="list.length!=false">
          <div class="control__item"
               :class="{'is-active':active==item.id}"
               v-for="(item,index) in list"
               :key="index"
               @click="setGroupId(item)">
            {{item.name}}
          </div>
        </div>
      </div>
    </el-drawer>
  </div>

</template>
<script>
import { getObj } from '@/api/visual'
import { uuid } from '@/utils/utils'
import * as mqtt from 'mqtt/dist/mqtt.min';
import { mqttUrl } from '@/config'
export default {
  data () {
    return {
      active: null,
      box: false,
      loading: false,
      client: null,
      id: '',
      list: [],
    }
  },
  computed: {
    control_keys () {
      return 'control_' + this.id
    }
  },
  methods: {
    handleSearch () {
      this.list = []
      this.loading = true
      getObj(this.id).then(res => {
        this.loading = false
        let data = res.data.data.config
        data = JSON.parse(data.detail);
        this.list = data.group || []
        this.client && this.client.end();
        if (this.list.length != false) this.initControl()
      })
    },
    initControl () {
      this.client = mqtt.connect(mqttUrl, {
        clientId: 'control_' + uuid()
      })
      this.client.on("connect", () => {
        this.client.subscribe(this.control_keys, () => {
          console.log(`Subscribe to topic '${this.control_keys}'`)
        })
      })
    },
    setGroupId (item) {
      this.active = item.id;
      this.client.publish(this.control_keys, JSON.stringify({
        type: 'group',
        id: item.id
      }), { qos: 0 })
      this.$message.success('切换完成')
    }
  }
}
</script>
<style lang="scss" scoped>
.control {
  &__list {
    margin-top: 50px;
  }
  &__item {
    border-radius: 3px;
    padding: 3px 12px;
    box-sizing: border-box;
    margin: 10px 0;
    color: #333;
    line-height: 40px;
    background-color: rgba(38, 129, 255, 0.1);
    &:hover {
      cursor: pointer;
    }
    &.is-active {
      background-color: rgba(38, 129, 255, 0.3);
      font-weight: bold;
    }
  }
  &__content {
    padding: 20px;
  }
}
</style>