// import { initialState } from './initalState.js';

import { fetchError, updateCards, userLogin, userSignup } from '../store/actions';
import { TloginData, TsignupData } from './constants';

// import { fetchCardsRequest, fetchCardsSuccess } from "../store/actions";
// import store from "../store/store";
// import { Tcard } from "./constants";

const url = 'http://localhost:3000/';

// export async function getJSONdata() {
//   try {
//     await fetchColumns();
//     await fetchLogs()
//   }
//   catch (error) {
//     console.log(error);
//   }
// }
// export async function fetchColumns() {
//   await fetch(url + "columns").then(response => response.json())
//     .then(json => {
//       initialState.columns = [...json]
//     })
// }
// export async function fetchLogs() {
//   await fetch(url + "logs").then(response => response.json()).then(json => {
//     initialState.logs = [...json];
//   })
// }
// export function getArticles(regionId:number,categoryId:number) {
//     const params ={
//         regionId:regionId,
//         categoryId:categoryId,
//     }
//     const query= getQuery(params);
//     fetch(url + "articles?"+query).then(response => response.json())
//     .then(json => {
//       initialState.columns = [...json]
//     })

//   }

export async function fetchLogin(loginData: TloginData) {
    try {
      // const query = getQuery(loginData);
      const response = 
      await fetch(url + 'login' ,{
          method : "POST",
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(loginData)
      });
  
      const userLoginData = await response.json();
      console.log(userLoginData)
      return userLogin(userLoginData);
    } catch (error) {
      console.log(error);
      return fetchError(error);
    }
  }
  export async function fetchSignup(signupData: TsignupData) {
    try {
      // const query = getQuery(loginData);
      const response = 
      await fetch(url + 'singup' ,{
          method : "POST",
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(signupData)
      });
  
      const userSignupData = await response.json();
      console.log(userSignupData)
      return userSignup(userSignupData);
    } catch (error) {
      console.log(error);
      return fetchError(error);
    }
  }

export async function getArticles(regionId: number, categoryId: number) {
  try {
    const params = {
      regionId: regionId,
      categoryId: categoryId,
    };
    const query = getQuery(params);
    const response = await fetch(url + 'articles?' + query);

    const cardDatas = await response.json();
    return updateCards(cardDatas);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}

// export  function getArticles1(regionId: number, categoryId: number) {
//     // let cardData: Tcard[] = [];
//       const params = {
//         regionId: regionId,
//         categoryId: categoryId
//       };
//       const query = getQuery(params);
//       return fetch(url + "articles?" + query)
//         .then((response) => response.json())
//         .catch(()=>{console.log('catch the error');
//     });

//   }
//   export function getArticles(regionId: number, categoryId: number) {
//     // eslint-disable-next-line no-debugger
//     debugger;
//       store.dispatch(fetchCardsRequest());
//       const params = {
//         regionId: regionId,
//         categoryId: categoryId
//       };
//       const query = getQuery(params);
//       console.log(query)
//       fetch(url + "articles?" + query)
//         .then((response) => response.json())
//         .then((json) => {
//           store.dispatch(fetchCardsSuccess(json));
//         }).catch(()=>{console.log('catch the error')});

//   }
//   export function patchCardList(colData) {
//     fetch(url + "columns/" + colData.id, {
//       method: "PATCH",
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify({
//         cards: colData.cards,
//       }),
//     })
//   }

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function getQuery(params: any): string {
  return Object.keys(params)
    .map((k) => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');
}
