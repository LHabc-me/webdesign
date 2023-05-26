import axios from "axios";

const instance = axios.create({
  baseURL: import.meta.env.VITE_API,
  timeout: 5000,
})

instance.interceptors.request.use(function (config) {
  const token = window.localStorage.getItem("token")
  config.headers.token = token;
  return config;
}, function (error) {
  return Promise.reject(error);
});


function post(url, data, params = {}, headers = {'Content-Type': 'application/json'}) {
  return instance.post(url, data, {
    headers,
    withCredentials: true,
    params,
  })
}

function get(url, params = {}, headers = {}) {
  return instance.get(url, {
    headers,
    withCredentials: true,
    params,
  })
}

export {get, post}
