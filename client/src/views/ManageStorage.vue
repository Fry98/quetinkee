<template>
  <div id='manage-storage'>
    <form class='content-wrap' @submit='handleSubmit'>
      <h1>Správa skladu</h1>
      <button class='btn'>Uložit stav</button>
      <input type='text' class='search' v-model='query' placeholder='Vyhledávat v květinách' @keydown='cancelSubmit($event)'>
      <table>
        <thead>
        <tr>
          <th>Název květiny</th>
          <th>Minimální počet</th>
          <th>Počet na skladu</th>
          <th>Rezervováno</th>
        </tr>
        </thead>
        <tbody>
        <tr :class='{"changed": isChanged(flower)}' v-for='flower in filteredFlowers' :key='flower.id'>
          <td>{{ flower.name }}</td>
          <td>
            <span class='countChange' @click='flower.minCount -= 1'><font-awesome-icon icon="minus"/></span>
            <input type='number'
                   v-model='flower.minCount'
                   @blur='flower.minCount = Number(flower.minCount)'
                   @keydown='cancelSubmit($event)'
                   @focus='$event.target.select()'>
            <span class='countChange' @click='flower.minCount += 1'><font-awesome-icon icon="plus"/></span>
          </td>
          <td>
            <span class='countChange' @click='flower.count -= 1'><font-awesome-icon icon="minus"/></span>
            <input type='number'
                   v-model='flower.count'
                   @blur='flower.count = Number(flower.count)'
                   @keydown='cancelSubmit($event)'
                   @focus='$event.target.select()'>
            <span class='countChange' @click='flower.count += 1'><font-awesome-icon icon="plus"/></span>
          </td>
          <td>
            {{ flower.reserved }}
          </td>
        </tr>
        </tbody>
      </table>
      <button class='btn'>Uložit stav</button>
    </form>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    name: "ManageStorage",

    data() {
      return {
        query: '',
        flowers: [],
        originalCount: {},
        originalMinCount: {}
      }
    },
    created() {
      this.loadData();
    },
    computed: {
      filteredFlowers() {
        return this.flowers.filter(flower => flower.name.toLowerCase().includes(this.query.toLowerCase()));
      }
    },
    methods: {
      async loadData() {
        try {
          const res = await axios({
            method: 'get',
            url: '/api/inventory'
          });
          this.flowers = res.data.content;
        } catch(err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
        this.setOriginalCounts(this.flowers);
      },
      setOriginalCounts(from) {
        this.originalCount = from.reduce((map, flower) => {
          map[flower.id] = Number(flower.count);
          return map;
        }, {});
        this.originalMinCount = from.reduce((map, flower) => {
          map[flower.id] = Number(flower.minCount);
          return map;
        }, {});
      },
      isChanged(flower) {
        return flower.count !== this.originalCount[flower.id] || flower.minCount !== this.originalMinCount[flower.id];
      },
      cancelSubmit(e) { // so that form doesnt get submitted when user presses Enter in input field
        if (e.key === 'Enter') {
          e.preventDefault();
          e.stopPropagation();
        }
      },
      handleSubmit(e) {
        e.preventDefault();
        e.stopPropagation();

        const data = this.flowers.reduce((map, flower) => {
          if (this.isChanged(flower)) {
            map[flower.id] = {free: flower.count - flower.reserved, minCount: flower.minCount};
          }
          return map;
        }, {});
        if(!data) return;
        axios({
          method: 'patch',
          url: '/api/inventory',
          data
        }).then(res => this.setOriginalCounts(res.data));
      }
    }
  }
</script>

<style scoped lang='scss'>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  .search {
    margin-top: 10px;
    font-size: .9em;
    padding: 7px 10px;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    box-sizing: border-box;
    border: 1px solid white;

    &:focus {
      outline: none;
      border: solid 1px $mainOrange;
    }
  }

  .countChange {
    user-select: none;
    font-size: 10px;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    /*border: solid 2px black;*/
    background-color: $mainBlue;
    border-radius: 50%;
    height: 20px;
    width: 20px;
    color: #fff;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);

    &:hover {
      background-color: $darkBlue;
      transition: .2s;
    }
  }

  input {
    outline: none;
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
    border: solid black 1px;
    padding: 3px;

    input {
      text-align: center;
      width: 35px;
      font-size: 1em;
      background-color: transparent;
      border: transparent;
    }
  }

  #manage-storage {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .changed {
    background-color: lightgoldenrodyellow;
  }

  .content-wrap {
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  h1 {
    text-align: center;
    padding: 0;
    margin: 0 0 15px;
  }

  /* Chrome, Safari, Edge, Opera */
  input::-webkit-outer-spin-button,
  input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  /* Firefox */
  input[type=number] {
    -moz-appearance: textfield;
  }
</style>