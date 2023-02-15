import {
  CHECK_LOGIN,
  FETCH_ERROR,
  FILTER_SEARCH,
  GET_COMMENT,
  READ_CARD,
  SEND_COMMENT,
  SET_MODAL,
  UPDATE_CARDS,
  USER_LOGIN,
  USER_LOGOUT,
} from './actions';
import { initialState } from '../server/initialstate';
import { Taction } from '../common/constants';

function reducer(state = initialState, action: Taction) {

  switch (action.type) {
      case FILTER_SEARCH:
        return {
          ...state,
          filters: action.payload.filters,
          isLoading: true,
        };
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
        modalAction: 'action.payload.modalAction',
      };
    case READ_CARD:
      return { ...state, readingCard: action.payload.readingCard, comments:action.payload.commentsData };
    case SEND_COMMENT:
      return {
        ...state,
        isLoading: true,
      };
    case GET_COMMENT:
      console.log(action.payload.commentData)
      return { ...state, comments: action.payload.commentData};
    case CHECK_LOGIN:
      console.log( action.payload.cookie)
      return { ...state, sessionId: action.payload.cookie };

    case USER_LOGIN:
      return { ...state, sessionId: action.payload.cookie, redirect:'/' };
    case USER_LOGOUT:
      //TODO: 쿠키의 세션id 제거
      //home('/')으로 redirect하기
      return { ...state, sessionId: '' };
    case SET_MODAL:
      return { ...state, modalAction: action.payload.modalAction };
    default:
      return state;
  }
}

export default reducer;
