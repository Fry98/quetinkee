<template>
  <div id='layout-wrap'>
    <div id='layout'>
      <sidebar :key='$store.getters.sidebar'></sidebar>
      <main ref='main'>
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
  },
  computed: {
    path() {
      return this.$route.path;
    }
  },
  watch: {
    path() {
      this.$refs.main.scrollTop = 0;
    }
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
