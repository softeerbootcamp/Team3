import { TuserInfo } from '../../../common/constants';
import store from '../../../store/store';
import profileUserHost from './profileUserHost';

class profileUserHostList {
  element: HTMLDivElement;
  userInfoData: TuserInfo;

  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('user-host-meet-list');
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
        <h3 style = "padding : 1rem 0 0 0"><strong>내가 호스트한 만남</strong></h3>
        <table>
            <thead>
                <tr>
                    <th>만남 이름</th>
                    <th>인원</th>
                    <th>진행상태</th>
                    <th>만남 일정</th>
                </tr>
            </thead>
            <tbody>
          </tbody>
        </table>

`;

    this.userInfoData.userHostCards.forEach((e) => {
      const userHostElement = new profileUserHost(e);
      this.element.querySelector('tbody')?.appendChild(userHostElement.element);
    });

  }
}
export default profileUserHostList;
