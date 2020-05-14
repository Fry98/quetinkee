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
        <div class='btn' @click='createFlower'>+</div>
      </div>
      <div class='existing-flowers'>
        <div v-for='flower in flowers' :key='flower.id' class='flower'>
          <span>{{ flower.name }}</span>
          <font-awesome-icon
              class='icon'
              :icon="['far', 'trash-alt']"
              @click='handleRemoveClick(flower)'
          ></font-awesome-icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Confirm from '../components/Confirm';
  import axios from "axios";

  export default {
    name: "ManageFlowers",
    components: {
      Confirm
    },
    created() {
      this.loadData();
    },
    data() {
      return {
        popup: null,
        newFlower: '',
        flowers: [],
        toRemove: null
      }
    },
    methods: {
      async loadData() {
        try {
          const res = await axios({
            method: 'get',
            url: '/api/flowers/list'
          });
          this.flowers = res.data;
        } catch(err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      async createFlower() {
        if (this.newFlower) {
          try {
            const res = await axios({
              method: 'POST',
              url: '/api/flowers',
              data: {
                name: this.newFlower,
                price: 50
              }
            });
            if (res.data) {
              this.flowers.push({id: res.data, name: this.newFlower});
              this.newFlower = ''
            }
          } catch(err) {
            this.$store.dispatch('openModal', err.response.data.message);
          }
        }
      },
      handleRemoveClick(flower) {
        this.toRemove = flower.id;
        this.popup = `Opravdu chcete smazat květinu <strong>${flower.name}</strong>?`;
      },
      async removeFlower() {
        try {
          const res = await axios({
            method: 'DELETE',
            url: `/api/flowers/${this.toRemove}`
          });
          if (res.status === 200) {
            const removeIndex = this.flowers.findIndex(flower => flower.id === this.toRemove);
            this.flowers.splice(removeIndex, 1);
            alert("Smazáno");
          }
        } catch(err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      cancel() {
        this.popup = null;
        alert("Zrušeno");
      },
      confirm() {
        this.popup = null;
        this.removeFlower();
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
