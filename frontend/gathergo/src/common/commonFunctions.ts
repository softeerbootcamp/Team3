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
// Check if the user is logged in
export function checkLogin() {
  return !!sessionStorage.getItem('isLoggedIn');
}

// Log the user in
export function logInSuccess() {
  sessionStorage.setItem('isLoggedIn', 'true');
}

// Log the user out
export function logOut() {
  sessionStorage.removeItem('isLoggedIn');
}