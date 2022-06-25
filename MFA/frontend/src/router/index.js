import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SingIn from "@/components/SingIn";



const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/SendSamplePassword',
    name: 'SendSamplePassword',
    component: () => import(/* webpackChunkName: "SendSamplePassword" */ '../views/SendSamplePassword.vue')
  },
  {
    path: "/scanningQrCodePageView",
    name: "scanningQrCodePageView",
    component: () => import(/* webpackChunkName: "scanningQrCodePage" */ '../views/scanningQrCodePageView.vue')

  },
  {
    path: "/SingIn",
    name: "SingIn",
    component: SingIn
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
