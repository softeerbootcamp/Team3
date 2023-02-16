import { TuserInfo } from '../../../common/constants';
import store from '../../../store/store';

class profileUserInfo {
  element: HTMLDivElement;
  userInfoData: TuserInfo;

  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('sidebar-user-info');
    this.element.classList.add('container-md');
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
        <div class="user-profilepage">
            <img class="user-profilepage-img" src=${this.userInfoData.profileImg} alt="User" />
        </div>
        <div class="container-sm">
            <strong class="user-id">${this.userInfoData.userId}</strong>
            <span class="user-email">${this.userInfoData.email}</span>
        </div>
    </div>
    `;
  }
}
export default profileUserInfo;
