import { createMemoryHistory, createRouter } from "vue-router";

import Home from "./view/Home.vue";
import NewProduct from "./view/NewProduct.vue";

const routes = [
  { path: "/", component: Home },
  { path: "/create", component: NewProduct}
]

export const router = createRouter({
  history: createMemoryHistory(),
  routes,
})

