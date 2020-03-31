<template>
  <div id='login-wrap'>
    <div id='login-center'>
      <div id='login-box'>
        <h1>Přihlášení</h1>
        <form @submit.prevent>
          <login-input title='E-mail' v-model='mail'></login-input>
          <login-input title='Heslo' type='password' v-model='pwd'></login-input>
          <input type="submit" class='button' value="Přihlásit se">
        </form>
        <div id='pwd-reset'>
          <span>Zapomněli jste heslo?</span>
        </div>
      </div>
      <div id='signup-box'>
        <h2>Ještě nemáte účet?</h2>
        <input type='button' class='button signup' value="Vytvořit nový účet" @click='$router.push("/signup")'>
      </div>
    </div>
  </div>
</template>

<script>
import LoginInput from '../components/LoginInput';

export default {
  data() {
    return {
      mail: '',
      pwd: ''
    };
  },
  components: {
    LoginInput
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  #login-wrap {
    flex: 1;
    position: relative;
  }

  #login-center {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
  }

  #login-box {
    background: $almostWhite;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 12px;
    padding: 20px 20px;
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

  h1 {
    margin: 0;
    text-align: center;
    margin-bottom: 20px;
  }

  h2 {
    margin: 0;
    text-align: center;
  }

  #pwd-reset {
    text-align: center;
    margin-top: 15px;
    font-size: .85em;
    & > span {
      cursor: pointer;
      transition-duration: .2s;
      &:hover {
        color: $mainBlue;
      }
    }
  }

  #signup-box {
    margin-top: 20px;
    background: $almostWhite;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 12px;
    padding: 20px 20px;
  }

  .signup {
    margin-top: 10px !important;
  }
</style>
