import { fetchCardDetail, getArticles } from '../common/Fetches';
import Navigate from '../common/utils/navigate';
import CardList from '../components/card/CardList';
import CardModal from '../components/card/CardModal';
import HeaderHome from '../components/header/HeaderHome';
import TabHome from '../components/tabs/tabHome';
import TabContent from '../components/tabs/tabContent';
import store from '../store/store';
import Fba from '../components/fba/fba';
class Home {
  $container: HTMLElement | null;
  navigate: Navigate;
  constructor($container: HTMLElement | null, navigate: Navigate) {
    this.$container = $container;
    this.navigate = navigate;
    this.render();
  }

  async render() {
    if (!this.$container) return;
    const headerHome = new HeaderHome();
    this.$container.appendChild(headerHome.element);


    store.dispatch(await getArticles(store.getState().filters));
    const cardList = new CardList(this.navigate,'NEW');
    // this.$container.appendChild(cardList.element);
    const cardList2 = new CardList(this.navigate,'RECENT');
    // this.$container.appendChild(cardList2.element);
    const tabContent = new TabContent(cardList.element,cardList2.element);
    // this.$container.appendChild(tabContent.element);

    const homeTab = new TabHome(tabContent);
    this.$container.appendChild(homeTab.element);
    this.$container.appendChild(homeTab.tabContent.element);

    const modal = new CardModal();
    this.$container.appendChild(modal.element);
    this.closeModalEvent(modal.element);

    const queryString = new URLSearchParams(window.location.search);
    const feed = queryString.get('feed');
    if (feed) {
      cardList.openCardModal();
      store.dispatch(await fetchCardDetail(feed));
    }

    this.matchSearchBarValue();
    this.keywordSearchEvent();
    this.setFBA();
    this.openSidebarEvent();
  }
  closeModalEvent(modalEle: HTMLElement) {
    modalEle.addEventListener('click', (e) => {
      const target = e.target as Element;
      if (target.id === 'modal-background') this.closeModal(modalEle);
      const closeIcon = target.closest('#modal-close-icon');
      if (closeIcon) this.closeModal(modalEle);
    });

    window.addEventListener('popstate', () => {
      // if(!modalEle.classList.contains('out'))
      this.closeModal(modalEle);
    });
  }

  closeModal(modalContainer: HTMLElement) {
    modalContainer.classList.add('out');
    document.body?.removeAttribute('class');
    // this.navigate.to('/')
    history.replaceState(store.getState(), '', '/');
  }

  matchSearchBarValue() {
    const categorySidebar = document.querySelector(
      '.keyword-input'
    ) as HTMLInputElement;
    const hideSidebar = document.querySelector(
      '.keyword-input-sticky'
    ) as HTMLInputElement;

    categorySidebar?.addEventListener('keyup', (e) => {
      hideSidebar.value = categorySidebar.value;
      if (e.key === 'Enter') this.keywordSearch();
    });
    hideSidebar?.addEventListener('keyup', (e) => {
      categorySidebar.value = hideSidebar.value;
      if (e.key === 'Enter') this.keywordSearch();
    });
  }
  keywordSearchEvent() {
    const searchBtns = document.querySelectorAll('.keyword-search-btn');
    searchBtns.forEach((btn) => {
      btn.addEventListener('click', () => {
        this.keywordSearch();
      });
    });
  }
  async keywordSearch() {
    let keywrodValue =
      document.querySelector<HTMLInputElement>('.keyword-input')?.value;
    if (!keywrodValue) keywrodValue = '';
    store.dispatch(
      await getArticles({ ...store.getState().filters, keyword: keywrodValue })
    );
  }
  setFBA() {
    new Fba(this.$container);
  }
  openSidebarEvent() {
    const alarm = document.querySelector('#sidebar-tigger');
    console.log(alarm);

    document
      .querySelector<HTMLElement>('#sidebar-tigger')
      ?.addEventListener('click', () => {
        this.openSidebar();
      });
  }
  openSidebar() {
    document.querySelector('#alarm-sidebar')?.classList.add('active');
    document.querySelector('.sidebar-container')?.classList.add('active');
    document.body.classList.add('modal-active');
  }
}

export default Home;
