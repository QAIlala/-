<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>进销存管理</el-breadcrumb-item>
        <el-breadcrumb-item>入库管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="2">
            <el-button type="primary" @click="handleAdd">农产品入库</el-button>
      </el-col>
      <el-col :span="3">
            <el-button type="primary" @click="delbatch">批量删除</el-button>
      </el-col>
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
    style="width: 100%"
    :row-key="(row)=>{ return row.id}"
    @selection-change="handleSelectionChange"
    >
    <el-table-column type="selection" :reserve-selection="true"></el-table-column>
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
            prop="batchNum"
            label="批号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="wname"
            label="存储仓库"
            width="120">
        </el-table-column>
        <el-table-column
            prop="inPrice"
            label="入库价格"
            width="120">
        </el-table-column>
        <el-table-column
          prop="snum"
          label="入库数量"
          width="120"
        >
        </el-table-column>
        <el-table-column
            prop="unit"
            label="单位"
            width="80">
        </el-table-column>
        <el-table-column
            prop="stotalPrice"
            label="合计价格"
            width="120">
        </el-table-column>
        <el-table-column
            prop="speople"
            label="入库人"
            width="120">
        </el-table-column>
        <el-table-column
            prop="sphone"
            label="入库人联系方式"
            width="120">
        </el-table-column>
        <el-table-column
            prop="remarks"
            label="备注"
            width="220">
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
        <el-table-column label="操作" width="90" fixed="right">
          <template slot-scope="scope">
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

    <el-dialog title="农产品入库" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
          <el-form :model="farmForm"  label-width="120px" :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item label="请选择农产品" prop="farmProductsNumber">
              <el-select v-model="farmForm.farmProductsNumber" filterable  placeholder="请选择农产品" required>
                    <el-option
                    v-for="item in farmProducts"
                    :key="item.number"
                    :label="item.name"
                    :value="item.number">
                    </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="请选择仓库" prop="wId">
              <el-select v-model="farmForm.wId" filterable  placeholder="请选择仓库" required>
                    <el-option
                    v-for="item in warehouses"
                    :key="item.wid"
                    :label="item.wname"
                    :value="item.wid">
                    </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="批号" prop="batchNum" required>
                <el-input v-model="farmForm.batchNum"></el-input>
            </el-form-item>
            <el-form-item label="入库数量" prop="sNum" required>
                <el-input v-model="farmForm.sNum"></el-input>
            </el-form-item>
            <el-form-item label="入库人" prop="sPeople" required>
                <el-input v-model="farmForm.sPeople"></el-input>
            </el-form-item>
            <el-form-item label="入库人联系方式" label-width="130px" prop="sPhone" required>
                <el-input v-model="farmForm.sPhone"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="farmForm.remarks"></el-input>
            </el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitAdd">确 定</el-button>
          </el-form>
        </div >
      </el-dialog>
  </el-card>
</template>

<script>
import Qs from 'qs'
import store from '@/store'
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
      // 农产品列表
      farmProducts: {},
      // 仓库列表
      warehouses: {},
      // table数据
      tabledata: [],
      dialogFormVisible: false,
      farmForm: {},
      multipleSelection: [],
      batchPassArr: [],
      rules: {
        farmProductsNumber: [
          { required: true, message: '请选择农产品', trigger: 'change' }
        ],
        wId: [
          { required: true, message: '请选择仓库', trigger: 'change' }
        ],
        batchNum: [
          { required: true, message: '请填写批号', trigger: 'change' }
        ],
        sNum: [
          { required: true, message: '请填写入库数量', trigger: 'change' }
        ],
        sPeople: [
          { required: true, message: '请填写入库人', trigger: 'change' }
        ],
        sPhone: [
          { required: true, message: '请填写入库人联系方式', trigger: 'change' }
        ]
      },
      user: {}
    }
  },
  created () {
    // 查询首页的数据
    this.init()
    this.getStorage()
    this.getFarmProducts()
    this.getWarehouses()
  },
  methods: {
    async init () {
      this.user = store.getters.getUserInfo
    },
    async getFarmProducts () {
      const res = await axios({
        method: 'get',
        url: '/base-farm-products/getAll'
      })
      this.farmProducts = res.data
    },
    async getWarehouses () {
      const res = await axios({
        method: 'get',

        url: '/jxc-warehouse/getAll'
      })
      this.warehouses = res.data
    },
    // 点击多选
    handleSelectionChange (val) {
      this.multipleSelection = val

      this.batchPassArr = [] // 每次点击需清空原本数组的内容

      this.multipleSelection.map(item => { // 遍历数组，把id存进自定义的数组里
        this.batchPassArr.push(item.id)
      })
      // console.log(this.batchPassArr)
    },
    submitAdd () {
      this.$refs['ruleForm'].validate((valid) => {
        // 验证通过才提交
        if (valid) {
          axios({
            method: 'post',
            data: Qs.stringify(this.farmForm),
            url: '/jxc-storage/save'
          }).then(jsondata => {
            if (jsondata.code === '200') {
              this.$message({
                type: 'success',
                message: '添加成功'
              })
              this.dialogFormVisible = false
              this.getStorage()
            }
          })
          // console.log(Qs.stringify(this.ruleForm))
        } else {
          return false
        }
      })
    },
    handleAdd () {
      this.farmForm = {}
      this.farmForm.sPeople = this.user.trueName
      this.farmForm.sPhone = this.user.phone
      this.dialogFormVisible = true
    },
    delbatch () {
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
          url: '/jxc-storage/del'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getStorage()
          } else {
            this.$message({
              type: 'error',
              message: '删除失败!'
            })
          }
        })
      })
    },
    handleDelete (row) {
      this.batchPassArr = []
      this.batchPassArr.push(row.id)
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
          url: '/jxc-storage/del'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getStorage()
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
    async getStorage () {
      let params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/jxc-storage/storageList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      this.search.pageno = pageno
      this.getStorage()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getStorage()
    },
    // 点击查询按钮
    clicksearch () {
      this.search.pageno = 1
      // this.search.pagesize = pagesize
      this.getStorage()
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
