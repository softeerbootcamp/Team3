
import { fetchCardDetail, getArticles } from '../common/Fetches';
import Navigate from '../common/utils/navigate';
import CardList from '../components/card/CardList';
import CardModal from '../components/card/CardModal';
import HeaderHome from '../components/header/HeaderHome';
import store from '../store/store';

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

    // console.log(window.location.href);
    store.dispatch(await getArticles(store.getState().filters))
    const cardList = new CardList(this.navigate);
    this.$container.appendChild(cardList.element);

    const modal = new CardModal();
    this.$container.appendChild(modal.element);
    this.closeModalEvent(modal.element);

    
    const queryString = new URLSearchParams(window.location.search);
    const feed = queryString.get('feed');
    if (feed) {
      cardList.openCardModal();
      store.dispatch( await fetchCardDetail(feed));
    }

    this.matchSearchBarValue();
    this.keywordSearchEvent();
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

  matchSearchBarValue(){
    const categorySidebar = document.querySelector('.keyword-input') as HTMLInputElement;
    const hideSidebar = document.querySelector('.keyword-input-sticky') as HTMLInputElement;
    
    categorySidebar?.addEventListener('keyup',(e)=>{
      
      hideSidebar.value = categorySidebar.value;
      if(e.key==='Enter') this.keywordSearch();
    })
    hideSidebar?.addEventListener('keyup',(e)=>{
      categorySidebar.value = hideSidebar.value;
      if(e.key==='Enter') this.keywordSearch();
    })
  }
  keywordSearchEvent(){
    
    const searchBtns = document.querySelectorAll('.keyword-search-btn');
    searchBtns.forEach((btn) => {
        btn.addEventListener('click',()=>{
          this.keywordSearch();
        })
    });
  }
  async keywordSearch(){
    let keywrodValue = document.querySelector<HTMLInputElement>('.keyword-input')?.value;
    if( !keywrodValue) keywrodValue=""
    store.dispatch(await getArticles({...store.getState().filters, keyword:keywrodValue}))

    console.log('ljlkjlk')
  }
}

export default Home;
