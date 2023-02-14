import {
  fetchError,
  getComments,
  readCard,
  updateCards,
  userLogin,
  userLogout,
  userSignup,
} from '../store/actions';
import { Tcomment, Tfilters, TloginData, TsignupData } from './constants';

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
    if(userLoginData.status == 500) throw new Error("비밀번호가 틀렸습니다.")
      console.log(userLoginData.status)
    return userLogin(userLoginData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchSignup(signupData: TsignupData) {
  try {
    const response = await fetch(url + 'api/signup/', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(signupData),
    });

    const userSignupData = await response.json();
    return userSignup(userSignupData);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchLogout() {
    try {
      await fetch(url + 'api/logout/', {
        method: 'DELETE',
        credentials: 'include',
      });
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
    const sample = await response.json();
    console.log(sample)
    const cardDatas = [
        {
          uuid: '1',
          title: '점심 같이 드실분 점심 같이 드실분',
          curr: 1,
          total: 4,
          isClosed: false,
          meetingDay: new Date('1212-01-23T12:40:50'),
          regionId: 5,
          categoryId: 18,
        },
        {
          uuid: '3',
          title: '강남구 축구부원 모집합니다.',
          curr: 1,
          total: 99,
          isClosed: false,
          meetingDay: new Date('1212-01-23T12:40:50'),
          regionId: 1,
          categoryId: 1,
        },]
    return updateCards(cardDatas);
  } catch (error) {
    return fetchError(error);
  }
}

export async function fetchCardDetail(cardId:string){
    try {
        //TODO: response 잘 들어오는지 확인하고 cardDataDetail에 넣어주고 return
    
        // const response = await fetch(url + 'api/articles/' + cardId);
    
        // const responseData = await response.json();
        // const cardDataDetail= {...responseData.data.article,...responseData.data.host}
        const cardDataDetail = 
            {
                "uuid": "게시물 식별자",
				"title": "점심 같이 드실 분",
				"curr": 3,
				"total": 4,
				"isClosed": false,
				"content": "쌈밥정식 드실 분 구합니다.",
				"meetingDay": new Date("1997-06-24T14:00:40"),
				"location": '서울 서초구 헌릉로 12',
				"locationDetail": "현대자동차사옥연구센타",
				"regionId": 1,
				"categoryId": 1,
				"hasJoined": false,
				"isHost": true,
                "hostId": "abc313",
                "hostDesc": "hey 모두들 안녕~ 내가 누군지 아니~", 
                "hostProfile" : "../../public/assets/cherryblossom.jpeg", 

            }
        const commentsData:Tcomment[] = 
        [
            {
              uuid: '100001',
              userId: '101',
              date: '3일전',
              content: '바람도 서롤 감싸게 했죠',
              isMyComment: true,
            },
            {
              uuid: '100002',
              userId: '103',
              date: '3일전',
              content: '햇살은 우릴 위해 내리고',
              isMyComment: false,
            },
            {
              uuid: '100003',
              userId: '101',
              date: '3일전',
              content: '우리 웃음속에 계절은 오고 또 갔죠',
              isMyComment: true,
            },
            {
              uuid: '100004',
              userId: '103',
              date: '3일전',
              content: '바람에 흔들리는 머릿결',
              isMyComment: false,
            },
            {
              uuid: '100005',
              userId: '105',
              date: '3일전',
              content: '내게 불어오는 그대 향기',
              isMyComment: false,
            },
            {
              uuid: '100006',
              userId: '103',
              date: '6일전',
              content: '예쁜 두눈도 웃음 소리도',
              isMyComment: false,
            },
            {
              uuid: '100007',
              userId: '105',
              date: '8일전',
              content: '모두 다 내 것이었죠',
              isMyComment: false,
            },
            {
              uuid: '100008',
              userId: '103',
              date: '10일전',
              content: '이런 사랑 이런 행복 쉽다 했었죠',
              isMyComment: false,
            },
            {
              uuid: '100009',
              userId: '105',
              date: '12일전',
              content: '이런 웃음 이런 축복 내게 쉽게 올리 없죠',
              isMyComment: false,
            },
            {
              uuid: '100010',
              userId: '103',
              date: '122일전',
              content: '눈물 조차 울음조차 닦지 못한 나',
              isMyComment: false,
            },
            {
              uuid: '100010',
              userId: '103',
              date: '122일전',
              content: '눈물 조차 울음조차 닦지 못한 나',
              isMyComment: false,
            },
            {
              uuid: '100010',
              userId: '103',
              date: '122일전',
              content: '눈물 조차 울음조차 닦지 못한 나',
              isMyComment: false,
            },
            {
              uuid: '100010',
              userId: '103',
              date: '122일전',
              content: '눈물 조차 울음조차 닦지 못한 나',
              isMyComment: false,
            },
            {
              uuid: '100010',
              userId: '103',
              date: '122일전',
              content: '눈물 조차 울음조차 닦지 못한 나',
              isMyComment: false,
            },
          ]
        return readCard(cardDataDetail,commentsData)
      } catch (error) {
        console.log(error);
        return fetchError(error);
      }
    // return readCard(cardId)
}
export async function fetchSendComment(uuid:string,commentData:{content:string, date:string}) {
  try {
    // const response =
    // await fetch(url + 'api/articles/' + uuid + '/comments', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(commentData),
    // });

    return await fetchGetComments(uuid);
  } catch (error) {
    return fetchError(error);
  }
}
export async function fetchGetComments(cardID: string) {
    try {
        console.log('getcomm')
    //    const response =
    //   await fetch(url + 'api/articles/' + cardID );
    //   const cardDetailData = await response.json();
    //   return getComments(cardDetailData.data.comments);
     return getComments([{
        uuid: '100001',
        userId: '101',
        date: '3일전',
        content: '바람도 서롤 감싸게 했죠',
        isMyComment: true,
      },
      {
        uuid: '100002',
        userId: '103',
        date: '3일전',
        content: '햇살은 우릴 위해 내리고',
        isMyComment: false,
      },])
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
