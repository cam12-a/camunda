<template>

  <div class="container" >
    <div class="d-flex justify-content-center h-100">

      <div class="card">
        <div class="form-group">
          <input type="button" value="Регистрация" class="btn float-right " style="background: none; color:white" @click="registrationPage">
        </div>
        <div class="card-header">
          <h3>Вход в систему</h3>
        </div>
        <div class="card-body">

        <Login/>

        </div>

      </div>
    </div>


  </div>
</template>

<script>


import Login from "@/components/Login";



export default {
  name: 'HelloWorld',
  /*props: {
    msg: String
  }

   */
  components: {
    Login
  },


  data() {
    return {
      errorMessage: null,
      user: ''
    }
  },

  mounted() {
    if(localStorage.getItem("access_token")==='redirect'){
      localStorage.removeItem("access_token")

    }
    else{
      console.log("null")
    }
  },

  methods: {


    registrationPage(){
      this.$router.push('/SingIn/')
    }


  }






}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>




@import "~@/assets/css/AdminLogin.css";


h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

</style>
