<template>
  <div class='car-wrap'>
    <div class='car-btn car-fwd'>
      <font-awesome-icon icon='chevron-right'></font-awesome-icon>
    </div>
    <div class='car-btn car-bck'>
      <font-awesome-icon icon='chevron-left'></font-awesome-icon>
    </div>
    <div class='carousel'>
      <div class='car-cont'>
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .carousel {
    width: 100%;
    overflow: hidden;
    padding: 5px;
    margin: -5px;
  }

  .car-cont {
    display: flex;
    transform: translateX(-218px);
  }

  .car-wrap {
    position: relative;
  }

  .car-btn {
    width: 55px;
    height: 55px;
    background: $mainBlue;
    border-radius: 30px;
    position: absolute;
    z-index: 2;
    top: 50%;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.336);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 2em;
    color: white;
    transition-duration: .2s;
  }

  .car-fwd {
    transform: translate(32px, -70%);
    right: 0px;
    svg {
      transform: translateX(2px);
    }
  }

  .car-bck {
    transform: translate(-32px, -70%);
    left: 0px;
    svg {
      transform: translateX(-2px);
    }
  }
</style>
