// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import * as echarts from 'echarts'

Vue.config.productionTip = false
Vue.prototype.$echarts = echarts // 可以全局使用 不用每次使用需要在页面导入
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router, // this.$router
  store, // this.$store
  components: { App },
  template: '<App/>'
})
