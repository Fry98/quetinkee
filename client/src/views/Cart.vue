<template>
  <div class="cart">
    <h2 class="text-center">Košík</h2>

    <div class="items">
      <div class="item" v-for="item in items">
        <div class="left">
          <div class="image"></div>
          <span class="name">{{item.name}}</span>
        </div>
        <div class="right">
          <span class="price">{{item.price * item.count}}&nbsp;Kč</span>
          <div class="count">
            <div class="quantity">
              <div class="q-btn" @click="changeQuantity(-1, item)">
                <font-awesome-icon icon="minus"/>
              </div>
              <input type="text" v-model="item.count" @change="resetQuantity(item)">
              <div class="q-btn" @click="changeQuantity(1, item)">
                <font-awesome-icon icon="plus"/>
              </div>
            </div>
          </div>
          <span class="delete" @click="remove(item)">
            <font-awesome-icon icon="times"/>
          </span>
        </div>
      </div>
      <div class="summary">
        <span class="total">Cena celkem: {{total}}</span>
        <button class="button">Pokračovat</button>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    components: {},
    data() {
      return {
        items: {
          item1: {
            name: 'Název kytice',
            price: 249,
            count: 1,
            oldCount: 1
          },
          item2: {
            name: 'Bezva kytice',
            price: 549,
            count: 1,
            oldCount: 1
          },
          item3: {
            name: 'Super kytice',
            price: 749,
            count: 1,
            oldCount: 1
          },
          item4: {
            name: 'Pěkná kytice',
            price: 349,
            count: 1,
            oldCount: 1
          }
        },
        total: 0
      }
    },
    watch: {
      count() {
        if (/^[0-9]{0,2}$/.test(this.count)) {
          let numCount = this.count;
          if (this.count !== '') {
            numCount = Number(this.count);
            if (numCount < 1 || numCount > 50) {
              this.count = this.oldCount;
              return;
            }
          }
          this.count = numCount;
          this.oldCount = numCount;
          return;
        }
        this.count = this.oldCount;
      }
    },
    methods: {
      changeQuantity(x, item) {
        const newCount = item.count + x;
        if (newCount < 1 || newCount > 50) return;
        item.count = newCount;
      },
      resetQuantity(item) {
        if (item.count === '') {
          item.count = 1;
          item.oldCount = 1;
        }
      },
      remove(item) {
        Vue.$delete(this.items, item);
      },
      submitForm() {

        alert('added');
      }
    }
  }


</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .text-center {
    text-align: center;
  }

  .cart {
    padding: 50px;

    h2 {
      font-size: 3em;
      font-weight: 500;
    }

    .price, .name {
      font-weight: 700;
      font-size: 1.5em;
    }

    .items {
      background-color: $almostWhite;
      border-radius: 12px;
      padding: 25px;

      .item {
        background-color: #fff;
        display: flex;
        align-items: center;
        height: 171px;
        border-radius: 12px;
        margin: 15px 0;

        .left {
          display: flex;
          align-items: center;
          width: 50%;
          margin: 0 25px;

          .image {
            width: 180px;
            height: 120px;
            background-color: #f00;
            margin-right: 15px;
          }

        }

        .right {
          display: flex;
          align-items: center;
          justify-content: end;
          width: 50%;
          margin: 0 25px;
        }
      }
    }

    .delete {
      svg {
        color: $darkBlue;
        cursor: pointer;
      }
    }

    .quantity {
      display: flex;
      position: relative;
      align-items: center;
      height: 37px;
      border: 0.5px solid black;
      border-radius: 7px;
      font-size: 0.9em;
      overflow: hidden;
      margin: 0 15px;

      input {
        font-size: 1.3em;
        height: 100%;
        box-sizing: border-box;
        text-align: center;
        width: 40px;
        border: none;
        border-left: 0.5px solid black;
        border-right: 0.5px solid black;
      }

      .q-btn {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 30px;
        height: 100%;
        cursor: pointer;
        transition-duration: .2s;

        &:hover {
          color: white;
          background: $mainBlue;
        }
      }
    }

    input[type="number"] {
      -webkit-appearance: textfield;
      -moz-appearance: textfield;
      appearance: textfield;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
      -webkit-appearance: none;
    }


    .summary {
      display: flex;
      justify-content: end;
      flex-direction: column;

      .total {
        font-size: 1.5em;
        margin-bottom: 5px;
      }

      .total, .button {
        width: 250px;
      }
    }

    .button {
      border: none;
      border-radius: 7px;
      background: $mainBlue;
      color: white;
      padding: .5rem 1rem;
      font-size: 1.1em;
      cursor: pointer;
      transition-duration: .2s;

      &:hover {
        background: $darkBlue;
      }
    }
  }
</style>
