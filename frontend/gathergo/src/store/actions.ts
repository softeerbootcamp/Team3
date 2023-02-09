export const FILTER_REGION = 'FILTER_REGION';

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
