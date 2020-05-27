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
      <div class='item'>
        <div class='name'>Adresa:</div>
        <div class='value'>{{ orders[index].address }}</div>
      </div>
      <div class='item'>
        <div class='name'>Datum doručení:</div>
        <div class='value'>{{ orders[index].dateStr }}</div>
      </div>
      <div class='item'>
        <div class='name'>Položky:</div>
        <div class='cont'>
          <div v-for='(item, i) in orders[index].contents' :key='i'>{{ item }}</div>
        </div>
      </div>
      <div class='btn-wrap'>
        <button class='btn' @click='$emit("close")'>Zavřít</button>
      </div>
    </div>
  </div>
</template>

<script>
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
    width: 450px;
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
    width: 155px;
    text-align: right;
    padding-right: 10px;
  }

  .btn-wrap {
    display: flex;
    justify-content: center;
  }

  .cont {
    flex: 1;
    max-height: 150px;
    overflow-y: auto;
  }
</style>
