<script setup>
    import { defineProps } from "vue";

    const props = defineProps({
        product: {
            name: {
                type: String,
                required: true
            },
            price: {
                type: Number,
                required: true
            }
        }
    });
    
</script>

<template>
  <div class="card">
    <h3>{{ props.product.name }}</h3>
    <h4>{{ props.product.price }}</h4>
  </div>
</template>

<style scoped>
    .card {
        border: 1px solid rebeccapurple;
        box-shadow: 12px 12px 0 rebeccapurple;
        flex: 1;
        padding: 0.875rem;
    }
</style>