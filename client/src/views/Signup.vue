<template>
  <div id="signup">
    <div id='signup-center'>
      <div id='signup-box'>
        <h1>Registrace</h1>
        <div class='form'>
          <div v-show='!step2'>
            <h2>Osobní informace</h2>
            <general-input title='Jméno' v-model='name'></general-input>
            <general-input title='Příjmení' v-model='surname'></general-input>
            <general-input title='E-mail' v-model='mail'></general-input>
            <general-input title='Telefon' v-model='phone'></general-input>
            <h2>Nastavení účtu</h2>
            <general-input title='Heslo' type='password' v-model='pwd'></general-input>
            <general-input title='Heslo znovu' type='password' v-model='rePwd'></general-input>
            <div id='button-wrap'>
              <button class='button step back-btn' @click="$router.push('/login')">
                <font-awesome-icon icon='chevron-left'></font-awesome-icon>
                <span>Zpět</span>
              </button>
              <div id='filler'></div>
              <button class='button step' @click="nextStep">
                <font-awesome-icon icon='chevron-right'></font-awesome-icon>
                <span>Pokračovat</span>
              </button>
            </div>
          </div>
          <div v-show='step2'>
            <h2>Doručovací adresa</h2>
            <general-input title='Město' v-model='city'></general-input>
            <general-input title='Ulice, č.p.' v-model='street'></general-input>
            <general-input title='PSČ' v-model='zip'></general-input>
            <div id='adr-check'>
              <label>
                <input type='checkbox' v-model='billingIsSame'>
                Doručovací adresa je shodná s fakturační adresou
              </label>
            </div>
            <div v-if='!billingIsSame'>
              <h2>Fakturační adresa</h2>
              <general-input title='Město' v-model='billingCity'></general-input>
              <general-input title='Ulice, č.p.' v-model='billingStreet'></general-input>
              <general-input title='PSČ' v-model='billingZip'></general-input>
            </div>
            <div id='end-buttons'>
              <button class='button step back-btn' @click="step2 = false">
                <font-awesome-icon icon='chevron-left'></font-awesome-icon>
                <span>Zpět</span>
              </button>
              <button class='button sub-btn' @click="submitForm">Registrovat se</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import GeneralInput from '../components/GeneralInput';
  import axios from 'axios';

  export default {
    data() {
      return {
        name: '',
        surname: '',
        mail: '',
        phone: '',
        pwd: '',
        rePwd: '',
        city: '',
        street: '',
        zip: '',
        billingIsSame: true,
        billingCity: '',
        billingStreet: '',
        billingZip: '',
        step2: false
      };
    },
    components: {
      GeneralInput
    },
    created() {
      document.addEventListener('keydown', this.enterHandler);
    },
    destroyed() {
      document.removeEventListener('keydown', this.enterHandler);
    },
    methods: {
      enterHandler(e) {
        if (e.keyCode !== 13 || e.cancel) return;
        if (!this.step2) {
          this.nextStep();
        } else {
          this.submitForm();
        }
      },
      nextStep() {
        if (this.name.trim().length === 0) {
          this.$store.dispatch('openModal', 'Jméno je povinná položka');
          return;
        }

        if (this.surname.trim().length === 0) {
          this.$store.dispatch('openModal', 'Příjmení je povinná položka');
          return;
        }

        if (!this.mail.match(/^(.+)@(.+).(.){2,6}$/)) {
          this.$store.dispatch('openModal', 'Nesprávně zadaný email');
          return;
        }

        if (!this.phone.replace(/ /g, '').match(/^[0-9]{9}$/)) {
          this.$store.dispatch('openModal', 'Neplatné telefonní číslo');
          return;
        }

        if (this.pwd.length < 6) {
          this.$store.dispatch('openModal', 'Heslo musí mít aspoň 6 znaků');
          return;
        }

        if (this.rePwd !== this.pwd) {
          this.$store.dispatch('openModal', 'Zadaná hesla se neshodují');
          return;
        }

        this.step2 = true;
      },
      addressError(err) {
        this.$store.dispatch(
          'openModal',
          `${this.billingIsSame ? '' : '[Doručovací adresa] '}${err}`
        );
      },
      async submitForm() {
        if (this.city.trim().length === 0) {
          this.addressError('Město je povinná položka');
          return;
        }

        if (this.street.trim().length === 0) {
          this.addressError('Ulice je povinná položka');
          return;
        }

        if (!this.zip.replace(/ /g, '').match(/^[0-9]{5}$/)) {
          this.addressError('Neplatné směrovací číslo');
          return;
        }

        if (!this.billingIsSame) {
          if (this.billingCity.trim().length === 0) {
            this.$store.dispatch('openModal', '[Fakturační adresa] Město je povinná položka');
            return;
          }

          if (this.billingStreet.trim().length === 0) {
            this.$store.dispatch('openModal', '[Fakturační adresa] Ulice je povinná položka');
            return;
          }

          if (!this.billingZip.replace(/ /g, '').match(/^[0-9]{5}$/)) {
            this.$store.dispatch('openModal', '[Fakturační adresa] Neplatné směrovací číslo');
            return;
          }
        }

        const data = {
          firstName: this.name,
          lastName: this.surname,
          mail: this.mail,
          password: this.pwd,
          phone: this.phone,
          addressDelivery: {
            street: this.street,
            city: this.city,
            zip: this.zip
          }
        };

        if (!this.billingIsSame) {
          data.addressBilling = {
            street: this.billingStreet,
            city: this.billingCity,
            zip: this.billingZip
          };
        }

        try {
          await axios({
            url: '/api/profile',
            method: 'post',
            data
          });
          this.$router.push('/');
        } catch (err) {
          this.$store.dispatch('openModal', err.response.data);
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  #signup {
    flex: 1;
    position: relative;
  }

  #signup-center {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    min-width: 520px;
  }

  #signup-box {
    background: $almostWhite;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 12px;
    padding: 20px 20px;
  }

  h1 {
    margin: 0;
    text-align: left;
  }

  h2 {
    font-size: 1.1em;
    margin: 0;
    text-align: left;
    margin-bottom: 7px;
    color: $midGrey;
  }

  .button {
    border: none;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    background: $mainBlue;
    color: white;
    padding: 7px 0px;
    font-size: 1.1em;
    cursor: pointer;
    transition-duration: .2s;

    &:hover {
      background: $darkBlue;
    }
  }

  .sub-btn {
    flex: 1;
    margin-left: 5px;
  }

  .back-btn {
    background: $midGrey;

    &:hover {
      background: $darkGrey;
    }
  }

  .step {
    --padding-horizontal: 10px;
    width: auto;
    padding-left: var(--padding-horizontal);
    padding-right: var(--padding-horizontal);

    & > span {
      margin-left: 7px;
    }
  }

  #button-wrap {
    justify-content: flex-end;
    display: flex;
  }

  #end-buttons {
    display: flex;
    margin-top: 15px;
  }

  #filler {
    flex: 1;
  }

  #adr-check {
    margin-bottom: 10px;
  }
</style>
