<template>
  <nav class="mynav">
    <div class="container-fluid">
      <div class="navbar-header">
        <router-link to="/" class="navbar-brand" >MARALAYS</router-link>
      </div >
        <ul class="nav justify-content-end">
          <li  class="nav-item"><router-link  to="/" @click="logout"><span class="glyphicon glyphicon-log-in"></span> Login</router-link></li>
        </ul>
    </div>

  </nav>
</template>
<script>



import axios from "axios";

export default {
  name: 'MaralaysNav',
  methods: {
   async logout(){
     let a=atob(localStorage.getItem("access_token").split('.')[1]);

     const response=await axios.get("/api/auth/logOut/",{
       params:{
         "username":a.username
       }

     })

     if(response.status==200){
       localStorage.removeItem("access_token")
        this.$router.push("/")
     }
   }
  }

}

</script>

<style scoped>
.mynav{
  background-color: rgba(62,89,50,0.5);
  height: 15px;
}
.nav-item a:hover{
  background: none;
  border: none;
}

</style>