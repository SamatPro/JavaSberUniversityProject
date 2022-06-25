<template>
<v-container>
    <h1>Заказы</h1>
 <v-container class="col-8">
    <div v-for="order in orders" :key="order.id">
      <v-alert border="left" colored-border :type="order.status == 'IN_PROGRESS' ? 'success' : order.status == 'RECEIVED' ? 'info' : 'error'" elevation="2">
        <v-row>
          <v-col>Заказ №{{order.id}}</v-col>
          <v-col>
            <div v-for="item in order.cartItems" :key="item.dish.id">{{item.count}} X {{item.dish.name}}</div>
          </v-col>
          <v-col>
            {{orderStatusText(order)}}
            <span v-if="order.restaurantValidate != null && order.restaurantValidate.status == 'REJECTED' && order.customerValidate != null && order.customerValidate.status   != 'REJECTED'">
            </span>
            </v-col>
        </v-row>
      </v-alert>
    </div>
  </v-container>
</v-container>
 
</template>

<script>
import { AXIOS } from "../main";
export default {
  name: "Orders",
  data: () => ({
    orders: [
      
    ],
    statusText: {
      success: 'Ваш заказ скоро приготовят и доставят',
      info: 'Ваш заказ обрабатывается...',
      error: {
        cityError: 'К сожаления, мы не работаем в вашем городе',
        dishError: 'К сожалению, на данный момент мы не можем приготовить ваш заказ'
      }
    }
  }),
  methods: {
    loadPage() {
      let self = this;
      AXIOS.get("/orders/" +this.$store.getters.getId , {headers: {Authorization: 'Bearer ' + this.$store.getters.getToken}})
        .then(response => {
          self.orders = response.data;
          console.log(self.orders);
        })
        .catch(err => {
          console.log(err);
        });
    },
    orderStatusText(order) {
      if (order.status === 'IN_PROGRESS') {
       return this.statusText.success 
      } else if (order.status === 'RECEIVED') {
        return this.statusText.info
      } else {
        console.log(order.customerValidate != null);
        console.log( order.customerValidate.status == 'REJECTED');
        
        
        if (order.customerValidate != null && order.customerValidate.status == 'REJECTED') {
          return this.statusText.error.cityError
        } else {
          return this.statusText.error.dishError
        }
      }
    }
  
  },
  mounted() {
    this.loadPage();
    
  },
  computed: {
    
  }
};
</script>

<style scoped>
  .cancelled-dishes {
    font-weight: bold;
    color: red;
  }
</style