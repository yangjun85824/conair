<template>
  <el-container class="index gpt">
    <el-aside class="left"
              v-if="this.validatenull(type)">
      <div class="chat-list">
        <div class="chat-title">智能机器人</div>
        <div class="chat-item"
             :class="{'is-active':index==select}"
             @click="handleSelect(index)"
             v-for="(item,index) in list"
             :key="index">
          {{item.name}}
        </div>
        <div class="chat-item is-active"
             @click="box=true">
          <el-icon>
            <el-icon-plus></el-icon-plus>
          </el-icon>
          AI配置
        </div>
      </div>
    </el-aside>
    <el-main class="main">
      <el-container class="chat-window">
        <div class="messages"
             ref="messageContainer">
          <div v-for="(msg, index) in messages"
               :key="index"
               class="message"
               :class="{ 'message-right': msg.username === currentUser, 'message-left': msg.username !== currentUser }">
            <img :src="msg.avatar"
                 class="avatar" />
            <div class="message-content">
              <div class="username">{{ msg.username }}</div>
              <div class="message-bubble">
                <i class="message-copy el-icon-copy-document"
                   @click="handleCopy(msg.text)"></i>
                <p v-if="msg.username === currentUser">{{ msg.text }}</p>
                <avue-highlight v-else
                                :value="extractContent('```'+msg.text,obj.temp,'```') "
                                height="400"></avue-highlight>
              </div>
              <span class="timestamp">{{ msg.time }}</span>
            </div>
          </div>
          <div v-loading="loading"></div>
        </div>
        <ul class="exam-list">
          <template v-for="(item,index) in obj.exam">
            <li v-if="item"
                @click="sendMessage(item)"
                :key="index">
              {{ item }}
            </li>
          </template>

        </ul>
        <div class="input-area">
          <el-input type="textarea"
                    v-model="newMessage"
                    :rows="5"
                    :disabled="loading"
                    @keyup.enter="sendMessage"
                    :placeholder="placeholder" />
          <i class="el-icon-position button"
             :disabled="loading"
             @click="sendMessage()"></i>
        </div>
      </el-container>
    </el-main>

    <el-drawer title="AI配置"
               v-model="box"
               class="avue-dialog"
               size="40%">
      <avue-crud ref="crud"
                 :option="option"
                 v-model:page="page"
                 @row-save="rowSave"
                 @row-update="rowUpdate"
                 @row-del="rowDel"
                 @on-load="onLoad"
                 v-loading="loading"
                 v-bind="$loadingParams"
                 :data="data">
      </avue-crud>
    </el-drawer>
  </el-container>

</template>

<script>
import dayjs from 'dayjs'
import store2 from 'store2'
const format = 'YYYY-MM-DD HH:mm:ss'
//AI密钥申请教程：https://www.yuque.com/smallwei/avue-data/lhvzflu38rwvyy50
const baseUrl = 'https://api.siliconflow.cn';
const authorization = 'authorization'
import { getList, add, update, remove } from "@/api/ai";
export default {
  props: {
    type: Number
  },
  data () {
    return {
      data: [],
      box: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      option: {
        height: 'auto',
        calcHeight: 40,
        index: true,
        labelWidth: 110,
        column: [{
          label: '名称',
          prop: 'name',
          rules: [{
            required: true,
            message: "请输入名称",
            trigger: "blur"
          }]
        }, {
          label: '使用关键字',
          prop: 'idx'
        }, {
          label: '模板关键字',
          prop: 'temp',
          rules: [{
            required: true,
            message: "请输入模板关键字",
            trigger: "blur"
          }]
        }, {
          label: '输入提示',
          prop: 'placeholder',
          hide: true
        }, {
          label: '例子',
          prop: 'exam',
          span: 24,
          hide: true,
          type: 'array',
          separator: '|',
          dataType: 'string'
        }, {
          label: '规则',
          prop: 'rules',
          span: 24,
          hide: true,
          type: 'array',
          separator: '|',
          dataType: 'string'
        }]
      },
      select: '',
      loading: false,
      list: [],
      messages: [],
      newMessage: '',
      currentUser: 'avue-data',
      currentUserAvatar: 'https://avuejs.com/images/logo.png',
    };
  },
  computed: {
    placeholder () {
      return this.obj.placeholder
    },
    storeName () {
      return 'chatgpt' + this.obj.idx
    },
    obj () {
      return this.list[this.select] || {}
    }
  },
  created () {
    this.getList(() => {
      if (!this.validatenull(this.type)) {
        const index = this.list.findIndex(ele => ele.idx === this.type)
        this.select = index;
      } else {
        this.select = 0;
        this.messages = store2.get(this.storeName) || []
      }
      this.scrollToBottom();
    })
  },
  methods: {
    getList (callback) {
      getList({
        current: 1,
        size: 99
      }).then(res => {
        let data = res.data.data.records
        data.forEach(ele => {
          ele.exam = ele.exam.split('|')
          ele.rules = ele.rules.split('|')
        })
        this.list = data;
        callback()
      })
    },
    rowSave (row, done, loading) {
      add(row).then(() => {
        this.onLoad();
        this.$message({
          type: "success",
          message: "操作成功!"
        });
        done();
      }).catch(err => {
        loading()
      });
    },
    rowUpdate (row, index, done, loading) {
      if (this.vaildData(index) && this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        done();
        return false;
      }
      update(row).then(() => {
        done();
        this.onLoad();
        this.$message({
          type: "success",
          message: "操作成功!"
        });
      }).catch(err => {
        loading()
      })
    },
    rowDel (row, index) {
      if (this.vaildData(index) && this.$website.isDemo) {
        this.$message.error(this.$website.isDemoTip)
        return false;
      }
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          return remove(row.id);
        })
        .then(() => {
          this.onLoad();
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        });

    },
    handleSelect (index) {
      this.select = index
      this.messages = store2.get(this.storeName) || []
      this.scrollToBottom();
    },
    onLoad () {
      this.loading = true
      this.getList()
      getList({
        current: this.page.currentPage,
        size: this.page.pageSize,
      }).then(res => {
        this.loading = false
        const data = res.data.data;
        let records = data.records
        this.page.total = data.total;
        this.data = records;
      });
    },
    getModeList () {
      const options = {
        method: 'GET',
        headers: {
          authorization: authorization
        },
      };
      fetch(baseUrl + '/v1/models', options)
        .then(response => response.json())
        .then(response => {
        })
        .catch(err => console.error(err));
    },
    extractContent (str, startDelimiter, endDelimiter) {
      const startIndex = str.indexOf(startDelimiter);
      if (startIndex === -1) return ''; // 如果开始字符串未找到，返回空字符串

      const endIndex = str.indexOf(endDelimiter, startIndex + startDelimiter.length);
      if (endIndex === -1) return ''; // 如果结束字符串未找到，返回空字符串

      return str.substring(startIndex + startDelimiter.length, endIndex);
    },
    getMessage (msg, callback) {
      this.$emit('start')
      let messageList = this.messages
      if (messageList.length > 9) {
        this.$message.warning('每人只能体验3条对话记录')
        this.loading = false;
        return
      }
      messageList.forEach(ele => {
        ele.role = ele.username == this.currentUser ? 'user' : 'assistant';
        ele.content = ele.text
      })
      messageList[messageList.length - 1].content = messageList[messageList.length - 1].content + '遵守以下规则:' + this.obj.rules.join(',')
      const options = {
        method: 'POST',
        headers: {
          accept: 'application/json',
          'content-type': 'application/json',
          authorization: authorization
        },
        body: JSON.stringify({
          model: 'deepseek-ai/DeepSeek-V2-Chat',
          messages: messageList,
          max_tokens: 4096
        })
      };

      fetch(baseUrl + '/v1/chat/completions', options)
        .then(response => response.json())
        .then(response => {
          let message = response.choices[0].message.content
          let code = this.extractContent(message, '```' + this.obj.temp, '```')
          callback(response.choices[0].message.content)
          this.$emit('code', code.trim())
        })
        .catch(err => {
          console.error(err)
          this.loading = false;
        });
    },
    handleCopy (data) {
      this.$Clipboard({
        text: data
      }).then(() => {
        this.$message.success('复制成功')
      }).catch(() => {
        this.$message.error('复制失败')
      });
    },
    sendMessage (msg, type) {
      if (msg) this.newMessage = msg;
      if (type) {
        this.messages = []
        this.select = type;
      }
      if (this.newMessage.trim() === '') return;
      this.loading = true
      this.messages.push({
        username: this.currentUser,
        text: this.newMessage,
        time: dayjs().format(format),
        avatar: this.currentUserAvatar,
      });
      this.getMessage(this.newMessage, (msg) => {
        this.loading = false;
        this.messages.push(
          { username: '智能助手AI', text: msg, time: dayjs().format(format), avatar: '/img/ai.svg' },
        )
        if (this.validatenull(this.type)) {
          store2.set(this.storeName, this.messages)
        }
        this.scrollToBottom();
      })

      this.newMessage = '';
      if (this.validatenull(this.type)) {
        store2.set(this.storeName, this.messages)
      }
      this.$nextTick(() => {
        this.scrollToBottom();
      })
    },
    scrollToBottom () {
      this.$nextTick(() => {
        const messageContainer = this.$refs.messageContainer;
        if (messageContainer) messageContainer.scrollTop = messageContainer.scrollHeight;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.gpt {
  min-width: 600px;
  min-height: 400px;
}
.exam-list {
  padding: 5px 10px;
  font-size: 12px;
  li {
    width: 250px;
    margin: 0 5px 5px 0;
    border: 1px solid #333;
    padding: 8px 15px;
    color: #eee;
    cursor: pointer;
    list-style: none;
  }
}
.chat-title {
  font-size: 30px;
  font-weight: bold;
  background-image: linear-gradient(120deg, #54b6d0 16%, #3f8bdb, #3f8bdb);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-weight: bold;
  padding: 20px 0;
  text-align: center;
}
.chat-list {
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}
.chat-item {
  margin: 8px 5px;
  padding: 10px 10px;
  text-align: center;
  border: 1px solid #2d2d2d;
  color: rgba(255, 255, 255, 0.82);
  border-radius: 5px;
  font-size: 12px;
  cursor: pointer;
  &.is-disabled {
    color: #eee;
  }
  &.is-active {
    border-color: #2681ff;
    color: #2681ff;
  }
}
.chat-window {
  width: 100%;
  margin: 0 auto;
  border: 1px solid #2d2d2d;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.messages {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  padding-top: 40px;
  border-bottom: 1px solid #2d2d2d;
}

.message {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10px;
}

.message-left {
  justify-content: flex-start;
}

.message-right {
  justify-content: flex-end;
}

.avatar {
  padding: 5px;
  box-sizing: border-box;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  margin-right: 10px;
  border: 1px solid #2d2d2d;
  background-color: #fff;
}

.message-content {
  max-width: 70%;
}

.message-bubble {
  position: relative;
  background-color: #36373b;
  color: rgba(255, 255, 255, 0.82);
  border-radius: 10px;
  padding: 10px;
  font-size: 12px;
}
.message-copy {
  position: absolute;
  right: 5px;
  top: -15px;
  font-size: 12px;
  cursor: pointer;
}

.message-right .message-bubble {
  background-color: #0084ff;
  color: white;
}

.message-right .avatar {
  order: 2;
  margin-left: 10px;
  margin-right: 0;
}

.message-right .message-content {
  align-items: flex-end;
}

.username {
  font-weight: bold;
  color: #fff;
  margin-bottom: 8px;
}

.timestamp {
  display: block;
  font-size: 0.8em;
  color: gray;
  margin-top: 5px;
}

.input-area {
  display: flex;
  align-items: center;
  padding: 10px;
}

.button {
  padding: 0 20px;
  font-size: 30px;
  color: #fff;
  cursor: pointer;
}
</style>