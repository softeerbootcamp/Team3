import profileUserInfo from './tabProfile/profileUserInfo';
import profileUserDesc from './tabProfile/profileUserDesc';
import profileUserHost from './tabProfile/profileUserHostList';
import profileUserJoin from './tabProfile/profileUserJoinList';
import store from '../../store/store';
import Navigate from '../../common/utils/navigate';

class tabProfileMain {
  element: HTMLDivElement;
  navigate: Navigate;
  constructor(navigate: Navigate) {
    this.element = document.createElement('div');
    this.element.classList.add('tabcontent');
    this.element.classList.add('profile');
    this.element.id = 'sidebar-profile-body';
    this.navigate = navigate;
    this.render();

    store.subscribe(() => {
      this.render();
    });
  }
  render() {
    const profileTitleText = document.createElement('div');
    profileTitleText.innerHTML = `
        <h2 style = "margin : 1rem 0 2rem 2.8rem"><strong>프로필</strong></h2>
    `;
    this.element.appendChild(profileTitleText);
    const profileUserInfoDom = new profileUserInfo();
    this.element.appendChild(profileUserInfoDom.element);

    const profileDescDom = new profileUserDesc();
    this.element.appendChild(profileDescDom.element);

    const lineDom = document.createElement('div');
    lineDom.classList.add('line');

    this.element.appendChild(lineDom);

    const profileUserHostDom = new profileUserHost(this.navigate);
    this.element.appendChild(profileUserHostDom.element);

    this.element.appendChild(lineDom.cloneNode(true));

    const profileUserJoinDom = new profileUserJoin(this.navigate);
    this.element.appendChild(profileUserJoinDom.element);
  }
}
export default tabProfileMain;
