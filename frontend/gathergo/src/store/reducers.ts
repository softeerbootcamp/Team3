import {
  FETCH_ERROR,
  // FETCH_CARDS_REQUEST,
  // FETCH_CARDS_SUCCESS,
  FILTER_CATEGORY,
  FILTER_REGION,
  READ_CARD,
  UPDATE_CARDS,
  USER_LOGIN,
} from './actions';
import { initialState } from '../server/initialstate';
import { getArticles } from '../common/Fetches';
// import { getArticles } from '../common/Fetches';
// import store from './store';
// import {
//   postColumn,
//   postLog,
//   patchColumnTitle,
//   patchCardList,
//   deleteColumn,
// } from '../../server/server.js';

type action = {
  type: string;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  payload: any;
};
 function reducer(state = initialState, action: action) {
  switch (action.type) {
    case FILTER_REGION:
      //  getArticles(action.payload.regionId,state.filterCategory).then(
      //   (json) => {
      //     state.cards = json.articles}
      //     //           store.dispatch(fetchCardsSuccess(json));
      // )
      //TODO: state.cards <- 이거 다시 받아오기 서버에 요청해서
      // 서버에 요청할때 하나의 함수: pram{reId, caId, }
      // state.cards = await getArticles(action.payload.regionId,state.filterCategory);
      return { ...state, filterRegion: action.payload.regionId,isLoading: true, };
    case FILTER_CATEGORY:
      // getArticles(state.filterRegion,action.payload.categoryId)
      return { ...state, filterCategory: action.payload.categoryId, isLoading: true, };

    // case FETCH_CARDS_REQUEST:
    //   return {
    //     ...state,
    //     isLoading: true,
    //   };
    case UPDATE_CARDS:
      return {
        ...state,
        isLoading: false,
        cards: action.payload.cardsData,
      };
    case FETCH_ERROR:
      return {
        ...state,
        isLoading: false,
        error: action.payload.error,
      };
    case READ_CARD:
      //TODO: fetch 해서 cardId던져서, cardId= action.payload.cardId
      //TcardDetail형식의 데이터 받아오기
      //받은 데이터를 state.readingCard에 넣고 return
      state.readingCard = {
        id: 'string',
        title: '점심 같이 드실분 점심 같이 드실분',
        curr: 1,
        total: 4,
        isClosed: false,
        categoryId: 12,
        regionId: 5,
        meetingDay: new Date('1212-01-23T12:40:50'),
        hostId: 'string',
        hostDesc: `어릴 때부터 부모님으로부터 남을 배려하고 남의 말에 귀 기울일 줄
        아는 태도를 배우며 자랐습니다. 부모님이 귀찮아하실 정도로 활달하고
        말이 많았던 어린 시절의 저에게 늘 귀를 기울여주시고 친구처럼 대화를
        해주셨습니다. 말은 하는 것도 중요하지만 듣는 것이 더욱 중요하다고
        항상 생각해 왔습니다. 특히 대인관계에서 의사소통이 잘 되려면 제일
        중요한 것이 상대방의 이야기를 잘 듣는 것에서부터 시작한다고
        생각합니다. 그래서 저는 부모님이 저에게 그렇게 하셨듯이, 사람들을
        대할 때는 상대방의 이야기를 잘 들으려고 항상 노력하였습니다. 그래서
        친구들 사이에서 저는 고민상담사로 통합니다.`,
        content: '나랑 밥먹을래 사귈래',
        location: '대전시 유성구 계룡로 92',
        locationDetail: '101-1501호',
      };
      // state.comments: [{~~~},{},{},]
      return { ...state };
    case USER_LOGIN:
      return { ...state,userLoginId:action.payload.userLoginData.userId };
    default:
      return state;
  }
}

export default reducer;
