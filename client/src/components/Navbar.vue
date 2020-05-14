<template>
  <header>
    <main>
      <div id='logo' @click='navigate("/")'>
        <img src="../assets/logotype.png" alt="Logo">
      </div>
      <div id='filler'></div>
      <div class='nav-button' @click='navigate("/cart")'>
        <div id='cart-amount'>0</div>
        <font-awesome-icon class='nav-icon' icon='shopping-cart'></font-awesome-icon>
      </div>
      <div id='nav-profile' class='nav-button' v-if='$store.getters.isLogged'>
        <font-awesome-icon id='nav-profile-icon' class='nav-icon' icon='user'></font-awesome-icon>
        <div class='profile-opts'>
          <div class='opt-box'>
            <div v-if='$store.getters.isAdmin'>
              <div class='opt-item' @click='navigate("/admin")'>
                <font-awesome-icon icon='users-cog'></font-awesome-icon>
                <span>Admin</span>
              </div>
              <div class='opt-spacer'></div>
            </div>
            <div v-if='$store.getters.isDelivery'>
              <div class='opt-item' @click='navigate("/delivery")'>
                <font-awesome-icon icon='truck'></font-awesome-icon>
                <span>Objednávky</span>
              </div>
              <div class='opt-spacer'></div>
            </div>
            <div class='opt-item' @click='navigate("/profile")'>
              <font-awesome-icon icon='address-card'></font-awesome-icon>
              <span>Profil</span>
            </div>
            <div class='opt-spacer'></div>
            <div class='opt-item' @click='logout'>
              <font-awesome-icon icon='sign-out-alt'></font-awesome-icon>
              <span>Odhlásit se</span>
            </div>
          </div>
        </div>
      </div>
      <div id='login' @click="navigate('/login')" v-if='!$store.getters.isLogged'>
        <font-awesome-icon icon='user'></font-awesome-icon>
        <div>Přihlášení</div>
      </div>
    </main>
    <div id='stripe'></div>
  </header>
</template>

<script>
  import axios from 'axios';

  export default {
    methods: {
      navigate(path) {
        if (this.$route.path !== path) this.$router.push(path);
      },
      logout() {
        axios({
          url: '/api/logout',
          method: 'get'
        }).then(() => {
          this.$store.dispatch('logout');
          this.navigate('/');
          this.$store.commit('reloadSidebar');
        }).catch(() => {
          this.$store.dispatch('openModal', "Chyba při odhlášení");
        });
      }
    }
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

  .nav-icon {
    font-size: 2.2em;
    cursor: pointer;
    transition-duration: .2s;
    padding: 4px 6px;
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

  .nav-button {
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

  #nav-profile-icon {
    width: 45px;
    padding-left: 0px;
    padding-right: 0px;
  }

  #nav-profile {
    margin-left: 3px;
    position: relative;
    &:hover .profile-opts {
      // display: block;
      opacity: 1;
      pointer-events: all;
      transform: none;
    }
  }

  .profile-opts {
    // display: none;
    width: 160px;
    position: absolute;
    right: 0px;
    padding-top: 3px;
    z-index: 1;
    box-sizing: border-box;
    opacity: 0;
    pointer-events: none;
    transition-duration: .2s;
    transform: translateY(-10px);
  }

  .opt-box {
    background: $almostWhite;
    border-radius: 12px;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.281);
    width: 100%;
    height: 100%;
    padding: 5px;
    box-sizing: border-box;
    cursor: pointer;
  }

  .opt-item {
    color: black;
    font-size: 1.15em;
    padding: 4px 6px;
    border-radius: 7px;
    transition-duration: .2s;
    span {
      margin-left: 5px;
    }
    &:hover {
      background: $darkBlue;
      color: white;
    }
  }

  .opt-spacer {
    height: 5px;
  }
</style>
