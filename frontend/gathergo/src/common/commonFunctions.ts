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
export function getKeyByValue(object:any, value:any) {
  return Object.keys(object).find(key => object[key] === value);
}