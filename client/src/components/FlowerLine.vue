<template>
  <div class='flower-line'>
    <span class='flower-name'>
      {{ flower.name }}
      <font-awesome-icon class='icon' :icon="['far', 'trash-alt']" @click='handleRemoveClick'></font-awesome-icon>
    </span>
    <input v-model='strCount' @focus='$event.target.select()' @blur='resetCount()'>
  </div>
</template>
<script>
  export default {
    name: 'flowerLine',
    props: {
      flower: {
        required: true
      }
    },
    data() {
      return {
        strCount: this.flower.count.toString()
      }
    },
    watch: {
      'strCount': {
        handler(newVal, oldVal) {
          if (/^[0-9]{0,3}$/.test(newVal)) {
            this.flower.count = parseFloat(newVal);
            this.$emit('count-change', this.flower);
          } else {
            this.strCount = oldVal;
          }
        }
      }
    },
    methods: {
      handleRemoveClick() {
        this.$emit('remove', this.flower.id);
      },
      resetCount() {
        if (this.strCount === '') {
          this.strCount = '1';
        }
      }
    }
  }
</script>
<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  .flower-line {
    border-radius: 15px;
    display: flex;
    background-color: $lightGrey;
    margin-bottom: 8px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);

    .flower-name {
      flex: 20;
      background-color: #fff;
      padding: 6px 10px 6px 10px;
      font-weight: bold;
      border-radius: 15px;

      .icon {
        margin-right: 4px;
        font-size: 1.1em;
        float: right;
        cursor: pointer;
      }
    }

    input {
      outline: none;
      border: 0;
      min-width: 0;
      font-size: 1em;
      width: 1px;
      font-weight: bold;
      flex: 3;
      background-color: transparent;
      margin-right: 5px;
      text-align: center;
      -moz-appearance: textfield;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
      -webkit-appearance: none;
      margin: 0;
    }

  }
</style>