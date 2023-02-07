export function getElementIndex(ele: Element) {
  if (!ele) return;
  let _i = 0;
  while (
    ele.previousElementSibling != null &&
    (ele = ele.previousElementSibling) != null
  ) {
    _i++;
  }
  return _i;
}
