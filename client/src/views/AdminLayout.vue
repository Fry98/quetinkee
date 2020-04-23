<template>
  <div id='layout-wrap'>
    <div id='layout'>
      <admin-sidebar></admin-sidebar>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
import AdminSidebar from "../components/AdminSidebar";

export default {
  components: {
    AdminSidebar
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
