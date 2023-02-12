import { routes } from './common/constants';
import Navigate from './common/utils/navigate';
import NotFound from './pages/notfound';

class Router {
  $container: HTMLElement | null;
  currentPage: HTMLElement | undefined;
  navigate: Navigate;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.currentPage = undefined;
    this.navigate = new Navigate(this);
    this.route();
  }
  route() {
    if(this.$container === null) return

    this.$container.innerHTML = '';
    const TargetPage = this.findMatchedRoute()?.element || NotFound;
    new TargetPage(this.$container,this.navigate);
  }
  findMatchedRoute() {
    return routes.find((route) => route.path.test(location.pathname));
  }
}
export default Router;
