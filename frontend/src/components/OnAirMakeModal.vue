<template>
  <div>
    <v-row
      justify="center"
    >
      <!-- 여기부터 모달창 -->
      <v-dialog
        v-model="onAirStore.makeRoomDialog"
      >
        <v-card>
          <v-card-text>
            <div class="makeroom-dialog">
              <div class="makeroom-dialog-logo">
                <img :src="makeroomLogo" class="makeroom-logo">
              </div>
              <div class="makeroom-dialog-info">
                <a>방 만들기</a>
              </div>
              <div style="width: 340px; margin-top: 25px;">
                <v-select
                :items="items"
                label="경기 목록"
                density="compact"
                solo
                ></v-select>
              </div>
              <div style="margin-top:-18px;">
                <input v-model="roomTitle" type="text" placeholder="방 제목" class="makeroom-dialog-textinput">
              </div>
              <div style="display: flex; margin-top:20px">
                <input v-model="roomPassword" style="width: 290px;" type="password" placeholder="비밀번호" class="makeroom-dialog-textinput">
                <div style="display: flex; flex-direction: column; align-items: center; width: 40px; height: 40px; margin-left: 10px; font-size: 12px;">
                  <div style="width: 40px; height: 15px; text-align: center;">
                    비공개
                  </div>
                  <input style="width:22px; height:22px; margin-top: 3px;" type="checkbox">
                </div>
              </div>
              <v-btn
                style="margin-top: 35px; color:white;"
                color="#2E6AFD"
                width="340px"
                @click="onAirStore.makeRoom()"
              >
                같이 집관
              </v-btn>
            </div>
          </v-card-text>

          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="onAirStore.makeRoomDialogToggle()"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      
      <v-dialog
        v-model="dialog2"
      >
        <v-card>
          <v-card-title>
            <span>에러 표시용 모달</span>
          </v-card-title>
          <v-card-text>

          </v-card-text>
          <v-card-actions>
            <v-btn
              color="primary"
              text
              @click="dialog2 = false"
            >
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useOnAirStore } from '@/store/index.js'
const onAirStore = useOnAirStore()
const dialog2 = ref(false)
const makeroomLogo = require('../assets/image/로고.png')
const roomTitle = ref('')
const roomPassword = ref('')
const items = [
  {title: '브라이튼 앤 호브 알비온 vs 브라이튼 앤 호브 알비온'},
  {title: '프리미어리그'},
  {title: '라리가'},
  {title: '세리에A'},
  {title: '분데스리가'},
  {title: '리그 1'},
  {title: 'K리그'},
]

</script>

<style scoped>
.makeroom-dialog {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 460px;
  height: 460px;
}
.makeroom-dialog-logo {
  display: flex;
  justify-content: center;
  height: 100px;
  margin-top: 10px;
}
.makeroom-logo {
  width: 100px;
  height: 100px;
}
.makeroom-dialog-info {
  height: 30px;
  font-size: 25px;
  margin-bottom: 10px;
}
.makeroom-dialog-textinput {
  width:340px;
  height:40px;
  border-radius: 1px;
  border: 1px solid #bcbcbc;
  padding-left: 10px;
}
::v-deep .v-field {
  font-size: 12px !important;
}
</style>