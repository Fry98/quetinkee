import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home';
import Login from './views/Login';
import Signup from './views/Signup';
import Profile from './views/Profile';
import MainLayout from './views/MainLayout';
import NewBouquet from "./views/NewBouquet";
import AdminLayout from "./views/AdminLayout";
import ProductDetail from './views/ProductDetail';
import ManageFlowers from "./views/ManageFlowers";
import ManageStorage from "./views/ManageStorage";
import Cart from './views/Cart';
import store from './store';

Vue.use(Router);

const AuthLevel = {
  REGULAR: 1,
  ADMIN: 2,
  DELIVERY: 3
};

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: MainLayout,
      children: [
        {
          path: '',
          component: Home
        },
        {
          path: 'detail',
          component: ProductDetail
        }
      ]
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: 'manage-flowers',
          component: ManageFlowers
        },
        {
          path: 'new-bouquet',
          component: NewBouquet
        }
      ]
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/signup',
      component: Signup
    },
    {
      path: '/profile',
      meta: {
        auth: AuthLevel.REGULAR
      },
      component: Profile
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (!to.meta.auth) return next();
  switch (to.meta.auth) {
    case AuthLevel.REGULAR:
      if (store.getters.isLogged) return next();
      return next('/');
    case AuthLevel.ADMIN:
      break;
    case AuthLevel.DELIVERY:
      break;
  }
});

export default router;
