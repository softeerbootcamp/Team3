import { TuserInfo } from '../../../common/constants';
import store from '../../../store/store';

class profileUserDesc {
  element: HTMLDivElement;
  userInfoData: TuserInfo;

  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('user-desc');
    this.userInfoData = store.getState().userInfo;

    this.render();

    store.subscribe(() => {
      const newUserProfileInfo = store.getState().userInfo;
      if (newUserProfileInfo !== this.userInfoData) {
        this.userInfoData = newUserProfileInfo;
        this.render();
      }
    });
  }
  render() {
    this.element.innerHTML = `
      <div class="user-profile-desc-header">
          <h3><strong>한 줄 소개</strong></h3>
      </div>
    <small class="muted-text">${this.userInfoData.introduction}</small>
        `;
  }
}
export default profileUserDesc;
