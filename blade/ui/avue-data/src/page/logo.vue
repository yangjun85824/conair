<template>
  <div class="logo">
    <span>{{ $website.title }}</span>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.logo {
  font-size: 20px;
  background-image: linear-gradient(120deg, #54b6d0 16%, #3f8bdb, #3f8bdb);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-weight: bold;
  padding: 30px 0;
  text-align: center;
}
</style>