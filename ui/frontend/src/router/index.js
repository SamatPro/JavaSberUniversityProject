import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/SignIn.vue'
import Foods from "../views/Foods.vue"
import Orders from "../views/Orders.vue"
import Cart from "../views/Cart.vue"
import SignUp from "../views/SignUp.vue"


Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/foods',
    name: 'Foods',
    component: Foods
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },
  {
    path: '/signUp',
    name: 'SignUp',
    component: SignUp
  }
 
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
