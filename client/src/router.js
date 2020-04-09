import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home';
import Login from './views/Login';
import Signup from './views/Signup';
import Profile from './views/Profile';
import MainLayout from './views/MainLayout';
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
      name: 'home',
      component: MainLayout,
      children: [
        {
          path: '',
          component: Home
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/signup',
      name: 'signup',
      component: Signup
    },
    {
      path: '/profile',
      name: 'profile',
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
