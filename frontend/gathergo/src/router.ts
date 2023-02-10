import { routes } from './common/constants';
import NotFound from './pages/notfound';

class Router {
  $container: HTMLElement | null;
  currentPage: HTMLElement | undefined;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.currentPage = undefined;
    this.init();
    this.route();
  }
  init() {
    // window.addEventListener('historychange', ({ detail }) => {
    //   const { to, isReplace } = detail;
    //   if (isReplace || to === location.pathname)
    //     history.replaceState(null, '', to);
    //   else history.pushState(null, '', to);
    //   this.route();
    // });
    // window.addEventListener('popstate', () => {
    //   this.route();
    // });
  }
  route() {
    const TargetPage = this.findMatchedRoute()?.element || NotFound;
    // console.log(TargetPage);
    //this.currentPage = new
    new //this.currentPage = new
    TargetPage(this.$container);
  }
  findMatchedRoute() {
    return routes.find((route) => route.path.test(location.pathname));
  }
}
export default Router;
