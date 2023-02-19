import Card from './Card';
import store from '../../store/store';
import { Tcard } from '../../common/constants';
import Navigate from '../../common/utils/navigate';
import { fetchCardDetail } from '../../common/Fetches';
import { cloneDeep } from 'lodash';
class CardList {
  cardsState: Tcard[];
  element: HTMLDivElement;
  navigate: Navigate;
  type:string;
  constructor(navigate:Navigate,type:string) {
    this.cardsState = store.getState().cards;
    this.element = document.createElement('div');
    this.element.classList.add('card-wrapper');
    this.navigate = navigate;
    this.type = type;
    this.render();
    store.subscribe(() => {
      const newState = store.getState().cards;
      if (this.cardsState !== newState) {
        this.cardsState = newState;
        this.render();
      }
    });
  }
  render() {
    this.element.innerHTML = '';
    this.generateCards();
  }
  generateCards() {
    const state = store.getState();
    let cardDatas = cloneDeep(state.cards);
    if(this.type==='RECENT'){
      cardDatas = cardDatas.sort((a:Tcard, b:Tcard) => a.meetingDay.getTime() - b.meetingDay.getTime() );
    }
      cardDatas.forEach((cardData: Tcard) => {
      const card = new Card(cardData);
      this.element.appendChild(card.element);
      card.element.addEventListener('click', async () => {
        // this.navigate.to(`/?feed=${cardData.uuid}`)
        history.replaceState(store.getState(), "", `/?feed=${cardData.uuid}`);
        this.openCardModal();
        store.dispatch( await fetchCardDetail(cardData.uuid));
      });
    });
  }
  openCardModal() {
    const modalContainer =
      document.querySelector<HTMLElement>('#modal-container');
    modalContainer?.removeAttribute('class');
    modalContainer?.classList.add('modal-animation');
    document.body.classList.add('modal-active');
  }
}
export default CardList;
