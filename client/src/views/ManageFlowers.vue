<template>
  <div id='manage-flowers'>
    <confirm :msg='popup' @cancel='cancel' @confirm='confirm'></confirm>
    <div class='mf-wrap'>
      <h1>Správa květin</h1>
      <div class='new-flower'>
        <label>
          <span>Nová květina: </span>
          <input type='text' placeholder='Název květiny' v-model='newFlower'>
        </label>
        <div class='btn'>+</div>
      </div>
      <div class='existing-flowers'>
        <div v-for='flower in flowers' :key='flower.id' class='flower'>
          <span>{{ flower.name }}</span>
          <font-awesome-icon
              class='icon'
              :icon="['far', 'trash-alt']"
              @click='removeFlower(flower)'
          ></font-awesome-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Confirm from '../components/Confirm';

  export default {
    name: "ManageFlowers",
    components: {
      Confirm
    },
    data() {
      return {
        popup: null,
        newFlower: '',
        flowers: [
          {
            id: 0,
            name: 'Bílá růže'
          },
          {
            id: 1,
            name: 'Červená růže'
          },
          {
            id: 2,
            name: 'Gerbera'
          },
          {
            id: 3,
            name: 'Modrá růže'
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

  #manage-flowers {
    display: flex;
    justify-content: center;
  }

  .mf-wrap {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  .new-flower {
    display: flex;
  }

  .existing-flowers {
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .flower {
      width: 100%;
      padding: 3px;
      border-radius: 7px;
      transition: .2s;
      display: flex;
      justify-content: space-between;
      &:hover {
        background-color: $lightGrey;
      }
      .icon {
        margin-left: 15px;
        cursor: pointer;
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
