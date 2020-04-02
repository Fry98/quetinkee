<template>
  <div id="signup">
    <div id='signup-center'>
      <div id='signup-box'>
        <h1>Registrace</h1>
        <form @submit.prevent>
          <div v-if='!step2'>
            <h2>Osobní informace</h2>
            <general-input title='Jméno' v-model='surname'></general-input>
            <general-input title='Příjmení' v-model='lastName'></general-input>
            <general-input title='E-mail' v-model='mail'></general-input>
            <general-input title='Telefon' v-model='phone'></general-input>
            <h2>Nastavení účtu</h2>
            <general-input title='Heslo' type='password' v-model='pwd'></general-input>
            <general-input title='Heslo znovu' type='password' v-model='rePwd'></general-input>
            <div id='button-wrap'>
              <button class='button step' @click="nextStep">
                <font-awesome-icon icon='chevron-right'></font-awesome-icon>
                <span>Pokračovat</span>
              </button>
            </div>
          </div>
          <div v-else>
            <h2>Doručovací adresa</h2>
            <general-input title='Město' v-model='city'></general-input>
            <general-input title='Ulice, č.p.' v-model='street'></general-input>
            <general-input title='PSČ' v-model='zip'></general-input>
            <div>
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
              <input type="submit" class='button sub-btn' value="Registrovat se">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import GeneralInput from '../components/GeneralInput';

export default {
  data() {
    return {
      surname: '',
      lastName: '',
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
  methods: {
    nextStep() {
      if (!this.phone.match(/^[0-9]{9}$/)) {
        this.$store.dispatch('openModal', 'Zadané telefonní číslo není platné');
        return;
      }
      this.step2 = true;
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
    text-align: right;
  }

  #end-buttons {
    display: flex;
    margin-top: 15px;
  }
</style>
