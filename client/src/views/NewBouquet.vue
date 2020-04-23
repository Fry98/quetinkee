<template>
  <div id='new-bouquet'>
    <div class='nb-wrap'>
      <div class='form'>
        <h1>Nová kytice</h1>
        <label>
          <span>Název </span>
          <input v-model='bouquetName' type='text'>
        </label>
        <label>
          <span>Kategorie </span>
          <select name='category' v-model='selectedCategories' multiple>
            <option v-for='option in categories'>{{ option }}</option>
          </select>
        </label>
        <label>
          <span>Cena </span>
          <input v-model='price' type='number' step='.01'>
        </label>
        <label>
          <span>Foto </span>
          <!-- TODO add image uploader -->
          <div class='photo-uploader'>+</div>
        </label>
        <label>
          <span>Květiny </span>
          <select name='flowers' v-model='selectedFlower' @change='handleFlowerSelect'>
            <option value='' selected disabled hidden>Vyberte květiny...</option>
            <option v-for='option in flowers'>{{ option }}</option>
          </select>
        </label>
        <div class='warning' v-if='selectedFlowers.length === 0'>Vyberte alespoň jednu květinu.</div>
        <div class='flower' v-for='flower in selectedFlowers'>
            <span class='flower-name'>
              {{ flower.name }}
              <font-awesome-icon
                  class='icon'
                  :icon="['far', 'trash-alt']"
                  @click='removeFlower(flower.name)'
              ></font-awesome-icon>
            </span>
          <input type='number' v-model='flower.count' min='1'>
        </div>
        <div class='btn' :class='{ "disabled": saveIsDisabled }'>Uložit</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "NewBouquet",
    data() {
      return {
        bouquetName: '',
        price: null,
        categories: ['Nové', 'V akci', 'Sezonní nabídka', 'Narozeniny', 'Pohřeb'],
        selectedCategories: ['Nové'],
        flowers: ['Bílá růže', 'Červená růže', 'Modrá růže', 'Gerbera'],
        selectedFlowers: [],
        selectedFlower: ''
      }
    },
    computed: {
      saveIsDisabled() {
        return this.selectedFlowers.length < 1 || this.selectedCategories.length < 1 || this.bouquetName === '' || this.price === '';
      }
    },
    methods: {
      handleFlowerSelect() {
        if (!this.selectedFlowers.find(flower => flower.name === this.selectedFlower)) {
          this.selectedFlowers.push({name: this.selectedFlower, count: 1});
        }
        this.selectedFlower = ''
      },
      removeFlower(flowerName) {
        this.selectedFlowers = this.selectedFlowers.filter((value => value.name !== flowerName));
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  .btn {
    margin-top: 15px;
    &.disabled {
      background-color: $lightGrey;
      color: $darkGrey;
      cursor: default;
    }
  }

  .photo-uploader {
    width: 120px;
    height: 120px;
    border-radius: 13px;
    background-color: $lightGrey;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 4em;
    color: #fff;
    cursor: pointer;
  }

  .flower {
    border-radius: 15px;
    display: flex;
    background-color: $lightGrey;
    margin-bottom: 8px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);

    .flower-name {
      flex: 20;
      background-color: #fff;
      padding: 6px 10px 6px 10px;
      font-weight: bold;
      border-radius: 15px;

      .icon {
        margin-right: 4px;
        font-size: 1.1em;
        float: right;
        cursor: pointer;
      }
    }

    input {
      font-size: 1em;
      width: 1px;
      font-weight: bold;
      flex: 3;
      background-color: transparent;
      border: transparent;
      margin-right: 5px;
      text-align: center;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }
  }

  .warning {
    text-align: center;
    font-weight: bold;
    color: red;
  }

  #new-bouquet {
    display: flex;
    justify-content: center;
  }

  .nb-wrap {
    display: flex;
    justify-content: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  .form label {
    display: flex;
    align-items: center;
    margin-bottom: 10px;

    span {
      width: 130px;
      float: left;
      font-weight: bold;
    }

    input, select {
      width: 200px;
      border: transparent;
      font-size: 1.2em;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      border-radius: 7px;
      padding: 3px 10px 3px 10px;
    }

    select {
      width: 220px;
    }
  }

  h1 {
    text-align: center;
    padding: 0;
    margin: 0 0 15px;
  }
</style>