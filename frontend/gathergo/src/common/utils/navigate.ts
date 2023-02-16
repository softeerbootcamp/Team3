
import Router from "../../router";
import store from "../../store/store";

class Navigate {
  router: Router;
  constructor(router: Router) {
    this.router = router;
  }
  to(path: string) {
    history.pushState(store.getState(), '', path);
    this.router.route();
  }
}
export default Navigate;