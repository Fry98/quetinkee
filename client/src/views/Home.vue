<template>
  <div id="content">
    <h1>Nové</h1>
    <carousel>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
      <product-tile name='Nazev kytice tohle je dlouhej text' price='899'></product-tile>
    </carousel>
    <h1>V Akci</h1>
    <product-tile name='Nazev kytice' price='899'></product-tile>
    <h1>Doporučené</h1>
    <product-tile name='Nazev kytice' price='899'></product-tile>
  </div>
</template>

<script>
  import ProductTile from '../components/ProductTile';
  import Carousel from '../components/Carousel';

  export default {
    name: 'home',
    components: {
      ProductTile,
      Carousel
    }
  };
</script>

<style lang='scss' scoped>
  #content {
    padding: 1.4em 40px;
  }

  h1 {
    margin: 1em 0 0 0;
    font-size: 1.7em;
    &:first-child {
      margin: 0;
    }
  }
</style>
