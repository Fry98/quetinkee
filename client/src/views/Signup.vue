<template>
  <div id="signup">
    <div id='signup-center'>
      <div id='signup-box'>
        <h1>Registrace</h1>
        <form @submit.prevent>
          <h2>Osobní informace</h2>
          <general-input title='Jméno' v-model='surname'></general-input>
          <general-input title='Příjmení' v-model='lastName'></general-input>
          <general-input title='E-mail' v-model='mail'></general-input>
          <general-input title='Telefon' v-model='phone'></general-input>
          <h2>Nastavení účtu</h2>
          <general-input title='Heslo' type='password' v-model='pwd'></general-input>
          <general-input title='Heslo znovu' type='password' v-model='rePwd'></general-input>
          <h2>Doručovací adresa</h2>
          <general-input title='Město' v-model='city'></general-input>
          <general-input title='Ulice, č.p.' v-model='street'></general-input>
          <general-input title='PSČ' v-model='zip'></general-input>
          <label>
            <input type='checkbox' v-model='billingIsSame'>
            Doručovací adresa je shodná s fakturační adresou
          </label>
          <div v-if='!billingIsSame'>
            <h2>Fakturační adresa</h2>
            <general-input title='Město' v-model='billingCity'></general-input>
            <general-input title='Ulice, č.p.' v-model='billingStreet'></general-input>
            <general-input title='PSČ' v-model='billingZip'></general-input>
          </div>
          <input type="submit" class='button' value="Registrovat se">
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
      billingIsSame: 'true',
      billingCity: '',
      billingStreet: '',
      billingZip: '',
    };
  },
  components: {
    GeneralInput
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
    min-width: 400px;
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
    margin-bottom: 0;
  }

  h2 {
    font-size: 1.1em;
    margin: 0;
    text-align: left;
    margin-bottom: 0;
    color: $midGrey;
  }
  
  .button {
    width: 100%;
    border: none;
    border-radius: 7px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    background: $mainBlue;
    color: white;
    padding: 7px 0px;
    font-size: 1.1em;
    margin-top: 23px;
    cursor: pointer;
    transition-duration: .2s;
    &:hover {
      background: $darkBlue;
    }
  }

</style>
