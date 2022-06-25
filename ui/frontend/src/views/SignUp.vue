<template>
  <v-container class="col-8">
    <v-card>
      <v-card-title>Регистрация</v-card-title>
      <v-card-text>
        <v-text-field outlined placeholder="Имя" v-model="name"></v-text-field>
        <v-text-field outlined placeholder="Фамилия" v-model="surname"></v-text-field>
        <v-text-field outlined placeholder="Почта" v-model="mail"></v-text-field>
        <v-text-field outlined placeholder="Пароль" v-model="password"></v-text-field>
        <!-- <v-text-field outlined placeholder="Город" v-model="city"></v-text-field> -->
        <v-autocomplete outlined :items="citiesNames" placeholder="Город" v-model="city">

        </v-autocomplete>
        <v-card-actions>
          <v-btn dark color="blue" @click="signUp()">Зарегистрироваться</v-btn>
          <v-spacer/>
          <v-btn outlined @click="redirectToLogin()">Войти</v-btn>
        </v-card-actions>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import { AXIOS_CUSTOMER, AXIOS } from "../main";

export default {
  name: "SignUp",
  data: () => ({
    name: "",
    surname: "",
    mail: "",
    password: "",
    city: "",
    cities: []
  }),
  methods: {
    signUp() {
      let self = this;
      let cityId = 0;
      this.cities.forEach(c => {
        if (self.city === c.name) {
          cityId = c.id
        }
      })
      AXIOS_CUSTOMER.post("/registration", {
        lastName: this.surname,
        firstName: this.name,
        email: this.mail,
        password: this.password,
        cityId: cityId
      })
        .then(response => {
          console.log(response.data);
          AXIOS.interceptors.request.use(function(config) {
            const token = response.data.value;
            config.headers.Authorization = 'Bearer ' + token;

            return config;
          });
          self.$store.dispatch('login', {
              token: response.data.value,
              id: response.data.customerId,
              mail: response.data.email
          }).then(() => {
              self.$router.push("/foods")
          })
        })
        .catch(err => {
          console.log(err);
        });
    },
    loadPage() {
      let self = this
      AXIOS_CUSTOMER.get("/cities")
      .then(response => {
        self.cities = response.data
      })
      .catch(err => {
        console.log(err);
        
      })
    },
    redirectToLogin() {
        this.$router.push("/")
    }
  },
  mounted() {
    this.loadPage()
  },
  computed: {
    citiesNames() {
      let names = []
      this.cities.forEach(c => {
        names.push(c.name)
      })
      return names
    }
  }
};
</script>

<style scoped>
</style>