import Navigate from './common/utils/navigate';
import Router from './router';
import store from './store/store';
import NotiModal from '../src/components/modals/notiModal';
import { checkLogin } from './store/actions';
class App {
  $container: HTMLElement | null;
  modalActionState: string;
  router: Router;
  navigate: Navigate;
  redirectState: string | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.router = new Router(this.$container);
    this.navigate = new Navigate(this.router);
    this.modalActionState = store.getState().modalAction;
    this.redirectState = store.getState().redirect;
    this.init();
    store.subscribe(() => {
      const newState = store.getState().modalAction;
      if (this.modalActionState !== newState) {
        this.modalActionState = newState;
        if (this.modalActionState !== '')
          this.setNotiModal(this.modalActionState);
      }
    });
    store.subscribe(() => {
      const newState = store.getState().redirect;
      if (this.redirectState !== newState) {
        this.redirectState = newState;
        if (this.redirectState !== null) this.navigate.to(this.redirectState);
      }
    });
  }
  init = () => {
    store.dispatch(checkLogin(document.cookie));
    window.addEventListener('popstate', (e) => {
      if (document.body.classList.contains('modal-active')) {
        e.preventDefault();
      } else {
        this.router.route();
      }
    });
    document.querySelectorAll('a').forEach((link) => {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const path = link.getAttribute('href');
        if (path === null) return;
        this.navigate.to(path);
      });
    });
  };
  setNotiModal(type: string) {
    new NotiModal(this.$container, this.navigate, type);
  }
}
export default App;
