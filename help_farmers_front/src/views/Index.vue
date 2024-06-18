<template>
  <div style="height: 100%;">
    <el-container>
      <el-aside width="auto">
          <el-menu unique-opened :collapse=collapse class="el-menu-vertical-demo" router
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
            default-active="/">
            <el-menu-item>
                <span slot="title">在线人数：{{ online }} </span>
              </el-menu-item>
          <el-menu-item index="/">
                <i class="el-icon-s-platform"></i>
                <span slot="title">控制台</span>
              </el-menu-item>
            <template v-for="(item,index) in menus">
              <el-submenu  v-if="item.state === '1'" :key="index" :index=index.toString()>
                  <template slot="title"><i :class="item.icon"></i><span slot="title">{{ item.name }}</span></template>
                  <template v-for="(citem,cindex) in item.child">
                    <el-menu-item v-if="citem.state === '0'" :key="cindex" :index="citem.url"><span slot="title">{{ citem.name }}</span></el-menu-item>
                  </template>
                  <template v-for="(citem,cindex) in item.child">
                    <el-submenu v-if="citem.state === '1'" :key="cindex" :index=numbers[cindex].toString()>
                      <template slot="title"><i :class="citem.icon"></i><span slot="title">{{ citem.name }}</span></template>
                      <el-menu-item v-for="(ditem,dindex) in citem.child" :key="dindex" :index="ditem.url"><span slot="title">{{ ditem.name }}</span></el-menu-item>
                    </el-submenu>
                  </template>
              </el-submenu>
            </template>
            <template v-for="(item,index) in menus">
              <el-menu-item  v-if="item.state === '0'" :key="index" :index=item.url>
                <i :class="item.icon"></i>
                <span slot="title">{{ item.name }}</span>
              </el-menu-item>
            </template>
              <el-menu-item @click="logout">
                <i class="el-icon-switch-button"></i>
                <span slot="title">退出系统</span>
              </el-menu-item>
          </el-menu>
        </el-aside>
      <el-container>
        <el-header style="background-color: #ffffff">
          <el-button type="info" icon="el-icon-s-fold" style="height: 40px; position: relative; top: 10px;" @click="opensidess"></el-button>
          <div class="logo">
            <h1 style="color: #409EFF;">助农仓储管理系统</h1>
          </div>
          <div class="user">
            <span class="userName">欢迎您，{{ user.trueName }}</span>
            <el-dropdown>
              <span class="el-dropdown-link">
                <!-- :src="$store.getters.getUserInfo.avatarUrl" -->
                <el-avatar src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"></el-avatar>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><el-button type="text" @click="myself">个人信息</el-button></el-dropdown-item>
                <el-dropdown-item><el-button type="text" @click="logout">退出登录</el-button></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import store from '@/store'
import axios from '@/utils/request'
export default {
  created () {
    axios({
      method: 'get',
      url: '/menu/getMenus'
    }).then(jsondata => {
      this.menus = jsondata.data
    })
  },
  mounted () {
    this.ws = new WebSocket('ws://localhost:10001/hp/ws?userName=' + this.user.userName)
    this.wsInit()
  },
  beforeDestroy () {
    this.ws.close()
  },
  beforeRouteEnter (to, from, next) {
    const id = store.getters.getUserInfo.id
    if (id) {
      return next()
    }
    next('/login')
  },
  data () {
    return {
      user: store.getters.getUserInfo,
      collapse: false,
      menus: [],
      numbers: [100, 200, 300, 500, 600, 700, 800, 900],
      ws: null,
      online: []
    }
  },
  methods: {
    wsInit () {
      this.ws.onopen = () => {
        console.log('服务已连接')
      }
      this.ws.onclose = () => {
        console.log('服务器关闭')
      }
      this.ws.onmessage = (message) => {
        console.log('收到服务器消息')
        console.log(message)
        this.online = message.data
      }
      this.ws.onerror = (error) => {
        console.log('报错了')
        console.log(error)
      }
    },
    getOnline () {
      axios({
        method: 'get',
        url: '/stats/hello/' + this.user.userName
      })
    },
    logout () {
      this.$confirm('确定退出登录?', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
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
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    myself () {
      this.$router.push('/myself')
    },
    opensidess () {
      if (this.collapse) {
        this.collapse = false
      } else {
        this.collapse = true
      }
    }
  }
}
</script>

<style scoped>

.el-header,
.el-footer {
  /* background-color: #B3C0D1; */
  color: #333;
  text-align: center;
  line-height: 60px;
  display: flex;
}

.logo {
  position: relative;
  top: -18px;
  left: 15px;
}

.user {
  flex: 1;
  text-align: right;
}

.user .el-avatar {
  position: relative;
  top: 7px;
  border: 1px solid #DCDFE6;
  box-shadow: 0 0 3px #909399;
}

.user .el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-menu {
  height: 100%;
}

.el-aside{
  height: 100%;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  height: 100%;
}

.el-container {
  height: 100%;
}

.user .userName {
  position: relative;
  right: 20px;
  bottom: 6px;
  color: #409EFF;
  font-weight: 600;
}

</style>
