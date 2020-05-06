<template>
  <div id='new-bouquet'>
    <div class='nb-wrap'>
      <form>
        <h1>Nová kytice</h1>
        <label>
          <span>Název </span>
          <input v-model='bouquetName' type='text'>
        </label>
        <label>
          <span>Kategorie </span>
          <select ref='sel' name='category' v-model='selectedCategories' multiple>
            <option v-for='category in categories' @mousedown='handleOptionMousedown(category, $event)'>{{ category }}</option>
          </select>
        </label>
        <label>
          <span>Cena </span>
          <input class='price' v-model='price' type='number' step='.01'>
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
            <span :class='{"checkbox-input": true, selected: selectedSize === 0}' @click='handleSizeClick(0)'>
              Malá
            </span>
            <span :class='{"checkbox-input": true, selected: selectedSize === 1}' @click='handleSizeClick(1)'>
              Střední
            </span>
            <span :class='{"checkbox-input": true, selected: selectedSize === 2}' @click='handleSizeClick(2)'>
              Velká
            </span>
          </div>
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
              <font-awesome-icomn
                  class='icon'
                  :icon="['far', 'trash-alt']"
                  @click='removeFlower(flower.name)'
              ></font-awesome-icomn>
            </span>
          <input type='number' v-model='flower.count' min='1' @focus='$event.target.select()'>
        </div>
        <button class='btn' :class='{ "disabled": saveIsDisabled }' :disabled='saveIsDisabled' type='submit'>Uložit
        </button>
      </form>
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
        selectedFlower: '',
        selectedColors: [false, false, false, false, false, false, false, false, false, false],
        selectedSize: null,
      }
    },
    computed: {
      saveIsDisabled() {
        return this.selectedFlowers.length < 1 ||
            this.selectedCategories.length < 1 ||
            this.bouquetName === '' ||
            this.price === '' ||
            this.selectedSize === null ||
            !this.selectedColors.find(value => value === true);
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
        const i = this.selectedCategories.indexOf(category);
        if (i > -1) {
          this.selectedCategories.splice(i, 1);
        } else {
          this.selectedCategories.push(category);
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
      border: 0;
      min-width: 0;
      font-size: 1em;
      width: 1px;
      font-weight: bold;
      flex: 3;
      background-color: transparent;
      margin-right: 5px;
      text-align: center;
      -moz-appearance: textfield;
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

  form {
    display: flex;
    flex-direction: column;

    label {
      display: flex;
      align-items: center;
      margin-bottom: 10px;

      &>span {
        width: 130px;
        float: left;
        font-weight: bold;
      }

      input, select {
        box-sizing: border-box;
        width: 220px;
        font-size: 1.2em;
        box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
        border-radius: 7px;
        padding: 3px 10px 3px 10px;
        border: solid 1px white;
        -moz-appearance: textfield;

        &:focus {
          outline: none;
          border: solid 1px $mainOrange;
        }
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