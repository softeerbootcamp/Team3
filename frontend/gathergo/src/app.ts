import Navigate from './common/utils/navigate';
import ErrorModal from './components/modals/errorModal';
import Router from './router';
import store from './store/store';

class App {
  $container: HTMLElement | null;
  errorState: Error|null
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.errorState = store.getState().error;
    this.init();
    store.subscribe(() => {
      const newState = store.getState().error;
      if (this.errorState !== newState) {
        this.errorState = newState;
       this.openErrorModal(this.errorState);}
    });
  }
  init = () => {
    const router = new Router(this.$container);
    const navigate = new Navigate(router);
    window.addEventListener('popstate', () => {
      router.route();
    });
    document.querySelectorAll('a').forEach(link => {
      link.addEventListener('click', e => {
        e.preventDefault();
        const path = link.getAttribute('href');
        if(path===null) return
        navigate.to(path);
      });
    });
    // this.setErrorModal();
  };
  // setErrorModal(){
  //   const errorModal = document.createElement('div')
  //   this.$container?.appendChild(errorModal)
  // }
  openErrorModal(error: Error|null){
    const errorModal = new ErrorModal(error?.message)
    this.$container?.appendChild(errorModal.element)
  }
}
export default App;
