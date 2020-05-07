<template>
  <div class='detail-wrap' :class="{close: !open}">
    <div class='details'>
      <div class='item'>
        <div class='name'>ID:</div>
        <div class='value'>7</div>
      </div>
      <div class='item'>
        <div class='name'>Jméno:</div>
        <div class='value'>Roman Toman</div>
      </div>
      <div class='item'>
        <div class='name'>Adresa:</div>
        <div class='value'>Ulicova 234/7, 134 12 Praha 7</div>
      </div>
      <div class='item'>
        <div class='name'>Čas doručení:</div>
        <div class='value'>12:30</div>
      </div>
      <div class='item'>
        <div class='name'>Položky:</div>
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
    order: {
      type: Object,
      required: true
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
  }

  .details {
    background: white;
    width: 450px;
    padding: 7px;
    border-radius: 12px;
  }

  .item {
    display: flex;
    font-size: 1.1em;
    margin-bottom: 5px;
  }

  .name {
    font-weight: bold;
    width: 160px;
    text-align: right;
    padding-right: 10px;
  }

  .btn-wrap {
    display: flex;
    justify-content: center;
  }
</style>
