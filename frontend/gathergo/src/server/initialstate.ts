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
  modalAction: '',
  sessionId: '',
  redirect: null,
  deleteCommentuuid: '',
  tabNumber : 0,
  userInfo: 
    {
      userName: 'testUser',
      email: 'hyundai@naver.com',
      profileImg: '',
      userDesc: '반갑습니다',
      userId: 'hyundai',
      userHostCards: [
        {
          id: '1',
          title: '점심 같이 드실분 점심 같이 드실분',
          curr: 1,
          total: 4,
          isClosed: false,
          meetingDay: new Date(),
        },
      ],
      userJoinCards: [
        {
          id: '4',
          title: '대전 유성국 독서모임',
          curr: 3,
          total: 4,
          isClosed: false,
          meetingDay: new Date(),
          regionId: 9,
          categoryId: 6,
        },
        {
          id: '6',
          title: '모각코',
          curr: 9,
          total: 49,
          isClosed: false,
          meetingDay: new Date(),
          regionId: 10,
          categoryId: 4,
        },
      ],
    },
  
};
