<template>
  <div id="content">
    <h1>Nové</h1>
    <carousel>
      <product-tile v-for='flower in newFlowers' :key='flower.id' :name='flower.name' :price='flower.price'></product-tile>
    </carousel>
    <h1>V Akci</h1>
    <carousel>
      <product-tile v-for='flower in discountedFlowers' :key='flower.id' :name='flower.name' :price='flower.price'></product-tile>
    </carousel>
    <h1>Doporučené</h1>
    <carousel>
      <product-tile v-for='flower in recommendedFlowers' :key='flower.id' :name='flower.name' :price='flower.price'></product-tile>
    </carousel>
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
    },
    data() {
      return {
        newFlowers: [
          {id: 0, name:'Nazev kytice', price:899},
          {id: 1, name:'Velmi dlouhy nazev kytice', price:899},
          {id: 2, name:'Nazev kytice', price:899},
          {id: 3, name:'Nazev kytice', price:899},
          {id: 4, name:'Nazev kytice', price:899},
          {id: 5, name:'Nazev kytice', price:899}
        ],
        discountedFlowers: [
          {name:'Nazev kytice', price:899},
          {name:'Velmi dlouhy nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899}
        ],
        recommendedFlowers: [
          {name:'Nazev kytice', price:899},
          {name:'Velmi dlouhy nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899},
          {name:'Nazev kytice', price:899}
        ]
      }
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
