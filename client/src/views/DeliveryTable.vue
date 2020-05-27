<template>
  <div
    class='del-table'
    v-infinite-scroll='fetchOrders'
    infinite-scroll-disabled='scrollDisabled'
    infinite-scroll-distance='10'
    infinite-scroll-listen-for-event='checkScroll'
  >
    <div class="del-title">{{ title }}</div>
    <table>
      <thead>
        <td>ID</td>
        <td>Jméno</td>
        <td>Adresa</td>
        <td>Čas doručení</td>
        <td>Detaily</td>
        <td>Stav</td>
      </thead>
      <tr v-for='(item, i) in orders' :key='item.id'>
        <td class='id'>{{ item.id }}</td>
        <td class='name'>{{ item.fullName }}</td>
        <td class='street'>{{ item.address }}</td>
        <td class='time'>{{ item.dateStr }}</td>
        <td class='details'><button @click='$emit("details", i)'>Zobrazit detaily</button></td>
        <td class='state-col'>
          <select class='state' @change='updateOrderStatus($event, i)' v-if='selected < 2'>
            <option :selected='selected === 0' value='0' v-if='selected < 1'>Čekající</option>
            <option :selected='selected === 1' value='1' v-if='selected < 2'>Probíhající</option>
            <option :selected='selected === 2' value='2'>Dokončená</option>
          </select>
          <span class='done' v-else>Dokončená</span>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    title: {
      type: String,
      default: 'Objednávky'
    },
    orders: {
      type: Array,
      default: []
    },
    selected: {
      type: Number,
      default: 0
    },
    ongoing: {
      type: Boolean,
      default: false
    },
    stopLoad: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    fetchOrders() {
      this.$emit('fetch');
    },
    updateOrderStatus(e, i) {
      let status = null;
      switch (Number(e.target.value)) {
        case 0:
          status = 'NEW';
          break;
        case 1:
          status = 'PENDING';
          break;
        case 2:
          status = 'FINISH';
          break;
      }

      axios({
        url: `/api/orders/${this.orders[i].id}/${status}`,
        method: 'PATCH'
      }).then(() => {
        this.$emit("remove", i);
      }).catch(() => {
        this.$store.dispatch('openModal', "Chyba při změně kategorie");
      });
    }
  },
  watch: {
    orders() {
      this.$emit('checkScroll');
    },
    selected() {
      this.$emit('checkScroll');
    }
  },
  computed: {
    scrollDisabled() {
      return this.stopLoad || this.ongoing;
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .del-table {
    padding: 15px 20px;
    width: 100%;
    overflow-y: auto;
  }

  .del-title {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 3px;
  }

  .state {
    width: 100%;
  }

  .details > button {
    width: 100%;
    height: 100%;
    border: none;
    background: $mainBlue;
    color: white;
    border-radius: 20px;
    padding: 3px 0px;
    cursor: pointer;
    transition-duration: .2s;
    &:hover {
      background: $darkBlue;
    }
  }

  table {
    border-collapse: collapse;
    width: 100%;
  }

  thead {
    font-weight: bold;
    text-align: center;
  }

  td {
    border: 1px solid black;
  }

  .id {
    width: 5%;
    text-align: center;
  }

  .name {
    width: 23%;
    padding-left: 10px;
  }

  .street {
    width: 32%;
    padding-left: 10px;
  }

  .time {
    width: 13%;
    text-align: center;
  }

  .details {
    width: 12%;
  }

  .state-col {
    width: 15%;
  }

  .done {
    font-size: .85em;
    margin-left: 3px;
  }
</style>
