<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>设置</el-breadcrumb-item>
        <el-breadcrumb-item>系统设置</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="3">
            <el-button type="primary" @click="handleAdd">添加用户</el-button>
      </el-col>
      <el-col :span="3">
            <el-input
                placeholder="请输入用户名"
                v-model="search.userName">
            </el-input>
        </el-col>
        <el-col :span="3">
            <el-input
                placeholder="请输入真实姓名"
                v-model="search.trueName">
            </el-input>
        </el-col>
        <el-col :span="5">
            <el-input
                placeholder="请输入电话"
                v-model="search.phone">
            </el-input>
        </el-col>
        <el-col :span="3">
            <el-select v-model="search.roleId" placeholder="请选择角色">
                <el-option
                    label=""
                    value=""
                >
                </el-option>
                <el-option
                v-for="item in roles"
                :key="item.id"
                :label="item.name"
                :value="item.id">
                </el-option>
            </el-select>
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
            prop="userName"
            label="用户名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="trueName"
            label="真实姓名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="roleName"
            label="角色"
            width="80">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话"
            width="130">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址"
            width="220">
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
        <el-table-column
          prop="banFlag"
          label="状态"
          width="100"
          :filters="[{ text: '启用', value: '0' }, { text: '禁用', value: '1' }]"
          :filter-method="filterTag"
          >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.banFlag === '1' ? 'danger' : 'success'"
              disable-transitions>{{ scope.row.banFlag === '0' ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除</el-button>
              <el-button
              size="mini"
              type="warning"
              @click="resetPass(scope.row)">重置密码</el-button>
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

    <el-dialog title="修改用户信息" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="userName">
                <el-input v-model="ruleForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="真实姓名" prop="trueName">
                <el-input v-model="ruleForm.trueName"></el-input>
            </el-form-item>
            <el-form-item label="电话号码" prop="phone">
                <el-input v-model="ruleForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
                <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="ruleForm.remarks"></el-input>
            </el-form-item>
            <el-form-item label="启用/禁用" prop="banFlag">
              <el-switch
                v-model="ruleForm.banFlag"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="0"
                inactive-value="1">
              </el-switch>
            </el-form-item>
            <el-form-item label="角色" prop="roleName">
              <el-select v-model="ruleForm.roleId" filterable  placeholder="请选择角色">
                <el-option
                      :label=ruleForm.roleName
                      :value=ruleForm.roleId
                  >
                  </el-option>
                  <el-option
                      label=""
                      value=""
                  >
                  </el-option>
                  <el-option
                  v-for="item in roles"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  </el-option>
              </el-select>
            </el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitUpd">确 定</el-button>
          </el-form>
        </div >
      </el-dialog>

      <el-dialog title="添加用户" :visible.sync="dialogFormVisible1" width="30%">
        <div class="el-dialog-div">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="userName">
                <el-input v-model="ruleForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="真实姓名" prop="trueName">
                <el-input v-model="ruleForm.trueName"></el-input>
            </el-form-item>
            <el-form-item label="电话号码" prop="phone">
                <el-input v-model="ruleForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
                <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="ruleForm.remarks"></el-input>
            </el-form-item>
            <el-form-item label="启用/禁用" prop="banFlag">
              <el-switch
                v-model="ruleForm.banFlag"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="0"
                inactive-value="1">
              </el-switch>
            </el-form-item>
            <el-form-item label="角色" prop="roleName">
              <el-select v-model="ruleForm.roleId" filterable  placeholder="请选择角色">
                <el-option
                      :label=ruleForm.roleName
                      :value=ruleForm.roleId
                  >
                  </el-option>
                  <el-option
                      label=""
                      value=""
                  >
                  </el-option>
                  <el-option
                  v-for="item in roles"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  </el-option>
              </el-select>
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
    var validateUserName = async (rule, value, callback) => {
      let resjsondata = await axios({
        method: 'get',
        url: '/user/checkname/' + value + ',' + this.ruleForm.id
      })
      if (resjsondata.data === true) {
        callback()
      } else {
        callback(new Error('您输入的用户名已存在，请重新输入'))
      }
    }
    return {
      // 查询条件
      search: {
        userName: '',
        trueName: '',
        phone: '',
        roleId: '',
        pageno: 1,
        pagesize: 10
      },
      total: 0,
      // 角色列表
      roles: [],
      // table数据
      tabledata: [],
      dialogFormVisible: false,
      ruleForm: {},
      rules: {
        userName: [
          // 预定义规则
          { required: true, message: '请输入用户名', trigger: 'blur' },
          // 自定义规则
          { validator: validateUserName, trigger: 'blur' }
        ]
      },
      dialogFormVisible1: false
    }
  },
  created () {
    // 1 查询出所有的角色
    this.getAllRoles()
    // 2 查询首页的数据
    this.getUsers()
  },
  methods: {
    resetPass (row) {
      this.$confirm('确认重置？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          method: 'patch',
          url: '/user/resetPass/' + row.id
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '重置成功!'
            })
            this.getUsers()
          }
        })
      })
    },
    submitAdd () {
      // 整个form 验证一下
      this.$refs['ruleForm'].validate((valid) => {
        // 验证通过才提交
        if (valid) {
          axios({
            method: 'post',
            data: Qs.stringify(this.ruleForm),
            url: '/user/saveUser'
          }).then(jsondata => {
            if (jsondata.code === '200') {
              this.$message({
                type: 'success',
                message: '添加成功'
              })
              this.dialogFormVisible1 = false
              this.getUsers()
            }
          })
          // console.log(Qs.stringify(this.ruleForm))
        }
      })
    },

    submitUpd () {
      // 整个form 验证一下
      this.$refs['ruleForm'].validate((valid) => {
        // 验证通过才提交
        if (valid) {
          axios({
            method: 'patch',
            data: Qs.stringify(this.ruleForm),
            url: '/user/updUser'
          }).then(jsondata => {
            if (jsondata.code === '200') {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
              this.dialogFormVisible = false
              this.getUsers()
            }
          })
        }
      })
    },

    handleAdd () {
      this.ruleForm = {
        id: 0,
        banFlag: '0'
      }
      this.dialogFormVisible1 = true
    },

    handleEdit (row) {
      this.ruleForm = row
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
          url: '/user/delUser/' + row.id
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getUsers()
          } else {
            this.$message({
              type: 'error',
              message: '删除失败!'
            })
          }
        })
      })
    },
    filterTag (value, row) {
      return row.banFlag === value
    },
    // 查询所有的角色
    async getAllRoles () {
      const res = await axios({
        method: 'get',
        url: '/role/getAll'
      })
      // console.log(res.data.data)
      this.roles = res.data
    },
    // 根据查询条件查询数据
    async getUsers () {
      let params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/user/userList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 根据角色id 获得角色的名称
    getRoleName (roleid) {
      let role
      for (role of this.roles) {
        if (role.roleid === roleid) {
          // console.log(role.rolename)
          return role.rolename
        }
      }
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      console.log(pageno)
      this.search.pageno = pageno
      this.getUsers()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      console.log(pagesize)
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getUsers()
    },
    // 点击查询按钮
    clicksearch () {
      this.search.pageno = 1
      // this.search.pagesize = pagesize
      this.getUsers()
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
