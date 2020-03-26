<template>
  <div id="app">
    <navbar></navbar>
    <router-view></router-view>
  </div>
</template>

<script>
import Navbar from './components/Navbar';

export default {
  components: {
    Navbar
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
    min-height: 100vh;
  }
</style>
