<template>
  <div id='manage-bouquets'>
    <confirm :msg='popup' @cancel='cancel' @confirm='confirm'></confirm>
    <div class='mb-wrap'>
      <h1>Správa kytic</h1>
      <button class='btn' @click='$router.push("/admin/new-bouquet")'>Nová kytice</button>
      <div class='existing-bouquets'>
        <div v-for='bouquet in bouquets' :key='bouquet.id' class='bouquet'>
          <span>{{ bouquet.name }}</span>
          <div class='actions'>
            <font-awesome-icon
                title='Upravit'
                class='icon edit-icon'
                icon='pen'
                @click='$router.push(`/admin/edit-bouquet/${bouquet.id}`)'
            ></font-awesome-icon>
            <font-awesome-icon
                title='Smazat'
                class='icon remove-icon'
                :icon="['far', 'trash-alt']"
                @click='handleRemoveClick(bouquet)'
            ></font-awesome-icon>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Confirm from '../components/Confirm';
  import axios from "axios";

  export default {
    name: "ManageBouquets",
    components: {
      Confirm
    },
    created() {
      this.loadData();
    },
    data() {
      return {
        popup: null,
        bouquets: [],
        toRemove: null
      }
    },
    methods: {
      async loadData() {
        try {
          const res = await axios({
            method: 'get',
            url: '/api/bouquets/list'
          });
          this.bouquets = res.data;
        } catch(err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      },
      handleRemoveClick(bouquet) {
        this.toRemove = bouquet.id;
        this.popup = `Opravdu chcete smazat kytici <strong>${bouquet.name}</strong>?`;
      },
      cancel() {
        this.popup = null;
      },
      confirm() {
        this.popup = null;
        this.removeBouquet();
      },
      async removeBouquet() {
        try {
          const res = await axios({
            method: 'DELETE',
            url: `/api/bouquets/${this.toRemove}`
          });
          if (res.status === 200) {
            const removeIndex = this.bouquets.findIndex(bouquet => bouquet.id === this.toRemove);
            this.bouquets.splice(removeIndex, 1);
          }
        } catch(err) {
          this.$store.dispatch('openModal', err.response.data.message);
        }
      }
    }
  }
</script>

<style lang='scss' scoped>
  @import "../scss/_vars.scss";
  @import "../scss/_button.scss";

  #manage-bouquets {
    display: flex;
    justify-content: center;
  }

  .mb-wrap {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: $almostWhite;
    border-radius: 13px;
    margin: 30px;
    padding: 30px 80px;
  }

  .existing-bouquets {
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .bouquet {
      width: 100%;
      margin-bottom: 5px;
      background-color: #fff;
      padding: 3px 7px;
      border-radius: 7px;
      transition: .2s;
      display: flex;
      align-items: center;
      justify-content: space-between;
      &:hover {
        background-color: $lightGrey;
      }
      .icon {
        cursor: pointer;
        transition: .2s;
        margin-left: 5px;
      }
      .actions {
        display: flex;
        align-items: center;
        margin-left: 10px;
      }
    }
  }

  .edit-icon:hover {
    color: $mainBlue;
  }

  .remove-icon:hover {
    color: red;
  }

  h1 {
    display: block;
    text-align: center;
    padding: 0;
    margin: 0 0 15px;
  }

  label {
    display: flex;
    align-items: center;
  }

  input {
    margin-left: 5px;
    font-size: 1.2em;
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.26);
    border-radius: 7px;
    padding: 3px 10px 3px 10px;
    border: solid 1px white;

    &:focus {
      outline: none;
      border: solid 1px $mainOrange;
    }
  }

  .btn {
    display: inline;
    margin-left: 5px;
  }
</style>
