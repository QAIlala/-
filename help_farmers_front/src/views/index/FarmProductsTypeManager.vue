<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>基本资料</el-breadcrumb-item>
        <el-breadcrumb-item>农产品管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="3">
            <el-button type="primary" @click="handleAdd">添加农产品类型</el-button>
      </el-col>
    </el-row>
    <!--表格-->
    <el-table
    :data="tabledata"
    border
    style="width: 100%">
    <el-table-column
            type="index"
            width="50">
        </el-table-column>
        <el-table-column
            prop="name"
            label="农产品类型名称"
            width="155">
        </el-table-column>
        <el-table-column
            prop="remarks"
            label="备注"
            width="295">
        </el-table-column>
        <el-table-column
            prop="createBy"
            label="创建人"
            width="100">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            width="220">
        </el-table-column>
        <el-table-column
            prop="updateBy"
            label="修改人"
            width="100">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="修改时间"
            width="220">
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除</el-button>
          </template>
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

    <el-dialog title="修改农产品类型信息" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
          <el-form :model="farmForm"  label-width="120px" class="demo-ruleForm">
            <el-form-item label="农产品类型名称" prop="name">
                <el-input v-model="farmForm.name"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="farmForm.remarks"></el-input>
            </el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitUpd">确 定</el-button>
          </el-form>
        </div >
      </el-dialog>

      <el-dialog title="添加农产品" :visible.sync="dialogFormVisible1" width="30%">
        <div class="el-dialog-div">
          <el-form :model="farmForm"  label-width="120px" class="demo-ruleForm">
            <el-form-item label="农产品类型名称" prop="name">
                <el-input v-model="farmForm.name"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="farmForm.remarks"></el-input>
            </el-form-item>
            <el-button @click="dialogFormVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="submitAdd">确 定</el-button>
          </el-form>
        </div >
      </el-dialog>

  </el-card>
</template>

<script>
import Qs from 'qs'
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
      dialogFormVisible: false,
      farmForm: {},
      dialogFormVisible1: false
    }
  },
  created () {
    // 查询首页的数据
    this.getfarmProductsTypes()
  },
  methods: {
    submitAdd () {
      axios({
        method: 'post',
        data: Qs.stringify(this.farmForm),
        url: '/base-farm-products-type/save'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '添加成功'
          })
          this.dialogFormVisible1 = false
          this.getfarmProductsTypes()
        }
      })
      // console.log(Qs.stringify(this.ruleForm))
    },

    submitUpd () {
      let params = {
        params: JSON.stringify(this.farmForm)
      }

      axios({
        method: 'patch',
        data: params,
        url: '/base-farm-products-type/upd'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.dialogFormVisible = false
          this.getfarmProductsTypes()
        }
      })
    },

    handleAdd () {
      this.farmForm = {}
      this.dialogFormVisible1 = true
    },

    handleEdit (row) {
      this.farmForm = row
      this.dialogFormVisible = true
    },
    handleDelete (row) {
      this.$confirm('确认删除？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          method: 'delete',
          url: '/base-farm-products-type/' + row.id
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getfarmProductsTypes()
          } else {
            this.$message({
              type: 'error',
              message: '删除失败!'
            })
          }
        })
      })
    },
    // 根据查询条件查询数据
    async getfarmProductsTypes () {
      let params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/base-farm-products-type/typeList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      console.log(pageno)
      this.search.pageno = pageno
      this.getfarmProductsTypes()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      console.log(pagesize)
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getfarmProductsTypes()
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
