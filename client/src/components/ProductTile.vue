<template>
  <div class="product-tile">
    <div class='image' :style='style'></div>
    <div class="info-wrap">
      <div class='left-flex'>
        <div class="bouquet-name">{{name}}</div>
        <div class="bouquet-price">{{price}}</div>
      </div>
      <div class="cart-add">
        <font-awesome-icon id='cart' icon='cart-plus'></font-awesome-icon>
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
      name: {
        type: String,
        required: true
      },
      price: {
        type: String,
        required: true
      },
      img: {
        type: String,
        required: true
      }
    },
    computed: {
      style() {
        return `background-image: url(${this.img});`;
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
    &:hover {
      background: $darkBlue;
    }
  }

  .left-flex {
    flex: 1;
    position: relative;
    overflow: hidden;
  }
</style>
