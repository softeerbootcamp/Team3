import { FILTER_REGION, READ_CARD } from './actions';
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
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  payload: any;
};
function reducer(state = initialState, action: action) {
  switch (action.type) {
    case FILTER_REGION:
      return { ...state };
    case READ_CARD:
      //TODO: fetch 해서 cardId던져서, cardId= action.payload.cardId
      //TcardDetail형식의 데이터 받아오기
      //받은 데이터를 state.readingCard에 넣고 return
      state.readingCard = {
        id: 'string',
        title: '점심 같이 드실분 점심 같이 드실분',
        curr: 1,
        total: 4,
        isClosed: false,
        categoryId: 12,
        regionId: 5,
        meetingDay: new Date('1212-01-23T12:40:50'),
        hostId: 'string',
        hostDesc: `어릴 때부터 부모님으로부터 남을 배려하고 남의 말에 귀 기울일 줄
        아는 태도를 배우며 자랐습니다. 부모님이 귀찮아하실 정도로 활달하고
        말이 많았던 어린 시절의 저에게 늘 귀를 기울여주시고 친구처럼 대화를
        해주셨습니다. 말은 하는 것도 중요하지만 듣는 것이 더욱 중요하다고
        항상 생각해 왔습니다. 특히 대인관계에서 의사소통이 잘 되려면 제일
        중요한 것이 상대방의 이야기를 잘 듣는 것에서부터 시작한다고
        생각합니다. 그래서 저는 부모님이 저에게 그렇게 하셨듯이, 사람들을
        대할 때는 상대방의 이야기를 잘 들으려고 항상 노력하였습니다. 그래서
        친구들 사이에서 저는 고민상담사로 통합니다.`,
        content: '나랑 밥먹을래 사귈래',
        location: '서울 강남구 강남대로62길 23',
        locationDetail: '4층 코드스쿼드',
        // comments: [],
      };
      return { ...state };
    default:
      return state;
  }
}

export default reducer;
