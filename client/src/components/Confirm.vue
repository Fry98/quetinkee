<template>
  <div id='modal-wrap' :class='{ close: msg === null }'>
    <div id='modal'>
      <div id='modal-msg' v-html="visibleMessage"></div>
      <button class='button cancel' @click='$emit("cancel")'>Zrušit</button>
      <button class='button' @click='$emit("confirm")'>Potvrdit</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      oldMsg: ''
    };
  },
  props: {
    msg: String
  },
  watch: {
    msg() {
      if (this.msg !== null) this.oldMsg = this.msg;
    }
  },
  computed: {
    visibleMessage() {
      return this.msg === null ? this.oldMsg : this.msg;
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  #modal-wrap {
    position: fixed;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.24);
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
    transition-duration: .3s;
  }

  #modal {
    max-width: 300px;
    background: white;
    border-radius: 12px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.281);
    text-align: center;
    padding: 10px;
    transition-duration: .3s;
  }

  #modal-msg {
    font-size: 1.3em;
  }

  .button {
    border: none;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    background: $mainBlue;
    color: white;
    padding: 5px 9px;
    font-size: 1.2em;
    cursor: pointer;
    transition-duration: .2s;
    margin-top: 7px;
    &:hover {
      background: $darkBlue;
    }
  }

  .cancel {
    margin-right: 5px;
    background: rgb(148, 148, 148);
    &:hover {
      background: rgb(161, 161, 161);
    }
  }

  .close {
    pointer-events: none;
    opacity: 0;
    #modal {
      transform: translateY(25px);
    }
  }
</style>
