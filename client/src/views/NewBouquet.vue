<template>
  <div id='new-bouquet'>
    <div class='nb-wrap'>
      <form @submit='handleSubmit($event)'>
        <h1>{{ this.id ? 'Úprava' : 'Nová' }} kytice</h1>
        <label>
          <span>Název </span>
          <input v-model='bouquetName' type='text'>
        </label>
        <label>
          <span>Kategorie </span>
          <select ref='sel' name='category' v-model='selectedCategories' multiple>
            <option
                v-for='category in categories'
                @mousedown='handleOptionMousedown(category, $event)'
                :value='category.id'
                :key='category.id'
            >{{ category.name }}</option>
          </select>
        </label>
        <label>
          <span>Cena </span>
          <input class='price' v-model='price' @blur='validatePrice()'>
        </label>
        <label>
          <span>Barvy </span>
          <div id='colors'>
            <div class="color-checkbox" style='background-color: #FFF' @click='handleColorClick(0)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[0]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #FFF281' @click='handleColorClick(1)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[1]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #FFB74A' @click='handleColorClick(2)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[2]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #FF4A4A' @click='handleColorClick(3)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[3]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #FF63AE' @click='handleColorClick(4)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[4]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #CB89FF' @click='handleColorClick(5)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[5]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #7785FF' @click='handleColorClick(6)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[6]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #8EEBFF' @click='handleColorClick(7)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[7]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #7AFFAF' @click='handleColorClick(8)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[8]}'></font-awesome-icon>
            </div>
            <div class="color-checkbox" style='background-color: #82BD66' @click='handleColorClick(9)'>
              <font-awesome-icon icon='check' :class='{"clr-show": selectedColors[9]}'></font-awesome-icon>
            </div>
          </div>
        </label>
        <label>
          <span>Velikost </span>
          <div id='sizes'>
            <span :class='{"checkbox-input": true, selected: selectedSize === "s"}' @click='handleSizeClick("s")'>
              Malá
            </span>
            <span :class='{"checkbox-input": true, selected: selectedSize === "m"}' @click='handleSizeClick("m")'>
              Střední
            </span>
            <span :class='{"checkbox-input": true, selected: selectedSize === "l"}' @click='handleSizeClick("l")'>
              Velká
            </span>
          </div>
        </label>
        <label>
          <span>Foto </span>
          <input type='file' accept='image/*' @change='handleImageUpload($event)'>
        </label>
        <label>
          <span>Popis </span>
          <textarea v-model='description'></textarea>
        </label>
        <label>
          <span>Květiny </span>
          <select name='flowers' v-model='selectedFlower' @change='handleFlowerSelect'>
            <option value='' selected disabled hidden>Vyberte květiny...</option>
            <option v-for='option in flowers' :value='option.id' :key='option.id'>{{ option.name }}</option>
          </select>
        </label>
        <div class='warning' v-if='selectedFlowers.length === 0'>Vyberte alespoň jednu květinu.</div>
        <div class='flower' v-for='flower in selectedFlowers' :key="flower.id">
          <flowerLine :flower='flower'
                      @count-change='handleFlowerCountChange'
                      @remove='removeFlower'/>
        </div>
        <button class='btn' :class='{ "disabled": saveIsDisabled }' :disabled='saveIsDisabled' type='submit'>Uložit
        </button>
      </form>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import FlowerLine from "../components/FlowerLine";

  export default {
    name: "NewBouquet",
    components: {FlowerLine},
    data() {
      return {
        bouquetName: '',
        price: null,
        categories: [],
        selectedCategories: [],
        description: '',
        image: null,
        flowers: [],
        selectedFlowers: [],
        selectedFlower: '',
        selectedColors: [false, false, false, false, false, false, false, false, false, false],
        selectedSize: '',
      }
    },
    props: ['id'],
    created() {
      this.loadData();
    },
    watch: {
      id() {
        this.deleteAllFields();
      },
      price(newVal, oldVal) {
        if (newVal && typeof newVal !== 'number') {
          if (newVal.endsWith('.') || newVal.endsWith(',')) {
            if (newVal.length === 1 || newVal.match(/[.,]/g).length > 1) {
              this.price = oldVal;
            }
            return;
          }
          const match = newVal.match(/\d{1,6}[.,]\d{1,2}|\d{1,6}/g);
          if (!match || !match.includes(newVal)) {
            this.price = oldVal;
          }
        }
      }
    },
    computed: {
      saveIsDisabled() {
        return this.selectedFlowers.length < 1 ||
            this.selectedCategories.length < 1 ||
            !this.bouquetName ||
            !this.price ||
            (!this.id && !this.image) ||
            !this.description ||
            this.selectedSize === null ||
            !this.selectedColors.find(value => value === true);
      }
    },
    methods: {
      async loadData() {
        try {
          const promises = [];
          promises.push(axios({
            method: 'GET',
            url: '/api/categories/list'
          }));
          promises.push(axios({
            method: 'GET',
            url: '/api/flowers/list'
          }));
          if (this.id) {
            promises.push(axios({
              method: 'GET',
              url: `/api/bouquets/${this.id}`
            }))
          }
          const res = await Promise.all(promises);
          this.categories = res[0].data;
          this.flowers = res[1].data;
          if (this.id) {
            this.loadBouquet(res[2].data);
          }
        } catch (err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      loadBouquet(bouquetJson) {
        this.bouquetName = bouquetJson.bouquet.name;
        this.description = bouquetJson.bouquet.perex;
        this.price = bouquetJson.bouquet.price.toString();
        this.selectedSize = bouquetJson.bouquet.size;
        this.active = bouquetJson.bouquet.active;
        this.selectedCategories = bouquetJson.keyCategories;
        this.selectedCategories.map(String);
        bouquetJson.keyColors.forEach(color => this.selectedColors[color] = true);
        Object.getOwnPropertyNames(bouquetJson.keyFlowerCount).forEach(key => {
          const selectedFlower = this.flowers.find(flower => flower.id === Number(key));
          selectedFlower.count = bouquetJson.keyFlowerCount[key];
          this.selectedFlowers.push(selectedFlower);
        });
      },
      handleSubmit(event) {
        event.stopPropagation();
        event.preventDefault();

        const bouquetJSON = JSON.stringify({
          bouquet: {
            name: this.bouquetName,
            perex: this.description,
            price: parseFloat(this.price),
            size: this.selectedSize,
            active: true
          },
          keyCategories: this.selectedCategories.map(x => Number(x)),
          keyColors: this.selectedColors.reduce((out, bool, index) => bool ? out.concat(index) : out, []),
          keyFlowerCount: this.selectedFlowers.reduce((map, flower) => {
            map[flower.id] = Number(flower.count);
            return map;
          }, {})
        });
        const formData = new FormData();
        formData.append('bouquet', new Blob([bouquetJSON], { type: 'application/json' }));
        if (this.image) {
          formData.append('blob', this.image);
        }

        if (this.id) {
          this.updateBouquet(formData);
        } else {
          this.createBouquet(formData);
          this.deleteAllFields();
        }
      },
      async createBouquet(formData) {
        try {
          await axios({
            method: 'POST',
            url: '/api/bouquets',
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            data: formData
          });
        } catch (err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      async updateBouquet(formData) {
        try {
          const res = await axios({
            method: 'PATCH',
            url: `/api/bouquets/${this.id}`,
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            data: formData
          });
          if (res.status === 200) {
            this.$store.dispatch('openModal', 'Kytice byla uložena.');
          }
        } catch (err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      handleImageUpload(e) {
        this.image = e.target.files[0];
      },
      handleFlowerSelect() {
        const selectedFlower = this.flowers.find(flower => flower.id === this.selectedFlower);
        selectedFlower.count = 1;
        if (!this.selectedFlowers.find(flower => flower.id === this.selectedFlower)) {
          this.selectedFlowers.push(selectedFlower);
        }
        this.selectedFlower = '';
      },
      removeFlower(flowerId) {
        this.selectedFlowers = this.selectedFlowers.filter((value => value.id !== flowerId));
      },
      handleSizeClick(size) {
        this.selectedSize = size
      },
      handleColorClick(color) {
        this.$set(this.selectedColors, color, !this.selectedColors[color]);
      },
      handleOptionMousedown(category, event) {
        event.preventDefault();
        event.stopPropagation();
        this.$refs.sel.focus();
        this.toggleCategory(category);
      },
      toggleCategory(category) {
        const i = this.selectedCategories.indexOf(category.id);
        if (i > -1) {
          this.selectedCategories.splice(i, 1);
        } else {
          this.selectedCategories.push(category.id);
        }
      },
      deleteAllFields() {
        this.bouquetName = '';
        this.price = null;
        this.selectedCategories = [];
        this.description = '';
        this.image = null;
        this.selectedFlowers = [];
        this.selectedFlower = '';
        this.selectedColors = [false, false, false, false, false, false, false, false, false, false];
        this.selectedSize = '';
      },
      handleFlowerCountChange(flower) {
        const index = this.selectedFlowers.findIndex(f => f.id === flower.id);
        this.$set(this.selectedFlowers, index, flower);
      },
      validatePrice() {
        if (this.price.endsWith('.') || this.price.endsWith(',')) {
          this.price = this.price.slice(0, -1);
        }
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  .btn {
    margin-top: 15px;
    align-self: center;

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

  form {
    display: flex;
    flex-direction: column;

    label {
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      & > span {
        width: 130px;
        float: left;
        font-weight: bold;
      }

      input, select, textarea {
        box-sizing: border-box;
        width: 220px;
        font-size: 1.2em;
        box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
        border-radius: 7px;
        padding: 3px 10px 3px 10px;
        border: solid 1px white;
        -moz-appearance: textfield;

        &[type='file'] {
          font-size: 1em;
        }

        &:focus {
          outline: none;
          border: solid 1px $mainOrange;
        }
      }

      textarea {
        font-size: 1em;
      }

      #sizes {
        display: flex;
        width: 220px;
        justify-content: stretch;

        .checkbox-input {
          flex-grow: 1;
          user-select: none;
          color: $darkGrey;
          transition: .15s;
          cursor: pointer;
          padding: 6px 8px;
          background-color: white;
          box-shadow: 0 1px 2px rgba(0, 0, 0, 0.40);
          text-align: center;
          font-weight: bold;

          &.selected {
            background-color: $mainBlue;
            color: white;
          }

          &:last-child {
            border-radius: 0 7px 7px 0;
          }

          &:first-child {
            border-radius: 7px 0 0 7px;
          }

          &:hover:not(.selected) {
            background-color: $almostWhite;
          }
        }
      }

      #colors {
        justify-content: space-between;
        display: flex;
        flex-wrap: wrap;
        flex-direction: row;
        width: 220px;

        .clr-show {
          opacity: 1 !important;
          transform: none !important;
        }

        .color-checkbox {
          cursor: pointer;
          user-select: none;
          margin: 4px;
          display: flex;
          justify-content: center;
          align-items: center;
          height: 35px;
          width: 35px;
          color: white;
          font-size: 1.2em;
          box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
          border-radius: 7px;
          transition: .2s;
          /*flex: 1 0 17%;*/
          &:hover {
            filter: brightness(90%);
          }

          svg {
            stroke: black;
            stroke-width: 20;
            opacity: 0;
            transform: translateY(10px);
            transition-duration: .2s;
          }
        }
      }
    }

    h1 {
      text-align: center;
      padding: 0;
      margin: 0 0 15px;
    }
  }
</style>
