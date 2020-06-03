<template>
  <div class='detail-wrap' :class="{close: !open}" @click.self="$emit('close')">
    <div class='details'>
      <div class='item'>
        <div class='name'>ID:</div>
        <div class='value'>{{ orders[index].id }}</div>
      </div>
      <div class='item'>
        <div class='name'>Jméno:</div>
        <div class='value'>{{ orders[index].fullName }}</div>
      </div>
      <div class='item' v-if='orders[index].billing === null'>
        <div class='name'>Adresa:</div>
        <div class='value'>{{ orders[index].address }}</div>
      </div>
      <div v-else>
        <div class='item'>
          <div class='name'>Doručovací adresa:</div>
          <div class='value'>{{ orders[index].address }}</div>
        </div>
        <div class='item'>
          <div class='name'>Fakturační adresa:</div>
          <div class='value'>{{ orders[index].billing }}</div>
        </div>
      </div>
      <div class='item'>
        <div class='name'>Datum doručení:</div>
        <div class='value'>{{ orders[index].dateStr }}</div>
      </div>
      <div class='item'>
        <div class='name'>Položky:</div>
        <div class='cont'>
          <div v-for='(item, i) in contents' :key='i'>{{ item.quantity }}x {{ item.name }}</div>
          <div v-if='contents.length === 0'>Loading...</div>
        </div>
      </div>
      <div class='btn-wrap'>
        <button class='btn' @click='$emit("close")'>Zavřít</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    orders: {
      type: Array,
      required: true
    },
    index: {
      type: Number,
      default: 0
    },
    open: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      contents: []
    };
  },
  watch: {
    async open() {
      if (!this.open) return;
      this.contents = [];
      const res = await axios(`/api/orders/${this.orders[this.index].id}/info`);
      this.contents = res.data.contains;
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';
  @import '../scss/_button.scss';

  .close {
    opacity: 0;
    pointer-events: none;
    .details {
      transform: translateY(30px);
    }
  }

  .detail-wrap {
    position: fixed;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.185);
    z-index: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    transition-duration: .2s;
  }

  .details {
    background: white;
    width: 420px;
    padding: 12px;
    border-radius: 7px;
    position: relative;
    overflow: hidden;
    transition-duration: .3s;
    transform: none;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
  }

  .item {
    display: flex;
    font-size: 1.1em;
    margin-bottom: 5px;
  }

  .name {
    font-weight: bold;
    width: 180px;
    text-align: right;
    padding-right: 10px;
  }

  .btn-wrap {
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }

  .cont {
    flex: 1;
    max-height: 150px;
    overflow-y: auto;
  }
</style>
