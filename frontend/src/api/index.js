import axios from "axios";
import { API_HOST_URL } from "@/config";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_HOST_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance };
