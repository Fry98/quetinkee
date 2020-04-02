<template>
  <div id='home'>
    <h1>Nové</h1>
    <ProductTile name='Nazev kytice' price='899'></ProductTile>
    <h1>V Akci</h1>
    <ProductTile name='Nazev kytice' price='899'></ProductTile>
    <h1>Doporučené</h1>
    <ProductTile name='Nazev kytice' price='899'></ProductTile>
  </div>
</template>

<script>
import ProductTile from '../components/ProductTile';
export default {
  name: 'home',
  components: {ProductTile}
};
</script>

<style lang='scss' scoped>
  #home {
    padding: 1em 2em;
  }

  h1 {
    margin: 1em 0 0 0;
    &:first-child {
      margin: 0;
    }
  }
</style>
