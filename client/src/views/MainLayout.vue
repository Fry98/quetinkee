<template>
  <div id='layout-wrap' :key='$store.getters.sidebar'>
    <div id='layout'>
      <sidebar></sidebar>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
import Sidebar from "../components/Sidebar";

export default {
  components: {
    Sidebar
  }
}
</script>

<style lang="scss" scoped>
  #layout-wrap {
    flex: 1;
    overflow: hidden;
    position: relative;
    display: flex;
    flex-direction: column;
  }

  #layout {
    display: flex;
    flex-direction: row;
    max-height: 100%;
    flex: 1;
  }

  main {
    overflow-y: auto;
    flex: 1;
  }
</style>
