import { FILTER_REGION } from './actions';
import { initialState } from '../server/initialstate';
// import {
//   postColumn,
//   postLog,
//   patchColumnTitle,
//   patchCardList,
//   deleteColumn,
// } from '../../server/server.js';

type action = {
  type: string;
  payload: any;
};
function reducer(state = initialState, action: action) {
  let index;
  let logData;
  let colData;
  switch (action.type) {
    case FILTER_REGION:
      return { ...state };
    default:
      return state;
  }
}

export default reducer;
