import { TuserJoinCard } from '../../../common/constants';
import { regionSi, category } from '../../../common/constants';


class profileUserJoin {
  element: HTMLElement;
  userJoinData: TuserJoinCard;

  constructor($userHostData: TuserJoinCard) {
    this.element = document.createElement('li');
    this.element.classList.add('user-schedule');
    this.element.classList.add('profile-user-join');

    this.userJoinData = $userHostData;
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <div class="schedule-time-badge badge bg-light">
        <span class="schedule-date">${new Date(this.userJoinData.meetingDay).getMonth()+1}.${new Date(this.userJoinData.meetingDay).getDate()}</span>
        <span class="schedule-time">${new Date(this.userJoinData.meetingDay).getHours()}:${new Date(this.userJoinData.meetingDay).getMinutes()}</span>
        </div>
        <div class="schedule-info">
        <strong class="scheduel-title">${this.userJoinData.title}</strong>
        <div class="schedule-data">
            <span class="badge rounded-pill bg-secondary">${
              regionSi[this.userJoinData.regionId]
            }</span>
            <span class="badge rounded-pill bg-light">${
              category[this.userJoinData.categoryId]
            }</span>
            <div class="user-host-meet-peoples">
            <img class="people-icon icon" src="../../assets/Icons/peopleIcon.png" alt="User" />
            <span class="user-host-meet-peoples-status">${
              this.userJoinData.curr
            }/${this.userJoinData.total}</span>
            </div>
        </div>
        </div>
`;
this.addJoinLinkEvent()
  }

  addJoinLinkEvent() {
      this.element.addEventListener('click', () => {
        console.log(this.userJoinData.uuid);
      });
  }
}
export default profileUserJoin;
