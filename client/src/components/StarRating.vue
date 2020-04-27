<template>
  <div class="rating" :class="{ clickable }">
    <span v-for='i in 5' :key='i' class="star" :class="{fill: i <= value}" @click='$emit("input", i)'>
      <font-awesome-icon :icon="['fas', 'star']"/>
    </span>
  </div>
</template>

<script>
export default {
  props: {
    value: Number,
    clickable: Boolean
  },
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .clickable {
    cursor: pointer;
  }

  .star {
    color: $midGrey;
    font-size: 18px;
  }

  .fill {
    color: $mainOrange !important;
  }
</style>
