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
