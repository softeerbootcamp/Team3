const INITIALSTATE = {
  USER: [
    {
      ID: 101, // ID
      EMAIL: 'hyundai123@naver.com', // 이메일
      INTRODUCE: '안녕 반가워요', // 한 줄 소개
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 101, // ID
      EMAIL: 'hyundai999@naver.com', // 이메일
      INTRODUCE: 'ㅎㅇㅎㅇㅎㅇ', // 한 줄 소개
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 103, // ID
      EMAIL: 'hyndaicar97@gmail.com',
      INTRODUCE: '한줄 소개 입니다',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 104,
      EMAIL: 'hyundai99@daum.net',
      INTRODUCE: 'ㅎㅇㅎㅇㅎㅇ',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 105,
      EMAIL: 'devops@kakao.com',
      INTRODUCE: '남자 입니다',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 106,
      EMAIL: 'frontback@naver.com',
      INTRODUCE: '안녕하세요',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 107,
      EMAIL: 'hyundaiAuto@naver.com',
      INTRODUCE: '잘 부탁드립니다.',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 108,
      EMAIL: '42dot@naver.com',
      INTRODUCE: '처음뵙겠습니다.',
      PROFILE: 'localhost:5173/src',
    },
    {
      ID: 109,
      EMAIL: 'kia979@naver.com',
      INTRODUCE: '안녕!',
      PROFILE: 'localhost:5173/src',
    },
  ],
  ARTICLE: [
    {
      ID: 1,
      HOSTID: 101,
      TITLE: '점심 같이 드실분',
      THUMBNAIL: 'http://example.com/images/image1.jpg',
      CURR: 1,
      TOTAL: 4,
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 서초구',
      CATEGROY: '식사',
      COMMENTLIST: [100001,100002,100003,100004,100005,100006],
    },
    {
      ID: 2,
      HOSTID: 101,
      TITLE: '같이 노래들으실 분',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 1, //현재인원
      TOTAL: 2, // 총원
      ISCLOSED: true,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 구로구',
      CATEGROY: '음악',
      COMMENTLIST: [100007,100008,100009,100010],
    },
    {
      ID: 3,
      HOSTID: 102,
      TITLE: '야구 같이 보실분',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 2, //현재인원
      TOTAL: 4, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '대구시 수성구',
      CATEGROY: '야구관람',
      COMMENTLIST: [],
    },
    {
      ID: 4,
      HOSTID: 101,
      TITLE: '헌혈합니다',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 1, //현재인원
      TOTAL: 2, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 강남구',
      CATEGROY: '봉사활동',
      COMMENTLIST: [],
    },
    {
      ID: 5,
      HOSTID: 103,
      TITLE: '서점 가서 책 사실분',
      THUMBNAIL: 'http://example.com/images/image3.jpg',
      CURR: 3, //현재인원
      TOTAL: 6, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 용산구',
      CATEGROY: '인문학/책/글',
      COMMENTLIST: [],
    },
    {
      ID: 6,
      HOSTID: 101,
      TITLE: '영어 공부해요',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 2, //현재인원
      TOTAL: 3, // 총원
      ISCLOSED: true,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 강남구',
      CATEGROY: '언어',
      COMMENTLIST: [],
    },
    {
      ID: 7,
      HOSTID: 105,
      TITLE: '롤팟 구함',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 3, //현재인원
      TOTAL: 5, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 관악구',
      CATEGROY: '게임/오락',
      COMMENTLIST: [],
    },
    {
      ID: 8,
      HOSTID: 102,
      TITLE: '사진찍기',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 2, //현재인원
      TOTAL: 4, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 강동구',
      CATEGROY: '사진/영상',
      COMMENTLIST: [],
    },
    {
      ID: 9,
      HOSTID: 102,
      TITLE: '별 보러가요',
      THUMBNAIL: 'http://example.com/images/image2.jpg',
      CURR: 3, //현재인원
      TOTAL: 10, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '강원도 영월군',
      CATEGROY: '아웃도어/여행',
      COMMENTLIST: [],
    },
    {
      ID: 10,
      HOSTID: 107,
      TITLE: '달걀말이 가르쳐 줄 사람',
      THUMBNAIL: 'http://example.com/images/image4.jpg',
      CURR: 1, //현재인원
      TOTAL: 3, // 총원
      ISCLOSED: false,
      MEETINGDAY: "YYYY-MM-DD'T'hh:mm:ss",
      REGION: '서울시 노원구',
      CATEGROY: '요리',
      COMMENTLIST: [],
    },
  ],
  COMMENT: [
    {
      ID: 100001,
      ARTICLEID: 1,
      USERID: 101,
      DATE: new Date(),
      CONTENT: '바람도 서롤 감싸게 했죠',
    },
    {
      ID: 100002,
      ARTICLEID: 1,
      USERID: 103,
      DATE: new Date(),
      CONTENT: '햇살은 우릴 위해 내리고',
    },
    {
      ID: 100003,
      ARTICLEID: 1,
      USERID: 101,
      DATE: new Date(),
      CONTENT: '우리 웃음속에 계절은 오고 또 갔죠',
    },
    {
      ID: 100004,
      ARTICLEID: 1,
      USERID: 103,
      DATE: new Date(),
      CONTENT: '바람에 흔들리는 머릿결',
    },
    {
      ID: 100005,
      ARTICLEID: 1,
      USERID: 105,
      DATE: new Date(),
      CONTENT: '내게 불어오는 그대 향기',
    },
    {
      ID: 100006,
      ARTICLEID: 1,
      USERID: 103,
      DATE: new Date(),
      CONTENT: '예쁜 두눈도 웃음 소리도',
    },
    {
      ID: 100007,
      ARTICLEID: 2,
      USERID: 105,
      DATE: new Date(),
      CONTENT: '모두 다 내 것이었죠',
    },
    {
      ID: 100008,
      ARTICLEID: 2,
      USERID: 103,
      DATE: new Date(),
      CONTENT: '이런 사랑 이런 행복 쉽다 했었죠',
    },
    {
      ID: 100009,
      ARTICLEID: 2,
      USERID: 105,
      DATE: new Date(),
      CONTENT: '이런 웃음 이런 축복 내게 쉽게 올리 없죠',
    },
    {
      ID: 100010,
      ARTICLEID: 2,
      USERID: 103,
      DATE: new Date(),
      CONTENT: '눈물 조차 울음조차 닦지 못한 나',
    },
  ],
};