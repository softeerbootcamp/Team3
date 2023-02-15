import Home from '../pages/home';
import Profile from '../pages/profile';
import Login from '../pages/login';
import Post from '../pages/post';
// import Navigate from './utils/navigate';

const BASE_URL = 'http://localhost:5173';

const routes = [
  { path: /^\/$/, element: Home },
  { path: /^\/profile$/, element: Profile },
  { path: /^\/login$/, element: Login },
  { path: /^\/post$/, element: Post },
];
type Taction = {
  type: string;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  payload: any;
};
type TdropDown = {
  [key: number]: string;
};
type Tfilters = {
  regionId: number,
  categoryId: number,
  keyword: string,
}
type Tcard = {
  uuid: string;
  title: string;
  curr: number;
  total: number;
  isClosed: boolean;
  categoryId: number;
  regionId: number;
  meetingDay: Date;
};
type TcardDetail =
  | (Tcard & {
      hostId: string;
      hostDesc: string;
      hostProfile:string;
      content: string;
      location: string;
      locationDetail: string;
      hasJoined:boolean;
      isHost:boolean;
    })
  | null;
type Tcomment = {
  uuid: string|undefined;
  userId: string;
  content: string;
  date: string;
  isMyComment:boolean;
};

type TinitialState = {
  cards: Tcard[];
  readingCard: TcardDetail;
  comments: Tcomment[];
  filters: Tfilters;
  isLoading: boolean;
  // userLoginId: string|null;
  sessionId: string;
  error: Error|null;
  modalAction: string;
  redirect: string| null;
};
type TsignupData= {
  userId: string;
  userName: string;
  password: string;
  email: string;
}
type TloginData= {
  userId: string;
  password: string;
}
const regionSi: TdropDown = {
  1: '서울특별시',
  2: '부산광역시',
  3: '대구광역시',
  4: '인천광역시',
  5: '광주광역시',
  6: '대전광역시',
  7: '울산광역시',
  8: '세종특별자치시',
  9: '경기도',
  10: '강원도',
  11: '충청북도',
  12: '충청남도',
  13: '전라북도',
  14: '전라남도',
  15: '경상북도',
  16: '경상남도',
  17: '제주특별자치도',
    0: '지역을 선택하세요',
};
const category: TdropDown = {
  1: '아웃도어 / 여행',
  2: '운동 / 스포츠',
  3: '인문학 / 책/ 글',
  4: '업종 / 직무',
  5: '외국 / 언어',
  6: '문화 / 공연 / 축제',
  7: '음악 / 악기',
  8: '패션 / 뷰티',
  9: '공예 / 만들기',
  10: '댄스 / 무용',
  11: '봉사활동',
  12: '사교 / 인맥',
  13: '차 / 오토바이',
  14: '사진 / 영상',
  15: '야구관람',
  16: '게임 / 오락',
  17: '요리 / 제조',
  18: '반려동물',
  19: '자유주제',
    0: '카테고리를 선택하세요',
};

export {
  BASE_URL,
  routes,
  regionSi,
  category,
  Tfilters,
  Tcard,
  TcardDetail,
  Tcomment,
  TinitialState,
  TsignupData,
  TloginData,
  Taction
};
