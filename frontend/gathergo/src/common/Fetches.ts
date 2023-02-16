import {
  fetchError,
  getComments,
  postCard,
  readCard,
  setModal,
  updateCards,
  userLogin,
  userLogout,
} from '../store/actions';
import { Tcomment, Tfilters, TloginData, TpostCard, TsignupData } from './constants';

const url = 'https://gathergo.kro.kr/';

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
    if (userLoginData.status == 500) throw new Error('비밀번호가 틀렸습니다.');
    console.log(userLoginData.status);
    return userLogin(userLoginData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchSignup(signupData: TsignupData) {
  try {
    // console.log(signupData)
    const response = 
    await fetch(url + 'api/signup/', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(signupData),
    });

    const userSignupData = await response.json();
    if(userSignupData.status == 409) throw new Error(userSignupData.message)
    return setModal("SIGNUP_SUCCESS")//(userSignupData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchLogout() {
  try {
    await fetch(url + 'api/logout', {
      method: 'DELETE',
      credentials: 'include',
    });
    document.cookie = 'sessionId=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
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
    const cardDatas = articleDatas.data.articles.map(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      (article: any) => {
        return { ...article, meetingDay: new Date(article.meetingDay) };
      }
    );
    console.log(cardDatas)
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
        credentials: 'include'
    });

    const responseData = await response.json();
    console.log(responseData);
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
export async function fetchGetComments(cardId: string) {
  try {
    console.log('getcomm');
    const response = await fetch(url + 'api/articles/' + cardId);

    //    const response =
    //   await fetch(url + 'api/articles/' + cardID );
    const cardDetailData = await response.json();
    const commentsData: Tcomment[] = cardDetailData.data.comments.map(
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      (comment: any) => {
        return { ...comment, date: new Date(comment.date) };
      }
    );
    return getComments(commentsData);
    //   return getComments(cardDetailData.data.comments);
    //  return getComments([{
    //     uuid: '100001',
    //     userId: '101',
    //     date: '3일전',
    //     content: '바람도 서롤 감싸게 했죠',
    //     isMyComment: true,
    //   },
    //   {
    //     uuid: '100002',
    //     userId: '103',
    //     date: '3일전',
    //     content: '햇살은 우릴 위해 내리고',
    //     isMyComment: false,
    //   },])
  } catch (error) {
    console.log(error);
    return fetchError(error);
  }
}
export async function fetchPostCard(postCardData: TpostCard) {
    try {
      const response =await fetch(url + 'api/articles' , {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(postCardData),
      });
      const cardDetailData = await response.json();
      console.log(cardDetailData)
      return postCard("POSING");
    } catch (error) {
      console.log(error);
      return fetchError(error);
    }
  }

  export async function fetchEditCard(postCardData: TpostCard,uuid:string) {
    try {
      const response =await fetch(url + 'api/articles/'+uuid , {
        method: 'PUT',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(postCardData),
      });
      const cardDetailData = await response.json();
      console.log(cardDetailData)
      return postCard("POSING");
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
