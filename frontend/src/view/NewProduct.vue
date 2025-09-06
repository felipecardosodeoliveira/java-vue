<script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router";

  const router = useRouter();

  const form = ref({
      name: "",
      price: 0.0
  });

  const save = async() => {
    try {
        await fetch("http://localhost:8080/products", {
            method: "POST",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify(form.value)
        });
        
        router.push("/");
      } catch (error) {
          console.error("Error:", error);
      }
  }
</script>

<template>
  <div>
    <h1>Novo produto</h1>
    <form @submit.prevent="save" method="post">
      <div class="form-control">
          <input 
            placeholder="Produto..."
            type="text" 
            v-model="form.name"
          />
      </div>
      <div class="form-control">
          <input 
            placeholder="Preço..."
            type="text" 
            v-model="form.price"
          />
      </div>
      <div class="form-control">
          <input 
            type="submit" 
            value="Salvar"
          />
      </div>
    </form>
  </div>
</template>

<style scoped>
   .form-control input {
      border-radius: 0.875rem;
      border: 0;
      padding: 0.875rem;
      margin-bottom: 1rem;
      outline: hidden;
      width: 100%;
   }

</style>