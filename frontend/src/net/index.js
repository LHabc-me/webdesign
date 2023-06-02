import axios from "axios";

const instance = axios.create({
  baseURL: import.meta.env.VITE_API,
  timeout: 50000,
})

instance.interceptors.request.use(config => {
  config.headers.token = window.localStorage.getItem('token');
  return config
}, function (error) {
  return Promise.reject(error)
})


function post(url, data, params = {}, headers = {}) {
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
