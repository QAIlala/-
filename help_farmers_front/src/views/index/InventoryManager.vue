<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>进销存管理</el-breadcrumb-item>
        <el-breadcrumb-item>库存管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="5">
            <el-input
                placeholder="请输入农产品编号"
                v-model="search.farmProductsNumber">
            </el-input>
        </el-col>
        <el-col :span="5">
            <el-input
                placeholder="请输入农产品名称"
                v-model="search.farmProductsName">
            </el-input>
        </el-col>
        <el-col :span="3">
            <el-button type="primary" @click="clicksearch">查询</el-button>
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
            prop="farmProductsNumber"
            label="农产品编号"
            width="80">
        </el-table-column>
        <el-table-column
            prop="farmProductsName"
            label="农产品名称"
            width="120">
        </el-table-column>
        <el-table-column
            prop="wname"
            label="仓库名称"
            width="120">
        </el-table-column>
        <el-table-column
          prop="iyNum"
          label="库存总数量"
          width="120"
          :filters="[{ text: '库存充足', value: '0' }, { text: '库存不足', value: '1' }]"
          :filter-method="filterTag"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.warningValue > scope.row.iyNum ? 'danger' : 'success'"
              disable-transitions>{{ scope.row.iyNum }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="unit"
            label="单位"
            width="80">
        </el-table-column>
        <el-table-column
            prop="warningValue"
            label="预警值"
            width="120">
        </el-table-column>
        <el-table-column
            prop="createBy"
            label="创建人"
            width="100">
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            width="180">
        </el-table-column>
        <el-table-column
            prop="updateBy"
            label="修改人"
            width="100">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="修改时间"
            width="180">
        </el-table-column>
        <el-table-column label="操作" width="90" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)">编辑</el-button>
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

    <el-dialog title="修改预警值" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
          <el-form :model="farmForm"  label-width="100px" class="demo-ruleForm">
            <el-form-item label="预警值" prop="warningValue">
                <el-input v-model="farmForm.warningValue"></el-input>
            </el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitUpd">确 定</el-button>
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
        farmProductsNumber: '',
        farmProductsName: '',
        pageno: 1,
        pagesize: 10
      },
      total: 0,
      // 角色列表
      types: [],
      // table数据
      tabledata: [],
      dialogFormVisible: false,
      farmForm: {},
      updForm: {}
    }
  },
  created () {
    // 查询首页的数据
    this.getInventory()
  },
  methods: {
    filterTag (value, row) {
      let flag = '0'
      if (row.warningValue > row.iyNum) {
        flag = '1'
      }
      return flag === value
    },
    submitUpd () {
      this.updForm.id = this.farmForm.id
      this.updForm.warningValue = this.farmForm.warningValue
      axios({
        method: 'patch',
        data: Qs.stringify(this.updForm),
        url: '/jxc-inventory/upd'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.dialogFormVisible = false
          this.getInventory()
        }
      })
    },
    handleEdit (row) {
      this.farmForm = row
      this.dialogFormVisible = true
    },
    // 根据查询条件查询数据
    async getInventory () {
      let params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/jxc-inventory/inventoryList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      this.search.pageno = pageno
      this.getInventory()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getInventory()
    },
    // 点击查询按钮
    clicksearch () {
      this.search.pageno = 1
      // this.search.pagesize = pagesize
      this.getInventory()
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
