// import { TcardDetail } from '../common/constants';

import { Tcard, TloginData, TsignupData } from "../common/constants";

export const FILTER_REGION = 'FILTER_REGION';
export const FILTER_CATEGORY = 'FILTER_CATEGORY';
export const READ_CARD = 'READ_CARD';
export const USER_LOGIN = 'USER_LOGIN';
export const UPDATE_CARDS = 'UPDATE_CARDS';
// export const FETCH_CARDS_REQUEST = 'FETCH_CARDS_REQUEST';
// export const FETCH_CARDS_SUCCESS = 'FETCH_CARDS_SUCCESS';
export const FETCH_ERROR = 'FETCH_ERROR';
// export const REFRESH_CARDS = 'REFRESH_CARDS';

export function fiterRegion(regionId:number) {
  return {
    type: FILTER_REGION,
    payload: {
      regionId
    },
  };
}
export function filterCategory(categoryId:number) {
  return {
    type: FILTER_CATEGORY,
    payload: {
      categoryId
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

export function updateCards (cardsData:Tcard[]) {
  return {
    type: UPDATE_CARDS,
    payload: {
      cardsData,
    },
  };
}
export function fetchError (error:any) {
  return {
    type: FETCH_ERROR,
    payload: {
     error,
    },
  };
}

export function readCard(cardId: string) {
  // 모달창 띄워주기
  return {
    type: READ_CARD,
    payload: cardId,
  };
}

export function userLogin(userLoginData: TloginData) {
  return {
    type: USER_LOGIN,
    payload: {userLoginData,}
  };
}

export function userSignup(userSingupData: TsignupData) {
  return {
    type: USER_LOGIN,
    payload: {userSingupData,}
  };
}
