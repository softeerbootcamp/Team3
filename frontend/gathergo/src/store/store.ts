import { createStore, combineReducers } from 'redux';
// import { cardsReducer } from './reducers/cards';
// import { userReducer } from './reducers/user';
// import { filterReducer } from './reducers/filter';
// import { commentsReducer } from './reducers/comments';

const rootReducer = combineReducers({
  //   cards: cardsReducer,
  //   user: userReducer,
  //   filter: filterReducer,
  //   comments: commentsReducer,
});

const store = createStore(rootReducer);

export { store };
