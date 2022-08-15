import { apiInstance } from ".";

const api = apiInstance();

async function createRoom(params, success, fail) {
  await api.post(`/rooms/`, params).then(success).catch(fail);
}

async function getRoomInfo(path, success, fail) {
  await api.get(`/rooms/session/${path}`).then(success).catch(fail);
}

async function updateRoomHeadCount(path, headcount, success, fail) {
  let data = {
    headCount: headcount,
  };
  await api.put(`/rooms/${path}`, data).then(success).catch(fail);
}

async function getPopularRooms(success, fail) {
  await api.get(`/rooms/popular`).then(success).catch(fail);
}

async function deleteRoom(roomId, success, fail) {
  await api.delete(`/rooms/${roomId}`).then(success).catch(fail);
}

export { createRoom, getRoomInfo, updateRoomHeadCount, getPopularRooms, deleteRoom };
