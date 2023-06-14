<template>
  <div class="community-main">
    <div class="write-top">
      <div style="width: 180px">
        <v-select
          v-model="category"
          :items="items"
          item-title="title"
          item-value="apiId"
          label="리그 분류"
          density="compact"
          solo
        ></v-select>
      </div>
      <div>
        <input
          v-model="title"
          type="text"
          maxlength="25"
          placeholder=" 제목을 입력하세요."
          class="write-title"
        />
      </div>
    </div>

    <div class="write-author">
      <div style="width: 34px; height: 34px; margin: 0 10px 0 10px">
        <img
          :src="accountStore.profile.profileImage"
          style="width: 34px; height: 34px"
        />
      </div>
      <a>{{ accountStore.profile.nickname }} | {{ now }}</a>
    </div>
    <div style="width: 790px; margin-bottom: 15px">
      <textarea
        v-model="content"
        placeholder="내용을 입력하세요."
        class="write-content"
      ></textarea>
    </div>
    <div class="write-bottom">
      <v-btn
        style="height: 34px; margin-left: 20px; font-family: 'MICEGothic Bold'"
        @click="communityStore.communityToggle()"
        class="write-button"
        >취소하기</v-btn
      >
      <v-btn
        style="
          height: 34px;
          margin-left: 20px;
          color: white;
          font-family: 'MICEGothic Bold';
        "
        color="#2e6afd"
        v-if="isModify"
        @click="modifyButton()"
        class="write-button"
        >수정완료</v-btn
      >
      <v-btn
        style="
          height: 34px;
          margin-left: 20px;
          color: white;
          font-family: 'MICEGothic Bold';
        "
        color="#2e6afd"
        v-else
        @click="completeButton()"
        class="write-button"
        >작성완료</v-btn
      >
    </div>
  </div>
</template>

<script setup>
import router from "@/router";
import { useAccountStore, useCommunityStore } from "@/store";
import axios from "axios";
import Swal from "sweetalert2";
import { ref } from "vue";
const isModify = ref(false);
const category = ref(39);
const title = ref("");
const content = ref("");
const modifingArticleId = ref(false);
const communityStore = useCommunityStore();
const accountStore = useAccountStore();

if (communityStore.isModify) {
  title.value = communityStore.modifingArticleTitle;
  content.value = communityStore.modifingArticleContent;
  category.value = communityStore.modifingArticleApiId;
  modifingArticleId.value = communityStore.modifingArticleId;
  isModify.value = communityStore.isModify;
  communityStore.isModify = false;
}

const items = [
  { title: "프리미어리그", apiId: 39 },
  { title: "라리가", apiId: 140 },
  { title: "세리에A", apiId: 135 },
  { title: "분데스리가", apiId: 78 },
  { title: "리그 1", apiId: 61 },
  { title: "K리그", apiId: 292 },
];
const time = new Date();
const now = time.toLocaleString();
function completeButton() {
  if (title.value.length < 26 && !title.value == "") {
    axios({
      url: "https://i7b204.p.ssafy.io/cheertogether/articles",
      method: "POST",
      data: {
        content: content.value,
        leagueApiId: category.value,
        title: title.value,
      },
      params: {
        jwtToken: accountStore.accessToken,
      },
    })
      .then((res) => {
        console.log(res.data);
        router.go();
      })
      .catch((err) => {
        console.log(err);
        Swal.fire({
          icon: "error",
          title: "실패! 😨",
          text: "글 작성에 실패하였습니다.",
          confirmButtonText: "확인",
        });
      });
    communityStore.communityToggle();
  } else {
    Swal.fire({
      icon: "error",
      title: "제목이 올바르지 않습니다.",
    });
  }
}
function modifyButton() {
  axios({
    url:
      "https://i7b204.p.ssafy.io/cheertogether/articles/" +
      modifingArticleId.value,
    method: "POST",
    data: {
      content: content.value,
      leagueApiId: category.value,
      title: title.value,
    },
  })
    .then((res) => {
      console.log(res.data);
      router.push({
        name: "ArticleDetail",
        params: { articleid: modifingArticleId.value },
      });
    })
    .catch((err) => {
      console.log(err);
      Swal.fire({
        icon: "error",
        title: "실패! 😨",
        text: "글 작성에 실패하였습니다.",
        confirmButtonText: "확인",
      });
    });
  communityStore.communityToggle();
}
</script>

<style>
.write-top {
  display: flex;
  justify-content: space-between;
  width: 790px;
  height: 40px;
  margin-bottom: 15px;
}
.write-author {
  display: flex;
  align-items: center;
  width: 790px;
  height: 36px;
  margin-bottom: 15px;
  font-size: 13px;
}
.write-bottom {
  display: flex;
  justify-content: flex-end;
}
.write-button {
  height: 34px;
  margin: 0 10px 0 10px;
}
.write-title {
  width: 580px;
  height: 40px;
  border-radius: 5px;
  border: 1px solid #bcbcbc;
}
.write-content {
  width: 790px;
  height: 300px;
  min-height: 300px;
  max-height: 500px;
  border-radius: 5px;
  border: 1px solid #bcbcbc;
}
</style>
