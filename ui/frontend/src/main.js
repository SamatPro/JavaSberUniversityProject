import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import axios from 'axios'

export const AXIOS = axios.create({
  baseURL: 'http://localhost:8082/api',
  headers: {
    Authorization: 'Bearer ' + store.state.token,
    'Content-Type': 'application/json;charset=UTF-8'
  }
});

export const AXIOS_TEST = axios.create({
  baseURL: 'http://localhost:8082/api'
});

export const AXIOS_CUSTOMER = axios.create({
  baseURL: 'http://localhost:9000'
});
export const AXIOS_RESTAURANT = axios.create({
  baseURL: 'http://localhost:8090',
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  }
});

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: function (h) { return h(App) }
}).$mount('#app')
