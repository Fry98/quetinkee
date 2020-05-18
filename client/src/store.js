import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    error: null,
    user: null,
    categories: [],
    flowers: [],
    sidebar: 0,
    cart: {}
  },
  getters: {
    error: state => state.error,
    user: state => state.user,
    isLogged: state => state.user !== null,
    isAdmin: state => state.user !== null && state.user.role === 'ADMIN',
    isDelivery: state => true,
    categories: state => state.categories,
    flowers: state => state.flowers,
    sidebar: state => state.sidebar,
    cart: state => state.cart
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
    },
    addToCart(state, payload) {
      Vue.set(state.cart, payload.id, payload);
    },
    removeFromCart(state, payload) {
      Vue.delete(state.cart, payload);
    },
    setCart(state, payload) {
      state.cart = payload;
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
      context.dispatch('clearCart');
      localStorage.removeItem('user');
    },
    loadCart(context) {
      const cart = localStorage.getItem('cart');
      context.commit('setCart', cart !== null ? JSON.parse(cart) : {});
    },
    clearCart(context) {
      context.commit('setCart', {});
      localStorage.removeItem('cart');
    },
    addToCart(context, payload) {
      context.commit('addToCart', payload);
      localStorage.setItem('cart', JSON.stringify(context.getters.cart));
    },
    removeFromCart(context, payload) {
      context.commit('removeFromCart', payload);
      localStorage.setItem('cart', JSON.stringify(context.getters.cart));
    }
  }
});

store.dispatch('loadUser');
store.dispatch('loadCart');
export default store;
