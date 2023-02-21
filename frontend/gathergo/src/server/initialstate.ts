import { TinitialState } from '../common/constants';

export const initialState: TinitialState = {
  cards: [],
  readingCard: null,
  comments: [],
  filters: {
    regionId: 0,
    categoryId: 0,
    keyword: '',
  },
  isLoading: false,
  error: null,
  modalAction: '',
  sessionId: '',
  redirect: null,
  deleteCommentuuid: '',
  tabNumber: 0,
  notice: [],
  userInfo: {
    userName: '',
    email: '',
    profilePath: '',
    introduction: '',
    userId: '',
    uuid: '',
    userHostCards: [],
    userJoinCards: [],
  },
};
