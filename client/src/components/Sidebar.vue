<template>
  <div id="sidebar">
    <div id='fulltext-search'>
      <input type='text' placeholder='Hledat podle názvu...'>
      <div class="search-button">
        <font-awesome-icon id='search-icon' icon='search'></font-awesome-icon>
      </div>
    </div>
    <h1>Kategorie</h1>
    <ul>
      <li>Nové</li>
      <li>V Akci</li>
      <li>Sezonní nabídka</li>
      <li>Narozeniny</li>
      <li>Pohřeb</li>
    </ul>
    <h1>Vyhledávání</h1>
    <h2>Cena</h2>
    <h2>Velikost</h2>
    <div id='sizes'>
      <span :class='{"checkbox-input": true, selected: selectedSizes[0]}' @click="handleSizeClick(0)">
        Malé
      </span>
      <span :class='{"checkbox-input": true, selected: selectedSizes[1]}' @click="handleSizeClick(1)">
        Střední
      </span>
      <span :class='{"checkbox-input": true, selected: selectedSizes[2]}' @click="handleSizeClick(2)">
        Velké
      </span>
    </div>
    <h2>Barva</h2>
    <h2>Květiny</h2>
  </div>
</template>

<script>
  export default {
    name: 'Sidebar',
    data() {
      return {
        selectedSizes: [false, false, false]
      };
    },
    methods: {
      handleSizeClick(size) {
        this.selectedSizes[size] = !this.selectedSizes[size];
        this.selectedSizes = [...this.selectedSizes];
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";

  #sidebar {
    display: flex;
    flex-direction: column;
    padding-top: 10px;
    padding-right: 5px;
    background-color: $almostWhite;
    width: 290px;
    box-shadow: inset -3px 0px 5px rgba(0, 0, 0, 0.158);
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
      border: 0;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      margin-right: 8px;
      outline: none;
      border: 1px solid white;
      &:focus {
        border-color: $mainOrange;
      }
    }
    .search-button {
      user-select: none;
      align-self: center;
      height: 37px;
      width: 37px;
      border: none;
      border-radius: 100%;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      background: $mainBlue;
      color: white;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: .9em;
      cursor: pointer;
      transition-duration: .2s;
      &:hover {
        background: $darkBlue;
      }
    }
  }

  #sizes {
    user-select: none;
    display: flex;
    justify-content: center;
    .checkbox-input {
      transition: .15s;
      cursor: pointer;
      padding: 6px;
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

  h1 {
    margin: 5px 0;
    padding-left: 7px;
    border-left: solid $mainOrange 3px;
    font-size: 1.5m;
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
