import Navigate from './common/utils/navigate';
import Router from './router';

class App {
  $container: HTMLElement | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.init();
    console.log(window.sessionStorage)
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
  };
}
export default App;
