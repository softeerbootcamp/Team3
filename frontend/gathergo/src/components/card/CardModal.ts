import { category, regionSi, TcardDetail } from '../../common/constants';
import store from '../../store/store';
import CommentInput from '../comment/commentInput';
import CommentList from '../comment/commentList';

class CardModal {
  element: HTMLElement;
  readingCard: TcardDetail;
  constructor() {
    this.element = this.setDefaultModalElement();
    this.readingCard = null;
    store.subscribe(() => {
      const newReadingCard = store.getState().readingCard;
      if (this.readingCard !== newReadingCard) {
        this.readingCard = newReadingCard;
        this.render();
      }
    });
  }

  render() {
    const modalEle = this.element.querySelector<HTMLDivElement>('.modal');
    if (modalEle !== null) modalEle.innerHTML = this.setFeedCloseIcon();

    const feedWrapper = document.createElement('div');
    feedWrapper.classList.add('feed-main-wrapper');

    feedWrapper.innerHTML += this.setFeedLeftElement();
    feedWrapper.innerHTML += this.setFeedDivderElement();
    feedWrapper.innerHTML += this.setFeedRightElement();

    modalEle?.appendChild(feedWrapper);

    // const commentList = new CommentList();
    // const commentContainer = this.element.querySelector(
    //   '.feed-main-contenter-scrollable'
    // );
    // commentContainer?.appendChild(commentList.element);

    // const commentInput = new CommentInput();
    // // const commentContainer = this.element.querySelector('.feed-main-content-scrollable');
    // commentContainer?.insertAdjacentElement('beforeend', commentInput.element);
  }
  setDefaultModalElement() {
    const modalContainer = document.createElement('div');
    modalContainer.id = 'modal-container';
    modalContainer.innerHTML = `<div id="modal-background">
            <div class="modal">
            
            </div>
        </div>`;
    return modalContainer;
  }
  setFeedCloseIcon() {
    return `<div id="modal-close-icon">
    <img src="./assets/Icons/closeIcon.svg" alt="">
  </div>`;
  }
  setFeedLeftElement() {
    if (this.readingCard?.meetingDay == undefined) return 'error';
    return `<div class="feed-main-info">
    <div id="map-api-readOnly"></div>
    <h3 class="address"><strong>${this.readingCard?.location} ${
      this.readingCard?.locationDetail
    }</strong> </h3>
    <div class="info-detail">
      <div class="schedule-synthesis">
        <div class="schedule-time-badge badge bg-light">
          <span class="schedule-date">${
            this.readingCard?.meetingDay.getMonth() + 1
          }.${this.readingCard?.meetingDay.getDate()}</span>
          <span class="schedule-time">${this.readingCard?.meetingDay.getHours()}:${this.readingCard?.meetingDay.getMinutes()}</span>
        </div>
        <div class="schedule-info">
          <div class="schedule-data">
            <span class="badge rounded-pill bg-secondary">${
              regionSi[this.readingCard.regionId]
            }</span>

            <span class="badge rounded-pill bg-light">${
              category[this.readingCard.categoryId]
            }</span>
          </div>
          <div class="peoples">
            <img class="people-icon icon" src="./assets/Icons/peopleIcon.png" alt="User" />
            <span class="peoples-status">${this.readingCard?.curr}/${
      this.readingCard?.total
    }</span>
          </div>
        </div>
        <button type="button" class="feed-info btn btn-primary btn-lg "><strong>참가하기</strong></button>
      </div>
    </div>
  </div>
    `;
  }
  setFeedDivderElement() {
    return `<div class="feed-main-divider"></div>`;
  }
  setFeedRightElement() {
    const commentList = new CommentList();
    const commentInput = new CommentInput();
    return `<div class="feed-main-content">
    <div class="feed-main-content-scrollable">
      <div class="user-info container-md">
        <div class="user-profile">
          <img class="user-profile-img" src="./assets/userProfileImg.jpeg" alt="User" />
        </div>
        <div class="container-sm">
          <strong class="user-id">${this.readingCard?.hostId}</strong>
        </div>
        <div class="space"></div>
        <div>
          <div class="user-info-icon" tooltip="${this.readingCard?.hostDesc}" flow="down">
            <img class="user-info-icon icon" src="./assets/Icons/who.png" alt="User" />
          </div>
        </div>

      </div>
      <div class="line"></div>
      <div class="feed-text-container">
        <h2 class="feed-title"><strong>${this.readingCard?.title}</strong></h2>
        <p class="feed-content">${this.readingCard?.content}</p>
      </div>
      ${commentList.element.outerHTML}
    </div>
    ${commentInput.element.outerHTML}

  </div>`;
  }
}
export default CardModal;
