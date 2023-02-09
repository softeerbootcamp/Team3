class Card {
  element: HTMLDivElement;
  // cardId: number;
  // titel: string;
  // thumbnail: string;
  // curr: number;
  // total: number;
  // region: string;
  constructor(cardData: any) {
    this.element = document.createElement('div');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.element.classList.add('card', 'mb-3');
    this.element.innerHTML = `<div class="card-thumbnail-wrapper">
    <img class="card-thumbnail" src="./assets/category/thumbnails/15.jpg" alt="카드 썸네일">
  </div>
  <div class="card-body">
    <p class="card-text">
      <strong> 강남 농민 백암 순대 팟 구해요</strong>
    </p>
  </div>
  <div class="card-body">
    <span class="badge rounded-pill bg-secondary">대전광역시</span>
    <span class="badge rounded-pill bg-info">유성구</span>
    <span class="badge rounded-pill bg-light">패션</span>
  </div>
  <div class="card-footer">
    <div class="user-host-meet-peoples">
      <img class="people-icon icon" src="./assets/Icons/peopleIcon.png" alt="User" />
      <span class="user-host-meet-peoples-status">27/68</span>
    </div>
    <span class="card-date text-muted">2월 18일</span>
  </div>`;
  }
}
export default Card;
