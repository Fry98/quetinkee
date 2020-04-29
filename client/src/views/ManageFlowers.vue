<template>
  <div id='manage-flowers'>
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
        <div v-for='flower in flowers' class='flower'>
          <span>{{ flower }}</span>
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
  export default {
    name: "ManageFlowers",
    data() {
      return {
        newFlower: '',
        flowers: ['Bílá růže', 'Červená růže', 'Gerbera', 'Modrá růže']
      }
    },
    methods: {
      removeFlower() {
        // todo call api to remove flower
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
    border: transparent;
    font-size: 1.2em;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 7px;
    padding: 3px 10px 3px 10px;
  }

  .btn {
    display: inline;
    margin-left: 5px;
  }
</style>