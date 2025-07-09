<template>
  <el-dialog title="数据库测试连接"
             class="avue-dialog db"
             destroy-on-close
             append-to-body
             :close-on-click-modal="false"
             modal-append-to-body
             v-model="box"
             width="90%">
    <div class="db__box">
      <ul class="db__table">
        <div class="db__title">数据表</div>
        <li v-for="(item,index) in tableList"
            :class="{'is-active':item.name==selectTable}"
            :key="index"
            @click="handleGetColumn(item)">
          <el-tooltip effect="dark"
                      :content="item.name"
                      placement="right">
            <span>{{item.name}}</span>
          </el-tooltip>
        </li>
      </ul>
      <ul class="db__column">
        <div class="db__title">数据字段</div>
        <li v-for="(item,index) in columnList"
            :key="index">
          <el-tooltip effect="dark"
                      :content="item.name"
                      placement="right">
            <span> {{item.name}}</span>
          </el-tooltip>
          <span> {{item.type}}</span>
        </li>
      </ul>
      <div class="db__content">
        <div class="db__input">
          <monaco-editor v-model="sql"
                         language="sql"
                         height="200"></monaco-editor>
          <el-button type="primary"
                     icon="el-icon-thumb"
                     class="db__run"
                     @click="handleRun">运行</el-button>
        </div>
        <div class="db__result">
          <avue-crud style="width:100%;"
                     v-if="data.length!=false"
                     :option="option"
                     :data="data">
          </avue-crud>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import MonacoEditor from '@/page/components/monaco-editor'
import { dynamicSql, getDetail } from "@/api/db";
import crypto from '@/utils/crypto'
export default {
  components: {
    MonacoEditor
  },
  data () {
    return {
      form: {},
      data: [],
      box: false,
      id: null,
      sql: '',
      selectTable: '',
      tableList: [],
      columnList: [],
    }
  },
  computed: {
    option () {
      let column = []
      let obj = this.data[0]
      if (obj) {
        Object.keys(obj).forEach(ele => {
          column.push({
            label: ele,
            prop: ele,
            overHidden: true
          })
        })
      }
      return {
        height: 300,
        menu: false,
        header: false,
        column
      }
    }
  },
  methods: {
    getSql (sql) {
      return crypto.encrypt(JSON.stringify({
        id: this.id,
        sql: sql
      }))
    },
    handleOpen (id) {
      this.id = id;
      this.data = []
      this.sql = ''
      this.selectTable = ''
      this.tableList = []
      this.columnList = []
      getDetail(id).then(res => {
        this.form = res.data.data;
        this.handleGetTable()
        this.box = true
      })
    },
    handleRun () {
      this.data = []
      let sql = this.getSql(this.sql)
      dynamicSql(sql).then(res => {
        let list = res.data.data || []
        this.data = list;
      })
    },
    handleGetColumn (item) {
      this.selectTable = item.name;
      let sqlList = {
        'com.mysql.cj.jdbc.Driver': `SHOW COLUMNS FROM ${this.selectTable}`,
        'com.microsoft.sqlserver.jdbc.SQLServerDriver': '',
        'org.postgresql.Driver': `SELECT column_name, data_type, is_nullable, column_default FROM information_schema.columns WHERE table_name ='${this.selectTable}'`,
        'oracle.jdbc.OracleDriver': ''
      }
      let sql = this.getSql(sqlList[this.form.driverClass])
      dynamicSql(sql).then(res => {
        let list = res.data.data
        this.columnList = list.map(ele => {
          return {
            name: ele.Field,
            type: ele.Type
          }
        })
      })
      this.sql = `select * from ${this.selectTable} limit 0,10`
      this.handleRun()
    },
    handleGetTable () {
      let sqlList = {
        'com.mysql.cj.jdbc.Driver': 'SHOW TABLES',
        'com.microsoft.sqlserver.jdbc.SQLServerDriver': '',
        'org.postgresql.Driver': "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname='public';",
        'oracle.jdbc.OracleDriver': ''
      }
      let sql = this.getSql(sqlList[this.form.driverClass])
      dynamicSql(sql).then(res => {
        let list = res.data.data;
        let result = []
        list.forEach(ele => {
          Object.keys(ele).forEach(key => {
            result.push({
              name: ele[key]
            })
          })
        })
        this.tableList = result
      })
    },
  }
}
</script>

<style lang="scss">
.db {
  .el-dialog__body {
    padding: 0;
  }
  ul {
    box-sizing: border-box;
    position: absolute;
    padding: 20px;
    height: 100%;
    overflow: scroll;
    background-color: #2a2a2b;
    border-right: 2px solid #333;
    .is-active {
      color: #2681ff;
      font-weight: bold;
    }
  }
  li {
    padding-left: 5px;
    font-size: 13px;
    color: #eee;
    list-style: none;
    line-height: 28px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    &:hover {
      cursor: pointer;
    }
  }
  &__box {
    display: flex;
    height: 500px;
    background-color: #2a2a2b;
  }
  &__title {
    margin-bottom: 10px;
    font-weight: bold;
    color: #eee;
  }

  &__run {
    margin-right: 15px;
    float: right;
  }
  &__table {
    left: 0;
    width: 200px;
  }
  &__column {
    left: 200px;
    width: 250px;
    li {
      color: #ccc;
      display: flex;
      justify-content: space-between;
    }
  }
  &__content {
    height: 100%;
    left: 450px;
    position: absolute;
    width: calc(100% - 450px);
    display: flex;
    flex-direction: column;
    background-color: #2a2a2b;
  }
  &__input {
  }
  &__result {
  }
}
</style>