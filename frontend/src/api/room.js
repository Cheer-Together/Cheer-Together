import { apiInstance } from ".";

const api = apiInstance();

async function createRoom(params, success, fail) {
  await api.post("/rooms/", params).then(success).catch(fail);
}

export { createRoom };
