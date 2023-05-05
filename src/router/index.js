import Vue from 'vue'
import VueRouter from 'vue-router'
//import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  /* {
    path: '/',
    name: 'home',
    component: HomeView
  },*/
  {
    path: '/',
    redirect: '/lay',
  },
  /* {
    path: '/input',
    name: 'input',
    component: () => import('../views/inputView.vue'),
    children:[
      {
        path: '/ast',
        name: 'ast',
        component: () => import('../views/result/astView.vue')
      },
      {
        path: '/cfg',
        name: 'cfg',
        component: () => import('../views/result/cfgView.vue')
      },
      {
        path: '/ddg',
        name: 'ddg',
        component: () => import('../views/result/ddgView.vue')
      },
      {
        path: '/pdg',
        name: 'pdg',
        component: () => import('../views/result/pdgView.vue')
      }
    ],
  }, */
  {
    path: '/lay',
    name: 'lay',
    component: () => import('../views/test/layoutView.vue'),
    children:[
      {
        path: '/ast',
        name: 'ast',
        component: () => import('../views/result/astView.vue')
      },
      {
        path: '/cfg',
        name: 'cfg',
        component: () => import('../views/result/cfgView.vue')
      },
      {
        path: '/ddg',
        name: 'ddg',
        component: () => import('../views/result/ddgView.vue')
      },
    ],
  },
  {
    path: '/upload',
    name: 'upload',
    component: () => import('../views/test/uploadView.vue'),
    children:[
      {
        path: '/ast',
        name: 'ast',
        component: () => import('../views/result/astView.vue')
      },
      {
        path: '/cfg',
        name: 'cfg',
        component: () => import('../views/result/cfgView.vue')
      },
      {
        path: '/ddg',
        name: 'ddg',
        component: () => import('../views/result/ddgView.vue')
      },
      {
        path: '/pdg',
        name: 'pdg',
        component: () => import('../views/result/pdgView.vue')
      }
    ],
  },
]

const router = new VueRouter({
  routes
})

export default router
