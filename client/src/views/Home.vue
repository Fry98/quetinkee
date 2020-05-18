<template>
  <div id="content">
    <div v-for='(ctg, i) in $store.getters.categories' :key='ctg.id' class='ctg-list'>
      <div v-if='list[i] !== undefined && list[i].length !== 0'>
        <h1>{{ ctg.name }}</h1>
        <carousel>
          <product-tile v-for='flower in list[i]' :key='flower.id' :name='flower.name' :price='Number(flower.price)' :id='flower.id'></product-tile>
        </carousel>
      </div>
    </div>
  </div>
</template>

<script>
  import ProductTile from '../components/ProductTile';
  import Carousel from '../components/Carousel';
  import axios from 'axios';

  export default {
    name: 'home',
    components: {
      ProductTile,
      Carousel
    },
    created() {
      this.loadData();
    },
    watch: {
      categories() {
        this.loadData();
      }
    },
    computed: {
      categories() {
        return this.$store.getters.categories;
      }
    },
    methods: {
      async loadData() {
        if (this.categories.length === 0) return;
        const promises = [];
        for (const ctg of this.categories) {
          promises.push(axios(`/api/shop/category/${ctg.id}`));
        }

        const res = await Promise.all(promises);
        this.list = [];
        res.forEach(x => this.list.push(x.data.content));
      }
    },
    data() {
      return {
        list: []
      };
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

  .ctg-list {
    margin-bottom: 30px;
  }
</style>
