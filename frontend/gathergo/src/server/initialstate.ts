import { TinitialState } from '../common/constants';

export const initialState: TinitialState = {
  // navigate: null,
  cards: [],
  readingCard: null,
  comments:[],
  filters:{
    regionId: 0,
    categoryId: 0,
    keyword: "",
  },
  isLoading: false,
  error: null,
  modalAction: "",
  sessionId: '',
  redirect:null,

};
