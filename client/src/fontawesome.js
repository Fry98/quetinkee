import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faStar as faStarRegular,
  faTrashAlt
} from '@fortawesome/free-regular-svg-icons';
import {
  faUser,
  faShoppingCart,
  faChevronRight,
  faChevronLeft,
  faCartPlus,
  faSignOutAlt,
  faAddressCard,
  faSearch,
  faCheck,
  faStar,
  faPlus,
  faMinus,
  faTimes
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
library.add(faStar);
library.add(faStarRegular);
library.add(faPlus);
library.add(faMinus);
library.add(faTrashAlt);
library.add(faTimes);

Vue.component('font-awesome-icon', FontAwesomeIcon);
