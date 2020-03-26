<template>
  <header>
    <main>
      <div id='logo' @click='$router.push("/")'>
        <img src="../assets/logotype.png" alt="Logo">
      </div>
      <div id='filler'></div>
      <div id='cart-wrap'>
        <div id='cart-amount'>0</div>
        <font-awesome-icon id='cart' icon='shopping-cart'></font-awesome-icon>
      </div>
      <div id='login' @click="$router.push('/login')">
        <font-awesome-icon icon='user'></font-awesome-icon>
        <div>Přihlášení</div>
      </div>
    </main>
    <div id='stripe'></div>
  </header>
</template>

<script>
  export default {
  };
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  #logo > img {
    width: auto;
    height: 2em;
    padding-top: 5px;
    cursor: pointer;
  }

  main {
    background: $mainOrange;
    height: 3.3em;
    display: flex;
    align-items: center;
    color: white;
    padding: 0px 12px;
    font-size: .95em;
  }

  #stripe {
    background: $darkOrange;
    height: 2px;
  }

  #filler {
    flex: 1;
  }

  #cart {
    font-size: 2.2em;
    cursor: pointer;
    transition-duration: .2s;
    padding: 4px;
    border-radius: 7px;
    &:hover {
      background: $darkOrange;
    }
  }

  #login {
    font-size: 1.2em;
    margin-left: 10px;
    border: 1px solid white;
    border-radius: 7px;
    display: flex;
    align-items: center;
    padding: 5px 7px;
    cursor: pointer;
    transition-duration: .2s;
    & > div {
      margin-left: 4px;
    }
    &:hover {
      background: $darkOrange;
    }
  }

  #cart-wrap {
    position: relative;
  }

  #cart-amount {
    position: absolute;
    color: $mainOrange;
    top: 50%;
    left: 50%;
    transform: translate(-40%, -72%);
    pointer-events: none;
    font-weight: 600;
    text-align: center;
    width: 30px;
  }
</style>
