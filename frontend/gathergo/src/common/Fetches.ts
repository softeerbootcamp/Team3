import {
  fetchError,
  sendComment,
  updateCards,
  updateComments,
  userLogin,
  userSignup,
} from '../store/actions';
import { Tcomment, TloginData, TsignupData } from './constants';

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
    const response = await fetch(url + 'login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(loginData),
    });

    const userLoginData = await response.json();
    //   console.log(userLoginData)
    return userLogin(userLoginData);
  } catch (error) {
    // console.log(error);
    //   return userLogin({
    //     "userId": "abc",
    //     "password": "def",
    //   });
    return fetchError(error);
  }
}
export async function fetchSignup(signupData: TsignupData) {
  try {
    // const query = getQuery(loginData);
    const response = await fetch(url + 'singup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(signupData),
    });

    const userSignupData = await response.json();
    console.log(userSignupData);
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
export async function fetchSendComment(commentData: Tcomment) {
  try {
    // const response =
    await fetch(url + '/article/' + commentData.uuid + '/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(commentData),
    });

    // const commendResponse = await response.json();
    return sendComment();
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchGetComments(cardID: string|undefined) {
    try {
       const response =
      await fetch(url + '/article/' + cardID + '/comments');
  
      const commendResponse = await response.json();
      return updateComments(commendResponse);
    } catch (error) {
      console.log(error);
      return fetchError(error);
    }
  }
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function getQuery(params: any): string {
  return Object.keys(params)
    .map((k) => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');
}
