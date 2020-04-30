<template>
  <div class="count">
    <div class="quantity">
      <div class="q-btn" @click="changeQuantity(-1)">
        <font-awesome-icon icon="minus" />
      </div>
      <input type="text" v-model="count" @change="resetQuantity">
      <div class="q-btn" @click="changeQuantity(1)">
        <font-awesome-icon icon="plus" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      count: 1,
      oldCount: 1
    };
  },
  props: {
    value: {
      type: Number,
      required: true
    }
  },
  created() {
    this.updateByValue();
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
          this.$emit('input', numCount);
        }

        this.count = numCount;
        this.oldCount = numCount;
        return;
      }
      this.count = this.oldCount;
    },
    value() {
      this.updateByValue();
    }
  },
  methods: {
    changeQuantity(x) {
      const newCount = this.count + x;
      if (newCount < 1 || newCount > 50) return;
      this.count = newCount;
      this.$emit('input', this.count);
    },
    resetQuantity() {
      if (this.count === '') {
        this.count = 1;
        this.oldCount = 1;
        this.$emit('input', 1);
      }
    },
    updateByValue() {
      if (
        /^[0-9]{0,2}$/.test(this.value.toString()) &&
        this.value > 0 &&
        this.value < 51
      ) {
        this.count = this.value;
        this.oldCount = this.value;
        return;
      }

      this.$emit('input', this.count);
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .quantity {
    display: flex;
    position: relative;
    align-items: center;
    height: 37px;
    border: 0.5px solid black;
    border-radius: 7px;
    font-size: 0.9em;
    overflow: hidden;

    input {
      font-size: 1.3em;
      height: 100%;
      box-sizing: border-box;
      text-align: center;
      width: 40px;
      border: none;
      border-left: 0.5px solid black;
      border-right: 0.5px solid black;
    }

    .q-btn {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 30px;
      height: 100%;
      cursor: pointer;
      transition-duration: .2s;
      user-select: none;

      &:hover {
        color: white;
        background: $mainBlue;
      }
    }
  }
</style>
