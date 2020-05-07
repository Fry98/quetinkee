<template>
  <div class='del-table'>
    <div class="del-title">{{ title }}</div>
    <table>
      <thead>
        <td>ID</td>
        <td>Jméno</td>
        <td>Adresa</td>
        <td>Čas doručení</td>
        <td>Detaily</td>
        <td>Stav</td>
      </thead>
      <tr v-for='(item, i) in value' :key='item.id'>
        <td class='id'>{{ item.id }}</td>
        <td class='name'>{{ item.name }}</td>
        <td class='street'>{{ getAddress(i) }}</td>
        <td class='time'>{{ item.time }}</td>
        <td class='details'><button @click='$emit("details", i)'>Zobrazit detaily</button></td>
        <td class='state-col'>
          <select class='state' @change='removeItem(i)'>
            <option :selected='selected === 0'>Čekající</option>
            <option :selected='selected === 1'>Probíhající</option>
            <option :selected='selected === 2'>Dokončená</option>
          </select>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: 'Objednávky'
    },
    value: {
      type: Array,
      default: []
    },
    selected: {
      type: Number,
      default: 0
    }
  },
  methods: {
    getAddress(i) {
      const zipStr = this.value[i].zip.toString();
      const zip = zipStr.substr(0, 3) + " " + zipStr.substr(3, 2);
      const street = this.value[i].street;
      const city = this.value[i].city;

      return `${street}, ${zip} ${city}`;
    },
    removeItem(i) {
      this.value.splice(i, 1);
      this.$emit('input', this.value);
    }
  }
}
</script>

<style lang="scss" scoped>
  @import '../scss/_vars.scss';

  .del-table {
    padding: 15px 20px;
    width: 100%;
    overflow-y: auto;
  }

  .del-title {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 3px;
  }

  .state {
    width: 100%;
  }

  .details > button {
    width: 100%;
    height: 100%;
    border: none;
    background: $mainBlue;
    color: white;
    border-radius: 20px;
    padding: 3px 0px;
    cursor: pointer;
    transition-duration: .2s;
    &:hover {
      background: $darkBlue;
    }
  }

  table {
    border-collapse: collapse;
    width: 100%;
  }

  thead {
    font-weight: bold;
    text-align: center;
  }

  td {
    border: 1px solid black;
  }

  .id {
    width: 5%;
    text-align: center;
  }

  .name {
    width: 23%;
    padding-left: 10px;
  }

  .street {
    width: 32%;
    padding-left: 10px;
  }

  .time {
    width: 13%;
    text-align: center;
  }

  .details {
    width: 12%;
  }

  .state-col {
    width: 15%;
  }
</style>
