<template>
  <NavBar/>
  <div style="display:flex;">
    <SideBar/>
    <div class="community">
      <div v-if="axiosLoaded" class="community-main">
        <div class="community-detail-top">
          <div style="height:36px; width:36px; margin-right:10px;">
            <img :src="maincontent.leagueResponseExceptTeamList.logo" style="height:36px; width:36px;">
          </div>
          <a>{{maincontent.leagueResponseExceptTeamList.hanName}}</a>
        </div>
        <div class="community-detail-header">
          <div style="font-size:20px; font-family: 'MICEGothic Bold';">
            {{maincontent.title}}
          </div>
          <div style="font-size:13px;">
            <a class="word-link" @click="reportModalToggle()">신고하기</a><a> | </a>
            <a class="word-link" @click="toArticleListBtn()">글 목록</a>
          </div>
        </div>
        <div class="community-detail-content-top">
          <div style="display: flex; align-items: center;">
            <div style="height:36px; width:36px; margin:0 10px 0 10px;">
              <img :src="writerLogo" style="height:36px; width:36px;">
            </div>
            <a @click="toUserDetail(maincontent.memberResponse.id)" class="word-link" style="font-size:14px; margin-right:5px;">{{maincontent.memberResponse.nickname}}</a>
            <a> | {{createdDate}}</a>
          </div>
          <div v-if="userEmail==maincontent.memberResponse.email||userRole=='ADMIN'" class="community-detail-btns">
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
        <div style="margin: 10px; line-height: 200%;">
          {{maincontent.content}}
        </div>
        <div style="height:50px; margin-top: 50px;">
          <div @click="likeArticle" class="like-btn">
            <v-icon size="35px" color="white">
              mdi-thumb-up
            </v-icon>
          </div>
        </div>
        <div class="community-detail-writecomment">
          <input type="text" v-model="commentText" maxlength="30" placeholder=" 여기에 댓글을 달아주세요." class="community-detail-commentcontent">
          <v-btn @click="writeReply" style="height:34px; margin-left:20px">댓글작성</v-btn>
        </div>
        <ArticleDetailComment v-for="reply in maincontent.replyResponseList" :key="reply.id" :reply="reply" :userRole="userRole"/>
      </div>
      <ArticleSides/>
    </div>
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
              <button @click="reportModal = false" style="position:absolute; top:0; right:0;">
                <v-icon>
                  mdi-close-box
                </v-icon>
              </button>
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
const axiosLoaded = ref(false)
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
const userRole = ref('')
const loginUserId = jwt_decode(sessionStorage.getItem('token')).sub
const writerLogo = ref(require("../assets/image/로고.png"))

axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/articles/' + articleid,
  method: 'GET', 
}).then(res => {
  maincontent.value = res.data
  const createdTime = new Date(res.data.createDate)
  let yy = createdTime.getFullYear()
  let mm = createdTime.getMonth()+1
  if (mm < 10) {mm = '0' + mm}
  let dd = createdTime.getDate()
  if (dd < 10) {dd = '0' + dd}
  let hh = createdTime.getHours()
  if (hh < 10) {hh = '0' + hh}
  let mmm = createdTime.getMinutes()
  if (mmm < 10) {mmm = '0' + mmm}
  createdDate.value = yy +'/'+ mm +'/'+ dd + ' ' + hh + ":" + mmm
  if (res.data.memberResponse.favoriteTeamList.length > 0) {
    writerLogo.value = res.data.memberResponse.favoriteTeamList[0].logo
  }
  axiosLoaded.value = true
}).catch(err => {
  console.log(err)
})
axios({
  url: 'https://i7b204.p.ssafy.io/cheertogether/members/' + loginUserId,
  method: 'GET',
}).then(res =>{
  userEmail.value = res.data.email
  userRole.value = res.data.role
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
      title: '게시글 좋아요! 👍',
      text: '이 글을 좋아합니다.',
      confirmButtonText: '확인'
    })
  })
  .catch(err => {
    if (err.response.status=='400') {
      Swal.fire({
        icon: 'warning',
        title: '좋아요 실패! 😾',
        text: '이미 좋아요를 누른 게시글입니다.',
        confirmButtonText: '확인'
      })
    } else {
      console.log(err)
      Swal.fire({
        icon: 'error',
        title: "좋아요 실패! 😾",
        text: '유효하지 않은 요청입니다.',
        confirmButtonText: '확인'
      })
    } 
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
  communityStore.communityModify({
    id: maincontent.value.id,
    title: maincontent.value.title,
    content: maincontent.value.content,
    apiId: maincontent.value.leagueResponseExceptTeamList.apiId,
  })
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
        title: '신고 접수! 🚨',
        text: '게시글 신고가 접수되었습니다.',
        confirmButtonText: '확인'
      })
    })
    .catch(err => {
      if (err.response.status=='400') {
        reportModalToggle()
        Swal.fire({
          icon: 'error',
          title: '신고 실패! 😵',
          text: '이미 신고가 접수된 게시글입니다.',
          confirmButtonText: '확인'
        })
      } else {
        console.log(err)
        reportModalToggle()
        Swal.fire({
          icon: 'error',
          title: '싫어요 실패! 😵',
          text: '유효하지 않은 요청입니다.',
          confirmButtonText: '확인'
        })
      }
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
      title: '댓글 작성 실패! 😰',
      text: '댓글 작성 중 에러가 발생하였습니다.',
      confirmButtonText: '확인'
    })
  })
}
function toUserDetail(id) {
  router.push({name: 'Mypage', params: { userid: id }})
}
</script>

<style>
.community {
  display: flex;
  margin-top: 160px;
  margin-left: 210px;
  width:100%;
  justify-content: center;
  align-content: flex-start;
  min-width: 1400px;
}
@media (max-width: 1580px) {
  .community {
    margin-top: 120px;
    margin-left: 175px;
  }
}
.community-detail-top {
  display:flex;
  align-items: center;
  height:36px;
}
.community-detail-header {
  display:flex;
  height:38px;
  margin:10px;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #bcbcbc;
}
.community-detail-content-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height:36px;
  margin:20px 0 20px 0;
}
.community-detail-writecomment {
  display:flex; 
  align-items: center; 
  width:790px; 
  height:36px; 
  margin: 40px 10px 45px;
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
.like-btn {
  display: flex;
  height:50px; 
  width:50px; 
  margin:auto; 
  background-color:var(--main-color); 
  border-radius: 50%;
  align-items: center;
  justify-content: center;
}
.like-btn:hover {
  cursor: pointer;
}
</style>