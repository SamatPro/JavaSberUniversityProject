import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  id: localStorage.getItem('user-id') || 0,
  mail: localStorage.getItem("user-mail") || '',
  token: localStorage.getItem('user-token') || ''
}

const getters = {
  getId: state => {
    return state.id
  },

  getMail: state => {
    return state.mail
  },

  getToken: state => {
    return state.token
  }
}

const mutations = {
  auth_login: (state, user) => {
    console.log("storing user info");
    state.id = user.id
    state.mail = user.mail
    state.token = user.token    
    localStorage.setItem('user-id', user.id)
    localStorage.setItem('user-mail', user.mail)
    localStorage.setItem('user-token', user.token)
  },

  auth_logout: () => {
    state.id = ''
    state.mail = ''
    state.token = ''
    localStorage.removeItem('user-id')
    localStorage.removeItem('user-mail')
    localStorage.removeItem('user-token')
  }
}

const actions = {
  login: (context, user) => {
    context.commit('auth_login', user)
  },
  logout: (context, user) => {
    context.commit('auth_logout')
  }
}

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters
})
