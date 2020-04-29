import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    error: null,
    user: null
  },
  getters: {
    error: state => state.error,
    user: state => state.user,
    isLogged: state => state.user !== null
  },
  mutations: {
    setError(state, payload) {
      state.error = payload;
    },
    setUser(state, payload) {
      state.user = payload;
    }
  },
  actions: {
    openModal(context, payload) {
      context.commit('setError', payload);
    },
    closeModal(context) {
      context.commit('setError', null);
    },
    setUser(context, payload) {
      context.commit('setUser', payload);
      localStorage.setItem('user', JSON.stringify(payload));
    },
    loadUser(context) {
      const user = localStorage.getItem('user');
      if (user !== null) {
        context.commit('setUser', JSON.parse(user));
      }
    },
    logout(context) {
      context.commit('setUser', null);
      localStorage.removeItem('user');
    }
  }
});

store.dispatch('loadUser');
export default store;
