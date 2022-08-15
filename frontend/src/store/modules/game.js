import { defineStore } from "pinia";
import { getAllLiveGames, getLeagueLiveGames } from "@/api/game";

export const useGamesStore = defineStore("gameStore", {
  state: () => ({
    liveGames: [],
  }),
  actions: {
    getAllLiveGames() {
      getAllLiveGames(
        (res) => {
          this.liveGames = [];
          res.data.forEach((e) => {
            let title = e.home.hanName + " vs " + e.away.hanName;
            this.liveGames.push({
              title: title,
              id: e.id,
              homeLogo: e.home.logo,
              awayLogo: e.away.logo
            });
          });
          console.log(this.liveGames);
          sessionStorage.setItem('AllLiveGames', JSON.stringify(this.liveGames))
          console.log(JSON.parse(sessionStorage.getItem('AllLiveGames')))
        },
        (err) => {
          console.log(err);
        }
      );
    },
    getLeagueLiveGames(leagueCode) {
      getLeagueLiveGames(
        leagueCode,
        (res) => {
          this.liveGames = [];
          res.data.forEach((e) => {
            let title = e.home.hanName + " vs " + e.away.hanName;
            this.liveGames.push({
              title: title,
              id: e.id,
            });
          });
        },
        (err) => {
          console.log(err);
        }
      );
    },
  },
});
