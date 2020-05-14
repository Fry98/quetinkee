import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    error: null,
    user: null,
    categories: [],
    flowers: [],
    sidebar: 0
  },
  getters: {
    error: state => state.error,
    user: state => state.user,
    isLogged: state => state.user !== null,
    isAdmin: state => state.user !== null && state.user.role === 'ADMIN',
    isDelivery: state => true,
    categories: state => state.categories,
    flowers: state => state.flowers,
    sidebar: state => state.sidebar
  },
  mutations: {
    setError(state, payload) {
      state.error = payload;
    },
    setUser(state, payload) {
      state.user = payload;
    },
    setCategories(state, payload) {
      state.categories = payload;
    },
    setFlowers(state, payload) {
      state.flowers = payload;
    },
    reloadSidebar(state) {
      state.sidebar++;
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
