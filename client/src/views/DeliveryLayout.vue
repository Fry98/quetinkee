<template>
  <div id='delivery'>
    <delivery-sidebar
      @pending='loadPending'
      @ongoing='loadOngoing'
      @completed='loadCompleted'
    ></delivery-sidebar>
    <delivery-table :title='title'></delivery-table>
  </div>
</template>

<script>
import DeliverySidebar from '../components/DeliverySidebar';
import DeliveryTable from './DeliveryTable';

export default {
  components: {
    DeliverySidebar,
    DeliveryTable
  },
  data() {
    return {
      category: 0,
      title: 'Čekající objednávky'
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
    },
    loadOngoing() {
      if (this.category === 1) return;
      this.category = 1;
      this.title = 'Probíhající objednávky';
    },
    loadCompleted() {
      if (this.category === 2) return;
      this.category = 2;
      this.title = 'Dokončené objednávky';
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
