<template>
  <v-container>
    <h1>Корзина</h1>
    <v-row>
      <v-col class="col-6">
        <v-col v-for="item in getCartItems" :key="item.dish.id">
          <v-card>
            <v-row>
              <v-col>
                <v-img src="../../public/pizza.jpg"></v-img>
              </v-col>
              <v-col>
                <v-card-title>{{item.dish.name}}</v-card-title>
                <v-card-subtitle>{{item.dish.description}}</v-card-subtitle>
                <v-spacer />
                <v-card-text>
                  <v-card-actions>
                    <v-btn
                      icon
                      @click="decItemCount(item.dish.id)"
                      :disabled="item.count === 1 ? true : false"
                    >
                      <v-icon>mdi-minus</v-icon>
                    </v-btn>
                    <span class="item-count">{{item.count}}</span>
                    <v-btn icon @click="incItemCount(item.dish.id)">
                      <v-icon>mdi-plus</v-icon>
                    </v-btn>
                    <v-spacer />
                    <v-btn @click="deleteDish(item.dish.id)" color="red">
                      <v-icon color="white">mdi-delete</v-icon>
                    </v-btn>
                  </v-card-actions>
                </v-card-text>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-col>
      <v-col class="col-6 card-container">
        <v-col>
          <v-card class="summary-card">
            <v-card-title>Итог</v-card-title>
            <v-card-text>
              <div
                v-for="item in cart.cartItems"
                :key="item.dish.id"
              >{{item.count}} X {{item.dish.name}}</div>
            </v-card-text>
            <v-card-actions>
              <v-col>
                <v-btn @click="doOrder" color="green" class="cart-btn" dark>Купить</v-btn>
              </v-col>
              <v-col>
                <v-btn @click="deleteCart()" color="red" class="cart-btn" dark>Очистить корзину</v-btn>
              </v-col>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { AXIOS, AXIOS_TEST } from "../main";
export default {
  name: "Cart",
  data: () => ({
    cart: {
      cartItems: []
    }
  }),
  methods: {
    loadPage() {
      let self = this;
      AXIOS.get("/cart/" + this.$store.getters.getId)
        .then(response => {
          self.cart.cartItems = response.data.cartItems;
        })
        .catch(err => {
          console.log(err);
        });
    },
    incItemCount(id) {
      let self = this;
      AXIOS.post("/cart", { userId: this.$store.getters.getId, dishId: id })
        .then(response => {
          let idx = self.cart.cartItems.findIndex(c => {
            return c.dish.id === id;
          });
          self.cart.cartItems[idx].count++;
        })
        .catch(err => {
          console.log(err);
        });
    },
    decItemCount(id) {
      let self = this;
      AXIOS.post("/cart/deleteItem", {
        userId: this.$store.getters.getId,
        dishId: id
      })
        .then(response => {
          let idx = self.cart.cartItems.findIndex(c => {
            return c.dish.id === id;
          });
          self.cart.cartItems[idx].count--;
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteDish(id) {
      let self = this;
      AXIOS.post("/cart/deleteDish", {
        userId: this.$store.getters.getId,
        dishId: id
      })
        .then(response => {
          let idx = self.cart.cartItems.findIndex(c => {
            return c.dish.id === id;
          });
          console.log(idx)
          self.cart.cartItems.splice(idx, 1)
          console.log(self.cart.cartItems[idx])
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteCart() {
      let self = this;
      AXIOS.post("/cart/delete", {
        userId: this.$store.getters.getId
      })
        .then(response => {
          self.cart.cartItems = []
        })
        .catch(err => {
          console.log(err);
        });
    },
    doOrder() {
      let self = this;
      AXIOS.post("/order", { userId: this.$store.getters.getId })
        .then(response => {
          self.$router.push("/orders");
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  mounted() {
    this.loadPage();
  },
  computed: {
    getCartItems() {
      return this.cart.cartItems
    }
  }
};
</script>

<style scoped>
.item-count {
  margin: 0 2px;
}
.cart-btn {
  width: 100%;
}
.card-container {
  height: 50vh;
}
.summary-card {
  position: sticky;
  top: 0;
}
</style>