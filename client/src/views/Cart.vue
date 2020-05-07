<template>
  <div class="all">
    <div class="cart" v-if="step === 1">
      <h2 class="text-center">1. Košík</h2>

      <div class="items">
        <div class="item" v-for="item in items" :key="item.id">
          <div class="left">
            <div class="image"></div>
            <span class="name">{{item.name}}</span>
          </div>
          <div class="right">
            <span class="price">{{item.price * item.count}}&nbsp;Kč</span>
            <counter v-model="item.count" class="counter"/>
            <span class="delete" @click="remove(item.id)">
            <font-awesome-icon icon="times"/>
          </span>
          </div>
        </div>
      </div>
      <div class="summary">
        <span class="total">Cena celkem: {{ getTotal() }}</span>
        <button class="button" :class="{disabled: Object.keys(items).length === 0}" @click="nextStep(step)">Pokračovat
        </button>
      </div>
    </div>
    <div class="delivery" v-if="step === 2">
      <h2 class="text-center">2. Doručení a platba</h2>

      <div class="info">
        <div class="row">
          <div class="date">
            <span>Datum doručení:</span>
            <div class="datepick">
              <font-awesome-icon icon="calendar-alt"/>
              <functional-calendar :configs="calendarConfigs" v-model="calendarData"></functional-calendar>
            </div>
          </div>
          <div class="time">
            Čas doručení: {{calendarData.selectedHour && calendarData.selectedHour ? calendarData.selectedHour + ":"
            +calendarData.selectedMinute : ''}}
          </div>
          <div class="payment">
            Platba:
            <select v-model="payment">
              <option v-bind:value="{ card: 'Platba kartou' }">Platba kartou</option>
              <option v-bind:value="{ cash: 'Hotově' }">Hotově</option>
            </select>
          </div>
        </div>

        <div class="personal-info">
          <h3>Osobní informace</h3>
          <generalInput v-model="firstName" title="Jméno"/>
          <generalInput v-model="lastName" title="Příjmení"/>
          <generalInput v-model="mail" title="E-mail"/>
          <generalInput v-model="phone" title="Telefon"/>
          <label class="checkbox-container">
            <span class="checkmark" :class="{checked: billingIsSame}">
              <font-awesome-icon icon='check' v-show="billingIsSame"/>
            </span>
            Doručovací adresa je shodná s fakturační adresou
            <input type="checkbox" v-model="billingIsSame">
          </label>

          <h3>Doručovací informace</h3>
          <general-input v-model="city" title="Město"/>
          <general-input v-model="street" title="Ulice a č.p."/>
          <general-input v-model="zip" title="PSČ"/>

          <div v-if="!billingIsSame">
            <h3>Fakturační informace</h3>
            <general-input v-model="billingCity" title="Město"/>
            <general-input v-model="billingStreet" title="Ulice a č.p."/>
            <general-input v-model="billingZip" title="PSČ"/>
          </div>

          <label class="checkbox-container">
            <span class="checkmark" :class="{checked: termsAndConditions}">
              <font-awesome-icon icon='check' v-show="termsAndConditions"/>
            </span>
            Souhlasím s&nbsp;<a href="#" target="_blank">obchodními podmínkami</a> &nbsp;společnosti quetinkee
            <input type="checkbox" v-model="termsAndConditions">
          </label>
        </div>
      </div>
      <div class="submit">
        <button class="button" :class="{disabled: Object.keys(items).length === 0}" @click="nextStep(step)">
          Objednat
        </button>
      </div>
    </div>
    <div class="ending" v-if="step === 3">
      <h2 class="text-center">3. Děkujeme</h2>
      <p class="text-center">
        Na Vaši emailovou adresu {{mail}} bylo zasláno shrnutí Vaší objednávky. Děkujeme za Váš nákup.
      </p>
    </div>
  </div>
</template>

<script>
  import Counter from "../components/Counter";
  import {FunctionalCalendar} from 'vue-functional-calendar';
  import GeneralInput from "../components/GeneralInput";

  export default {
    components: {
      Counter,
      FunctionalCalendar,
      GeneralInput
    },
    data() {
      const user = this.$store.getters.user;
      return {
        items: {
          0: {
            id: 0,
            name: 'Název kytice',
            price: 249,
            count: 1,
            oldCount: 1
          },
          1: {
            id: 1,
            name: 'Bezva kytice',
            price: 549,
            count: 1,
            oldCount: 1
          },
          2: {
            id: 2,
            name: 'Super kytice',
            price: 749,
            count: 1,
            oldCount: 1
          },
          3: {
            id: 3,
            name: 'Pěkná kytice',
            price: 349,
            count: 1,
            oldCount: 1
          }
        },
        step: 1,
        calendarData: {},
        calendarConfigs: {
          sundayStart: false,
          dateFormat: 'dd.mm.yyyy',
          isDatePicker: true,
          isModal: true,
          disabledDates: ['beforeToday', 'afterToday + 5'],
          placeholder: ' ',
          dayNames: ['Po', 'Út', 'St', 'Čt', 'Pa', 'So', 'Ne'],
          disabledDayNames: ['So', 'Ne'],
          monthNames: ["Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"],
          withTimePicker: true,
        },
        payment: 'card',
        firstName: user ? user.firstName : '',
        lastName: user ? user.lastName : '',
        phone: user ? user.phone : '',
        mail: user ? user.mail : '',
        city: user ? user.addressDelivery.city : '',
        street: user ? user.addressDelivery.street : '',
        zip: user ? user.addressDelivery.zip : '',
        billingCity: user ? (user.addressBilling ? user.addressBilling.city : '') : '',
        billingStreet: user ? (user.addressBilling ? user.addressBilling.street : '') : '',
        billingZip: user ? (user.addressBilling ? user.addressBilling.zip : '') : '',
        billingIsSame: user ? user.addressBilling === null : true,
        termsAndConditions: false
      }
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
          }
          this.count = numCount;
          this.oldCount = numCount;
          return;
        }
        this.count = this.oldCount;
      },
    },
    computed: {},
    methods: {
      changeQuantity(x, item) {
        const newCount = item.count + x;
        if (newCount > 50) return;
        if (newCount < 1) {
          delete this.items[item.id];
          this.$forceUpdate();
        }
        item.count = newCount;
      },
      resetQuantity(item) {
        if (item.count === '') {
          item.count = 1;
          item.oldCount = 1;
        }
      },
      remove(id) {
        delete this.items[id];
        this.$forceUpdate();
      },
      getTotal() {
        let total = 0;

        for (const item in this.items) {
          total += this.items[item].price * this.items[item].count;
        }

        return total;
      },
      nextStep(step) {
        switch (step) {
          case 1:
            if (Object.keys(this.items).length < 1) {
              this.$store.dispatch('openModal', 'Košík je prázdný');
              return;
            }
            this.step = 2;
            break;
          case 2:
            if (!this.calendarData.selectedDateTime) {
              this.$store.dispatch('openModal', 'Zadejte datum a čas');
              return;
            }

            if (this.firstName.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte křestní jméno');
              return;
            }

            if (this.lastName.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte příjmení');
              return;
            }

            if (this.mail.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte e-mail');
              return;
            }

            if (!this.phone.replace(/ /g, '').match(/^[0-9]{9}$/)) {
              this.$store.dispatch('openModal', 'Nesprávně zadaný telefon');
              return;
            }

            if (this.city.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte město');
              return;
            }

            if (this.street.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte ulici a č.p.');
              return;
            }

            if (this.zip.trim().length === 0) {
              this.$store.dispatch('openModal', 'Zadejte PSČ');
              return;
            }

            if (!this.billingIsSame) {
              if (this.billingCity.trim().length === 0) {
                this.$store.dispatch('openModal', 'Zadejte město pro fakturaci');
                return;
              }

              if(this.billingStreet.trim().length === 0){
                this.$store.dispatch('openModal', 'Zadejte ulici a č.p. pro fakturaci');
                return;
              }

              if(this.billingZip.trim().length === 0){
                this.$store.dispatch('openModal', 'Zadejte PSČ pro fakturaci');
                return;
              }
            }

            if(!this.termsAndConditions){
              this.$store.dispatch('openModal', 'Musíte souhlasit s obchodními podmínkami');
            }

            this.step = 3;
            break;
          default:
            break;
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

  .text-right {
    text-align: right;
  }

  .row {
    display: flex;
    width: 100%;
  }

  .all {
    padding: 0 50px;
  }

  h2 {
    font-size: 3em;
    font-weight: 500;
  }

  .cart {

    .price, .name {
      font-weight: 700;
      font-size: 1.5em;
    }

    .items {
      background-color: $almostWhite;
      border-radius: 12px;
      padding: 25px;


      .item {
        background-color: #fff;
        display: flex;
        align-items: center;
        height: 171px;
        border-radius: 12px;
        margin: 15px 0;

        .left {
          display: flex;
          align-items: center;
          width: 50%;
          margin: 0 25px;

          .image {
            width: 180px;
            height: 120px;
            background-color: #f00;
            margin-right: 15px;
          }

        }

        .right {
          display: flex;
          align-items: center;
          justify-content: flex-end;
          width: 50%;
          margin: 0 25px;

          .counter {
            margin: 0 15px;
          }
        }
      }
    }

    .delete {
      svg {
        color: $darkBlue;
        cursor: pointer;
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


    .summary {
      display: flex;
      flex-direction: column;
      align-items: flex-end;

      .total {
        font-size: 1.5em;
        margin-bottom: 5px;
      }

      .total, .button {
        width: 250px;
      }
    }
  }

  .delivery {

    .submit {
      display: flex;
      justify-content: flex-end;
    }

    .row {
      align-items: center;
    }

    .info {
      background-color: $almostWhite;
      border-radius: 12px;
      padding: 25px;
    }

    h3 {
      color: $midGrey;
    }

    .date {
      width: 33.333%;
      display: flex;
      align-items: center;

      .datepick {
        position: relative;
        display: flex;
        align-items: center;
        width: 33.333%;
        margin-left: 5px;

        svg {
          position: absolute;
          left: 10px;
        }

        .vfc-single-input {
          font-size: .9em;
          border: 1px solid white;
          margin-top: 3px;
          padding: 7px 10px;
          border-radius: 7px;
          box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
          box-sizing: border-box;
          outline: none;
          width: 10em;
          flex: 1;

          &:focus {
            border-color: $mainOrange;
          }
        }

      }
    }

    .time {
      width: 33.333%;
      display: flex;
      justify-content: center;
    }

    .payment {
      width: 33.333%;
      display: flex;
      justify-content: flex-end;
      align-items: center;

      select {
        margin-left: 5px;
        font-size: .9em;
        border: 1px solid white;
        margin-top: 3px;
        padding: 3px 10px;
        border-radius: 7px;
        box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
        box-sizing: border-box;
        outline: none;
        width: 10em;

        &:focus {
          border-color: $mainOrange;
        }
      }
    }

    .personal-info {
      display: flex;
      justify-content: center;
      flex-direction: column;
      width: 70%;
      margin: 0 auto;
    }

    .checkbox-container {
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      cursor: pointer;

      input {
        position: absolute;
        opacity: 0;
      }

      .checkmark {
        display: inline-block;
        position: relative;
        width: 16px;
        height: 16px;
        background: #fff;
        padding: 5px;
        margin-right: 15px;
        transition-duration: .5s;
        box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);

        path {
          color: #fff;
        }

        &.checked {
          background: $mainBlue;
        }
      }
    }
  }

  .button {
    border: none;
    border-radius: 7px;
    background: $mainBlue;
    color: white;
    padding: .5rem 1rem;
    font-size: 1.1em;
    cursor: pointer;
    transition-duration: .2s;
    margin: 8px 0;

    &:hover {
      background: $darkBlue;
    }

    &.disabled {
      cursor: default;
      background: $darkGrey;

      &:hover {
        background: $darkGrey;
      }
    }
  }
</style>
