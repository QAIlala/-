<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
        <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单管理</el-breadcrumb-item>
        </el-breadcrumb>
    </div>
    <!-- 查询条件-->
    <el-row :gutter="20" class="search">
      <el-col :span="2">
            <el-button type="primary" @click="handleAdd">发起订单</el-button>
      </el-col>
      <el-col :span="3">
            <el-button type="primary" @click="delbatch">批量删除</el-button>
      </el-col>
      <el-col :span="3">
            <el-input
                placeholder="请输入订单编号"
                v-model="search.number">
            </el-input>
        </el-col>
        <el-col :span="3">
            <el-input
                placeholder="请输入手机号"
                v-model="search.phone">
            </el-input>
        </el-col>
        <el-col :span="6">
          <el-date-picker
            v-model="value2"
            type="daterange"
            align="right"
            unlink-panels
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
          </el-date-picker>
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
            prop="number"
            label="订单号"
            width="200">
        </el-table-column>
        <el-table-column
            prop="farmProducts"
            label="订单内容"
            width="150"
            :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column
            prop="status"
            header-align="left"
            align="center"
            label="订单状态"
            width="100">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              :title="
              scope.row.status === '5' ? '取消原因' :
              scope.row.status === '6' ? '拒单原因' :
              scope.row.status === '9' ? '驳回原因' :
              scope.row.status === '10' ? '退款原因' :
              ''"
              width="200"
              trigger="hover"
              >
                <p>{{
                scope.row.status === '5' ? scope.row.cancelReason :
                scope.row.status === '6' ? scope.row.rejectionReason :
                scope.row.status === '9' ? scope.row.backReason :
                scope.row.status === '10' ? scope.row.refundReason :
                '' }}</p>
                <el-divider v-if="scope.row.status === '5'"></el-divider>
                <p v-if="scope.row.status === '5'">
                  {{ '取消时间： '}}
                </p>
                <p v-if="scope.row.status === '5'">
                  {{ scope.row.cancelTime }}
                </p>
                <el-tag slot="reference"
                :type="
                scope.row.status === '1' ? 'warning' :
                scope.row.status === '2' ? 'warning' :
                scope.row.status === '4' ? 'success' :
                scope.row.status === '5' ? 'info' :
                scope.row.status === '6' ? 'danger' :
                scope.row.status === '9' ? 'danger' :
                scope.row.status === '10' ? 'info' :
                'primary'"
                disable-transitions>{{
                scope.row.status === '0' ? '待接单' :
                scope.row.status === '1' ? '待付款' :
                scope.row.status === '2' ? '待发货' :
                scope.row.status === '3' ? '已发货' :
                scope.row.status === '4' ? '已结清' :
                scope.row.status === '5' ? '已取消' :
                scope.row.status === '6' ? '拒单' :
                scope.row.status === '7' ? '待退款' :
                scope.row.status === '8' ? '待收货' :
                scope.row.status === '9' ? '已驳回' :
                scope.row.status === '10' ? '退款完毕' :
                '状态错误'
                }}
                </el-tag>
              </el-popover>
          </template>
        </el-table-column>
        <el-table-column
            prop="orderTime"
            label="下单时间"
            width="170">
        </el-table-column>
        <el-table-column
            prop="payStatus"
            header-align="left"
            align="center"
            label="付款状态"
            width="100">
            <template slot-scope="scope">
              <el-popover
              placement="top-start"
              :title="
              scope.row.payStatus === '2' ? '退款原因' :
              ''"
              width="200"
              trigger="hover"
              >
                <p>{{
                scope.row.payStatus === '2' ? scope.row.refundReason :
                '' }}</p>
               <el-tag slot="reference"
                :type="
                scope.row.payStatus === '0' ? 'warning' :
                scope.row.payStatus === '1' ? 'success' :
                'danger' "
                disable-transitions>{{
                scope.row.payStatus === '0' ? '未支付' :
                scope.row.payStatus === '1' ? '已支付' :
                scope.row.payStatus === '2' ? '退款' :
                '状态错误'
                }}
                </el-tag>
              </el-popover>
          </template>
        </el-table-column>
        <el-table-column
            prop="amount"
            label="订单金额"
            width="120">
            <template slot-scope="scope">
              {{ scope.row.amount + ' 元' }}
            </template>
        </el-table-column>
        <el-table-column
            prop="remarks"
            label="备注"
            width="220">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="手机号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="consignee"
            label="收货人"
            width="100">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址信息"
            width="180">
        </el-table-column>
        <el-table-column label="操作" width="300px" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="details(scope.row)">查看详情</el-button>
            <el-button
              v-if="user.id === scope.row.userId && (scope.row.status === '0' || scope.row.status === '1')"
              size="mini"
              type="info"
              @click="cancel(scope.row)">取消订单</el-button>
              <el-button
              v-if="user.id === scope.row.userId && scope.row.status === '1'"
              size="mini"
              type="warning"
              @click="pay(scope.row)">付款</el-button>
              <el-button
              v-if=" (user.id === scope.row.userId && scope.row.payStatus === '1') && (scope.row.status === '2' || scope.row.status === '3' || scope.row.status === '4')"
              size="mini"
              type="danger"
              @click="refund(scope.row)">退款</el-button>
              <el-button
              v-if="scope.row.status === '3' && user.id === scope.row.userId"
              size="mini"
              type="success"
              @click="confirmgoods2(scope.row)">确认收货</el-button>
              <el-button
              v-if="scope.row.status === '8' && user.roleId === 3"
              size="mini"
              type="success"
              @click="confirmgoods(scope.row)">确认收货</el-button>
              <el-button
              v-if="user.roleId === 4 && (user.id != scope.row.userId && scope.row.status === '0')"
              size="mini"
              type="warning"
              @click="order(scope.row)">接单</el-button>
              <el-button
              v-if="user.roleId === 3 && (user.id != scope.row.userId && scope.row.status === '2')"
              size="mini"
              type="warning"
              @click="shipments(scope.row)">发货</el-button>
              <el-button
              v-if="user.roleId === 2 && (user.id != scope.row.userId && scope.row.status === '7')"
              size="mini"
              type="warning"
              @click="handlerefunds(scope.row)">处理退款</el-button>
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
    <div>
    <!-- <qrcode-vue value="支付成功！" size="100" level="H" @scan="onScan"></qrcode-vue> -->
  </div>
      <el-dialog title="扫码支付" :visible.sync="showflag" width="30%">
        <div style="display: flex; justify-content: center;">
          <div id="qrcode" ref="qrCodeUrl"></div>
        </div>
        <el-button type="warning" @click="showflag=false" style="margin-top: 15px;">取消</el-button>
        <el-button type="primary" @click="handelpay()" style="margin-top: 15px;">已支付</el-button>
      </el-dialog>

    <el-dialog title="订单详情" :visible.sync="dialogFormVisible" width="30%">
        <div class="el-dialog-div">
          <el-card class="box-card">
              <div slot="header">
                <div style="display: flex; margin-right:auto; margin-top: -20px;">
                  <h1>{{
                  orderDetailForm.status === '0' ? '待接单' :
                  orderDetailForm.status === '1' ? '待付款' :
                  orderDetailForm.status === '2' ? '待发货' :
                  orderDetailForm.status === '3' ? '已发货' :
                  orderDetailForm.status === '4' ? '已结清' :
                  orderDetailForm.status === '5' ? '已取消' :
                  orderDetailForm.status === '6' ? '拒单' :
                  orderDetailForm.status === '7' ? '待退款' :
                  orderDetailForm.status === '8' ? '待收货' :
                  orderDetailForm.status === '9' ? '已驳回' :
                  orderDetailForm.status === '10' ? '退款完毕' :
                  '状态错误'
                  }}</h1>
                </div>
                <div style="display: flex; margin-right:auto; margin-top: -5px;">
                  <span style="font-size: 12px;font-weight: 100;">订单号：{{ orderDetailForm.number }}</span>
                  <span style="font-size: 12px;font-weight: 100; margin-left:auto;">{{ orderDetailForm.orderTime }}</span>
                </div>
                <div style="display: flex; margin-right:auto;margin-top: 5px;">
                  {{ orderDetailForm.consignee }}
                  <span style="margin-left:auto;"><i class="el-icon-phone"></i>&nbsp;&nbsp;{{ orderDetailForm.phone }}</span>
                </div>
                <div style="display: flex; margin-right:auto; font-weight: 100; margin-top: 10px;">
                  {{ orderDetailForm.address }}
                </div>
              </div>
              <div style="display: flex; margin-right:auto; margin-top: -10px;">
                  <span style="font-size: 12px;font-weight: 100;">农产品</span>
              </div>
              <el-table
              :data="orderDetailList"
              style="width: 100%"
              show-summary
              :summary-method="getSummaries"
              >
                <el-table-column
                  prop="farmProductsName"
                  label="农产品名称"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="price"
                  label="单价（元）"
                  width="80">
                </el-table-column>
                <el-table-column
                  prop="number"
                  label="数量"
                  width="80">
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="金额（元）"
                  width="100">
                </el-table-column>
              </el-table>
          </el-card>
        </div >
      </el-dialog>

      <el-dialog title="发起订单" :visible.sync="dialogFormVisible1" width="30%">
        <div class="el-dialog-div">
          <div style="display: flex; margin-left:30px; margin-bottom: 20px;;"><el-button type="primary" @click="addDomain">新增农产品</el-button></div>
          <el-form :model="dynamicValidateForm" ref="saveOrderForm" label-width="100px" class="demo-ruleForm">
            <el-form-item
              v-for="(domain, index) in dynamicValidateForm.domains"
              :label="'农产品' + (index + 1)"
              :key="domain.key"
              :prop="'domains.' + index + '.value'"
              :rules="{
                required: true, message: '农产品不能为空', trigger: 'blur'
              }"
            >
              <div>
                <el-select v-model="domain.value" filterable  placeholder="请选择农产品" required>
                <el-option
                v-for="item in farmProducts"
                :key="item.number"
                :label="item.name"
                :value="item.number"
                >
                </el-option>
              </el-select>
              <el-button style="margin-left: 10px;" type="danger" @click.prevent="removeDomain(domain)">删除</el-button>
            </div>
            <div>
              <el-input v-model="domain.count" placeholder="请输入数量（吨）" style="margin-top: 10px;"></el-input>
            </div>
            </el-form-item>
            <el-form-item
             label="联系电话"
              prop="phone"
              :rules="{
                required: true, message: '联系电话不能为空', trigger: 'change'
              }"
              >
                <el-input v-model="dynamicValidateForm.phone"></el-input>
            </el-form-item>
            <el-form-item
             label="收货人"
              prop="consignee"
              :rules="{
                required: true, message: '收货人不能为空', trigger: 'change'
              }"
              >
                <el-input v-model="dynamicValidateForm.consignee"></el-input>
            </el-form-item>
            <el-form-item
             label="收货地址"
              prop="address"
              :rules="{
                required: true, message: '收货地址不能为空', trigger: 'change'
              }"
            >
                <el-input v-model="dynamicValidateForm.address"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remarks">
                <el-input v-model="dynamicValidateForm.remarks"></el-input>
            </el-form-item>
            <el-button @click="dialogFormVisible1 = false">取 消</el-button>
            <el-button type="primary" @click="submitAdd">确 定</el-button>
          </el-form>
        </div >
      </el-dialog>

      <el-dialog
        :title="operateform.title"
        :visible.sync="dialogVisiblecancel"
        width="30%"
       >
        <el-input type="textarea" v-model="reason" :placeholder="operateform.placeholder"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisiblecancel = false">取 消</el-button>
          <el-button v-if="(temp.status === '0' && user.roleId != 4) || temp.status === '1'" type="primary" @click="handlecancel()">确 定</el-button>
          <el-button v-if="temp.payStatus === '1'" type="primary" @click="handlerefund()">仅退款</el-button>
          <el-button v-if="temp.payStatus === '1'" type="primary" @click="handlerefund2()">退货退款</el-button>
        </span>
      </el-dialog>

      <el-dialog
        :title="operateform2.title"
        :visible.sync="showflag2"
        width="30%"
       >
       <el-tag v-if="temp.status === '7'" type="info">退款原因：{{ temp.refundReason }}</el-tag>
       <el-divider v-if="temp.status === '7'"></el-divider>
        <el-input type="textarea" v-model="reason" :placeholder="operateform2.placeholder"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="showflag2 = false">取 消</el-button>
          <el-button v-if="temp.status === '0'" type="success" @click="handleorder()">接 单</el-button>
          <el-button v-if="temp.status === '0'" type="warning" @click="rejection()">拒 单</el-button>
          <el-button v-if="temp.status === '7'" type="danger" @click="overrule()">驳 回</el-button>
          <el-button v-if="temp.status === '7'" type="warning" @click="refunds()">退 款</el-button>
        </span>
      </el-dialog>

  </el-card>
</template>

<script>
// import Qs from 'qs'
// import QrcodeVue from 'qrcode.vue'
import QRCode from 'qrcodejs2'
import store from '@/store'
import axios from '@/utils/request'
export default {
  // components: {
  //   QrcodeVue
  // },
  data () {
    return {
      // 查询条件
      search: {
        number: '',
        phone: '',
        beginTime: '',
        endTime: '',
        pageno: 1,
        pagesize: 10
      },
      total: 0,
      // table数据
      tabledata: [],
      dialogFormVisible: false,
      farmForm: {
        farmProductArr: [],
        countArr: []
      },
      dialogFormVisible1: false,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      value2: '',
      // 农产品列表
      farmProducts: [],
      orderDetailForm: {},
      orderDetailList: {},
      sum: {},
      members: [],
      dynamicValidateForm: {
        domains: [{
          value: ''
        }]
      },
      user: {},
      batchPassArr: {},
      dialogVisiblecancel: false,
      reason: '',
      showflag: false,
      temp: {},
      operateform: {
        title: '',
        placeholder: ''
      },
      showflag2: false,
      operateform2: {
        title: '',
        placeholder: ''
      }
    }
  },
  created () {
    // 查询首页的数据
    this.getOrders()
    // 获取当前登录用户信息
    this.init()
  },
  methods: {
    overrule () {
      let temp1 = {
        id: this.temp.id,
        status: '9',
        reason: this.reason
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/account'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '驳回成功!'
          })
          this.getOrders()
          this.reason = ''
          this.showflag2 = false
        }
      })
    },
    refunds () {
      let temp1 = {
        id: this.temp.id,
        status: '10'
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/account'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '退款成功!'
          })
          this.getOrders()
          this.showflag2 = false
        }
      })
    },
    handlerefunds (row) {
      this.operateform2.title = '处理退款'
      this.operateform2.placeholder = '请填写驳回原因'
      this.reason = ''
      this.temp = row
      this.showflag2 = true
    },
    shipments (row) {
      this.$confirm('确认发货？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let temp1 = {
          id: row.id,
          status: row.status
        }
        let cancelParams = {
          params: JSON.stringify(temp1)
        }
        axios({
          method: 'patch',
          data: cancelParams,
          url: '/order/audit'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '发货成功!'
            })
            this.getOrders()
            this.dialogVisiblecancel = false
          }
        })
      })
    },
    rejection () {
      let temp1 = {
        id: this.temp.id,
        status: '6',
        reason: this.reason
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/audit'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '拒单成功!'
          })
          this.getOrders()
          this.reason = ''
          this.showflag2 = false
        }
      })
    },
    handleorder () {
      let temp1 = {
        id: this.temp.id,
        status: '1'
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/audit'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '接单成功!'
          })
          this.getOrders()
          this.showflag2 = false
        }
      })
    },
    order (row) {
      this.operateform2.title = '接单'
      this.operateform2.placeholder = '请填写拒单原因'
      this.reason = ''
      this.temp = row
      this.showflag2 = true
    },
    confirmgoods2 (row) {
      this.$confirm('确认收货？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let temp1 = {
          id: row.id,
          status: row.status
        }
        let cancelParams = {
          params: JSON.stringify(temp1)
        }
        axios({
          method: 'patch',
          data: cancelParams,
          url: '/order/operate'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '确认成功!'
            })
            this.getOrders()
            this.dialogVisiblecancel = false
          }
        })
      })
    },
    confirmgoods (row) {
      this.$confirm('确认收货？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'
      }).then(() => {
        let temp1 = {
          id: row.id,
          status: row.status
        }
        let cancelParams = {
          params: JSON.stringify(temp1)
        }
        axios({
          method: 'patch',
          data: cancelParams,
          url: '/order/audit'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '确认成功!'
            })
            this.getOrders()
            this.dialogVisiblecancel = false
          }
        })
      })
    },
    handlerefund2 () {
      let temp1 = {
        id: this.temp.id,
        status: '8',
        reason: this.reason
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/operate'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '申请成功!'
          })
          this.getOrders()
          this.dialogVisiblecancel = false
          this.reason = ''
        }
      })
    },
    handlerefund () {
      let temp1 = {
        id: this.temp.id,
        status: '7',
        reason: this.reason
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/operate'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '申请成功!'
          })
          this.getOrders()
          this.dialogVisiblecancel = false
          this.reason = ''
        }
      })
    },
    refund (row) {
      this.operateform.title = '申请退款'
      this.operateform.placeholder = '请输入退款原因'
      this.reason = ''
      this.temp = row
      this.dialogVisiblecancel = true
    },
    handelpay () {
      let temp1 = {
        id: this.temp.id,
        status: '2',
        payStatus: '1'
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/operate'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          const loading = this.$loading({
            lock: true,
            text: '正在查询支付结果...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          setTimeout(() => {
            loading.close()
            this.showflag = false
            this.$message({
              type: 'success',
              message: '支付成功!',
              offset: 200
            })
            this.getOrders()
          }, 2000)
        }
      })
    },
    // onScan (data) {
    //   console.log('二维码被扫描')
    // },
    creatQrCode () {
      document.getElementById('qrcode').innerHTML = ''
      var qrcode = new QRCode(this.$refs.qrCodeUrl, {
        text: '支付成功',
        width: 100,
        height: 100,
        colorDark: '#000000',
        colorLight: '#ffffff',
        correctLevel: QRCode.CorrectLevel.H
      })
      console.log(qrcode)
    },
    pay (row) {
      this.showflag = true
      this.temp = row
      this.$nextTick(() => {
        this.creatQrCode()
      })
    },
    cancel (row) {
      this.operateform.title = '取消订单'
      this.operateform.placeholder = '请输入取消原因'
      this.reason = ''
      this.temp = row
      this.dialogVisiblecancel = true
    },
    handlecancel () {
      let temp1 = {
        id: this.temp.id,
        status: '5',
        reason: this.reason
      }
      let cancelParams = {
        params: JSON.stringify(temp1)
      }
      axios({
        method: 'patch',
        data: cancelParams,
        url: '/order/operate'
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.$message({
            type: 'success',
            message: '取消成功!'
          })
          this.getOrders()
          this.dialogVisiblecancel = false
          this.reason = ''
        }
      })
    },
    async init () {
      this.user = store.getters.getUserInfo
      if (this.user.roleId === 5) {
        // 获取所有农产品信息
        this.getFarmProducts()
      }
    },
    async getFarmProducts () {
      const res = await axios({
        method: 'get',
        url: '/base-farm-products/getAll'
      })
      this.farmProducts = res.data
    },
    removeDomain (item) {
      var index = this.dynamicValidateForm.domains.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1)
      }
    },
    addDomain () {
      this.dynamicValidateForm.domains.push({
        value: '',
        key: Date.now()
      })
    },
    // 指定列求和
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.every(value => isNaN(value))) {
          if (column.property === 'totalPrice') {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr)
              if (!isNaN(value)) {
                return prev + curr
              } else {
                return prev
              }
            }, 0)

            sums[index] += ''
          }
        }
      })

      return sums
    },
    submitAdd () {
      this.$refs['saveOrderForm'].validate((valid) => {
        // 验证通过才提交
        if (valid) {
          this.farmForm = {
            farmProductArr: [],
            countArr: []
          }
          for (let j = 0; j < this.dynamicValidateForm.domains.length; j++) {
            this.getFarmProductsByNumber(this.dynamicValidateForm.domains[j].value, this.dynamicValidateForm.domains[j].count)
          }
          this.farmForm.phone = this.dynamicValidateForm.phone
          this.farmForm.consignee = this.dynamicValidateForm.consignee
          this.farmForm.address = this.dynamicValidateForm.address
          this.farmForm.remarks = this.dynamicValidateForm.remarks
          let orderParams = {
            params: JSON.stringify(this.farmForm)
          }
          axios({
            method: 'post',
            data: orderParams,
            url: '/order/save'
          }).then(jsondata => {
            if (jsondata.code === '200') {
              this.$message({
                type: 'success',
                message: '发起成功'
              })
              this.dialogFormVisible1 = false
              this.getOrders()
            }
          })
        } else {
          return false
        }
      })
    },
    getFarmProductsByNumber (number, count) {
      for (let i = 0; i < this.farmProducts.length; i++) {
        if (this.farmProducts[i].number === number) {
          this.farmForm.farmProductArr.push(this.farmProducts[i])
          this.farmForm.countArr.push(count)
        }
      }
    },
    handleAdd () {
      this.farmForm = {
        farmProductArr: [],
        countArr: []
      }
      this.dynamicValidateForm = {
        domains: [{
          value: ''
        }]
      }
      this.dynamicValidateForm.phone = this.user.phone
      this.dynamicValidateForm.address = this.user.address
      this.dynamicValidateForm.consignee = this.user.trueName
      this.dialogFormVisible1 = true
    },

    details (row) {
      this.orderDetailForm = row
      let params = row.id + '-' + row.number
      axios({
        method: 'get',
        url: '/order-detail/detail/' + params
      }).then(jsondata => {
        if (jsondata.code === '200') {
          this.dialogFormVisible = true
          this.orderDetailList = jsondata.data
        }
      })
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
          url: '/order/del'
        }).then(jsondata => {
          if (jsondata.code === '200') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getOrders()
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
    async getOrders () {
      let params = {
        params: this.search
      }
      const res = await axios({
        method: 'get',
        params,
        url: '/order/orderList'
      })
      this.tabledata = res.data.data
      this.total = res.data.total
    },
    // 分页的页码发生改变的事件
    handleCurrentChange (pageno) {
      this.search.pageno = pageno
      this.getOrders()
    },
    //  页的尺寸发生改变事件
    handleSizeChange (pagesize) {
      this.search.pageno = 1
      this.search.pagesize = pagesize
      this.getOrders()
    },
    // 点击查询按钮
    clicksearch () {
      this.search.pageno = 1
      this.search.beginTime = this.value2[0]
      this.search.endTime = this.value2[1]
      this.getOrders()
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
    .el-tooltip__popper{font-size: 14px; max-width:40% }/* 设置显示隐藏部分内容，按40%显示 */
</style>
