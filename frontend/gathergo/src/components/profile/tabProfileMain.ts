import profileUserInfo from './tabProfile/profileUserInfo';
import profileUserDesc from './tabProfile/profileUserDesc';
import profileUserHost from './tabProfile/profileUserHostList';
import profileUserJoin from './tabProfile/profileUserJoinList';

class tabProfileMain {
  element: HTMLDivElement;

  constructor() {

    this.element = document.createElement('div');
    this.element.classList.add('tabcontent');
    this.element.classList.add('profile');
    this.element.id = 'sidebar-profile-body';

    this.render();
  }
  render() {
    this.element.innerHTML = `
        <h2 style = "margin : 1rem 0 2rem 2.8rem"><strong>프로필</strong></h2>
    `;
    const profileUserInfoDom = new profileUserInfo();
    this.element.appendChild(profileUserInfoDom.element);

    const profileDescDom = new profileUserDesc();
    this.element.appendChild(profileDescDom.element);

    this.element.innerHTML += `<div class="line"></div>`;

    const profileUserHostDom = new profileUserHost();
    this.element.appendChild(profileUserHostDom.element);

    this.element.innerHTML += `<div class="line"></div>`;

    const profileUserJoinDom = new profileUserJoin();
    this.element.appendChild(profileUserJoinDom.element);
  }
}
export default tabProfileMain;
