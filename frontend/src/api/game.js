import { apiInstance } from ".";

const api = apiInstance();

async function getAllLiveGames(success, fail) {
  await api.get(`/games/live`).then(success).catch(fail);
}

async function getLeagueLiveGames(leagueCode, success, fail) {
  await api.get(`/games/live/${leagueCode}`).then(success).catch(fail);
}

export { getAllLiveGames, getLeagueLiveGames };
