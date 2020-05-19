<template>
  <div class="bouquet-all">
    <div class="bouquet">
      <div class="b-left" :style="bgImage"></div>
      <div class="b-right">
        <div class='detail-header'>
          <div class="title">
            {{ title }}
          </div>
          <star-rating :value='stars' v-if='stars !== null'></star-rating>
        </div>
        <div class="price">
          Cena <span class="small">s DPH</span> <span class='bold'>{{ price }} Kč</span>
        </div>
        <div class="description" v-html="desc"></div>
        <div class='filler'></div>
        <div class="stock bold">{{ stockText }}</div>
        <div class="cart-add">
          <counter v-model='count'></counter>
          <div class="cart-button">
            <button class="button" @click="addToCart">
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
        <div class="review-form" v-if='$store.getters.isLogged'>
          <div class='review-exp' v-if='reviewed'>Tuto kytici jste již recenzoval/a</div>
          <div :class='{reviewed}' >
            <textarea v-model="text" placeholder="Přidejte svojí recenzi..."/>
            <div class='review-submit'>
              <star-rating v-model='reviewStars' clickable></star-rating>
              <button class="button" @click="submitForm">Přidat recenzi</button>
            </div>
          </div>
        </div>
        <div class='review-wrap'
          v-infinite-scroll='fetchReviews'
          infinite-scroll-disabled='loadStop'
          infinite-scroll-distance='10'
        >
          <div class="review" v-for='review in reviews' :key='review.userName'>
            <div class='review-header'>
              <div>{{ review.userName }}</div>
              <star-rating :value='review.rating'></star-rating>
            </div>
            <div class="text">{{ review.message }}</div>
          </div>
          <div class='loading' v-if='!over'>Loading...</div>
          <div class='loading' v-if='reviews.length === 0 && over && !reviewed'>Tato kytice nemá žádné recenze</div>
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
  import axios from 'axios';

  export default {
    components: {
      GeneralInput,
      ProductTile,
      StarRating,
      Counter
    },
    props: ['id'],
    data() {
      return {
        title: '...',
        desc: '...',
        price: '...',
        stars: null,
        stock: null,
        img: null,
        text: '',
        reviewStars: 3,
        count: 1,
        page: 0,
        reviews: [],
        loadStop: false,
        reviewed: false,
        over: false
      }
    },
    async mounted() {
      try {
        const res = await axios(`/api/shop/bouquet/${this.id}`);
        this.title = res.data.bouquet.name;
        this.desc = res.data.bouquet.perex;
        this.price = res.data.bouquet.price;
        this.stars = res.data.rating !== null ? Math.round(res.data.rating) : null;
        this.stock = res.data.storage;
      } catch (e) {
        this.$router.push('/');
      }

      if (!this.$store.getters.isLogged) return;
      try {
        await axios(`/api/shop/bouquet/${this.id}/is-review`);
      } catch (e) {
        this.reviewed = true;
      }
    },
    computed: {
      stockText() {
        if (this.stock === 0) {
          return "Zboží není na skladě";
        } else if (this.stock > 0 && this.stock < 6) {
          return `Na skladě ${this.stock} ks`;
        } else if (this.stock > 5) {
          return 'Na skladě > 5 ks';
        } else {
          return "Množství na skladě nedostupné";
        }
      },
      bgImage() {
        if (this.img === null) {
          return {
            background: '#C4C4C4'
          };
        }
      }
    },
    methods: {
      submitForm() {
        if (this.text.trim().length === 0) {
          this.$store.dispatch('openModal', 'Zadejte text hodnocení');
          return;
        }

        axios({
          method: 'post',
          url: `/api/shop/bouquet/${this.id}/reviews`,
          data: {
            message: this.text,
            rating: this.reviewStars
          }
        }).then(() => {
          this.reviewed = true;
          this.reviews.unshift({
            rating: this.reviewStars,
            message: this.text,
            userName: this.$store.getters.fullName
          });
          this.text = '';
        }).catch(() => {
          this.$store.dispatch('openModal', 'Chyba při přidávání recenze');
        });
      },
      addToCart() {
        this.$store.dispatch('addToCart', {
          id: this.id,
          name: this.title,
          price: this.price,
          img: this.img,
          count: this.count
        });
        this.$router.push('/');
      },
      async fetchReviews() {
        this.loadStop = true;
        const res = await axios(`/api/shop/bouquet/${this.id}/reviews?page=${this.page}&size=15`);
        this.reviews.push(...res.data.content);
        this.loadStop = res.data.last;
        this.over = res.data.last;
        this.page++;
      }
    }
  }


</script>

<style lang="scss">
  .description > p {
    margin: 0px;
  }
</style>

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
        flex-shrink: 0;

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
        width: 70%;
        margin: auto;

        .review-form {
          width: 100%;
          margin-bottom: 10px;
          position: relative;
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

  .loading {
    text-align: center;
    margin-top: 5px;
  }

  .reviewed {
    filter: grayscale(1);
    opacity: 0.5;
    pointer-events: none;
  }

  .review-exp {
    position: absolute;
    color: black;
    left: 50%;
    top: 50%;
    z-index: 2;
    transform: translate(-50%, -130%);
    font-size: 1.3em;
    font-weight: 600;
  }
</style>
