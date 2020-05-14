<template>
  <div class="bouquet-all">
    <div class="bouquet">
      <div class="b-left"></div>
      <div class="b-right">
        <div class='detail-header'>
          <div class="title">
            Název květiny
          </div>
          <star-rating :value='stars'></star-rating>
        </div>
        <div class="price">
          Cena <span class="small">s DPH</span> <span class='bold'>1 999 Kč</span>
        </div>
        <div class="description">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
          facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
          scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
        </div>
        <div class='filler'></div>
        <div class="stock bold">
          Skladem > 5 ks
        </div>
        <div class="cart-add">
          <counter v-model='count'></counter>
          <div class="cart-button">
            <button class="button" @click="addToCart()">
              <font-awesome-icon icon="cart-plus" />
              Přidat do košíku
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="bouquet-detail">
      <div class="buttons">
        Recenze
      </div>
      <div class="reviews">
        <div class="review-form">
          <textarea v-model="text" placeholder="Přidejte svojí recenzi..." />
          <div class='review-submit'>
            <star-rating v-model='reviewStars' clickable></star-rating>
            <button class="button" @click="submitForm">Přidat recenzi</button>
          </div>
        </div>
        <div class='review-wrap'>
          <h2>Recenze</h2>
          <div class="review">
            <div class='review-header'>
              <div>Roman Toman</div>
              <star-rating :value='3'></star-rating>
            </div>
            <div class="text">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
              facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
              scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
            </div>
          </div>
          <div class="review">
            <div class='review-header'>
              <div>Mike Litoris</div>
              <star-rating :value='2'></star-rating>
            </div>
            <div class="text">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
              facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
              scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
            </div>
          </div>
          <div class="review">
            <div class='review-header'>
              <div>Hugh Janus</div>
              <star-rating :value='5'></star-rating>
            </div>
            <div class="text">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ullamcorper metus in quam cursus, non rutrum est
              facilisis. Phasellus euismod sem eu suscipit fermentum. Suspendisse potenti. Nulla lacus massa, eleifend in
              scelerisque eu, varius gravida enim. Nam quis ex a mauris congue tincidunt. Mauris placerat ligula sem.
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import GeneralInput from '../components/GeneralInput';
  import Carousel from '../components/Carousel';
  import ProductTile from '../components/ProductTile';
  import StarRating from '../components/StarRating';
  import Counter from '../components/Counter';

  export default {
    components: {
      GeneralInput,
      ProductTile,
      StarRating,
      Counter
    },
    data() {
      return {
        stars: 3,
        text: '',
        reviewStars: 3,
        count: 1
      }
    },
    methods: {
      submitForm() {
        if (this.text.trim().length === 0) {
          this.$store.dispatch('openModal', 'Zadejte text hodnocení');
          return;
        }

        alert('added');
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
    padding: 50px;

    .bouquet {
      display: flex;
      margin-bottom: 30px;

      .b-left {
        width: 370px;
        height: 370px;
        padding-right: 1rem;
        background: red;

        img {
          width: 100%;
          height: auto;
        }
      }

      .b-right {
        width: 600px;
        padding-left: 1rem;
        display: flex;
        flex-direction: column;

        .title {
          font-size: 1.8em;
          font-weight: 600;
          margin-right: 10px;
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
          font-size: 1.1em;
        }

        .stock {
          font-size: 1.2em;
          margin-bottom: 5px;
        }

        .price {
          font-size: 1.3em;
          margin-bottom: 10px;

          .small {
            font-size: .7em;
            margin-right: 1rem;
          }
        }

        .cart-add {
          display: flex;
          align-items: center;

          .cart-button {
            margin-left: 10px;

            .button {
              border: none;
              border-radius: 7px;
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
        font-size: 1.2em;

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
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 700px;
        margin: auto;

        .review-form {
          width: 100%;
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
            width: 100%;
            height: 10rem;
            resize: none;
            font-size: .9em;
            border: 1px solid rgb(247, 247, 247);
            margin: 10px 0;
            padding: 12px;
            border-radius: 7px;
            box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
            box-sizing: border-box;
            outline: none;
            background: rgb(247, 247, 247);
            flex: 1;
            font-size: 1.05em;

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
            padding: 7px 15px;
            font-size: 1.1em;
            cursor: pointer;
            transition-duration: .2s;

            &:hover {
              background: $darkBlue;
            }
          }
        }

        .review {
          margin-bottom: 30px;
          width: 100%;

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

  .detail-header {
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }

  .bold {
    font-weight: 600;
  }

  .filler {
    flex: 1;
  }

  .review-submit {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .review-wrap {
    width: 100%;

    h2 {
      margin-bottom: 5px;
      font-size: 1.5em;
    }
  }

  .review-header {
    display: flex;

    div {
      font-size: 1.1em;
      font-weight: 600;
      margin-right: 10px;
      margin-bottom: 3px;
    }
  }
</style>
