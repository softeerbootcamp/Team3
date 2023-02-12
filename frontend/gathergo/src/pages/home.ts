// import {store} from '../store/store';

import Navigate from '../common/utils/navigate';
import CardList from '../components/card/CardList';
import CardModal from '../components/card/CardModal';
import HeaderHome from '../components/header/HeaderHome';
import { readCard } from '../store/actions';
import store from '../store/store';

class Home {
  $container: HTMLElement | null;
  navigate: Navigate;
  constructor($container: HTMLElement | null, navigate: Navigate) {
    this.$container = $container;
    this.navigate = navigate;
    this.render();
  }

  render() {
    if (!this.$container) return;
    const headerHome = new HeaderHome();
    this.$container.appendChild(headerHome.element);

    // console.log(window.location.href);
    const cardList = new CardList(this.navigate);
    this.$container.appendChild(cardList.element);

    const modal = new CardModal();
    this.$container.appendChild(modal.element);
    this.closeModalEvent(modal.element);

    
    const queryString = new URLSearchParams(window.location.search);
    const feed = queryString.get('feed');
    if (feed) {
      cardList.openCardModal();
      store.dispatch(readCard(feed));
    }
  }
  closeModalEvent(modalEle: HTMLElement) {
    modalEle.addEventListener('click', (e) => {
      const target = e.target as Element;
      if (target.id === 'modal-background') this.closeModal(modalEle);
      const closeIcon = target.closest('#modal-close-icon');
      if (closeIcon) this.closeModal(modalEle);
    });
  }

  closeModal(modalContainer: HTMLElement) {
    modalContainer.classList.add('out');
    document.body?.removeAttribute('class');
    this.navigate.to('/')
  }
}

export default Home;
