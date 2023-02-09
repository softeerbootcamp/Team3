import Card from './Card';
import store from '../../store/store';
class CardList {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.element.classList.add('card-wrapper');
    this.generateCards();
  }
  generateCards() {
    // type cardDataHome = {
    //     id: string;
    //     categoryId: number,
    //     regionId: number,
    //     title: string,
    //     curr: number,
    //     total: number,
    //     meetingDay: number,
    // }
    const state = store.getState();
    state.cards.forEach((cardData) => {
      const card = new Card(cardData);
      this.element.appendChild(card.element);
    });
  }
}
export default CardList;
