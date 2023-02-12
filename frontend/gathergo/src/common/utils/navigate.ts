// export const navigate = (to: string, isReplace = false) => {
//   const historyChangeEvent = new CustomEvent('historychange', {
//     detail: {
//       to,
//       isReplace,
//     },
//   });

import Router from "../../router";

//   dispatchEvent(historyChangeEvent);
// };

class Navigate {
  router: Router;
  constructor(router: Router) {
    this.router = router;
  }
  to(path: string) {
    history.pushState({}, '', path);
    this.router.route();
  }
}
export default Navigate;
// type Navigate = (path: string) => void;
// navigate: Navigate = (path) => {
//   history.pushState({}, '', path);
//   this.route();
// };