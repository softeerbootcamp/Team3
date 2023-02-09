import Card from './Card';
import store from '../../store/store';
import { readCard } from '../../store/actions';
import { Tcard } from '../../common/constants';
class CardList {
  element: HTMLDivElement;
  cardsState: Tcard[];
  constructor() {
    this.cardsState = store.getState().cards;
    this.element = document.createElement('div');
    this.element.classList.add('card-wrapper');
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
      card.element.addEventListener('click', () => {
        this.openCardModal();
        store.dispatch(readCard(cardData.id));
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
  //TODO: card 클릭시 이벤트, state.redadingFeed = cardData
  //구독으로 인해 modal이 render 될때, modal에 클래스에 show 추가,
  // 생성할때는 아예엘리멘트 안만들기
}
export default CardList;
