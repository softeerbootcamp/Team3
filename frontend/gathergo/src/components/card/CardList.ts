import Card from './Card';

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
    //TODO: state.getState();
    //cardDataList : cardData[]
    //cardData : { id: string, hostid: string, title: stirng ~~~~}
    //cardDataList.foreach((cardData)=>{new Card(cardData); this.element.appendChaild(card.element)})
    const card = new Card(null);
    this.element.appendChild(card.element);
    this.element.appendChild(card.element);
  }
}
export default CardList;
