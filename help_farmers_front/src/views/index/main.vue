<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <el-card>
      <div id="echart1" style="width: 800px; height: 400px;"></div>
    </el-card>
    <el-card v-if="this.user.roleId === 1 || this.user.roleId === 2 || this.user.roleId === 3">
      <div id="echart2" style="width: 1000; height: 600px;"></div>
    </el-card>
    <el-card v-if="this.user.roleId === 1 || this.user.roleId === 2 || this.user.roleId === 3">
      <div id="echart3" style="width: 1000; height: 600px;"></div>
    </el-card>
  </el-card>
</template>

<script>
// import Qs from 'qs'
import store from '@/store'
import axios from '@/utils/request'
export default {
  created () {

  },
  mounted () {
    this.ws = new WebSocket('ws://localhost:10001/hp/ws')
    this.wsInit()
    this.init()
    this.drawChart1()
    if (this.user.roleId === 1 || this.user.roleId === 2 || this.user.roleId === 3) {
      this.drawChart2()
      this.drawChart3()
    }
  },
  beforeDestroy () {
    this.ws.close()
  },
  data () {
    return {
      user: {},
      ws: null
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
        this.drawChart1()
        if (this.user.roleId === 1 || this.user.roleId === 2 || this.user.roleId === 3) {
          this.drawChart2()
          this.drawChart3()
        }
      }
      this.ws.onerror = (error) => {
        console.log('报错了')
        console.log(error)
      }
    },
    async init () {
      this.user = store.getters.getUserInfo
    },
    drawChart1 () {
      axios({
        method: 'get',
        url: '/stats/getMonthSales'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
          let myChart = this.$echarts.init(document.getElementById('echart1'))
          // 指定图表的配置项和数据
          let option = {
            title: {
              text: '月内销量前10的农产品'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            itemStyle: {
              // 设置颜色
              color: '#3499e7'
            },
            legend: {
              data: ['销量（吨）']
            },
            xAxis: {
              data: jsondata.data.names
            },
            yAxis: {},
            series: [
              {
                name: '销量(吨)',
                type: 'bar',
                data: jsondata.data.data
              }
            ]
          }
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option)
        }
      })
    },

    drawChart2 () {
      axios({
        method: 'get',
        url: '/stats/getWeekOrderNumber'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
          let myChart = this.$echarts.init(document.getElementById('echart2'))
          // 指定图表的配置项和数据
          let option = {
            title: {
              text: '本周每日成交量（单）'
            },
            legend: {
              top: 'bottom'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            series: [
              {
                name: '本周每日成交量（单）',
                type: 'pie',
                radius: [50, 250],
                center: ['50%', '50%'],
                roseType: 'area',
                itemStyle: {
                  borderRadius: 8
                },
                data: jsondata.data
              }
            ]
          }
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option)
        }
      })
    },

    drawChart3 () {
      axios({
        method: 'get',
        url: '/stats/getWeekProfit'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
          let myChart = this.$echarts.init(document.getElementById('echart3'))
          // 指定图表的配置项和数据
          let option = {
            title: {
              text: '本周每日利润（元）'
            },
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: {
              type: 'category',
              data: jsondata.data.names
            },
            yAxis: {
              type: 'value'
            },
            itemStyle: {
              // 设置颜色
              color: '#3499e7'
            },
            series: [
              {
                data: jsondata.data.data,
                type: 'line'
              }
            ]
          }
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option)
        }
      })
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
