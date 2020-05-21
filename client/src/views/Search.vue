<template>
  <div
    id='content'
    v-infinite-scroll='runSearch'
    infinite-scroll-disabled='stopLoad'
    infinite-scroll-distance='10'
    infinite-scroll-listen-for-event='checkScroll'
  >
    <div class='card' v-for='item in items' :key='item.id'>
      <product-tile :name='item.name' :price='Number(item.price)' :id='item.id'></product-tile>
    </div>
    <div class='loading' v-if='!last'>Loading...</div>
    <div class='loading' v-if='last && items.length === 0'>Žádné nalezené záznamy</div>
  </div>
</template>

<script>
  import ProductTile from '../components/ProductTile';
  import axios from 'axios';

  export default {
    components: {
      ProductTile
    },
    data() {
      return {
        items: [],
        page: 0,
        last: false,
        loading: false
      };
    },
    computed: {
      search() {
        return this.$store.getters.search;
      },
      stopLoad() {
        return this.last || this.loading;
      }
    },
    watch: {
      search() {
        this.items = [];
        this.page = 0;
        this.last = false;
        this.loading = false;
        this.$emit('checkScroll');
      }
    },
    methods: {
      async runSearch() {
        try {
          this.loading = true;
          if (this.search === null) {
            this.$router.push('/');
          } else if (this.search.category !== undefined) {
            await this.category();
          } else if (this.search.fulltext !== undefined) {
            await this.fulltext();
          } else {
            await this.filter();
          }
          this.page++;
          this.loading = false;
          this.$emit('checkScroll');
        } catch (e) {
          console.log(e);
          this.$store.dispatch('openModal', "Chyba při vyhledávání");
        }
      },
      async category(search) {
        const res = await axios(`/api/shop/category/${this.search.category}?page=${this.page}&size=15`);
        this.items.push(...res.data.content);
        this.last = res.data.last;
      },
      async fulltext(search) {
        const res = await axios(encodeURI(`/api/shop/search?q=${this.search.fulltext}?page=${this.page}&size=15`));
        this.items.push(...res.data.content);
        this.last = res.data.last;
      },
      async filter(search) {
        const data = {};
        if (this.search.colors.length > 0) data.colors = this.search.colors;
        if (this.search.sizes.filter(x => x).length > 0) data.sizes = this.search.sizes;
        if (this.search.flowers.length > 0) data.flowers = this.search.flowers;

        const res = await axios({
          method: 'post',
          url: `/api/shop/filter?page=${this.page}&size=15`,
          data
        });
        this.items.push(...res.data.content);
        this.last = res.data.last;
      }
    }
  }
</script>

<style lang="scss" scoped>
  #content {
    padding: 1.4em 40px;
  }

  .card {
    display: inline-block;
    margin-bottom: 15px;
  }

  .loading {
    text-align: center;
    font-size: 1.2em;
  }
</style>
