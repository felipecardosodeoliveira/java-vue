<script setup>

</script>

<template>
   <nav>
    <RouterLink to="/">Produtos</RouterLink>
    <RouterLink to="/create">Novo</RouterLink>
  </nav>
  <main>
    <RouterView />
  </main>
</template>

<style src="">
  nav {
    border-bottom: 1px solid #ccc;
    display: flex;
    gap: 1rem;
    justify-content: flex-end;
  }

  nav, main {
    padding: 0.875rem;
  }
</style>