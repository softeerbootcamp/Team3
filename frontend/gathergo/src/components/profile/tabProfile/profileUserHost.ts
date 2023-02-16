import { TuserHostCard } from '../../../common/constants';

class profileUserHost {
  element: HTMLDivElement;
  userHostData: TuserHostCard;

  constructor($userHostData: TuserHostCard) {
    this.element = document.createElement('tr');
    this.userHostData = $userHostData;
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <td><strong>${this.userHostData.title}</strong></td>
        <td>${this.userHostData.curr}/${this.userHostData.total}</td>`;

    if (this.userHostData.isClosed)
      this.element.innerHTML += `<td>진행 완료</td>`;
    else this.element.innerHTML += `<td>진행중</td>`;

    this.element.innerHTML += `
        <td>${new Date(this.userHostData.meetingDay).getFullYear()}년 ${new Date(this.userHostData.meetingDay).getMonth()+1}월 ${new Date(this.userHostData.meetingDay).getDate()}일</td>
        `;
    this.addHostLinkEvent()

  }

  addHostLinkEvent() {
    this.element.addEventListener('click', () => {
        console.log(this.userHostData.id);
      });
  
  }
}
export default profileUserHost;
