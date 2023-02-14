import Card from './Card';
import store from '../../store/store';
import { Tcard } from '../../common/constants';
import Navigate from '../../common/utils/navigate';
import { fetchCardDetail } from '../../common/Fetches';
class CardList {
  cardsState: Tcard[];
  element: HTMLDivElement;
  navigate: Navigate;
  constructor(navigate:Navigate) {
    this.cardsState = store.getState().cards;
    this.element = document.createElement('div');
    this.element.classList.add('card-wrapper');
    this.navigate = navigate;
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
    state.cards.forEach((cardData: Tcard) => {
      const card = new Card(cardData);
      this.element.appendChild(card.element);
      card.element.addEventListener('click', async () => {
        this.navigate.to(`/?feed=${cardData.uuid}`)
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
