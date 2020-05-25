<template>
  <div id='missing-flowers'>
    <div class='content-wrap'>
      <font-awesome-icon title='Vytisknout' class='print-btn' icon='print' @click='print'></font-awesome-icon>
      <table>
        <thead>
        <tr>
          <th>Název květiny</th>
          <th>Počet k obědnání</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for='flower in flowers' :key='flower.id'>
          <td>{{ flower.name }}</td>
          <td>{{ flower.restock }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: "MissingFlowers",
    data() {
      return {
        flowers: []
      }
    },
    async created() {
      try {
        const res = await axios({
          method: 'get',
          url: '/api/inventory/missing'
        });
        this.flowers = res.data;
      } catch(err) {
        this.$store.dispatch('openModal', err.response.data.message);
      }
    },
    methods: {
      print() {
        window.print();
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";

  .content-wrap {
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  table {
    margin-top: 5px;
    margin-bottom: 10px;
    border-collapse: collapse;
  }

  th {
    padding: 1px 5px;
  }

  td {
    text-align: center;
    vertical-align: center;
    border-top: solid black 1px;
    padding: 3px;
  }

  .print-btn {
    align-self: flex-end;
    cursor: pointer;
  }

  @media print {
    #missing-flowers {
      visibility: hidden;
    }
    table {
      visibility: visible;
    }
  }
</style>