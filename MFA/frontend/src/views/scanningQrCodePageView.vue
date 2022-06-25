<template>
  <MaralaysNav/>
  <h3>Добро пожаловать в систему MARALAYS SECURITY, для получения доступа необходимо выполнить следующие действия  </h3>
  <hr>
  <div>
    <img id="qrcode" />
  </div>
  <hr>

    <ol class="list-group list-group-numbered">
      <li class="list-group-item">Открыть приложение MARALAYS SECURITY</li>
      <li class="list-group-item">Авторизироваться в нем при  этом необходимо вводить логин и пароль и выбрать соотвествующий сервис</li>
      <li class="list-group-item">При успешной аутентификации, вам необходимо сканировать отпечатку палец</li>
      <li class="list-group-item"> После сканирования отпечатки палец, следует сканировать QR-code который отображена высше на этой странице</li>
      <li class="list-group-item">  После верификации QR-code вы получите доступ к сервису</li>
    </ol>

</template>
<script>


import axios  from "axios";
import MaralaysNav from "@/components/MaralaysNav";
export default {
  components: {MaralaysNav},
  data(){
    return {
      link: '',
    }
  },

  async mounted() {
    if (localStorage.getItem("access_token") != undefined)
      var a = JSON.parse(atob(localStorage.getItem("access_token").split('.')[1]));
    if (a != undefined) {
        const response = await axios.get("api/qrcode/generateqrcode/" + a.username, {

        })

        if (response.status == 200) {
          //await console.log(response.data.headers)
          document.getElementById("qrcode").setAttribute('src', "data:image/jpg;base64," + response.data.responseModel.message);
        }

        console.log(response)
      }
  },
  methods: {


  }
}

</script>
<style>

body, html {
  background: none;
}
</style>
