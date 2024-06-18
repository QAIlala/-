<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right" separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的设置</el-breadcrumb-item>
      <el-breadcrumb-item>我的资料</el-breadcrumb-item>
      </el-breadcrumb>

       <!-- 分割线 -->
       <el-divider></el-divider>

    <el-card>
      <el-descriptions class="margin-top" title="个人信息" :column="2" border>
        <template slot="extra">
          <i class="el-icon-setting"></i>
            <span style="font-weight: 700;">权限：{{ user.roleName }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        </template>
        <template slot="extra">
          <el-button type="primary" size="small" @click="open">修改个人信息</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ user.userName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            姓名
          </template>
          {{ user.trueName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号码
          </template>
          {{ user.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-house"></i>
            地址
          </template>
          {{ user.address }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-house"></i>
            备注
          </template>
          {{ user.remarks }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

  </div>
</template>

<script>
// import axios from '@/utils/request'
import store from '@/store'
export default {
  name: 'Info',
  data () {
    return {
      user: {}
    }
  },
  created () {
    this.init()
  },
  methods: {
    async init () {
      // let response = await axios({
      //   method: 'get',
      //   url: '/api/user/now'
      // })
      // this.user = response.data
      this.user = store.getters.getUserInfo
    },
    open () {
      this.$confirm('修改个人信息？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push('/myselfupd')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    }
  }
}
</script>

<style scoped>

.el-avatar {
    width: 60px;
    height: 60px;
    display: block;
    border: 1px solid #DCDFE6;
    box-shadow: 0 0 3px #909399;
  }

</style>
