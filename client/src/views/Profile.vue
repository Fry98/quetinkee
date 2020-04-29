<template>
  <div id='profile'>
    <div class='content'>
      <div class='card'>
        <div>
          <form @submit.prevent='updateProfile'>
            <h1>Osobní informace</h1>
            <general-input title='Jméno' type="text" v-model='firstName'></general-input>
            <general-input title='Příjmení' type="text" v-model='lastName'></general-input>
            <general-input title='Telefon' type="text" v-model='phone'></general-input>
            <h2>Doručovací adresa</h2>
            <general-input title='Město' type="text" v-model='city'></general-input>
            <general-input title='Ulice' type="text" v-model='street'></general-input>
            <general-input title='PSČ' type="text" v-model='zip'></general-input>
            <div id='adr-check'>
              <label>
                <input type='checkbox' v-model='billingIsSame'>
                Doručovací adresa je shodná s fakturační adresou
              </label>
            </div>
            <div v-show='!billingIsSame'>
              <h2>Fakturační adresa</h2>
              <general-input title='Město' type="text" v-model='billingCity'></general-input>
              <general-input title='Ulice' type="text" v-model='billingStreet'></general-input>
              <general-input title='PSČ' type="text" v-model='billingZip'></general-input>
            </div>
            <div class='btn-wrap'>
              <input
                type='submit'
                class='btn'
                :class='{"btn-deac": !isModified || !isValid}'
                value="Uložit změny"
              >
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import GeneralInput from "../components/GeneralInput";
  import axios from 'axios';

  export default {
    name: "Profile",
    components: { GeneralInput },
    data() {
      const user = this.$store.getters.user;
      const data = {
        firstName: user.firstName,
        lastName: user.lastName,
        phone: user.phone,
        city: user.addressDelivery.city,
        street: user.addressDelivery.street,
        zip: user.addressDelivery.zip,
        billingCity: user.addressBilling ? user.addressBilling.city : '',
        billingStreet: user.addressBilling ? user.addressBilling.street : '',
        billingZip: user.addressBilling ? user.addressBilling.zip : '',
        billingIsSame: user.addressBilling === null
      };
      return {
        og: data,
        ...data
      };
    },
    methods: {
      updateProfile() {
        if (!this.isModified || !this.isValid) return;

        const data = {};
        if (this.firstName !== this.og.firstName) data.firstName = this.firstName;
        if (this.lastName !== this.og.lastName) data.lastName = this.lastName;
        if (this.phone !== this.og.phone) data.phone = this.phone;
        if (this.city !== this.og.city) this.addToObj(data, 'addressDelivery', 'city', this.city);
        if (this.street !== this.og.street) this.addToObj(data, 'addressDelivery', 'street', this.street);
        if (this.zip !== this.og.zip) this.addToObj(data, 'addressDelivery', 'zip', this.zip);

        if (!this.billingIsSame) {
          if (this.billingCity !== this.og.billingCity) this.addToObj(data, 'addressBilling', 'city', this.billingCity);
          if (this.billingStreet !== this.og.billingStreet) this.addToObj(data, 'addressBilling', 'street', this.billingStreet);
          if (this.billingZip !== this.og.billingZip) this.addToObj(data, 'addressBilling', 'zip', this.billingZip);
        }

        if (this.billingIsSame && !this.og.billingIsSame) {
          data.addressBilling = {};
        }

        axios({
          url: '/api/profile',
          method: 'patch',
          data
        }).then(res => {
          this.$store.dispatch('setUser', res.data);
          this.$router.push('/');
        }).catch(err => {
          this.$store.dispatch('openModal', err.response.data.message);
        });
      },
      addToObj(data, objName, prop, value) {
        if (data[objName] === undefined) data[objName] = {};
        data[objName][prop] = value;
      }
    },
    computed: {
      isModified() {
        if (
          this.firstName !== this.og.firstName ||
          this.lastName !== this.og.lastName ||
          this.phone !== this.og.phone ||
          this.city !== this.og.city ||
          this.street !== this.og.street ||
          this.zip !== this.og.zip ||
          this.billingIsSame !== this.og.billingIsSame
        ) {
          return true;
        }

        if (!this.billingIsSame && (
          this.billingCity !== this.og.billingCity ||
          this.billingStreet !== this.og.billingStreet ||
          this.billingZip !== this.og.billingZip ||
          this.billing !== this.og.billing
        )) {
          return true;
        }

        return false;
      },
      isValid() {
        if (this.firstName.trim().length === 0) {
          return false;
        }

        if (this.lastName.trim().length === 0) {
          return false;
        }

        if (!this.phone.replace(/ /g, '').match(/^[0-9]{9}$/)) {
          return false;
        }

        if (this.city.trim().length === 0) {
          return false;
        }

        if (this.street.trim().length === 0) {
          return false;
        }

        if (!this.zip.replace(/ /g, '').match(/^[0-9]{5}$/)) {
          return false;
        }

        if (!this.billingIsSame) {
          if (this.billingCity.trim().length === 0) {
            return false;
          }

          if (this.billingStreet.trim().length === 0) {
            return false;
          }

          if (!this.billingZip.replace(/ /g, '').match(/^[0-9]{5}$/)) {
            return false;
          }
        }

        return true;
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #profile {
    flex: 1;
    overflow-y: auto;

    h1 {
      margin: 0;
      margin-bottom: 5px;
    }

    h2 {
      font-size: 1.2em;
      margin-bottom: 3px;
    }

    .content {
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

    input[type=submit] {
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

  #adr-check {
    margin-bottom: 10px;
    text-align: center;
  }
</style>
