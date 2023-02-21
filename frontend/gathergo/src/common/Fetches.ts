import {
  changeProfileImg,
  changeUserIntroduction,
  fetchError,
  getComments,
  getNotice,
  getUserInfo,
  postCard,
  readCard,
  setModal,
  updateCards,
  userLogin,
  userLogout,
} from '../store/actions';
import {
  Tcomment,
  Tfilters,
  TloginData,
  TpostCard,
  TsignupData,
  PROFILE_BASE_URL
} from './constants';
import {postSubscription,deleteSubscription,returnTokenStore} from '../firebaseEvent.js'

const url = 'https://gathergo.kro.kr/';
const ERRORCODE = [0,307,400,401,404,409,500,502]
export async function fetchLogin(loginData: TloginData) {
  try {
    // const query = getQuery(loginData);
    const response = await fetch(url + 'api/login', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(loginData),
    });

    const userLoginData = await response.json();
    if (ERRORCODE.includes( userLoginData.status)) throw new Error(userLoginData.message);
    return userLogin(userLoginData);
  } catch (error) {
    console.log(error);
    
    return fetchError(error);
  }
}
export async function fetchSignup(signupData: TsignupData) {
  try {
    // console.log(signupData)
    const response = await fetch(url + 'api/signup/', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(signupData),
    });

    const userSignupData = await response.json();
    if (ERRORCODE.includes( userSignupData.status)) throw new Error(userSignupData.message);
    return setModal('SIGNUP_SUCCESS'); //(userSignupData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchLogout() {
  try {
    const response = await fetch(url + 'api/logout', {
      method: 'DELETE',
      credentials: 'include',
    });
    document.cookie = 'sessionId=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    document.cookie = 'sessionId=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';

    console.log(response)
    return userLogout();
  } catch (error) {
    return fetchError(error);
  }
}
export async function getArticles(filters: Tfilters) {
  try {
    const params = {
      regionId: filters.regionId,
      categoryId: filters.categoryId,
      keyword: filters.keyword,
    };
    const query = getQuery(params);
    const response = await fetch(url + 'api/articles?' + query);
   

    //TODO: response 잘 들어오는지 확인하고 cardDatas에 넣어주고 return
    // const cardDatas = await response.json();

    const articleDatas = await response.json();
    if (ERRORCODE.includes( articleDatas.status)) throw new Error(articleDatas.message)
    const cardDatas = articleDatas.data.articles.map(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      (article: any) => {
        return { ...article, meetingDay: new Date(article.meetingDay) };
      }
    );
    
    return updateCards(cardDatas);
  } catch (error) {
    return fetchError(error);
  }
}

export async function fetchCardDetail(cardId: string) {
  try {
    //TODO: response 잘 들어오는지 확인하고 cardDataDetail에 넣어주고 return

    const response = await fetch(url + 'api/articles/' + cardId, {
      method: 'GET',
      credentials: 'include',
    });

    const responseData = await response.json();
    if (ERRORCODE.includes( responseData.status)) throw new Error(responseData.message)
   
    const cardDataDetail = {
      ...responseData.data.article,
      meetingDay: new Date(responseData.data.article.meetingDay),
      hostId: responseData.data.host.hostId,
      hostDesc: responseData.data.host.hostDesc,
      hostProfile: responseData.data.host.hostProfile,
    };
    const commentsData: Tcomment[] = responseData.data.comments.map(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      (comment: any) => {
        return { ...comment, date: new Date(comment.date) };
      }
    );
    return readCard(cardDataDetail, commentsData);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
  // return readCard(cardId)
}
export async function fetchSendComment(
  uuid: string,
  commentData: { content: string; date: string }
) {
  try {
    // const response =
    await fetch(url + 'api/articles/' + uuid + '/comments', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(commentData),
    });

    return await fetchGetComments(uuid);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchGetComments(cardId: string|undefined) {
  try {
    const response = await fetch(url + 'api/articles/' + cardId, {
      method: 'GET',
      credentials: 'include',
    });

    const cardDetailData = await response.json();
    if (ERRORCODE.includes( cardDetailData.status)) throw new Error(cardDetailData.message)

    const commentsData: Tcomment[] = cardDetailData.data.comments.map(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      (comment: any) => {
        return { ...comment, date: new Date(comment.date) };
      }
    );
    return getComments(commentsData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function deleteComment(articleuuid:string|undefined,commentuuid:string) {
  try {
    await fetch(url + 'api/articles/'+articleuuid+'/comments/'+commentuuid, {
      method: 'DELETE',
      credentials: 'include',
    });
    return fetchGetComments(articleuuid);
    // return;// setModal('DELETE_COMMENT_SUCCESS');
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchJoin(articleuuid:string|undefined) {
  try {
    const response = await fetch(url + 'api/articles/'+articleuuid+'/users', {
      method: 'PUT',
      credentials: 'include',
    });
    const responseData = await response.json();
    if (ERRORCODE.includes( responseData.status)) throw new Error(responseData.message)
    
    postSubscription(returnTokenStore().token,articleuuid as string);
    return fetchCardDetail(responseData.data.articleUuid);
    // return;// setModal('DELETE_COMMENT_SUCCESS');
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchJoinCancel(articleuuid:string|undefined) {
  try {
    const response = await fetch(url + 'api/articles/'+articleuuid+'/users', {
      method: 'DELETE',
      credentials: 'include',
    });
    const cardDetailData = await response.json();
    
    deleteSubscription(returnTokenStore().token,articleuuid as string)

    return fetchCardDetail(cardDetailData.data.articleUuid);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchCloseMeeting(articleuuid:string|undefined) {
  try {
    // const response = 
    await fetch(url + 'api/articles/'+articleuuid+'/close', {
      method: 'PUT',
      credentials: 'include',
    });
    // const cardDetailData = await response.json();
    
    return //fetchCardDetail(cardDetailData.data.articleUuid);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchPostCard(postCardData: TpostCard) {
  try {
    const response = await fetch(url + 'api/articles', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(postCardData),
    });
    const cardDetailData = await response.json()
    postSubscription(returnTokenStore().token, cardDetailData.data);
    if (ERRORCODE.includes( cardDetailData.status)) throw new Error(cardDetailData.message)
    return postCard('POSTING_SUCCESS');
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}

export async function fetchEditCard(postCardData: TpostCard, uuid: string) {
  try {
    const response = await fetch(url + 'api/articles/' + uuid, {
      method: 'PUT',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(postCardData),
    });
    const cardDetailData = await response.json();
    if (ERRORCODE.includes( cardDetailData.status)) throw new Error(cardDetailData.message)
    return postCard('POSTING_EDIT_SUCCESS');
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchGetUserInfo() {
  try {
    const response = await fetch(url + 'api/users/', {
      method: 'GET',
      credentials: 'include',
    });
    const userInfoResponse = await response.json();
    console.log(userInfoResponse);
    return getUserInfo(userInfoResponse.data);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}

export async function changeUserProfileImg(
  formData: FormData,
  useruuId: string
) {
  try {
    const response = await fetch(url + 'api/image/' + useruuId, {
      method: 'POST',
      credentials: 'include',
      body: formData,
    });
    const postImgResponse = await response.json();
    console.log(postImgResponse);
    return changeProfileImg(PROFILE_BASE_URL+useruuId+'.png');
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}

export async function changeUserProfileIntroduction(
  introduction: string,
  id: string
) {
  console.log(id)
  try {
    const response = await fetch(url + 'api/users', {
      method: 'PUT',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        introduction: introduction,
      }),
    });
    const postIntroResponse = await response.json();
    console.log(postIntroResponse);
    return changeUserIntroduction(introduction);
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}

export async function getNoticeSidebar(){
  try{
    const response = await fetch(url+'api/notifications',{
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      }
    })
    const getNoticeResponse = await response.json();
    return getNotice(getNoticeResponse.data)
  }catch(error){
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
