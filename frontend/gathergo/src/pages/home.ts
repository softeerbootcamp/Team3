// import {store} from '../store/store';

import CardList from '../components/card/CardList';
import CardModal from '../components/card/CardModal';
import HeaderHome from '../components/header/HeaderHome';

class Home {
  $container: HTMLElement | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.render();
  }
  //   setState = () => {
  //     this.render();
  //   };

  render() {
    if (!this.$container) return;
    const headerHome = new HeaderHome();
    this.$container.appendChild(headerHome.element);

    // console.log(window.location.href);
    const cardList = new CardList();
    this.$container.appendChild(cardList.element);

    const modal = new CardModal();
    this.$container.appendChild(modal.element);
    this.closeModalEvent(modal.element);
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
    // document.body.classList.remove('modal-active');
  }
}

export default Home;
