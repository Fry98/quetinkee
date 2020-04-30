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
import store from './store';
import ManageFlowers from "./views/ManageFlowers";

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
          component: ManageFlowers,
          meta: {
            auth: AuthLevel.ADMIN
          },
        },
        {
          path: 'new-bouquet',
          component: NewBouquet,
          meta: {
            auth: AuthLevel.ADMIN
          },
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
  if (to.path === '/admin') {
    router.push('/admin/manage-flowers');
    return;
  }

  if (!to.meta.auth) return next();
  switch (to.meta.auth) {
    case AuthLevel.REGULAR:
      if (store.getters.isLogged) return next();
      return next('/');
    case AuthLevel.ADMIN:
      if (store.getters.isAdmin) return next();
      return next('/');
    case AuthLevel.DELIVERY:
      break;
  }
});

export default router;
