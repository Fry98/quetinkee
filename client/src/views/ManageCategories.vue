<template>
  <div id='manage-categories'>
    <confirm :msg='popup' @cancel='cancel' @confirm='confirm'></confirm>
    <div class='mc-wrap'>
      <h1>Správa kategorií</h1>
      <div class='new-cat'>
        <label>
          <span>Nová kategorie: </span>
          <input type='text' placeholder='Název kategorie' v-model='newCategory'>
        </label>
        <div class='btn'>+</div>
      </div>
      <div class='existing-ctgs'>
        <div v-for='ctg in categories' :key='ctg.id' class='ctg'>
          <span>{{ ctg.name }}</span>
          <font-awesome-icon
              class='icon'
              :icon="['far', 'trash-alt']"
              @click='removeFlower(ctg)'
          ></font-awesome-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Confirm from '../components/Confirm';

  export default {
    name: "ManageCategories",
    components: {
      Confirm
    },
    data() {
      return {
        popup: null,
        newCategory: '',
        categories: [
          {
            id: 0,
            name: 'Sezonní nabídka'
          },
          {
            id: 1,
            name: 'Narozeniny'
          },
          {
            id: 2,
            name: 'Pohřeb'
          }
        ]
      }
    },
    methods: {
      removeFlower(flower) {
        this.popup = `Opravdu chcete smazat květinu <strong>${flower.name}</strong>?`;
      },
      cancel() {
        this.popup = null;
        alert("Zrušeno");
      },
      confirm() {
        this.popup = null;
        alert("Smazáno");
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #manage-categories {
    display: flex;
    justify-content: center;
  }

  .mc-wrap {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  .new-cat {
    display: flex;
  }

  .existing-ctgs {
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .ctg {
      width: 100%;
      margin-bottom: 5px;
      background-color: #fff;
      padding: 3px 7px;
      border-radius: 7px;
      transition: .2s;
      display: flex;
      align-items: center;
      justify-content: space-between;
      &:hover {
        background-color: $lightGrey;
      }
      .icon {
        margin-left: 15px;
        cursor: pointer;
        transition: .2s;
        &:hover {
          color: red;
        }
      }
    }
  }

  h1 {
    display: block;
    text-align: center;
    padding: 0;
    margin: 0 0 15px;
  }

  label {
    display: flex;
    align-items: center;
  }

  input {
    margin-left: 5px;
    font-size: 1.2em;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 7px;
    padding: 3px 10px 3px 10px;
    border: solid 1px white;

    &:focus {
      outline: none;
      border: solid 1px $mainOrange;
    }
  }

  .btn {
    display: inline;
    margin-left: 5px;
  }
</style>
