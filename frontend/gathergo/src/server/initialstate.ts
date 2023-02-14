import { TinitialState } from '../common/constants';

export const initialState: TinitialState = {
  cards: [
    {
      id: '1',
      title: '점심 같이 드실분 점심 같이 드실분',
      curr: 1,
      total: 4,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 5,
      categoryId: 18,
    },
    {
      id: '3',
      title: '강남구 축구부원 모집합니다.',
      curr: 1,
      total: 99,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 1,
      categoryId: 1,
    },
    {
      id: '4',
      title: '대전 유성국 독서모임',
      curr: 3,
      total: 4,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 9,
      categoryId: 6,
    },
    {
      id: '6',
      title: '모각코',
      curr: 9,
      total: 49,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 10,
      categoryId: 4,
    },
    {
      id: '9',
      title: '미술관 같이 갈 예쁜 여성분 구해요',
      curr: 1,
      total: 2,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 10,
      categoryId: 6,
    },
    {
      id: '10',
      title: '아침 운동 할사람',
      curr: 1,
      total: 2,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 17,
      categoryId: 2,
    },
    {
      id: '11',
      title: '예수님의 사랑을 느껴보아요.-온누리교회',
      curr: 1,
      total: 2,
      isClosed: false,
      meetingDay: new Date('1212-01-23T12:40:50'),
      regionId: 1,
      categoryId: 11,
    },
  ],
  readingCard: null,
  comments: [
    {
      uuid: '100001',
      userId: '101',
      date: '3일전',
      content: '바람도 서롤 감싸게 했죠',
    },
    {
      uuid: '100002',
      userId: '103',
      date: '3일전',
      content: '햇살은 우릴 위해 내리고',
    },
    {
      uuid: '100003',
      userId: '101',
      date: '3일전',
      content: '우리 웃음속에 계절은 오고 또 갔죠',
    },
    {
      uuid: '100004',
      userId: '103',
      date: '3일전',
      content: '바람에 흔들리는 머릿결',
    },
    {
      uuid: '100005',
      userId: '105',
      date: '3일전',
      content: '내게 불어오는 그대 향기',
    },
    {
      uuid: '100006',
      userId: '103',
      date: '6일전',
      content: '예쁜 두눈도 웃음 소리도',
    },
    {
      uuid: '100007',
      userId: '105',
      date: '8일전',
      content: '모두 다 내 것이었죠',
    },
    {
      uuid: '100008',
      userId: '103',
      date: '10일전',
      content: '이런 사랑 이런 행복 쉽다 했었죠',
    },
    {
      uuid: '100009',
      userId: '105',
      date: '12일전',
      content: '이런 웃음 이런 축복 내게 쉽게 올리 없죠',
    },
    {
      uuid: '100010',
      userId: '103',
      date: '122일전',
      content: '눈물 조차 울음조차 닦지 못한 나',
    },
    {
      uuid: '100010',
      userId: '103',
      date: '122일전',
      content: '눈물 조차 울음조차 닦지 못한 나',
    },
    {
      uuid: '100010',
      userId: '103',
      date: '122일전',
      content: '눈물 조차 울음조차 닦지 못한 나',
    },
    {
      uuid: '100010',
      userId: '103',
      date: '122일전',
      content: '눈물 조차 울음조차 닦지 못한 나',
    },
    {
      uuid: '100010',
      userId: '103',
      date: '122일전',
      content: '눈물 조차 울음조차 닦지 못한 나',
    },
  ],
  filterRegion: 0,
  filterCategory: 0,
  isLoading: false,
  // userLoginId: null,
  error: null,
  modalAction: '',
  sessionId: '',
  redirect: null,
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
          meetingDay: new Date('1212-01-23T12:40:50'),
        },
      ],
      userJoinCards: [
        {
          id: '4',
          title: '대전 유성국 독서모임',
          curr: 3,
          total: 4,
          isClosed: false,
          meetingDay: new Date('1212-01-23T12:40:50'),
          regionId: 9,
          categoryId: 6,
        },
        {
          id: '6',
          title: '모각코',
          curr: 9,
          total: 49,
          isClosed: false,
          meetingDay: new Date('1212-01-23T12:40:50'),
          regionId: 10,
          categoryId: 4,
        },
      ],
    },
  
};
