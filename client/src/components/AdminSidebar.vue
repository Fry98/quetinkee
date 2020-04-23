<template>
  <div id="sidebar">
    <h1>Akce</h1>
    <h2>Kytice</h2>
    <ul>
      <li>Vytvořit novou kytici</li>
    </ul>
    <h2>Sklad</h2>
    <ul>
      <li>Zobrazit sklad</li>
      <li>Naskladnit</li>
    </ul>
    <h2>Kategorie</h2>
    <ul>
      <li>Spravovat kategorie</li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'Sidebar'
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #sidebar {
    display: flex;
    flex-direction: column;
    padding: 10px 5px 10px 0px;
    background-color: $almostWhite;
    width: 290px;
    box-shadow: inset -3px 0px 5px rgba(0, 0, 0, 0.158);
    overflow-y: scroll;
    &::-webkit-scrollbar {
      display: none;
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
    li {
      cursor: pointer;
      position: relative;
      padding-bottom: 10px;
      transition: .2s;
      &:hover {
        color: $darkOrange;
      }
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
