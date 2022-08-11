<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div v-if="loaded" class="community-main">
<!--     
    axios가 실행되기 전에 {{maincontent}}를 불러와서 에러 발생,
    axios가 실행되고 maincontent가 로드되면 렌더되도록 하기 위해
    로드가 완료될때까지 false를 출력하는 변수 loaded를 사용하여
    순차적으로 진행 될 수 있도록 구조화함
-->
      <div class="community-detail-top">
        <div style="height:36px; width:36px; margin-right:10px; background-color:aquamarine;">
        </div>
        <a>{{maincontent.leagueResponseExceptTeamList.hanName}}</a>
      </div>
      <div class="community-detail-header">
        <div style="margin-left: 10px; font-size:20px;">
          {{maincontent.title}}
        </div>
        <div style="font-size:13px;">
          <a class="word-link" @click="reportModalToggle()">신고하기</a><a> | </a>
          <a class="word-link" @click="toArticleListBtn()">글 목록</a>
        </div>
      </div>
      <div class="community-detail-content-top">
        <div style="display: flex; align-items: center;">
          <!-- <div style="height:36px; width:36px; margin:0 10px 0 10px; background-color:bisque;">
          </div> -->
          <a>{{maincontent.memberResponse.nickname}} | {{createdDate}}</a>
        </div>
        <div v-if="userEmail==maincontent.memberResponse.email" class="community-detail-btns">
          <div @click="modifyArticle()" class="word-link">
            <v-icon size="20px">
              mdi-pencil
            </v-icon>
            <a>수정하기</a>
          </div>
          <div @click="deleteArticle()" class="word-link" style="margin-left:10px">
            <v-icon size="20px">
              mdi-delete
            </v-icon>
            <a>삭제하기</a>
          </div>
        </div>
      </div>
      <div style="margin: 10px">
        {{maincontent.content}}
      </div>
      <div style="height:50px; margin-top: 30px;">
        <div @click="likeArticle" style="height:50px; width:50px; margin:auto; background-color:aquamarine">
        </div>
      </div>
      <div class="community-detail-writecomment">
        <input type="text" v-model="commentText" maxlength="30" placeholder=" 여기에 댓글을 달아주세요." class="community-detail-commentcontent">
        <v-btn @click="writeReply" style="height:34px; margin-left:20px">댓글작성</v-btn>
      </div>
      <ArticleDetailComment v-for="reply in maincontent.replyResponseList" :key="reply.id" :reply="reply"/>
    </div>
    <ArticleSides/>
  </div>

  <div>
    <v-row
      justify="center"
    >
      <!-- 여기부터 모달창 -->
      <v-dialog
        v-model="reportModal"
      >
        <v-card>
          <v-card-text>
            <div>
              <div style="margin-bottom:20px; font-size: 22px;">
                <p>신고하실 사유를 모두 선택해주세요.</p>
              </div>
              <v-checkbox v-model="check1" label="음담패설" color="error" density="compact" hide-details></v-checkbox>
              <v-checkbox v-model="check2" label="혐오스러운 내용 혹은 혐오 발언" color="error" density="compact" hide-details></v-checkbox>
              <v-checkbox v-model="check3" label="악의적인 내용" color="error" density="compact" hide-details></v-checkbox>
              <v-checkbox v-model="check4" label="범죄 조장 혹은 유해한 내용" color="error" density="compact" hide-details></v-checkbox>
              <v-checkbox v-model="check5" label="도배 혹은 오해의 소지가 있는 내용" color="error" density="compact" hide-details></v-checkbox>
              <div style="display: flex; justify-content:space-around; margin-top: 20px; margin-bottom: 15px;">
                <v-btn color="error" @click="reportArticle()">신고하기</v-btn>
                <v-btn @click="reportModalToggle()">취소하기</v-btn>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>

      <v-dialog v-model="reportErrorModal">
        <v-card>
          <v-card-text>
            <div>
              <div style="font-size:20px">
                <p>신고 사유를 선택해 주세요!</p>
              </div>
              <div style="text-align:center">
                <v-btn
                  style="margin-top:30px; color:white; margin-left: auto; margin-right: auto;"
                  color="#2E6AFD"
                  width="200px"
                  @click="reportErrorModal = false"
                >
                  다음
                </v-btn>
              </div>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" text @click="reportErrorModal = false">
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>


</template>

<script setup>
import { ref } from "vue"
import { useRoute } from 'vue-router'
import NavBar from "../components/NavBar.vue"
import SideBar from "../components/SideBar.vue"
import ArticleSides from "../components/ArticleSides.vue"
import ArticleDetailComment from "../components/ArticleDetailComment.vue"
import router from '@/router/index.js'
import axios from "axios"
import Swal from "sweetalert2"
import jwt_decode from "jwt-decode"
import { useCommunityStore } from "@/store"

const communityStore = useCommunityStore()
const route = useRoute()
const articleid = route.params.articleid
const maincontent = ref({})
const loaded = ref(false)
const createdDate = ref('')
const commentText = ref('')
const reportModal = ref(false)
const reportErrorModal = ref(false)
const check1 = ref(false)
const check2 = ref(false)
const check3 = ref(false)
const check4 = ref(false)
const check5 = ref(false)
const userEmail = ref('')
const loginUserId = jwt_decode(sessionStorage.getItem('token')).sub

axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/articles/' + articleid,
  method: 'GET', 
}).then(res => {
  maincontent.value = res.data
  const createdTime = new Date(res.data.createDate)
  let yy = createdTime.getFullYear()
  let mm = createdTime.getMonth()
  let dd = createdTime.getDate()
  let hh = createdTime.getHours()
  if (hh < 10) {hh = '0' + hh}
  let mmm = createdTime.getMinutes()
  if (mmm < 10) {mmm = '0' + mmm}
  createdDate.value = yy +'/'+ mm +'/'+ dd + ' ' + hh + ":" + mmm
  loaded.value = true
}).catch(err => {
  console.log(err)
})
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/members/' + loginUserId,
  method: 'GET',
}).then(res =>{
  userEmail.value = res.data.email
}).catch(err => {
  console.log(err)
})

function toArticleListBtn() {
  commentText.value = ''
  router.push({name: 'Article'})
}
function likeArticle() {
  axios({
    url: "https://i7b204.p.ssafy.io/cheertogether/articles/"+ route.params.articleid +"/like",
    method: 'POST',
    params: {
      jwtToken: sessionStorage.getItem('token')
    }
  })
  .then(res => {
    console.log(res.data)
    Swal.fire({
      icon: 'success',
      title: '이 글을 좋아합니다!'
    })
  })
  .catch(err => {
    console.log(err)
    Swal.fire({
      icon: 'error',
      title: '좋아요 실패'
    })
  })
}
function deleteArticle() {
  axios({
    url: 'https://i7b204.p.ssafy.io/cheertogether/articles/' + route.params.articleid,
    method: 'DELETE',
  }).then(res => {
    console.log(res)
    router.push({name: 'Article'})
  }).catch(err => {
    console.log(err)
  })
}
function reportModalToggle() {
  if (reportModal.value) {
    reportModal.value = false
  } else {
    reportModal.value = true
  }
}
function modifyArticle() {
  communityStore.communityModify(route.params.articleid)
}
function reportArticle() {
  if (check1.value||check2.value||check3.value||check4.value||check5.value) {
    axios({
      url: "https://i7b204.p.ssafy.io/cheertogether/articles/"+ route.params.articleid +"/unlike",
      method: 'POST',
      params: {
        jwtToken: sessionStorage.getItem('token')
      }
    })
    .then(res => {
      console.log(res.data)
      reportModalToggle()
      Swal.fire({
        icon: 'success',
        title: '신고가 접수되었습니다.'
      })
    })
    .catch(err => {
      console.log(err)
      reportModalToggle()
      Swal.fire({
        icon: 'error',
        title: '싫어요 전송 실패'
      })
    })
  } else {
    reportErrorModal.value = true
  }
}
function writeReply() {
  axios({
    url: "https://i7b204.p.ssafy.io/cheertogether/articles/"+ route.params.articleid +"/reply",
    method: 'POST',
    data: {
      content: commentText.value
    },
    params: {
      jwtToken: sessionStorage.getItem('token')
    }
  })
  .then(res => {
    console.log(res)
    router.go()
  })
  .catch(err => {
    console.log(err)
    Swal.fire({
      icon: 'error',
      title: '댓글 다는 중 에러 발생'
    })
  })
} 
</script>

<style>
.community-detail-top {
  display:flex;
  align-items: center;
  height:36px;
}
.community-detail-header {
  display:flex;
  height:38px;
  margin:0;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #bcbcbc;
}
.community-detail-content-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height:36px;
  margin:10px 0 10px 0;
}
.community-detail-writecomment {
  display:flex; align-items: center; width:790px; height:36px; margin: 20px 10px 25px;
}
.community-detail-commentcontent {
  width:650px;
  height:34px;
  border-radius:5px;
  border: 1px solid #bcbcbc;
}
.community-detail-btns {
  display: flex; 
  align-items: center;
  font-size: 13px;

}
.word-link:hover {
  color: var(--main-color);
  cursor: pointer
}
/* :deep(.v-selection-control .v-label) {
  height: 40px !important;
} */
</style>