import { timeDiff } from '../../common/commonFunctions';
import { Tnotice, monthNames } from '../../common/constants';
class Log {
  element: HTMLElement;
  $notice: Tnotice;
  constructor(notice: Tnotice) {
    this.element = document.createElement('div');
    this.element.classList.add('alarm-sidebar-log');
    this.$notice = notice;
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <li>
            <a href="#" target="_blank">
              <div class="date">
                <p class="day">${new Date(
                  this.$notice.meetingDay
                ).getDate()}</p>
                <p class="month">${
                  monthNames[new Date(this.$notice.meetingDay).getMonth()]
                }</p>
              </div>
              <div class="item-info-container">
                <h1>${this.$notice.title}</h1>
                <p class="item-description">
                  ${this.$notice.body}
                </p>
                 <p class="item-description">
                 ${timeDiff(new Date(this.$notice.issueDateTime))}
                </p>
              </div>
            </a>            
          </li>`;
  }
}
export default Log;
