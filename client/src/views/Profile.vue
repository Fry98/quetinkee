<template>
  <div id='profile'>
    <div id='sidebar'>
      <h1>Váš profil</h1>
      <ul>
        <li>Osobní informace</li>
        <li>Nastavení účtu</li>
        <li>Doručovací adresa</li>
        <li>Fakturační adresa</li>
        <li>Historie objednávek</li>
      </ul>
    </div>
    <div class='content'>
      <div class='card'>
        <div>
          <h1>Osobní informace</h1>
          <general-input title='Jméno' type="text"></general-input>
          <general-input title='Příjmení' type="text"></general-input>
          <general-input title='E-mail' type="text"></general-input>
          <general-input title='Telefon' type="text"></general-input>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import GeneralInput from "../components/GeneralInput";
  export default {
      name: "Profile",
      components: {GeneralInput}
  }
</script>

<style lang="scss" scoped>
  @import "../scss/_vars.scss";

  #profile {
    display: flex;
    flex: 1;

    h1 {
      margin: 0;
    }

    .content {
      flex: 1;
      display: flex;
      justify-content: center;
      padding: 20px;
      .card > div {
        padding: 20px;
        background-color: $almostWhite;
        border-radius: 7px;
      }
    }

    #sidebar {
      background-color: $lightGrey;
      padding: 15px;
      min-width: 200px;
    }
  }
</style>