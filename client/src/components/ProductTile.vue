<template>
  <div class="product-tile">
    <div class='image'></div>
    <div class="info-wrap">
      <div>
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
    export default {
        name: "ProductTile",
        props: {
            name: String,
            price: String
        }
    }
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .product-tile {
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.40);
    padding: 4px;
    border-radius: 7px;
    width: 15em;
    display: flex;
    flex-direction: column;

    .image {
      border-radius: 7px;
      width: 100%;
      height: 15em;
      background-size: cover;
      background-image: url('../assets/bouquet.jpg');
      background-position: center center;
    }
  }

  .bouquet-name {
    font-weight: bold;
    font-size: 1.5em;
  }

  .bouquet-price {
    font-size: 1em;
    &::after{
      content: ' Kč';
    }
  }

  .info-wrap {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .cart-add {
    border: none;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    background: $mainBlue;
    color: white;
    padding: .5em;
    font-size: 1.1em;
    margin: .25em 0;
    cursor: pointer;
    transition-duration: .2s;
    &:hover {
      background: $darkBlue;
    }
  }

</style>