// import { checkLogin } from '../../common/commonFunctions';
import { getKoreanTimeString } from '../../common/commonFunctions';
import {
  category,
  regionSi,
  TcardDetail,
  PROFILE_BASE_URL
} from '../../common/constants';
import { fetchSendComment } from '../../common/Fetches';
import { modalMapGenerator } from '../../common/kakaoMapAPI/kakaoMapAPI';
import { checkLogin, setModal } from '../../store/actions';
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
    
    feedWrapper.appendChild(this.setFeedLeftElement());
    feedWrapper.appendChild(this.setFeedDivderElement());
    feedWrapper.appendChild(this.setFeedRightElement());

    modalEle?.appendChild(feedWrapper);
    modalMapGenerator(this.readingCard?.location);
    this.commentBtnEvent();
    this.joinBtnEvent();
    this.modalDropDownEvent();
  }
  modalDropDownEvent(){
    const dropdownToggle = this.element.querySelector('.nav-item.dropdown');
    const dropdownItem = this.element.querySelector('.modal-setting-dropdown');
    dropdownToggle?.addEventListener('click',()=>{
      dropdownItem?.classList.toggle('show');
      dropdownToggle.classList.toggle('rotate');
    })
    document.addEventListener('click',(e)=>{
      const target = e.target as HTMLElement;
      if(target.closest('.nav-item.dropdown')!=dropdownToggle&&dropdownItem?.classList.contains('show')){

      dropdownItem?.classList.toggle('show');
      dropdownToggle?.classList.toggle('rotate');
      }
    })
    this.element.querySelector('.dropdown-item.delete')?.addEventListener('click',()=>{
      store.dispatch(setModal('CLOSE_MEETING'))
    })

    this.element.querySelector('.dropdown-item.edit')?.addEventListener('click',()=>{
      store.dispatch(setModal('EDIT_MEETING'))
    })
  }
  setDefaultModalElement() {
    const modalContainer = document.createElement('div');
    modalContainer.id = 'modal-container';
    modalContainer.innerHTML = `<div id="modal-background">
            <div class="modal"></div></div>`;
    return modalContainer;
  }
  setFeedCloseIcon() {
    return `<div id="modal-close-icon">
    <img src="./assets/Icons/closeIcon.svg" alt="">
  </div>`;
  }
  setFeedLeftElement() {
    const feedLeftElement = document.createElement('div');
    feedLeftElement.classList.add('feed-main-info');

    if (this.readingCard?.meetingDay == undefined) return feedLeftElement;
    feedLeftElement.innerHTML = `
    
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
        <button type="button" class="feed-info btn btn-primary btn-lg ${this.readingCard?.isHost ?`disabled`:``}"><strong>참가하기</strong></button>
      </div>
    </div>
    `;
    return feedLeftElement;
  }
  setFeedDivderElement() {
    const divider = document.createElement('div');
    divider.classList.add('feed-main-divider');
    return divider; 
  }
  setFeedRightElement() {
    const commentList = new CommentList();
    const commentInput = new CommentInput();
    const feedRigntElement = document.createElement('div');
    feedRigntElement.classList.add('feed-main-content');
    const modalIconHTML = this.readingCard?.isHost
      ? `
                
    <li class="nav-item dropdown">
      <img class="user-info-icon icon setting-icon" src="./assets/Icons/setting icon.png" alt="User" />

      <div class="nav-link dropdown-toggle modal-dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true"
        aria-expanded="false">
        </div>
      <div class="dropdown-menu  modal-setting-dropdown">
        <div class="dropdown-item delete" >마감하기</div>
        <div class="dropdown-item edit" >수정하기</div>
      </div>
    </li>`
      : `<div>
    <div class="user-info-icon" tooltip="${this.readingCard?.hostDesc}" flow="down">
      <img class="user-info-icon icon" src="./assets/Icons/who.png" alt="User" />
    </div>
  </div>`;
    const mainElement = document.createElement('div');
    mainElement.classList.add('feed-main-content-scrollable');
    mainElement.innerHTML += `
    <div class="user-info container-md">
      <div class="user-profile">
        <img class="user-profile-img" src="${
          this.readingCard?.hostProfile
            ? PROFILE_BASE_URL +
              this.readingCard?.hostProfile +
              '.png?' +
              Math.random()
            : `./assets/blankProfile.png`
        }" alt="User" onerror="this.src = '../../assets/blankProfile.png'" />
      </div>
      <div class="container-sm">
        <strong class="user-id">${this.readingCard?.hostId}</strong>
      </div>
      <div class="space"></div>
      ${modalIconHTML}
    </div>
    <div class="line"></div>
    <div class="feed-text-container">
      <h2 class="feed-title"><strong>${this.readingCard?.title}</strong></h2>
      <p class="feed-content">${this.readingCard?.content}</p>
    </div>`;
    mainElement.appendChild(commentList.element);
    feedRigntElement.appendChild(mainElement);
    feedRigntElement.appendChild(commentInput.element);
    return feedRigntElement;
  }
  joinBtnEvent() {
    const joinBtn =
      this.element.querySelector<HTMLButtonElement>('.feed-info.btn');
    joinBtn?.addEventListener('click', () => {
      let modaltype = '';
      if (!store.getState().sessionId) modaltype = 'NEED_LOGIN';
      else {
        if(this.readingCard?.hasJoined)
          modaltype = 'JOIN_CANCEL';
          else{
            modaltype = 'JOIN';
          }
      }

      store.dispatch(setModal(modaltype));
    });
  }
  commentBtnEvent() {
    const inputElement =
      this.element.querySelector<HTMLInputElement>('#comment-input');
    const btnElement =
      this.element.querySelector<HTMLButtonElement>('.comment-send');
    if (inputElement === null) return;
    if (btnElement === null) return;
    inputElement.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') {
        this.sendComment(inputElement,btnElement);
        return;
      }
      if (inputElement.value.length == 0) {
        btnElement?.classList.add('disabled');
        return;
      } else {
        btnElement?.classList.remove('disabled');
        return;
      }
      
    });
    btnElement?.addEventListener('click', () => {
      this.sendComment(inputElement,btnElement);
    });
  }
  async sendComment(inputElement: HTMLInputElement,btnElement:HTMLButtonElement) {
    // console.log('check')
    if (this.readingCard?.uuid == null) return;

    store.dispatch(checkLogin(document.cookie));
    
    if (!store.getState().sessionId) {
      store.dispatch(setModal('NEED_LOGIN'));
      return;
    }
    // const date = new Date();
    const commentData = {
      content: inputElement.value,
      date: getKoreanTimeString(),//date.toISOString(),
    };
    store.dispatch(await fetchSendComment(this.readingCard.uuid, commentData));

    inputElement.value = ""
    btnElement?.classList.add('disabled');
  }
}
export default CardModal;
