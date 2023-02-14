import Navigate from './common/utils/navigate';
import Router from './router';
import store from './store/store';
import NotiModal from '../src/components/modals/notiModal';
import { checkLogin } from './store/actions';
class App {
  $container: HTMLElement | null;
  // loadingState: boolean;
  modalActionState: string;
  router:Router;
  navigate:Navigate;
  redirectState: string | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.router = new Router(this.$container);
    this.navigate = new Navigate(this.router);
    // this.loadingState = store.getState().isLoading;
    this.modalActionState = store.getState().modalAction;
    this.redirectState = store.getState().redirect;
    this.init();
    store.subscribe(() => {
      const newState = store.getState().modalAction;
      if (this.modalActionState !== newState) {
        this.modalActionState = newState;
        if (this.modalActionState !== '') this.setNotiModal(this.modalActionState);
      }
    });
    store.subscribe(() => {
      const newState = store.getState().redirect;
      if (this.redirectState !== newState) {
        this.redirectState = newState;
        if (this.redirectState !== null) 
          this.navigate.to(this.redirectState);
      }
    });
  }
  init = () => {
    console.log(store.getState().sessionId)
    store.dispatch(checkLogin(document.cookie));
    console.log(store.getState().sessionId)
    // const router = new Router(this.$container);
    // const navigate = new Navigate(router);


    window.addEventListener('popstate', () => {
      this.router.route();
    });
    document.querySelectorAll('a').forEach((link) => {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const path = link.getAttribute('href');
        if (path === null) return;
        this.navigate.to(path);
      });
    });
    // this.setNotiModal("NEED_LOGIN");
  };
  setNotiModal(type: string) {
    new NotiModal(this.$container, type);
  }
  // openLoadingModal(isLoading:boolean){
  //   const errorModal = new LoadingModal("error?.message")
  //   this.$container?.appendChild(errorModal.element)
  // }
}
export default App;
