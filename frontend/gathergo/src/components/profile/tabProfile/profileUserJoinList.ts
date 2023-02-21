import { TuserInfo } from '../../../common/constants';
import Navigate from '../../../common/utils/navigate';
import store from '../../../store/store';
import profileUserJoin from './profileUserJoin';

class profileUserJoinList {
  element: HTMLDivElement;
  userScheduleList: HTMLElement;
  userInfoData: TuserInfo;
  navigate: Navigate;
  constructor(navigate: Navigate) {
    this.element = document.createElement('div');
    this.element.classList.add('user-schedule-wrapper');
    this.userInfoData = store.getState().userInfo;
    this.navigate = navigate;

    this.userScheduleList = document.createElement('ul');
    this.userScheduleList.classList.add('user-schedule');
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
        <h3 style = "padding-top : 1.5rem; padding-bottom : 1.5rem"><strong>만남 스케쥴</strong></h3>
`;
    this.element.appendChild(this.userScheduleList);

    this.userInfoData.userJoinCards.forEach((e) => {
      const userJoinElement = new profileUserJoin(e, this.navigate);
      this.userScheduleList.appendChild(userJoinElement.element);
    });
  }
}
export default profileUserJoinList;
