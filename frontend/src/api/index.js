import axios from "axios";
import { API_HOST_URL } from "@/config";
import { useAccountStore } from "@/store/index.js";
import jwt_decode from "jwt-decode";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_HOST_URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  return setInterceptors(instance);
}

function setInterceptors(instance) {
  instance.interceptors.request.use(
    async function (config) {
      var decoded = jwt_decode(useAccountStore().accessToken);
      let now = new Date();
      let expiry =
        decoded.exp - Number(now.getTime().toString().substring(0, 10));

      if (expiry <= 0) {
        await useAccountStore().reissueToken();
      }

      //액세스 토큰 헤더 설정
      config.headers.Authorization = useAccountStore().accessToken;
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    function (response) {
      // Any status code that lie within the range of 2xx cause this function to trigger
      // Do something with response data
      return response;
    },
    function (error) {
      // Any status codes that falls outside the range of 2xx cause this function to trigger
      // Do something with response error
      return Promise.reject(error);
    }
  );
  return instance;
}

export { apiInstance };
