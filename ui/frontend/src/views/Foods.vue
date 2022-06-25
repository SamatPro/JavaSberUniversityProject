<template>
  <v-container>
    <h1>Блюда</h1>
    <v-row>
      <v-col class="col-6" v-for="dish in dishes" :key="dish.id">
        <v-card class="food-card">
          <v-img height="250" src="https://cdn.vuetifyjs.com/images/cards/cooking.png"></v-img>
          <v-card-title>
            <span>{{dish.name}}</span>
            <v-spacer />
            <v-btn @click="addToCart(dish)" color="orange" dark>
              <v-icon color="white">mdi-cart</v-icon>
            </v-btn>
          </v-card-title>
          <v-divider />
          <v-card-text>
            <div>{{dish.description}}</div>
          </v-card-text>
          <v-divider />
          <v-card-title>Ингредиенты</v-card-title>
          <v-card-text>
            <v-chip-group dark>
              <v-chip v-for="ingredient in dish.ingredients" :key="ingredient.id">{{ingredient.name}}</v-chip>
            </v-chip-group>
          </v-card-text>

        </v-card>
      </v-col>
    </v-row>
    <v-bottom-sheet v-model="sheet" inset>
      <v-sheet  class="text-center sheet" height="200px">
        <div class="my-3">
          <h2>
          Ваш товар успешно добавлен в корзину!
          </h2>
        </div>
          <v-btn @click="closeSheet()" text color="red" class="text-decoration-underline">Продолжить покупки</v-btn>
          <v-btn href='/cart' dark color='red'>Корзина</v-btn>
      </v-sheet>
    </v-bottom-sheet>
  </v-container>
</template>
<script>
import { AXIOS, AXIOS_TEST, AXIOS_RESTAURANT } from "../main";

export default {
  name: "Foods",
  data: () => ({
    dishes: [
      {
        id: 0,
        name: "",
        description: ""
      }
    ],
    sheet: false
  }),
  methods: {
    closeSheet() {
      this.sheet = false
    },
    addToCart(dish) {
      let self = this
      AXIOS.post("/cart", {
        userId: this.$store.getters.getId,
        dishId: dish.id,
        name: dish.name,
        description: dish.description
      })
        .then(response => {
          self.sheet = !self.sheet
        })
        .catch(err => {
          console.log(err);
        });
    },
    loadPage() {
      let self = this;
      AXIOS_RESTAURANT.get("/dish")
        .then(response => {
          self.dishes = response.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.loadPage();
  }
};
</script>
<style scoped>
.sheet {
  padding-top: 20px;
}
</style>