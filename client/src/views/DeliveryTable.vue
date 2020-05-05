<template>
  <div class='del-table'>
    {{ title }}
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: 'Objednávky'
    }
  }
}
</script>
