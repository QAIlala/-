<template>
  <div class="role">
    <el-breadcrumb separator-class="el-icon-arrow-right" separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>设置</el-breadcrumb-item>
        <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 分割线 -->
    <el-divider></el-divider>

     <!-- 上方添加button -->
    <el-row :gutter="20" class="search">
      <el-col :span="3">
            <el-button type="primary" @click="addroleshow">添加角色</el-button>
      </el-col>
    </el-row>
    <!-- table -->
    <el-table
      :data="tableData"
      style="width: 100%"
      :header-cell-style="{
            textAlign: 'center'
        }"
      :cell-style="{
        textAlign: 'center'
      }">
      <el-table-column
      type="index"
      width="80">
      </el-table-column>

      <el-table-column
      prop="name"
      label="角色名称"
      width="120">
      </el-table-column>

      <el-table-column
      prop="bz"
      label="角色描述"
      width="230">
      </el-table-column>

      <el-table-column
      prop="remarks"
      label="角色备注"
      width="230">
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

      <el-table-column
     label="菜单授权"
      width="120">
      <template slot-scope="scope">
        <el-button type="primary" icon="el-icon-edit" circle @click="grantclick(scope)"></el-button>
      </template>
      </el-table-column>

      <el-table-column
     label="动作授权"
      width="120">
      <template slot-scope="scope">
        <el-button type="success" icon="el-icon-edit" circle @click="actionclick(scope)"></el-button>
      </template>
      </el-table-column>

      <el-table-column
     label="编辑角色"
      width="150">
      <template slot-scope="scope">
        <el-button type="primary" icon="el-icon-edit-outline" circle @click="editRole(scope)"></el-button>
      </template>
      </el-table-column>

      <el-table-column
     label="删除角色"
      width="150">
      <template slot-scope="scope">
        <el-button type="danger" icon="el-icon-delete" circle @click="deleteRole(scope)"></el-button>
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

    <el-dialog title="添加角色" :visible.sync="addFormVisible" @closed="closeDialog">
      <el-form :model="addform">
        <el-form-item label="角色名" prop="name">
            <el-input v-model="addform.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="bz">
            <el-input v-model="addform.bz"></el-input>
        </el-form-item>
        <el-form-item label="角色备注" prop="remarks">
            <el-input v-model="addform.remarks"></el-input>
        </el-form-item>
        <el-form-item label="启用/禁用" prop="banFlag">
          <el-switch
            v-model="addform.banFlag"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="0"
            inactive-value="1">
          </el-switch>
        </el-form-item>
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="addRole">确 定</el-button>
      </el-form>
    </el-dialog>

    <el-dialog title="修改角色" :visible.sync="updFormVisible" @closed="closeDialog">
      <el-form :model="updform">
        <el-form-item label="角色名" prop="name">
            <el-input v-model="updform.name"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="bz">
            <el-input v-model="updform.bz"></el-input>
        </el-form-item>
        <el-form-item label="角色备注" prop="remarks">
            <el-input v-model="updform.remarks"></el-input>
        </el-form-item>
        <el-form-item label="启用/禁用" prop="banFlag">
          <el-switch
            v-model="updform.banFlag"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="0"
            inactive-value="1">
          </el-switch>
        </el-form-item>
        <el-button @click="updFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUpd">确 定</el-button>
      </el-form>
    </el-dialog>

    <!-- 给角色赋权的dialog
        dialogFormVisible 是否可见
        title： dialog 标题
        el-form :model form绑定的数据
      -->
      <el-dialog title="菜单授权" :visible.sync="grantdialogFormVisible">
        <el-form :model="grantform">
          <el-form-item label="角色名称">
            <el-input v-model="grantform.name" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <!--
            :data 树的数据（所有权限数据）
            show-checkbox 是否显示checkbox
            ref:  寻找组件的id  this.$ref.tree
            :default-expand-all: 默认是否全张开
            default-checked-keys ： 默认选中的节点
            node-key： 节点的主键
            :props
          -->
          <el-tree
            :data="grantform.allRights"
            show-checkbox
            ref="tree"
            node-key="id"
            :default-expand-all="true"
            :check-strictly = "checkStrictly"
            :default-checked-keys="grantform.checkedRights"
            :props="defaultProps">
        </el-tree>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="grantdialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitGrant" >确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="动作授权" :visible.sync="actiondialogFormVisible">
        <el-form :model="grantform">
          <el-form-item label="角色名称">
            <el-input v-model="grantform.name" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <!--
            :data 树的数据（所有权限数据）
            show-checkbox 是否显示checkbox
            ref:  寻找组件的id  this.$ref.tree
            :default-expand-all: 默认是否全张开
            default-checked-keys ： 默认选中的节点
            node-key： 节点的主键
            :props
          -->
          <el-tree
            :data="grantform.allActions"
            show-checkbox
            ref="tree1"
            node-key="id"
            :default-expand-all="true"
            :check-strictly = "checkStrictly"
            :default-checked-keys="grantform.checkedActions"
            :props="defaultProps">
        </el-tree>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="actiondialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitAction" >确 定</el-button>
        </div>
      </el-dialog>

  </div>
</template>

<script>
import axios from '@/utils/request'
import Qs from 'qs'
export default {
  data () {
    return {
      search: {
        pageno: 1,
        pagesize: 10
      },
      total: 0,
      tableData: [],
      addform: {},
      addFormVisible: false,
      updform: {},
      updFormVisible: false,
      defaultProps: {
        children: 'child',
        label: 'name'
      },
      // 授权dialog 是否可见
      grantdialogFormVisible: false,
      actiondialogFormVisible: false,
      grantform: {},
      checkStrictly: true
    }
  },
  created () {
    this.getTableData()
  },
  methods: {
    filterTag (value, row) {
      return row.banFlag === value
    },
    async getTableData () {
      // var params = {
      //   params: this.search
      // }
      let params = this.search
      let response = await axios({
        method: 'get',
        params,
        url: '/role/getAllNotDel'
      })
      this.tableData = response.data.data
      this.total = response.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      console.log(pageno)
      this.search.pageno = pageno
      this.getTableData()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      console.log(pagesize)
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getTableData()
    },
    editRole (scope) {
      this.updFormVisible = true
      this.updform = scope.row
    },
    submitUpd () {
      axios({
        method: 'patch',
        data: Qs.stringify(this.updform),
        url: '/role/upd'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.updFormVisible = false
          this.getTableData()
        }
      })
    },
    addroleshow () {
      this.addFormVisible = true
    },
    closeDialog () {
      this.addFormVisible = false
      this.addform = {}
    },
    async addRole () {
      let jsondata = await axios({
        method: 'post',
        url: '/role/save',
        data: Qs.stringify(this.addform)
      })
      if (jsondata.code === '200') {
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.closeDialog()
        this.getTableData()
      }
    },
    // 授权按钮被点击事件
    async grantclick (scope) {
      this.checkStrictly = true
      this.grantform.name = scope.row.name
      this.grantform.id = scope.row.id
      // 查询出所有的权限 绑定在树上
      let jsondata = await axios({
        method: 'get',
        url: '/menu/queryAllMenus/' + this.grantform.id
      })
      // console.log(jsondata.data)
      this.grantform.allRights = jsondata.data.list

      let checkedIds = jsondata.data.checkedId

      checkedIds.sort(function (a, b) { return a - b })

      this.grantform.checkedRights = checkedIds

      this.grantdialogFormVisible = true
      this.$nextTick(() => {
        // set checked state of a node not affects its father and child nodes
        this.checkStrictly = false
      })
    },
    async actionclick (scope) {
      this.checkStrictly = true
      this.grantform.name = scope.row.name
      this.grantform.id = scope.row.id
      let jsondata = await axios({
        method: 'get',
        url: '/action/queryAllActions/' + this.grantform.id
      })
      this.grantform.allActions = jsondata.data.list

      let checkedIds = jsondata.data.checkedId

      checkedIds.sort(function (a, b) { return a - b })

      this.grantform.checkedActions = checkedIds
      this.actiondialogFormVisible = true
      this.$nextTick(() => {
        // set checked state of a node not affects its father and child nodes
        this.checkStrictly = false
      })
    },
    deleteRole (scope) {
      this.$confirm('确认删除？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let jsondata = await axios({
          method: 'delete',
          url: '/role/' + scope.row.id
        })
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getTableData()
        }
      })
    },
    async submitGrant () {
      // 1 获得选择的节点
      let checked = this.$refs.tree.getCheckedKeys()
      // 2 获得半选节点
      let hchecked = this.$refs.tree.getHalfCheckedKeys()

      let all = [...checked, ...hchecked]
      // console.log(checked + '-------' + hchecked + '------' + all)

      var params = {
        roleid: this.grantform.id,
        rights: all
      }

      let jsondata = await axios({
        method: 'post',
        data: Qs.stringify(params),
        url: '/menu/grant'
      })
      if (jsondata.code === '200') {
        this.$message({
          type: 'success',
          message: '菜单权限授予成功'
        })
        this.grantdialogFormVisible = false
      }
    },
    async submitAction () {
      // 1 获得选择的节点
      let checked = this.$refs.tree1.getCheckedKeys()
      // 2 获得半选节点
      let hchecked = this.$refs.tree1.getHalfCheckedKeys()

      let all = [...checked, ...hchecked]
      // console.log(checked + '-------' + hchecked + '------' + all)

      var params = {
        roleid: this.grantform.id,
        rights: all
      }

      let jsondata = await axios({
        method: 'post',
        data: Qs.stringify(params),
        url: '/action/grant'
      })
      if (jsondata.code === '200') {
        this.$message({
          type: 'success',
          message: '动作权限授予成功'
        })
        this.actiondialogFormVisible = false
      }
    }
  }
}
</script>

<style scpoed>
   .search{
        margin-bottom: 20px;
    }
</style>
