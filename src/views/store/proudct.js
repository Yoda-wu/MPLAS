import Vue from "vue"
import Vuex from "vuex"
Vue.use(Vuex)
const state = {
 id:null,
 code:null,

}
const mutations = {
  //保存数据
  CHANGE_ACTIVE_LI(state, { id, code }) {
    state.id = id
    state.code = code
  },
   //清除数据
   SET_CLEAR_DATA(state,data){
    state.id=data
  }
}
const actions = {
  //保存数据
  changeSetting({ commit }, data) {
    commit('CHANGE_ACTIVE_LI', { id: data.id, code: data.code })
  },
  //清除数据
  clearVuex({ commit }) {
    commit("SET_CLEAR_DATA", null);
  },

}
export default {
  //解决模块名冲突
  namespaced: true,
  state,
  mutations,
  actions
}

