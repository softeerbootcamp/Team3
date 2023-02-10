import { Tcard, category, regionSi } from '../../common/constants';
class Card {
  element: HTMLDivElement;
  //   id: string;
  //   categoryId: number;
  //   regionId: number;
  //   title: string;
  //   curr: number;
  //   total: number;
  //   meetingDay: Date;
  cardData: Tcard;
  constructor(cardData: Tcard) {
    this.element = document.createElement('div');
    this.cardData = cardData;
    // this.id = cardData.id;
    // this.categoryId = cardData.categoryId;
    // this.regionId = cardData.regionId;
    // this.curr = cardData.curr;
    // this.total = cardData.total;
    // this.title = cardData.title;
    // this.meetingDay = new Date(cardData.meetingDay);
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.element.classList.add('card', 'mb-3');
    this.element.innerHTML = `<div class="card-thumbnail-wrapper">
    <img class="card-thumbnail" src="./assets/category/thumbnails/${
      this.cardData.categoryId
    }.jpg" alt="카드 썸네일">
  </div>
  <div class="card-body">
    <p class="card-text">
      <strong>${this.cardData.title}</strong>
    </p>
  </div>
  <div class="card-body">
    <span class="badge rounded-pill bg-secondary">${
      regionSi[this.cardData.regionId]
    }</span>
    <span class="badge rounded-pill bg-light">${
      category[this.cardData.categoryId]
    }</span>
  </div>
  <div class="card-footer">
    <div class="user-host-meet-peoples">
      <img class="people-icon icon" src="./assets/Icons/peopleIcon.png" alt="User" />
      <span class="user-host-meet-peoples-status">${this.cardData.curr}/${
      this.cardData.total
    }</span>
    </div>
    <span class="card-date text-muted">${
      this.cardData.meetingDay.getMonth() + 1
    }월 ${this.cardData.meetingDay.getDate()}일</span>
  </div>`;
  }
}
export default Card;
