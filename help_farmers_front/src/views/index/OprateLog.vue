<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>操作日志</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="3">
            <el-button type="primary" @click="handleDelete">批量删除</el-button>
      </el-col>
    </el-row>
    <!--表格-->
    <el-table
    :data="tabledata"
    border
    style="width: 100%"
    :row-key="(row)=>{ return row.id}"
    @selection-change="handleSelectionChange">
    <el-table-column type="selection" :reserve-selection="true"></el-table-column>
        <el-table-column
            type="index"
            width="50">
        </el-table-column>
        <el-table-column
            prop="name"
            label="操作名称"
            width="120">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="操作人"
            width="200">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="操作时间"
            width="220">
        </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="block">
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="search.pageno"
        :page-sizes="[3, 5, 10, 20]"
        :page-size="search.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
        </el-pagination>
    </div>

  </el-card>
</template>

<script>
// import Qs from 'qs'
import axios from '@/utils/request'
export default {
  data () {
    return {
      // 查询条件
      search: {
        pageno: 1,
        pagesize: 10
      },
      total: 0,
      // table数据
      tabledata: [],
      batchPassArr: {}
    }
  },
  created () {
    // 查询首页的数据
    this.getLog()
  },
  methods: {
    // 点击多选
    handleSelectionChange (val) {
      this.multipleSelection = val

      this.batchPassArr = [] // 每次点击需清空原本数组的内容

      this.multipleSelection.map(item => { // 遍历数组，把id存进自定义的数组里
        this.batchPassArr.push(item.id)
      })
      // console.log(this.batchPassArr)
    },
    handleDelete () {
      let arrParams = {
        params: JSON.stringify(this.batchPassArr)
      }
      this.$confirm('确认删除？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          method: 'delete',
          data: arrParams,
          url: '/log/del'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getLog()
          }
        })
      })
    },
    // 根据查询条件查询数据
    async getLog () {
      var params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/log/logList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      console.log(pageno)
      this.search.pageno = pageno
      this.getLog()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      console.log(pagesize)
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getLog()
    }
  }
}
</script>
<style scoped>
    /* .box-card{
        height: 100%;
    } */
    .search{
        margin-bottom: 20px;
    }
    .block{
        margin-top: 20px;
        text-align: center;
    }
</style>
