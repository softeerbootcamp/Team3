import { TuserHostCard } from '../../../common/constants';
import Navigate from '../../../common/utils/navigate';

class profileUserHost {
  element: HTMLDivElement;
  userHostData: TuserHostCard;
  navigate : Navigate;
  constructor($userHostData: TuserHostCard,navigate:Navigate) {
    this.element = document.createElement('tr');
    this.userHostData = $userHostData;
    this.navigate = navigate;
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <td><strong>${this.userHostData.title}</strong></td>
        <td>${this.userHostData.curr}/${this.userHostData.total}</td>`;

    if (this.userHostData.closed)
      this.element.innerHTML += `<td>진행 완료</td>`;
    else this.element.innerHTML += `<td>진행중</td>`;

    this.element.innerHTML += `
        <td>${new Date(this.userHostData.meetingDay).getFullYear()}년 ${new Date(this.userHostData.meetingDay).getMonth()+1}월 ${new Date(this.userHostData.meetingDay).getDate()}일</td>
        `;
    this.addHostLinkEvent()

  }

  addHostLinkEvent() {
    this.element.addEventListener('click', () => {
      this.navigate.to(`/feed=${this.userHostData.uuid}`);

      });
  
  }
}
export default profileUserHost;
