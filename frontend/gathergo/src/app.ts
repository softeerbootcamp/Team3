import { navigate } from './common/utils/navigate';
import { $ } from './common/utils/querySelctor';
import { BASE_URL } from './common/constants';
import Router from './router';

class App {
  $container: HTMLElement | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.init();
  }
  init = () => {
    // $(".navbar")?.addEventListener("click", (e) => {
    //   const target: HTMLAnchorElement= e.target?.closest("a");
    //   if (!(target instanceof HTMLAnchorElement)) return;

    //   e.preventDefault();
    //   const targetURL = e.target.href.replace(BASE_URL, "");
    //   navigate(targetURL);
    // });

    new Router(this.$container);
  };
}
export default App;
