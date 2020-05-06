<template>
  <div id='manage-storage'>
    <form class='content-wrap'>
      <h1>Správa skladu</h1>
      <button class='btn'>Uložit stav</button>
        <input type='text' class='search' v-model='query' placeholder='Vyhledávat v květinách' @keydown='cancelSubmit($event)'>
      <table>
        <thead>
        <tr>
          <th>Název květiny</th>
          <th>Počet na skladu</th>
        </tr>
        </thead>
        <tbody>
        <tr :class='{"changed": flower.oldCount !== flower.newCount}' v-for='flower in filteredFlowers' :key='flower.id'>
          <td>{{ flower.name }}</td>
          <td>
            <span class='countChange' @click='changeCount(flower, -1)'><font-awesome-icon icon="minus"/></span>
            <input type='number'
                   v-model='flower.newCount'
                   @blur='handleFlowerCountBlur(flower)'
                   @keydown='cancelSubmit($event)'
                   @focus='$event.target.select()'>
            <span class='countChange' @click='changeCount(flower, 1)'><font-awesome-icon icon="plus"/></span>
          </td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</template>

<script>
  export default {
    name: "ManageStorage",

    data() {
      return {
        query: '',
        flowers: [
          {id: 0, name: 'Kvetina 1', oldCount: 10, newCount: 10},
          {id: 1, name: 'Kvetina 2', oldCount: 10, newCount: 10},
        ]
      }
    },

    computed: {
      filteredFlowers() {
        return this.flowers.filter(flower => flower.name.toLowerCase().includes(this.query.toLowerCase()));
      }
    },

    methods: {
      handleFlowerCountBlur(flower) {
        console.log(flower.newCount);
        this.flowers.find(el => el.name === flower.name).newCount = Number(flower.newCount);
      },
      changeCount(flower, by) {
        this.flowers.find(el => el.name === flower.name).newCount += by;
      },
      cancelSubmit(e) { // so that form doesnt get submitted when user presses Enter in input field
        if (e.key === 'Enter') {
          e.preventDefault();
          e.stopPropagation();
        }
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