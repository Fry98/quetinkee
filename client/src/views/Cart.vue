<template>
  <div class="cart">
    <h2 class="text-center">Košík</h2>

    <div class="items">
      <div class="item" v-for="item in items" :key="item.id">
        <div class="left">
          <div class="image"></div>
          <span class="name">{{item.name}}</span>
        </div>
        <div class="right">
          <span class="price">{{item.price * item.count}}&nbsp;Kč</span>
          <counter v-model="item.count" class="counter" />
          <span class="delete" @click="remove(item.id)">
            <font-awesome-icon icon="times"/>
          </span>
        </div>
      </div>
      <div class="summary">
        <span class="total">Cena celkem: {{ getTotal() }}</span>
        <button class="button">Pokračovat</button>
      </div>
    </div>
  </div>
</template>

<script>
  import Counter from "../components/Counter";

  export default {
    components: {
      Counter
    },
    data() {
      return {
        items: {
          0: {
            id: 0,
            name: 'Název kytice',
            price: 249,
            count: 1,
            oldCount: 1
          },
          1: {
            id: 1,
            name: 'Bezva kytice',
            price: 549,
            count: 1,
            oldCount: 1
          },
          2: {
            id: 2,
            name: 'Super kytice',
            price: 749,
            count: 1,
            oldCount: 1
          },
          3: {
            id: 3,
            name: 'Pěkná kytice',
            price: 349,
            count: 1,
            oldCount: 1
          }
        }
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
      },
    },
    computed: {},
    methods: {
      changeQuantity(x, item) {
        const newCount = item.count + x;
        if (newCount > 50) return;
        if (newCount < 1) {
          delete this.items[item.id];
          this.$forceUpdate();
        }
        item.count = newCount;
      },
      resetQuantity(item) {
        if (item.count === '') {
          item.count = 1;
          item.oldCount = 1;
        }
      },
      remove(id) {
        delete this.items[id];
        this.$forceUpdate();
      },
      getTotal() {
        let total = 0;

        for (const item in this.items) {
          total += this.items[item].price * this.items[item].count;
        }

        return total;
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
          justify-content: flex-end;
          width: 50%;
          margin: 0 25px;

          .counter{
            margin: 0 15px;
          }
        }
      }
    }

    .delete {
      svg {
        color: $darkBlue;
        cursor: pointer;
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
      flex-direction: column;
      align-items: flex-end;  

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
