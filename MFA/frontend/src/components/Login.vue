<template>
  <form method="Post" @submit.prevent="login" >

    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"><i class="fas fa-user"></i></span>
      </div>
      <input type="text" class="form-control" placeholder="username" name="email" id="username" v-model="username" required>

    </div>
    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"><i class="fas fa-key"></i></span>
      </div>
      <input type="password" class="form-control" placeholder="password" name="password" id="password" v-model="password" required>
    </div>


    <p style="color:red; flex-flow: row">{{errorMessage}}</p>



    <div class="form-group">
      <input type="submit" value="Войти" class="btn float-right login_btn">
    </div>



    <div class="form-group float-right " style="transform:translate(-10%,70%); font-size: 20px; margin-top: 10%">
      <a href="./SendSamplePassword/" class="sendSamplePassword">Забыли пароль?</a>
    </div>
  </form>

</template>
<script>

import axios from "axios";


export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',

  methods: {
    async login(){

    /* var dataForm=new FormData()
      dataForm.set("username",this.username);
      dataForm.set("password",this.password);
      params.append(" username",this.username)
      params.append("password",this.password)

     */


      /*var params = new URLSearchParams();
      params.append("username",this.username)
      params.append("password",this.password)*/

      const response=await axios.post("/api/auth/login/", {
        "username": this.username,
        "password": this.password
      }, {
        "Content-Type": "application/json"
      });
      console.log(response);
      if(response.data.responseModel.message==="credential invalid"){
        this.errorMessage="Логин или пароль неверный"
      }
      if(response.data.responseModel.message==="login success"){
        this.errorMessage=""
        localStorage.setItem("access_token",response.data.access_token)

        console.log("/n header"+JSON.stringify(response.headers))

        await this.$router.push('/scanningQrCodePageView/')

      }

    },
  }

}

</script>
<style>

</style>