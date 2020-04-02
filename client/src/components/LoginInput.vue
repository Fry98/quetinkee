<template>
  <div class='inp-wrap'>
    <div class='inp-title'>{{ title }}</div>
    <input :value='value' :type='type' @input='$emit("input", $event.target.value)'>
  </div>
</template>

<script>
export default {
  props: {
    value: String,
    type: {
      type: String,
      default: 'text'
    },
    title: {
      type: String,
      required: true
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .inp-wrap {
    width: 100%;
    margin-bottom: 15px;
    font-size: 1.1em;
    & > input {
      width: 100%;
      font-size: .9em;
      border: 1px solid white;
      margin-top: 3px;
      padding: 7px 10px;
      border-radius: 7px;
      box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
      box-sizing: border-box;
      outline: none;
      &:focus {
        border-color: $mainOrange;
      }
    }
  }

  .inp-title {
    padding-left: 2px;
  }
</style>
