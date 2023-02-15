import { TuserHostCard } from '../../../common/constants';

class profileUserHost {
  element: HTMLDivElement;
  userHostData: TuserHostCard;

  constructor($userHostData : TuserHostCard) {
    this.element = document.createElement('tr');
    this.userHostData = $userHostData;
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <td><strong>${this.userHostData.title}</strong></td>
        <td>${this.userHostData.curr}/${this.userHostData.total}</td>
        <td>${this.userHostData.isClosed}</td>
        <td>${this.userHostData.meetingDay}</td>
`;
  }
}
export default profileUserHost;
