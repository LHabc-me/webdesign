import axios from "axios";

const instance = axios.create({
  baseURL: import.meta.env.VITE_API,
  timeout: 5000,
})

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
