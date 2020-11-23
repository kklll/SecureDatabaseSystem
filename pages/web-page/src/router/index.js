import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../components/login'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: login
    },
    {
        path: '/',
        name: 'Main',
        redirect: '/add',
        component: () => import(/* webpackChunkName: "Main" */ '../components/Main'),
        children: [
            {
                path: '/Home',
                name: "Home",
                component: () => import(/* webpackChunkName: "Home" */ '../components/Home')
            }, {
                path: '/add',
                name: "add",
                component: () => import(/* webpackChunkName: "Home" */ '../components/add')
            },
        ]
    },

]

const router = new VueRouter({
    // mode:"history",
    // base: process.env.BASE_RUL,
    routes
})
//哨兵
router.beforeEach((to, from, next) => {
    //to 去哪
    //from 从哪来
    //next 是函数表示放行
    if (to.path === '/login') {
        return next();
    } else {
        const token = window.sessionStorage.getItem("token");
        if (!token) {
            return next('login');
        }
        next();
    }
})


export default router
