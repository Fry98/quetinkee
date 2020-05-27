<template>
  <div id='delivery'>
    <order-details
      :open='open'
      :orders='ordersComp'
      :index='index'
      @close='closeDetails'
      v-if='orders.length > 0'
    ></order-details>
    <delivery-sidebar
      @pending='initCategory(0, "Čekající objednávky")'
      @ongoing='initCategory(1, "Probíhající objednávky")'
      @completed='initCategory(2, "Dokončené objednávky")'
    ></delivery-sidebar>
    <delivery-table
      :title='title'
      :selected='selected'
      :orders='ordersComp'
      :stopLoad='stopLoad'
      :ongoing='ongoing'
      @remove='removeItem'
      @details='openDetails'
      @fetch='loadMore'
    ></delivery-table>
  </div>
</template>

<script>
import DeliverySidebar from '../components/DeliverySidebar';
import DeliveryTable from './DeliveryTable';
import OrderDetails from '../components/OrderDetails';
import axios from 'axios';

export default {
  components: {
    DeliverySidebar,
    DeliveryTable,
    OrderDetails
  },
  data() {
    return {
      category: 0,
      title: 'Čekající objednávky',
      selected: 0,
      open: false,
      stopLoad: false,
      index: 0,
      orders: [],
      page: 0,
      ongoing: false
    };
  },
  mounted() {
    this.initCategory(0, "Čekající objednávky");
  },
  methods: {
    loadMore() {
      switch (this.category) {
        case 0:
          this.fetchOrders("NEW");
          break;
        case 1:
          this.fetchOrders("PENDING");
          break;
        case 2:
          this.fetchOrders("FINISH");
          break;
      }
    },
    initCategory(categoryId, title) {
      if (this.category === categoryId) return;
      this.category = categoryId;
      this.title = title;
      this.selected = categoryId;
      this.orders = [];
      this.stopLoad = false;
    },
    async fetchOrders(statusId) {
      this.ongoing = true;
      const res = await axios(`/api/orders?status=${statusId}&page=${this.page}&size=15`);
      this.orders.push(...res.data.content);
      this.stopLoad = res.data.last;
      this.ongoing = false;
    },
    openDetails(i) {
      this.open = true;
      this.index = i;
    },
    closeDetails() {
      this.open = false;
    },
    removeItem(i) {
      this.orders.splice(i, 1);
    }
  },
  computed: {
    ordersComp() {
      return this.orders.map(x => {
        // const zipStr = x.zip.toString();
        // const zip = zipStr.substr(0, 3) + " " + zipStr.substr(3, 2);
        // const street = x.street;
        // const city = x.city;

        const date = new Date(x.day);
        const dateStr = `${date.getDate()}.${date.getMonth()}.${date.getFullYear()} ${x.time.substr(0, 5)}`;

        return {
          fullName: `${x.userFirstName} ${x.userLastName}`,
          dateStr,
          ...x
        };
      });
    }
  }
}
</script>

<style lang="scss" scoped>
  #delivery {
    flex: 1;
    display: flex;
    overflow: hidden;
  }
</style>
