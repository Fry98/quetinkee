<template>
  <div id='home'>
    <sidebar></sidebar>
    <div id="content">
      <h1>Nové</h1>
      <ProductTile name='Nazev kytice' price='899'></ProductTile>
      <h1>V Akci</h1>
      <ProductTile name='Nazev kytice' price='899'></ProductTile>
      <h1>Doporučené</h1>
      <ProductTile name='Nazev kytice' price='899'></ProductTile>
    </div>
  </div>
</template>

<script>
import ProductTile from '../components/ProductTile';
import Sidebar from "../components/Sidebar";
export default {
  name: 'home',
  components: {Sidebar, ProductTile}
};
</script>

<style lang='scss' scoped>
  #home {
    min-height: 100%;
    display: flex;
    flex-direction: row;
    #content {
      padding: 1em 2em;
    }
  }

  h1 {
    margin: 1em 0 0 0;
    &:first-child {
      margin: 0;
    }
  }
</style>
