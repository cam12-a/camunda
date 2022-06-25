<template>
  <div class="container" >
    <div class="d-flex justify-content-center h-100">

      <div class="card">
        <div class="form-group">
          <input type="button" value="Войти" class="btn float-right " style="background: none; color:white" @click="LoginPage">
        </div>
        <div class="card-header">
          <h3>Регистрация в систему</h3>
        </div>
        <div class="card-body">
      <form method="Post" @submit.prevent="SingIn">

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="glyphicon glyphicon-envelope"></i></span>
          </div>
          <input type="text" class="form-control" placeholder="почта" name="username" id="username" v-model="username" required>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-user"></i></span>
          </div>
          <input type="text" class="form-control" placeholder="фамилия" name="firstname" id="firstname" v-model="firstname" required>

        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-user"></i></span>
          </div>
          <input type="text" class="form-control" placeholder="имя" name="lastname" id="lastname" v-model="lastname" required>

        </div>


        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-user"></i></span>
          </div>
          <input type="text" class="form-control" placeholder="отчество" name="name" id="patronymic" v-model="patronymic" required>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-key"></i></span>
          </div>
          <input type="password" class="form-control" placeholder="пароль" name="password" id="password" v-model="password" required>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fa fa-group"></i></span>
          </div>
            <select class="form-control" id="myRoleList" name="users" v-model="roleName">
              <option v-for="item in selectItem" v-bind:key="item.index">{{item}}</option>
            </select>
        </div>

        <div class="form-group">
          <input type="submit" value="Регистрироваться" class="btn float-right login_btn">
        </div>


      </form>

        </div>

      </div>
    </div>


  </div>
</template>
<script>



import axios from "axios";


export default {
  name: "SingIn",

  data(){
    return {
      selectItem: []
    }
  },

 async mounted() {

    const response = await axios.get("api/roles/role_list/");
     this.selectItem.push("Выберите роль");
      if(response.status==200){
        response.data.forEach((item)=>{
          this.selectItem.push(item.roleName)
        });
        }
      console.log(this.selectItem[0])
  },

  methods: {
    LoginPage(){
      this.$router.push("/")
    },
   async  SingIn(){

       const response= await axios.post("api/user/new_account/",{
         username: this.username,
         password: this.password,
         firstname: this.firstname,
         lastname: this.lastname,
         users: [
             {
               roles: {
                 roleName: this.roleName
           }
          }
         ],
         name: this.name,

       });
       console.log(response)
      if(response.data.message==="success")
        await this.$router.push("/")

    }
  }
}


</script>

<style>


</style>