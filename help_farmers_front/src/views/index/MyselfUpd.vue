<template>
    <div>

      <el-breadcrumb separator-class="el-icon-arrow-right" separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>我的资料</el-breadcrumb-item>
      <el-breadcrumb-item>我的设置</el-breadcrumb-item>
      <el-breadcrumb-item>信息编辑</el-breadcrumb-item>
      </el-breadcrumb>

      <!-- 分割线 -->
      <el-divider></el-divider>

      <el-card>
        <el-descriptions class="margin-top" title="修改个人信息" :column="2" border>
          <template slot="extra">
            <el-button type="primary" size="small" @click="open">修改密码</el-button>
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
          <el-input v-model="user.trueName"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号码
          </template>
          <el-input v-model="user.phone"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-house"></i>
            地址
          </template>
          <el-input v-model="user.address"></el-input>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-house"></i>
            备注
          </template>
          <el-input v-model="user.remarks"></el-input>
        </el-descriptions-item>
        </el-descriptions>
        <div style="margin-top: 20px;">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="submit">提 交</el-button>
        </div>
      </el-card>

      <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
            <!-- 用div包住内容，设定内容高度和overflow auto -->
            <el-form :rules="rules" ref="ruleForm" :model="ruleForm">
              <el-form-item label="原始密码" prop="beforepass">
                <el-input type="password" v-model="ruleForm.beforepass" autocomplete="off" show-password></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="pass">
                <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" show-password></el-input>
              </el-form-item>
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitPwd">确 定</el-button>
            </el-form>
        </div >
      </el-dialog>
    </div>
  </template>

<script>
import store from '@/store'
import axios from '@/utils/request'
import Qs from 'qs'
export default {
  name: 'Info',
  data () {
    var validatePass = (rule, value, callback) => {
      let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/
      if (!reg.test(value)) {
        callback(new Error('密码必须是由6-20位字母+数字组合'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      user: {},
      dialogFormVisible: false,
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      },
      ruleForm: {
        beforepass: '',
        pass: '',
        checkPass: ''
      }
    }
  },
  created () {
    this.init()
  },
  methods: {
    async init () {
      this.user = store.getters.getUserInfo
    },
    open () {
      this.$confirm('修改密码？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.dialogFormVisible = true
      })
    },
    submit () {
      axios({
        method: 'patch',
        data: Qs.stringify(this.user),
        url: '/user/updUserByUser'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.$router.push('/set/my/userInfo')
        }
      })
    },
    handleClose () {
      this.$router.push('/set/my/userInfo')
    },
    submitPwd () {
      this.$refs['ruleForm'].validate((valid) => {
        if (!valid) {
          // console.log('未通过')
        } else {
          // console.log('已通过')
          // console.log(this.ruleForm.checkPass)
          let pdata = {
            oldPassword: this.ruleForm.beforepass,
            newPassword: this.ruleForm.checkPass
          }
          axios({
            method: 'patch',
            url: '/user/updpwd',
            data: Qs.stringify(pdata)
          }).then(jsondata => {
            if (jsondata.code === '200') {
              this.$message({
                type: 'success',
                message: '修改成功，请重新登录'
              })
              this.dialogFormVisible = false
              this.ruleForm.beforepass = ''
              this.ruleForm.pass = ''
              this.ruleForm.checkPass = ''
              axios({
                method: 'get',
                url: '/user/logout'
              }).then(jsondata => {
                if (jsondata.code === '200') {
                  this.$message({
                    type: 'success',
                    message: '退出成功!'
                  })
                  this.$store.dispatch('asyncUpdateUserInfo', {})
                  sessionStorage.removeItem('userInfo')
                  this.$router.push('/login')
                }
              })
            }
          })
        }
      })
    }
  }
}

</script>

  <style scoped>

  </style>
