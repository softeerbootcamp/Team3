export function getElementIndex(element: Element) {
  let index = 0;
  let sibling = element.previousElementSibling;
  while (sibling) {
    index++;
    sibling = sibling.previousElementSibling;
  }
  return index;
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function getKeyByValue(object: any, value: any) {
  return Object.keys(object).find((key) => object[key] === value);
}

export function timeDiff(date: Date) {
  const now = new Date();
  const timeDiffMin = Math.floor((now.getTime() - date.getTime()) / 1000 / 60);
  if (timeDiffMin < 1) return '방금 전';
  if (timeDiffMin < 60) {
    return `${timeDiffMin}분 전`;
  }
  const timeDiffHour = Math.floor(timeDiffMin / 60);
  if (timeDiffHour < 24) {
    return `${timeDiffHour}시간 전`;
  }
  const timeDiffDay = Math.floor(timeDiffHour / 24);
  if (timeDiffDay < 365) {
    return `${timeDiffDay}일 전`;
  }
  return `${Math.floor(timeDiffDay / 365)}년 전`;
}
export function getKoreanTimeString(time = new Date()) {
  const koreaTime = new Date(time.getTime() + 540 * 60000);
  return koreaTime.toISOString();
}
