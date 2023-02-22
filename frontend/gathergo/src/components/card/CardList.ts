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
  type: string;
  observer: IntersectionObserver;
  cardDatas: Tcard[];
  increment: number;
  cardIndex: number;
  constructor(navigate: Navigate, type: string) {
    this.cardsState = store.getState().cards;
    this.element = document.createElement('div');
    this.element.classList.add('card-wrapper');
    this.navigate = navigate;
    this.type = type;
    this.cardDatas = this.getCardDatas();
    this.increment = 40;
    this.cardIndex = 0;
    this.observer = new IntersectionObserver(
      this.handleIntersection.bind(this),
      { threshold: 0.1 }
    );
    this.render();
    store.subscribe(() => {
      const newState = store.getState().cards;
      if (this.cardsState !== newState) {
        this.cardIndex = 0;

        this.cardsState = newState;
        this.render();
      }
    });
  }
  render() {
    this.element.innerHTML = '';
    this.cardDatas = this.getCardDatas();
    this.generateCards();
  }
  getCardDatas() {
    let cardDatas = cloneDeep(this.cardsState);
    if (this.type === 'RECENT') {
      cardDatas = cardDatas.sort(
        (a: Tcard, b: Tcard) => a.meetingDay.getTime() - b.meetingDay.getTime()
      );
    }
    return cardDatas;
  }
  generateCards() {
    if (this.cardDatas.length === 0) return;
    for (
      let i = this.cardIndex;
      i < this.cardDatas.length && i < this.increment + this.cardIndex;
      i++
    ) {
      const card = new Card(this.cardDatas[i]);
      this.element.appendChild(card.element);
      card.element.addEventListener('click', async () => {
        history.replaceState(
          store.getState(),
          '',
          `/?feed=${this.cardDatas[i].uuid}`
        );
        store.dispatch(await fetchCardDetail(this.cardDatas[i].uuid));
      this.openCardModal();
        });
      if (i == this.increment + this.cardIndex - 1) {
        this.observer.observe(card.element);
      }
    }
    this.cardIndex += this.increment;
  }
  handleIntersection(entries: IntersectionObserverEntry[]) {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        const lastCard = entry.target;
        this.generateCards();
        this.observer.unobserve(lastCard);
      }
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
