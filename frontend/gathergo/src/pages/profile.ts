import { fetchGetUserInfo } from '../common/Fetches';
import profileLayout from '../components/profile/profileLayout';
import HeaderDefault from '../components/header/headerDefault';
import store from '../store/store';
import { changeProfileTab, checkLogin } from '../store/actions';
import Navigate from '../common/utils/navigate';

class Profile {
  $container: HTMLElement;
  navigate: Navigate;
  constructor($container: HTMLElement, navigate: Navigate) {
    this.$container = $container;
    this.navigate = navigate;
    this.render();
  }
  setState = () => {
    this.render();
  };

  async render() {
    store.dispatch(checkLogin(document.cookie));
    if (!store.getState().sessionId) {
      this.navigate.to('/');
      return;
    }
    store.dispatch(await fetchGetUserInfo());
    if (!this.$container) return;
    const headerDefault = new HeaderDefault('profile');
    this.$container.appendChild(headerDefault.element);

    const profilePageLayout = new profileLayout(this.navigate);
    this.$container.appendChild(profilePageLayout.element);
    // this.$container.querySelector<HTMLButtonElement>('#logoutbtn')?.addEventListener(('click'),async ()=>{
    // // logOut();
    // store.dispatch(await fetchLogout())
    // })

    this.addTabButtonEvent();
  }

  addTabButtonEvent() {
    const tabMainDom = document.querySelector('#profile-tabMain');
    const tabEditDom = document.querySelector('#profile-tabEdit');
    const tabLogoutDom = document.querySelector('#profile-logout');

    tabMainDom?.classList.add('active');
    tabEditDom?.classList.remove('active');

    tabMainDom?.addEventListener('click', () => {
      store.dispatch(changeProfileTab(0));
    });
    tabEditDom?.addEventListener('click', () => {
      store.dispatch(changeProfileTab(1));
    });
    tabLogoutDom?.addEventListener('click', () => {
      store.dispatch(changeProfileTab(2));
    });
  }
}

export default Profile;
