import { timeDiff } from '../../common/commonFunctions';
class Log {
    element: HTMLElement;
    constructor() {
      this.element = document.createElement('div');
      this.element.classList.add( "alarm-sidebar-log")
      this.render();
      // store.subscribe(() => this.render());
    }
    render() {
        this.element.innerHTML=`
        <li>
            <a href="#" target="_blank">
              <div class="date">
                <p class="day">23</p>
                <p class="month">nov</p>
              </div>
              <div class="item-info-container">
                <h1>만남 제목입니다.</h1>
                <p class="item-description">
                  알람 내용입니다. ex) aa님이 모임에 참가했습니다.
                </p>
                 <p class="item-description">
                 ${timeDiff(new Date())}
                </p>
              </div>
            </a>            
          </li>`
    }
}
export default Log;