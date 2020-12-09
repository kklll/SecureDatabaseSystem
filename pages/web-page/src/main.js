import Vue from 'vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import axios from 'axios'
import '@/assets/css/global.css'
import App from "@/App"
import {Message} from "element-ui";


Vue.config.productionTip = false
// router配置
let baseUrl = "";
switch (process.env.NODE_ENV) {
    case 'development':
        baseUrl = "http://localhost:8889/api/"  //开发环境url
        break
    case 'production':
        baseUrl = "http://dlddw.xyz:8889/api/"   //生产环境url
        break
}
//axios全局配置
axios.defaults.baseURL = baseUrl
axios.defaults.timeout = 2 * 1000
//request拦截器
axios.interceptors.request.use(config => {
        config.headers.authorization = window.sessionStorage.getItem("token");
        console.log(config)
        return config;
    },
    err => {
        Message.error({message: '请求超时!'});
        return Promise.resolve(err);
    });
//response拦截器
axios.interceptors.response.use(data => {
    if (data.status && data.status === 200 && data.data.status === 'error') {
        Message.error({message: data.data.msg});
        return;
    }
    if (data.status && data.status === 200 && data.data.code === '-1') {
        Message.error({message: data.data.message});
        return;
    }
    return data;
}, err => {
    if (err.response.status === 504 || err.response.status === 404 || err.response.status === 500) {
        Message.error({message: '服务器被吃了⊙﹏⊙∥'});
    } else if (err.response.status === 403) {
        Message.error({message: '认证超时，请重新登陆！'});
        router.push("/login").then().catch()
    } else {
        Message.error({message: '未知错误！'});
    }
    return Promise.resolve(err);
})
Vue.prototype.$http = axios
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
