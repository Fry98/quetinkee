<template>
  <div id='home'>
    <sidebar></sidebar>
    <div id="content">
      <h1>Nové</h1>
      <ProductTile name='Nazev kytice tohle je dlouhej text' price='899'></ProductTile>
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
        components: {
            Sidebar,
            ProductTile
        }
    };
</script>

<style lang='scss' scoped>
  #home {
    display: flex;
    flex-direction: row;
    flex: 1;

    #content {
      padding: 1em 2em;
    }
  }

  h1 {
    margin: 1em 0 0 0;
    font-size: 1.7em;
    &:first-child {
      margin: 0;
    }
  }
</style>
