import profileUserInfo from './profileUserInfo/profileUserInfo';
import profileUserDesc from './profileUserInfo/profileUserDesc';

class profileMain {
  element: HTMLDivElement;
  tabcontent: HTMLDivElement;

  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('profile-main');

    this.tabcontent = document.createElement('div');
    this.tabcontent.classList.add('tabcontent');
    this.tabcontent.classList.add('profile');
    this.tabcontent.id = 'sidebar-profile-body';

    this.render();
  }
  render() {
    this.element.appendChild(this.tabcontent);
    this.tabcontent.innerHTML = `
        <h2 style = "margin : 1rem 0 2rem 2.8rem"><strong>프로필</strong></h2>
    `;
    const profileUserInfoDom = new profileUserInfo();
    this.tabcontent.appendChild(profileUserInfoDom.element);

    const profileDescDom = new profileUserDesc();
    this.tabcontent.appendChild(profileDescDom.element);

    this.tabcontent.innerHTML += `<div class="line"></div>`;
  }
}
export default profileMain;
