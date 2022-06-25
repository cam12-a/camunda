import axios from "axios";


axios.defaults.baseURL="http://localhost:8085/"
axios.defaults.headers.post['Content-Type'] ='application/json;charset=utf-8'
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'

var headers = function() {
    return ({
        "Authorization": `${localStorage.getItem("access_token")}`
    });
};

headers()

axios.interceptors.request.use(config =>{

    if(localStorage.getItem("access_token")==="redirect"){
        window.location="/"
        localStorage.removeItem("access_token")
    }
    else {
        config.headers['Authorization']= localStorage.getItem("access_token");
    }
    return config

}, error=>{
        return Promise.reject(error)
    })


axios.interceptors.response.use(response =>{
    //console.log(response)
    if (response.data.token=="redirect"){
        window.location="/"
        localStorage.removeItem("access_token")
    }
    console.log("inter res "+response)

    if(response.status==200){
        localStorage.setItem("access_token",response.data.token);

    }

    return response
}, (error, status,headers,body) =>{
    console.log("intercept rejected response "+JSON.stringify(error, status,headers, body))
    }
    )

