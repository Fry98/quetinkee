import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {} from '@fortawesome/free-regular-svg-icons';
import {
  faUser,
  faShoppingCart,
  faChevronRight,
  faChevronLeft,
  faCartPlus,
  faSignOutAlt,
  faAddressCard,
  faSearch,
  faCheck
} from '@fortawesome/free-solid-svg-icons';

library.add(faUser);
library.add(faShoppingCart);
library.add(faChevronRight);
library.add(faChevronLeft);
library.add(faCartPlus);
library.add(faSignOutAlt);
library.add(faAddressCard);
library.add(faSearch);
library.add(faCheck);

Vue.component('font-awesome-icon', FontAwesomeIcon);
