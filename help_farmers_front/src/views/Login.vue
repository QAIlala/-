<template>
    <body id="poster">
      <div class="login">
      <!-- model绑定的数据对象 rules绑定的验证规则 ref相当于id label-width是文本宽度 -->
      <!-- <el-page-header @back="goBack" style="color: #409eff;">
        <span slot="title">
          <span style="color: #409eff; font-weight: 900; font-size: larger;">注册</span>
        </span>
      </el-page-header> -->
      <!-- <el-divider></el-divider> -->
      <!-- <div id="poster"> -->
        <el-form :model="user" status-icon ref="ruleForm" label-width="100px" class="login-box" style="opacity: 0.8;">
          <h1 class="login-title">欢迎登录</h1>
          <el-tabs v-model="activeName">
            <el-tab-pane name="first">
              <el-form-item>
                <span slot="label">
                  <span style="color: #0b1894; font-weight: 900;">用户名：</span>
                </span>
                <el-input prefix-icon="el-icon-user" v-model="user.userName" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <span slot="label">
                  <span style="color: #0b1894; font-weight: 900;">密码：</span>
                </span>
                <el-input prefix-icon="el-icon-menu" type="password" v-model="user.userPassword" autocomplete="off" show-password></el-input>
              </el-form-item>
              <el-form-item>
              <el-button type="primary" @click="login">登录</el-button>
              </el-form-item>
            </el-tab-pane>
          </el-tabs>
        </el-form>
      <!-- </div> -->
    </div>
    </body>
  </template>

<script>
import axios from '@/utils/request'
import Qs from 'qs'
export default {
  data () {
    return {
      user: {},
      activeName: 'first'
    }
  },
  methods: {
    login () {
      axios({
        method: 'post',
        data: Qs.stringify(this.user),
        url: '/login'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          // 要把当前的用户信息保存到vuex中
          // 同步修改
          this.$store.commit('updateUserInfo', jsondata.data)
          sessionStorage.setItem('userInfo', JSON.stringify(jsondata.data)) // 本地存储一份
          // 跳转index
          this.$router.push('/index')
        }
      })
    }
  }
}

</script>

  <style scoped>

  #poster {
      background:url("../assets/background7.gif") no-repeat;
      background-position: center;
      height: 100%;
      width: 100%;
      background-size: cover;
      position: fixed;
      left: 0;
    }
    body{
      margin: 0px;
      padding: 0;
    }

  .login-box {
      border: 1px solid #DCDFE6;
      width: 450px;
      margin: 180px auto;
      padding: 35px 35px 15px 5px;
      border-radius: 15px;
      -webkit-border-radius: 15px;
      -moz-border-radius: 15px;
      box-shadow: 0 0 25px #909399;
    }

    .login-title {
      text-align: center;
      margin: 0 auto 0px 45px;
      color: #0b1894;
    }

/*
    .el-divider--horizontal {
      display: block;
      height: 1px;
      width: 100%;
      margin-top: 10px;
      margin-bottom: 1px;
    } */

  </style>
