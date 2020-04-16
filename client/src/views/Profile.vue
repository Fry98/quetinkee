<template>
  <div id='profile'>
    <div class='content'>
      <div class='card'>
        <div>
          <h1>Osobní informace</h1>
          <general-input title='Jméno' type="text" v-model='firstName'></general-input>
          <general-input title='Příjmení' type="text" v-model='lastName'></general-input>
          <general-input title='E-mail' type="text" v-model='mail'></general-input>
          <general-input title='Telefon' type="text" v-model='phone'></general-input>
          <h2>Doručovací adresa</h2>
          <general-input title='Město' type="text" v-model='city'></general-input>
          <general-input title='Ulice' type="text" v-model='street'></general-input>
          <general-input title='PSČ' type="text" v-model='zip'></general-input>
          <div v-if='billingCity !== null'>
            <h2>Fakturační adresa</h2>
            <general-input title='Město' type="text" v-model='billingCity'></general-input>
            <general-input title='Ulice' type="text" v-model='billingStree'></general-input>
            <general-input title='PSČ' type="text" v-model='billingZip'></general-input>
          </div>
          <div class='btn-wrap'>
            <button class='btn' :class='{"btn-deac": !isModified}'>Uložit změny</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import GeneralInput from "../components/GeneralInput";
  export default {
    name: "Profile",
    components: { GeneralInput },
    data() {
      const user = this.$store.getters.user;
      const data = {
        firstName: user.firstName,
        lastName: user.lastName,
        mail: user.mail,
        phone: user.phone,
        city: user.addressDelivery.city,
        street: user.addressDelivery.street,
        zip: user.addressDelivery.zip,
        billingCity: user.addressBilling ? user.addressBilling.city : null,
        billingStreet: user.addressBilling ? user.addressBilling.street : null,
        billingZip: user.addressBilling ? user.addressBilling.zip : null
      };
      return {
        og: data,
        ...data
      };
    },
    computed: {
      isModified() {
        return this.firstName !== this.og.firstName ||
          this.lastName !== this.og.lastName ||
          this.mail !== this.og.mail ||
          this.phone !== this.og.phone ||
          this.city !== this.og.city ||
          this.street !== this.og.street ||
          this.zip !== this.og.zip ||
          this.billingCity !== this.og.billingCity ||
          this.billingStreet !== this.og.billingStreet ||
          this.billingZip !== this.og.billingZip;
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #profile {
    display: flex;
    flex: 1;

    h1 {
      margin: 0;
      margin-bottom: 5px;
    }

    h2 {
      font-size: 1.2em;
      margin-bottom: 3px;
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
        width: 550px;
      }
    }

    button {
      font-size: 1.1em;
    }

    .btn-wrap {
      display: flex;
      justify-content: flex-end;
    }

    .btn-deac {
      cursor: default;
      background: $lightGrey;
    }
  }
</style>
