import Vue from 'vue';
import App from './App.vue';
import store from './store';
import router from './router';
import infiniteScroll from 'vue-infinite-scroll';
import './fontawesome.js';

Vue.config.productionTip = false;

Vue.use(infiniteScroll);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
