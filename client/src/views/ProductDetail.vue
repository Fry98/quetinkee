<template>
  <div class="bouquet-all">
    <div class="bouquet">
      <div class="b-left">
        <img src="https://via.placeholder.com/450" alt="image"/>
      </div>
      <div class="b-right">
        <div class="title">
          Název květiny
        </div>
        <div class="rating">
          <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
          <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
          <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
          <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
          <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
        </div>
        <div class="description">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
          facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
          scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
        </div>
        <div class="stock">
          Skladem > 5 ks
        </div>
        <div class="price">
          Cena <span class="small">s DPH</span> 1 999 Kč
        </div>
        <div class="cart-add">
          <div class="count">
            <div class="quantity">
              <input type="number" v-model="count" readonly="" />
              <div class="q-up" @click="quantityAdd">
                <font-awesome-icon icon="arrow-up"/>
              </div>
              <div class="q-down" @click="quantitySubtraction">
                <font-awesome-icon icon="arrow-down"/>
              </div>
            </div>
          </div>
          <div class="cart-button">
            <button class="button" @click="addToCart()">
              <svg data-v-50bb8ed5="" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="cart-plus"
                   role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512" id="cart"
                   class="svg-inline--fa fa-cart-plus fa-w-18">
                <path data-v-50bb8ed5="" fill="currentColor"
                      d="M504.717 320H211.572l6.545 32h268.418c15.401 0 26.816 14.301 23.403 29.319l-5.517 24.276C523.112 414.668 536 433.828 536 456c0 31.202-25.519 56.444-56.824 55.994-29.823-.429-54.35-24.631-55.155-54.447-.44-16.287 6.085-31.049 16.803-41.548H231.176C241.553 426.165 248 440.326 248 456c0 31.813-26.528 57.431-58.67 55.938-28.54-1.325-51.751-24.385-53.251-52.917-1.158-22.034 10.436-41.455 28.051-51.586L93.883 64H24C10.745 64 0 53.255 0 40V24C0 10.745 10.745 0 24 0h102.529c11.401 0 21.228 8.021 23.513 19.19L159.208 64H551.99c15.401 0 26.816 14.301 23.403 29.319l-47.273 208C525.637 312.246 515.923 320 504.717 320zM408 168h-48v-40c0-8.837-7.163-16-16-16h-16c-8.837 0-16 7.163-16 16v40h-48c-8.837 0-16 7.163-16 16v16c0 8.837 7.163 16 16 16h48v40c0 8.837 7.163 16 16 16h16c8.837 0 16-7.163 16-16v-40h48c8.837 0 16-7.163 16-16v-16c0-8.837-7.163-16-16-16z"
                      class=""></path>
              </svg>
              Přidat do košíku
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="bouquet-detail">
      <div class="buttons">
        <a class="button" v-bind:class="{fill: description}" @click="showDetail(true)">
          Podrobnější informace
        </a>
        <a class="button" v-bind:class="{fill: !description}" @click="showDetail(false)">
          Recenze
        </a>
      </div>
      <div class="description" v-show="description">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
        facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
        scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
      </div>
      <div class="reviews" v-show="!description">
        <div class="review-form">
          <div class="rating">
            <label for="rating-1">
              <span class="star" v-bind:class="{fill: stars >= 1}">
                <font-awesome-icon :icon="['fas', 'star']"/>
              </span>
            </label>
            <input type="radio" id="rating-1" name="rating" v-model="stars" value="1"/>
            <label for="rating-2">
              <span class="star" v-bind:class="{fill: stars >= 2}">
                <font-awesome-icon :icon="['fas', 'star']"/>
              </span>
            </label>
            <input type="radio" id="rating-2" name="rating" v-model="stars" value="2"/>
            <label for="rating-3">
              <span class="star" v-bind:class="{fill: stars >= 3}">
                <font-awesome-icon :icon="['fas', 'star']"/>
              </span>
            </label>
            <input type="radio" id="rating-3" name="rating" v-model="stars" value="3"/>
            <label for="rating-4">
              <span class="star" v-bind:class="{fill: stars >= 4}">
                <font-awesome-icon :icon="['fas', 'star']"/>
              </span>
            </label>
            <input type="radio" id="rating-4" name="rating" v-model="stars" value="4"/>
            <label for="rating-5">
              <span class="star" v-bind:class="{fill: stars >= 5}">
                <font-awesome-icon :icon="['fas', 'star']"/>
              </span>
            </label>
            <input type="radio" id="rating-5" name="rating" v-model="stars" value="5"/>
          </div>
          <textarea v-model="text" title=""/><br/>
          <button class="button" @click="submitForm">Přidat hodnocení</button>
        </div>

        <h2>Recenze</h2>
        <div class="review">
          <div class="rating">
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
          </div>
          <div class="text">
            Top kytka!
          </div>
        </div>
        <div class="review">
          <div class="rating">
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
          </div>
          <div class="text">
            Trash!
          </div>
        </div>
        <div class="review">
          <div class="rating">
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star fill"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
            <span class="star"><font-awesome-icon :icon="['fas', 'star']"/></span>
          </div>
          <div class="text">
            Celkem v klidu.
          </div>
        </div>
      </div>
    </div>

    <div class="recommended">
      <h2 class="text-center">Mohlo by vás také zaujmout</h2>
      <carousel>
        <product-tile v-for='flower in recommendedFlowers' :key='flower.id' :name='flower.name'
                      :price='flower.price'></product-tile>
      </carousel>
    </div>
  </div>
</template>

<script>
  import GeneralInput from '../components/GeneralInput';
  import Carousel from '../components/Carousel';
  import ProductTile from '../components/ProductTile';

  export default {
    components: {
      GeneralInput,
      ProductTile,
      Carousel
    },
    data() {
      return {
        count: 1,
        description: true,
        recommendedFlowers: [
          {name: 'Nazev kytice', price: 899},
          {name: 'Velmi dlouhy nazev kytice', price: 899},
          {name: 'Nazev kytice', price: 899},
          {name: 'Nazev kytice', price: 899},
          {name: 'Nazev kytice', price: 899},
          {name: 'Nazev kytice', price: 899}
        ],
        stars: 0,
        text: ''
      }
    },
    methods: {
      quantityAdd() {
        this.count += 1;
      },
      quantitySubtraction() {
        if (this.count > 1) {
          this.count -= 1;
        }
      },
      showDetail(val) {
        this.description = val;
      },
      async addToCart(){
        if(this.count < 1){
          this.count = 1;
        }


      },
      async submitForm() {
        if (this.stars === 0) {
          this.$store.dispatch('openModal', 'Zadejte hodnocení');
          return;
        }

        if (this.text.trim().length === 0) {
          this.$store.dispatch('openModal', 'Zadejte text hodnocení');
          return;
        }


      }
    }
  }


</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .text-center {
    text-align: center;
  }


  .bouquet-all {
    padding: 2rem;

    .bouquet {
      display: flex;
      margin-bottom: 30px;

      .b-left {
        width: 50%;
        padding-right: 1rem;

        img {
          width: 100%;
          height: auto;
        }
      }

      .b-right {
        width: 50%;
        padding-left: 1rem;

        .title {
          font-size: 36px;
          font-weight: 600;
        }

        .rating {

          .star {
            color: $midGrey;

            &.fill {
              svg {
                color: $mainOrange;
              }
            }
          }
        }

        .description {
          font-size: 24px;
          padding: .5rem 0;
        }

        .stock {
          font-size: 30px;
          margin: 10px 0;
        }

        .price {
          font-size: 30px;
          margin: 10px 0;

          .small {
            font-size: 18px;
            margin-right: 1rem;
          }
        }

        .cart-add {
          display: flex;
          align-items: center;

          .quantity {
            display: block;
            position: relative;

            input {
              width: 30px;
              height: 25px;
              border: 1px solid $darkGrey;
              border-radius: 10px;
              padding: 10px 25px;
              font-size: 1.5rem;
            }

            .q-up {
              position: absolute;
              display: flex;
              justify-content: center;
              align-items: center;
              top: 0;
              right: 0;
              width: 25%;
              height: 48%;
              border: 1px solid $darkGrey;
              border-top-right-radius: 10px;
            }

            .q-down {
              position: absolute;
              display: flex;
              justify-content: center;
              align-items: center;
              bottom: 0;
              right: 0;
              width: 25%;
              height: 48%;
              border: 1px solid $darkGrey;
              border-bottom-right-radius: 10px;
              margin: 0;
            }

          }

          input[type="number"] {
            -webkit-appearance: textfield;
            -moz-appearance: textfield;
            appearance: textfield;
          }

          input[type=number]::-webkit-inner-spin-button,
          input[type=number]::-webkit-outer-spin-button {
            -webkit-appearance: none;
          }

          .cart-button {
            padding: 0 21px;

            .button {
              border: none;
              border-radius: 7px;
              box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
              background: $mainBlue;
              color: white;
              padding: .5rem 1rem;
              font-size: 1.1em;
              cursor: pointer;
              transition-duration: .2s;

              &:hover {
                background: $darkBlue;
              }
            }
          }
        }
      }
    }

    .bouquet-detail {
      .buttons {
        padding: .5rem 0;
        border-bottom: 1px solid $darkGrey;
        margin-bottom: 15px;

        .button {
          border: none;
          border-radius: 7px 7px 0 0;
          box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
          background: #fff;
          color: $darkGrey;
          padding: .5rem 1rem;
          font-size: 1.1em;
          cursor: pointer;
          transition-duration: .2s;

          &.fill {
            background: $almostWhite;
          }

          &:hover {
            background: $almostWhite;
          }
        }
      }

      .description {
        margin: 0 1rem;
      }

      .reviews {
        margin: 0 1rem;

        .review-form {
          .rating {
            .star {
              color: $midGrey;

              &.fill {
                svg {
                  color: $mainOrange;
                }
              }
            }

            input {
              opacity: 0;
              position: absolute;
            }
          }

          textarea {
            width: 25rem;
            height: 10rem;
            resize: none;
            font-size: .9em;
            border: 1px solid white;
            margin: 10px 0;
            padding: 7px 10px;
            border-radius: 7px;
            box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
            box-sizing: border-box;
            outline: none;

            flex: 1;

            &:focus {
              border-color: $mainOrange;
            }
          }

          .button {
            border: none;
            border-radius: 7px;
            box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
            background: $mainBlue;
            color: white;
            padding: 7px 5px;
            font-size: 1.1em;
            cursor: pointer;
            transition-duration: .2s;

            &:hover {
              background: $darkBlue;
            }
          }
        }

        .review {
          margin-bottom: 2rem;


          .rating {
            .star {
              color: $midGrey;

              &.fill {
                svg {
                  color: $mainOrange;
                }
              }
            }
          }
        }
      }
    }

    .recommended {
      padding: 2rem 0;

      h2 {
        font-size: 30px;
        font-weight: 600;
      }
    }
  }
</style>
