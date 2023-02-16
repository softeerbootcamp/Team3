// import { TcardDetail } from '../common/constants';

import {
  Tcard,
  TcardDetail,
  Tcomment,
  Tfilters,
  TuserInfo,
} from '../common/constants';
import Navigate from '../common/utils/navigate';

export const SET_NAVIGATE = 'SET_NAVIGATE';
export const FILTER_REGION = 'FILTER_REGION';
export const FILTER_CATEGORY = 'FILTER_CATEGORY';
export const FILTER_SEARCH = 'FILTER_SEARCH';
export const READ_CARD = 'READ_CARD';
export const CHECK_LOGIN = 'CHECK_LOGIN';
export const USER_LOGIN = 'USER_LOGIN';
export const USER_SIGNUP = 'USER_SIGNUP';
export const USER_LOGOUT = 'USER_LOGOUT';
export const UPDATE_CARDS = 'UPDATE_CARDS';
export const SEND_COMMENT = 'SEND_COMMENT';
export const UPDATE_COMMENT = 'UPDATE_COMMENT';
export const GET_COMMENT = 'GET_COMMENT';
export const FETCH_ERROR = 'FETCH_ERROR';
export const SET_MODAL = 'SET_MODAL';
export const POST_CARD = 'POST_CARD';
export const EDIT_CARD = 'EDIT_CARD';
export const PROFILE_TAB = 'PROFILE_TAB';
export const SET_PROFILE = 'SET_PROFIlE';
export const CHANGE_PROFILEIMG = 'CHANGE_PROFILEIMG';

// export const REFRESH_CARDS = 'REFRESH_CARDS';

export function setNavigate(navigate: Navigate) {
  return {
    type: SET_NAVIGATE,
    payload: {
      navigate,
    },
  };
}
export function fiterRegion(regionId: number) {
  return {
    type: FILTER_REGION,
    payload: {
      regionId,
    },
  };
}
export function filterCategory(categoryId: number) {
  return {
    type: FILTER_CATEGORY,
    payload: {
      categoryId,
    },
  };
}
export function filterSearch(filters: Tfilters) {
  return {
    type: FILTER_SEARCH,
    payload: {
      filters,
    },
  };
}
// export function fetchCardsRequest() {
//   return {
//     type: FETCH_CARDS_REQUEST,
//     payload: {},
//   };
// }
// export function fetchCardsSuccess (cardsData:Tcard[]) {
//   return {
//     type: FETCH_CARDS_SUCCESS,
//     payload: {
//      cardsData,
//     },
//   };
// }

export function updateCards(cardsData: Tcard[]) {
  return {
    type: UPDATE_CARDS,
    payload: {
      cardsData,
    },
  };
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function fetchError(error: any) {
  return {
    type: FETCH_ERROR,
    payload: {
      error,
    },
  };
}

export function readCard(readingCard: TcardDetail, commentsData: Tcomment[]) {
  // 모달창 띄워주기
  console.log(commentsData);
  return {
    type: READ_CARD,
    payload: { readingCard, commentsData },
  };
}

export function checkLogin(cookie: string) {
  return {
    type: CHECK_LOGIN,
    payload: { cookie },
  };
}

export function userLogin(cookie: string) {
  return {
    type: USER_LOGIN,
    payload: { cookie },
  };
}

// export function userLogin(userLoginData: TloginData) {
//   return {
//     type: USER_LOGIN,
//     payload: {userLoginData,}
//   };
// }

// export function userSignup(userSingupData: TsignupData) {
//   return {
//     type: USER_SIGNUP,
//     payload: {userSingupData,}
//   };
// }
export function userLogout() {
  return {
    type: USER_LOGOUT,
    payload: {},
  };
}
export function sendComment() {
  return {
    type: SEND_COMMENT,
    payload: {},
  };
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
// export function updateComments(commendResponse:any) {
//   return {
//     type: UPDATE_COMMENT,
//     payload: {commendResponse}
//   };
// }
export function getComments(commentData: Tcomment[]) {
  return {
    type: GET_COMMENT,
    payload: { commentData },
  };
}

export function setModal(modalAction: string, uuid = '') {
  return {
    type: SET_MODAL,
    payload: { modalAction, uuid },
  };
}

export function postCard(modalAction: string) {
  return {
    type: POST_CARD,
    payload: { modalAction },
  };
}

export function editCard(modalAction: string) {
  return {
    type: EDIT_CARD,
    payload: { modalAction },
  };
}

export function changeProfileTab(tabNumber: number) {
  return {
    type: PROFILE_TAB,
    payload: { tabNumber },
  };
}

export function getUserInfo(userInfoResponse: TuserInfo) {
  return {
    type: SET_PROFILE,
    payload: { userInfoResponse },
  };
}

export function changeProfileImg(imageSrc : string){
  return{
    
  }
}