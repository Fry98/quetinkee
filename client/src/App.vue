<template>
  <div id="app">
    <modal></modal>
    <navbar></navbar>
    <router-view></router-view>
  </div>
</template>

<script>
import Navbar from './components/Navbar';
import Modal from './components/Modal';

export default {
  components: {
    Navbar,
    Modal
  }
}
</script>

<style lang="scss">
  @import url('https://fonts.googleapis.com/css?family=Montserrat:400,600&display=swap');

  * {
    font-family: 'Montserrat', sans-serif;
  }

  body {
    margin: 0px;
  }

  #app {
    display: flex;
    flex-direction: column;
    height: 100vh;
    overflow: hidden;
  }
</style>
