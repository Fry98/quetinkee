<template>
  <div class='car-wrap'>
    <div class='car-btn car-fwd' :class='{"car-fwd-hide": !fwd}' @click='goFwd'>
      <font-awesome-icon icon='chevron-right'></font-awesome-icon>
    </div>
    <div class='car-btn car-bck' :class='{"car-bck-hide": !back}' @click='goBack'>
      <font-awesome-icon icon='chevron-left'></font-awesome-icon>
    </div>
    <div class='carousel'>
      <div class='car-cont' :style='style' ref='crsl'>
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      offset: 0,
      fwd: true,
      transition: true
    };
  },
  mounted() {
    window.addEventListener('resize', this.onResize);
    this.fwd = this.$refs.crsl.scrollWidth > this.$refs.crsl.clientWidth;
  },
  destroyed() {
    window.removeEventListener('resize', this.onResize);
  },
  methods: {
    onResize() {
      this.transition = false;
      if (-this.offset > this.$refs.crsl.scrollWidth - this.$refs.crsl.clientWidth) {
        this.offset = -(this.$refs.crsl.scrollWidth - this.$refs.crsl.clientWidth);
      }
      this.fwd = this.$refs.crsl.scrollWidth > this.$refs.crsl.clientWidth && this.offset !== -(this.$refs.crsl.scrollWidth - this.$refs.crsl.clientWidth);
    },
    goFwd() {
      this.transition = true;
      if (this.$refs.crsl.scrollWidth - this.$refs.crsl.clientWidth + this.offset - 218 > 0) {
        if (this.offset % 218 !== 0) {
          this.offset -= 218 + (this.offset % 218);
        } else {
          this.offset -= 218;
        }
      } else {
        this.offset = -(this.$refs.crsl.scrollWidth - this.$refs.crsl.clientWidth);
        this.fwd = false;
      }
    },
    goBack() {
      this.transition = true;
      if (this.offset % 218 !== 0) {
        this.offset -= this.offset % 218;
        this.fwd = true;
        return;
      }
      this.offset += 218;
    }
  },
  computed: {
    back() {
      return this.offset < 0;
    },
    style() {
      return `
        transform: translateX(${this.offset}px);
        ${this.transition ?
          'transition-duration: .4s;' :
          ''
        }
      `;
    }
  }
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
    cursor: pointer;
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

  .car-bck-hide {
    transform: translate(-32px, -50%);
    opacity: 0;
    pointer-events: none;
  }

  .car-fwd-hide {
    transform: translate(32px, -50%);
    opacity: 0;
    pointer-events: none;
  }
</style>
