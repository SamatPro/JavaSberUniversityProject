<template>
  <v-container class="col-8">
    <v-card>
      <v-card-title>Вход</v-card-title>
      <v-card-text>
        <v-text-field outlined placeholder="Почта" v-model="mail"></v-text-field>
        <v-text-field outlined placeholder="Пароль" v-model="password"></v-text-field>
        <v-card-actions>
          <v-btn dark color="blue" @click="signIn()">Войти</v-btn>
          <v-spacer />
          <v-btn outlined @click="redirectToSignUp()">Зарегистрироваться</v-btn>
        </v-card-actions>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<style scoped>
.login-btn {
  width: 100%;
}

.login-card {
  padding: 10px;
}
</style>
<script>
import { AXIOS_CUSTOMER } from "../main";
import { AXIOS } from "../main";

export default {
  name: "SignIn",
  data: () => ({
    mail: "",
    password: ""
  }),
  methods: {
    signIn() {
      let self = this;
      AXIOS_CUSTOMER.post("/login", {
        email: this.mail,
        password: this.password
      })
        .then(response => {
          console.log(response.data);
          AXIOS.interceptors.request.use(function(config) {
            const token = response.data.value;
            config.headers.Authorization = 'Bearer ' + token;

            return config;
          });
          self.$store
            .dispatch("login", {
              token: response.data.value,
              id: response.data.customerId,
              mail: response.data.email
            })
            .then(() => {
              self.$router.push("/foods");
            });
        })
        .catch(err => {
          console.log(err);
        });
    },
    redirectToSignUp() {
      this.$router.push("/signUp");
    }
  }
};
</script>