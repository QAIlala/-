import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/views/Index'
import Login from '@/views/Login'
import RoleManager from '@/views/index/RoleManager'
import Myself from '@/views/index/Myself'
import MyselfUpd from '@/views/index/MyselfUpd'
import userManager from '@/views/index/userManager'
import FarmProductsManager from '@/views/index/FarmProductsManager'
import FarmProductsTypeManager from '@/views/index/FarmProductsTypeManager'
import WarehouseManager from '@/views/index/WarehouseManager'
import InventoryManager from '@/views/index/InventoryManager'
import StorageManager from '@/views/index/StorageManager'
import OutputManager from '@/views/index/OutputManager'
import OrderManager from '@/views/index/OrderManager'
import OprateLog from '@/views/index/OprateLog'
import main from '@/views/index/main'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/main'
    },
    {
      path: '/index',
      component: Index,
      redirect: '/main',
      children: [
        {
          path: '/set/system/roleManager',
          component: RoleManager
        },
        {
          path: '/myselfupd',
          component: MyselfUpd
        },
        {
          path: '/set/system/userManager',
          component: userManager
        },
        {
          path: '/set/my/userInfo',
          component: Myself
        },
        {
          path: '/basicinfor/farmproduct',
          component: FarmProductsManager
        },
        {
          path: '/basicinfor/farmproducttype',
          component: FarmProductsTypeManager
        },
        {
          path: '/jxc/warehouse',
          component: WarehouseManager
        },
        {
          path: '/jxc/inventory',
          component: InventoryManager
        },
        {
          path: '/jxc/storage',
          component: StorageManager
        },
        {
          path: '/jxc/output',
          component: OutputManager
        },
        {
          path: '/orders',
          component: OrderManager
        },
        {
          path: '/log',
          component: OprateLog
        },
        {
          path: '/main',
          component: main
        }
      ]
    },
    {
      path: '/login',
      component: Login
    }
  ]
})
