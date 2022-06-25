<template>
  <v-app>
    <v-app-bar class="app-bar" app color="red" dark>
      <v-avatar class="logo" tile>
        <v-img contain src="../public/logo.png"></v-img>
      </v-avatar>
      <v-spacer />
      <template v-if="this.$store.getters.getId != 0">
        <v-btn disabled text>
          <v-icon class="icon">mdi-account</v-icon>{{this.$store.state.mail}}
        </v-btn>
        <v-btn href="/foods" text>
          <v-icon class="icon">mdi-pasta</v-icon>Меню
        </v-btn>
        <v-btn href="/cart" text>
          <v-icon class="icon">mdi-cart</v-icon>Корзина
        </v-btn>
        <v-btn href="/orders" text>
          <v-icon class="icon">mdi-ballot</v-icon>Заказы
        </v-btn>
        <v-btn @click="logout()" text>
          <v-icon class="icon">mdi-logout</v-icon>Выйти
        </v-btn>
      </template>

      <template v-else>
        <v-btn href="/signUp" text>
          <v-icon class="icon">mdi-account-plus</v-icon>Регистрация
        </v-btn>
        <v-btn href="/" text>
          <v-icon class="icon">mdi-login</v-icon>Вход
        </v-btn>
      </template>
    </v-app-bar>

    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import HelloWorld from "./components/HelloWorld";

export default {
  name: "App",

  components: {
    HelloWorld
  },

  data: () => ({
    //
  }),
  methods: {
    logout() {
      let self = this;
      this.$store.dispatch("logout").then(() => {
        self.$router.push("/"); 
        console.log(self.$store.getters.getToken);
        
      });
    }
  }
};
</script>

<style scoped>
.logo {
  height: 70px !important;
}
.app-bar {
  /* padding: 0 5%; */
}
.icon {
  margin-right: 5px;
}
</style>