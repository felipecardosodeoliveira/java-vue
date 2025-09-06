<script setup>
    import { ref, onMounted } from "vue";
    import ProductCard from "./ProductCard.vue";

    const productList = ref([]);

    onMounted(async () => {
        try {
            const response = await fetch("http://localhost:8080/products");
            productList.value = await response.json();
        } catch (error) {
            productList.value = [];
            console.error("Error:", error);
        }
    });
</script>

<template>
  <div>
    <div v-if="productList.length > 0">
        <div class="card-list">
            <ProductCard  
                v-for="product in productList"
                :key="product.id" 
                :product="product"
            />
        </div>
    </div>
    <div v-else>
      Nenhum produto encontrado ou erro ao carregar.
    </div>
  </div>
</template>

<style scoped>
    .card-list {
        display: flex;
        flex-wrap: wrap;
        gap: 2rem;
    }
</style>