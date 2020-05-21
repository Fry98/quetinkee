<template>
  <div class="product-tile" @click='openDetail'>
    <div class='image' :style='style'></div>
    <div class="info-wrap">
      <div class='left-flex'>
        <div class="bouquet-name">{{name}}</div>
        <div class="bouquet-price">{{price}}</div>
      </div>
      <div class="cart-add" :class='{"in-cart": inCart}' @click='addToCart'>
        <font-awesome-icon id='cart' icon='check' v-if='inCart'></font-awesome-icon>
        <font-awesome-icon id='cart' icon='cart-plus' v-else></font-awesome-icon>
      </div>
    </div>
  </div>
</template>

<script>
  import Sidebar from "./Sidebar";
  export default {
    name: "ProductTile",
    components: { Sidebar },
    props: {
      id: {
        type: Number,
        required: true
      },
      name: {
        type: String,
        required: true
      },
      price: {
        type: Number,
        required: true
      },
      img: {
        type: String,
        default: 'https://images.pexels.com/photos/736230/pexels-photo-736230.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500'
      }
    },
    computed: {
      style() {
        return `background-image: url(${this.img});`;
      },
      inCart() {
        return this.$store.getters.cart[this.id] !== undefined;
      },
      
    },
    methods: {
      addToCart(e) {
        e.stopPropagation();
        if (this.inCart) return;
        this.$store.dispatch('addToCart', {
          id: this.id,
          name: this.name,
          price: this.price,
          img: this.img,
          count: 1
        });
      },
      openDetail() {
        this.$router.push(`/product/${this.id}`);
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .product-tile {
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.40);
    padding: 4px;
    border-radius: 7px;
    width: 200px;
    display: flex;
    flex-direction: column;
    background: white;
    margin-right: 10px;
    cursor: pointer;
    flex-shrink: 0;

    .image {
      border-radius: 7px;
      width: 100%;
      height: 200px;
      background-size: cover;
      background-color: $darkOrange;
      background-position: center center;
    }
  }

  .bouquet-name {
    font-weight: bold;
    font-size: 1.1em;
    text-overflow: ellipsis;
    white-space: nowrap;
    flex-grow: 0;
    overflow: hidden;
  }

  .bouquet-price {
    font-size: .9em;
    &::after{
      content: ' Kč';
    }
  }

  .info-wrap {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-top: 4px;
  }

  .cart-add {
    border: none;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    background: $mainBlue;
    color: white;
    padding: .5em;
    font-size: 1.1em;
    transition-duration: .2s;
    width: 20px;
    text-align: center;
    &:hover {
      background: $darkBlue;
    }
  }

  .left-flex {
    flex: 1;
    position: relative;
    overflow: hidden;
  }

  .in-cart {
    background: rgb(28, 167, 58);
    box-shadow: none;
    &:hover {
      background: rgb(28, 167, 58);
    }
  }
</style>
