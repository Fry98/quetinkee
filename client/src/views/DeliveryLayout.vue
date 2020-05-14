<template>
  <div id='delivery'>
    <order-details
      :open='open'
      :orders='ordersComp'
      :index='index'
      @close='closeDetails'
    ></order-details>
    <delivery-sidebar
      @pending='loadPending'
      @ongoing='loadOngoing'
      @completed='loadCompleted'
    ></delivery-sidebar>
    <delivery-table
    :title='title'
    :selected='selected'
    :orders='ordersComp'
    @remove='removeItem'
    @details='openDetails'
    ></delivery-table>
  </div>
</template>

<script>
import DeliverySidebar from '../components/DeliverySidebar';
import DeliveryTable from './DeliveryTable';
import OrderDetails from '../components/OrderDetails';

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
      index: 0,
      orders: [
        {
          id: 5,
          firstName: 'Roman',
          lastName: 'Toman',
          street: 'Ulicova 234/7',
          city: 'Praha 7',
          zip: 13412,
          time: '13:30',
          contents: [
            '2 x První kytice',
            '1 x Jiná kytice'
          ]
        },
        {
          id: 7,
          firstName: 'Alex',
          lastName: 'Nguyen',
          street: 'Ulicova 234/7',
          city: 'Praha 7',
          zip: 13412,
          time: '13:30',
          contents: [
            '2 x První kytice',
            '2 x Jiná kytice',
            '4 x Item',
            '2 x První kytice',
            '2 x Jiná kytice',
            '4 x Item',
            '2 x První kytice',
            '2 x Jiná kytice',
            '4 x Item',
          ]
        },
        {
          id: 12,
          firstName: 'Vítek',
          lastName: 'Cheatek',
          street: 'Ulicova 234/7',
          city: 'Praha 7',
          zip: 13412,
          time: '13:30',
          contents: [
            '7 x První kytice',
            '2 x Jiná kytice'
          ]
        },
        {
          id: 24,
          firstName: 'Honza',
          lastName: 'Rykl',
          street: 'Ulicova 234/7',
          city: 'Praha 7',
          zip: 13412,
          time: '13:30',
          contents: [
            '3 x První kytice',
            '4 x Jiná kytice'
          ]
        },
      ]
    };
  },
  mounted() {
    this.loadPending();
  },
  methods: {
    loadPending() {
      if (this.category === 0) return;
      this.category = 0;
      this.title = 'Čekající objednávky';
      this.selected = 0;
    },
    loadOngoing() {
      if (this.category === 1) return;
      this.category = 1;
      this.title = 'Probíhající objednávky';
      this.selected = 1;
    },
    loadCompleted() {
      if (this.category === 2) return;
      this.category = 2;
      this.title = 'Dokončené objednávky';
      this.selected = 2;
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
        const zipStr = x.zip.toString();
        const zip = zipStr.substr(0, 3) + " " + zipStr.substr(3, 2);
        const street = x.street;
        const city = x.city;

        return {
          address: `${street}, ${zip} ${city}`,
          fullName: `${x.firstName} ${x.lastName}`,
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
