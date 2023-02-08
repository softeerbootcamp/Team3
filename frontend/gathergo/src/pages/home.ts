// import {store} from '../store/store';

import HeaderHome from '../components/header/headerHome';

// export const HomePage = (store:store) => {
//   // code to render the HomePage
//   // access state from store
//   // subscribe to changes in the state
//   store.getState();
//   const columnList = new ColumnList();
//     document.querySelector('body').appendChild(columnList.element);

//     const modal = new Modal();
//     document.querySelector('body').appendChild(modal.element);
// };

// import { navigate } from "../utils/navigate";
// import { $ } from "../utils/querySelector";

class Home {
  $container: HTMLElement | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.render();
  }
  setState = () => {
    this.render();
  };

  render() {
    if (!this.$container) return;
    const headerHome = new HeaderHome();
    this.$container.appendChild(headerHome.element);

    // console.log(window.location.href);
  }
}

export default Home;
