import {
  CHECK_LOGIN,
  FETCH_ERROR,
  FILTER_SEARCH,
  GET_COMMENT,
  POST_CARD,
  READ_CARD,
  SEND_COMMENT,
  SET_MODAL,
  SET_NAVIGATE,
  UPDATE_CARDS,
  USER_LOGIN,
  USER_LOGOUT,
  PROFILE_TAB,
  SET_PROFILE,
  CHANGE_PROFILEIMG,
  CHANGE_PROFILEINTRO,
} from './actions';
import { initialState } from '../server/initialstate';
import { Taction } from '../common/constants';

function reducer(state = initialState, action: Taction) {
  switch (action.type) {
    case SET_NAVIGATE:
      return {
        ...state,
        navigate: action.payload.navigate,
      };
    case FILTER_SEARCH:
      return {
        ...state,
        filters: action.payload.filters,
        isLoading: true,
      };
    case UPDATE_CARDS:{
      return {
        ...state,
        isLoading: false,
        cards: action.payload.cardsData,
      };
    }
    case FETCH_ERROR:
      return {
        ...state,
        isLoading: false,
        error: action.payload.error,
        modalAction: 'ERROR',
      };
    case READ_CARD:
      return {
        ...state,
        readingCard: action.payload.readingCard,
        comments: action.payload.commentsData,
      };
    case SEND_COMMENT:
      return {
        ...state,
        isLoading: true,
      };
    case GET_COMMENT:
      console.log(action.payload.commentData);
      return { ...state, comments: action.payload.commentData };
    case CHECK_LOGIN:
      console.log(action.payload.cookie);
      return { ...state, sessionId: action.payload.cookie };

    case USER_LOGIN:
      return { ...state, sessionId: action.payload.cookie, redirect: '/' };
    case USER_LOGOUT:
      return { ...state, sessionId: '', redirect: '/' };
    case SET_MODAL: {
      return {
        ...state,
        modalAction: action.payload.modalAction,
        deleteCommentuuid: action.payload.uuid,
      };
    }
    case POST_CARD:
      return {
        ...state,
        modalAction: action.payload.modalAction,
        filters: {
          regionId: 0,
          categoryId: 0,
          keyword: '',
        },
      };
    case PROFILE_TAB:
      state.tabNumber = action.payload.tabNumber;
      return { ...state, profileTab: action.payload.tabNumber };
    case SET_PROFILE:
      state.userInfo = action.payload.userInfoResponse;
      state.userInfo.profilePath =
        'https://team3-gathergo.s3.ap-northeast-2.amazonaws.com/'+action.payload.userInfoResponse.uuid+'.png';
      state.userInfo.userHostCards = action.payload.userInfoResponse.hostingArticleList;
      state.userInfo.userJoinCards = action.payload.userInfoResponse.articleList;
      return {...state}
    case CHANGE_PROFILEIMG:
      state.userInfo.profilePath = action.payload.imageSrc
      console.log(state.userInfo.profilePath)
      return {...state}
    case CHANGE_PROFILEINTRO:
      state.userInfo.introduction = action.payload.introduction;
      return {...state}
    default:
      return state;
  }
}

export default reducer;
