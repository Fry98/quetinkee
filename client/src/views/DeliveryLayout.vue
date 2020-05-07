<template>
  <div id='delivery'>
    <order-details :open='open' @close='closeDetails'></order-details>
    <delivery-sidebar
      @pending='loadPending'
      @ongoing='loadOngoing'
      @completed='loadCompleted'
    ></delivery-sidebar>
    <delivery-table
    :title='title'
    :selected='selected'
    v-model='orders'
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
      orders: [
        {
          id: 5,
          name: 'Roman Toman',
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
          name: 'Roman Toman',
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
          id: 12,
          name: 'Roman Toman',
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
          id: 24,
          name: 'Roman Toman',
          street: 'Ulicova 234/7',
          city: 'Praha 7',
          zip: 13412,
          time: '13:30',
          contents: [
            '2 x První kytice',
            '1 x Jiná kytice'
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
    },
    closeDetails() {
      this.open = false;
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
