<template>
  <div id='layout-wrap'>
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
  }

  #layout {
    display: flex;
    flex-direction: row;
    max-height: 100%;
  }

  main {
    overflow-y: scroll;
    flex: 1;
  }
</style>
