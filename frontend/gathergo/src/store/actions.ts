// import { TcardDetail } from '../common/constants';

export const FILTER_REGION = 'FILTER_REGION';
export const READ_CARD = 'READ_CARD';

export function fiterRegion(/*form, columnId = null, cardId = null*/) {
  return {
    type: FILTER_REGION,
    payload: {
      // form,
      // columnId,
      // cardId,
    },
  };
}

export function readCard(cardId: string) {
  // 모달창 띄워주기
  return {
    type: READ_CARD,
    payload: cardId,
  };
}
