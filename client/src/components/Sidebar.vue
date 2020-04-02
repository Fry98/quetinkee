<template>
  <div id="sidebar">
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
    <h2>Barva</h2>
    <h2>Květiny</h2>
  </div>
</template>

<script>
    export default {
        name: 'Sidebar'
    }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";

  #sidebar {
    padding: 10px;
    background-color: $lightGrey;
    min-height: 100%;
  }
  h1 {
    margin: 5px 0;
    border-left: #006BA6 5px;
  }
  h2 {
    margin: 5px 0 0;
    &:first-child {
      margin: 0;
    }
  }
</style>