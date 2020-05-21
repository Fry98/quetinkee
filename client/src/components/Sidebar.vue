<template>
  <div id="sidebar">
    <div id='fulltext-search'>
      <input type='text' placeholder='Hledat podle názvu...' v-model="searchBox">
      <div class='btn search-button' @click='fulltext'>
        <font-awesome-icon id='search-icon' icon='search'></font-awesome-icon>
      </div>
    </div>
    <h1>Kategorie</h1>
    <ul>
      <li v-for='ctg in $store.getters.categories' :key='ctg.id' @click='category(ctg.id)'>{{ ctg.name }}</li>
    </ul>
    <h1>Vyhledávání</h1>
    <h2>Cena</h2>
    <div id='price'>
      <input type='number' placeholder='Od' v-model='priceFrom'> - <input type='number' placeholder='Do' v-model='priceTo'>
    </div>
    <h2>Velikost</h2>
    <div id='sizes'>
      <span :class='{"checkbox-input": true, selected: selectedSizes[0]}' @click='handleSizeClick(0)'>
        Malé
      </span>
      <span :class='{"checkbox-input": true, selected: selectedSizes[1]}' @click='handleSizeClick(1)'>
        Střední
      </span>
      <span :class='{"checkbox-input": true, selected: selectedSizes[2]}' @click='handleSizeClick(2)'>
        Velké
      </span>
    </div>
    <h2>Barva</h2>
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
    <h2>Květiny</h2>
    <div id='flowers'>
      <multiselect select-label='Vybrat'
                   deselect-label='Odebrat'
                   selected-label='Vybráno'
                   placeholder='Vyberte květiny'
                   v-model='selectedFlowers'
                   :options='flowerOptions'
                   :multiple='true'
                   :close-on-select='false'
                   label='name'
                   track-by='name'
      >
        <span slot="noResult">Květina nebyla nalezena...</span>
      </multiselect>
    </div>
    <div class='btn' @click='filter'>Vyhledat</div>
  </div>
</template>

<script>
  import Multiselect from 'vue-multiselect';
  import axios from 'axios';

  export default {
    name: 'Sidebar',
    components: { Multiselect },
    data() {
      return {
        searchBox: '',
        priceFrom: null,
        priceTo: null,
        selectedFlowers: [],
        selectedSizes: [false, false, false],
        selectedColors: [false, false, false, false, false, false, false, false, false, false],
        numberToColor: ['white', 'yellow', 'orange', 'red', 'pink', 'purple', 'blue', 'turquoise', 'seafoam', 'green']
      };
    },
    async mounted() {
      const res = await axios.get('/api/shop/filter');
      this.$store.commit('setCategories', res.data.categories);
      this.$store.commit('setFlowers', res.data.flowers);
    },
    methods: {
      handleSizeClick(size) {
        this.$set(this.selectedSizes, size, !this.selectedSizes[size]);
      },
      handleColorClick(color) {
        this.$set(this.selectedColors, color, !this.selectedColors[color]);
      },
      category(id) {
        this.$store.commit('setSearch', {
          category: id
        });
        this.navigate('/search');
      },
      navigate(path) {
        if (this.$route.path !== path) this.$router.push(path);
      },
      fulltext() {
        const term = this.searchBox.trim();
        if (term.length === 0) {
          this.$store.dispatch('openModal', "Vyhledávání nemůže být prázné");
          return;
        }
        this.$store.commit('setSearch', {
          fulltext: term
        });
        this.navigate('/search');
      },
      filter() {
        this.$store.commit('setSearch', {
          flowers: this.selectedFlowers.map(x => Number(x.id)),
          priceFrom: this.priceFrom,
          priceTo: this.priceTo,
          sizes: this.selectedSizes,
          colors: this.selectedColors.reduce((out, bool, index) => bool ? out.concat(index) : out, [])
        });
        this.navigate('/search');
      }
    },
    computed: {
      flowerOptions() {
        return this.$store.getters.flowers;
      }
    }
  }
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<style lang='scss'>
  @import "../scss/_vars.scss";

  .multiselect__tags {
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
  }
  .multiselect__option--highlight {
    background-color: $mainBlue;
    &:after {
      background-color: $mainBlue;
    }
    &.multiselect__option--selected {
      &:after {
        background-color: $darkOrange;
      }
      background-color: $darkOrange;
      color: #fff;
    }
  }
  .multiselect__tag {
    background-color: $mainBlue;
    cursor: default;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
  }
  i.multiselect__tag-icon {
    &:after {
      color: #eee;
    }
    &:hover {
      background-color: $darkBlue;
    }
  }
  .multiselect--above .multiselect__content-wrapper {
    border-radius: 7px 7px 0 0;
    box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.26);
  }
  .multiselect__content-wrapper {
    border-radius: 0 0 7px 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
  }
</style>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #price {
    padding: 10px 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: row;
    input {
      width: 38%;
      padding: 3px 10px;
      font-size: 1.2em;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      border-radius: 7px;
    }
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
    cursor: pointer;
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

  .clr-show {
    opacity: 1 !important;
    transform: none !important;
  }

  #colors {
    justify-content: space-between;
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    padding: 10px 30px;
  }

  #flowers {
    padding: 10px 30px;
  }

  #sidebar {
    display: flex;
    flex-direction: column;
    padding: 10px 0px;
    padding-right: 5px;
    background-color: $almostWhite;
    width: 290px;
    box-shadow: inset -3px 0px 5px rgba(0, 0, 0, 0.158);
    overflow-y: scroll;
    scrollbar-width: none;
    &::-webkit-scrollbar {
      display: none;
    }
  }

  #fulltext-search {
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-bottom: 5px;
    input[type='text'] {
      width: 75%;
      padding: 7px 10px;
      font-size: 1.1em;
      border-radius: 50px;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      margin-right: 8px;
    }
    .search-button {
      height: 37px;
      width: 37px;
      border-radius: 100%;
      padding: 0;
      font-size: .9em;
    }
  }

  #sizes {
    user-select: none;
    display: flex;
    justify-content: center;
    padding: 10px 0;
    .checkbox-input {
      color: $darkGrey;
      font-weight: bold;
      transition: .15s;
      cursor: pointer;
      padding: 6px 8px;
      margin: 5px 0 5px 0;
      background-color: white;
      box-shadow: 0 1px 2px rgba(0, 0, 0, 0.40);
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

  input[type='number'],input[type='text'] {
    border: 1px solid white;
    outline: none;
    &:focus {
      border-color: $mainOrange;
    }
  }

  h1 {
    margin: 5px 0;
    padding-left: 7px;
    border-left: solid $mainOrange 3px;
    font-size: 1.6em;
  }

  h2 {
    padding-left: 7px;
    border-left: solid $mainBlue 3px;
    font-size: 1.2em;
    margin: 5px 0 0;
    &:first-child {
      margin: 0;
    }
  }

  ul {
    margin-top: 3px;
    list-style: none;
    cursor: pointer;
    li {
      position: relative;
      padding-bottom: 10px;
      &:before {
        content: '';
        position: absolute;
        border-right: 3px solid $darkBlue;
        border-bottom: 3px solid $darkBlue;
        width: 5px;
        height: 5px;
        top: calc(50% - 6px);
        left: -13px;
        transform: translateY(-50%) rotate(-45deg);
      }
    }
  }
</style>
