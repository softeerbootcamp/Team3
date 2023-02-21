import Log from './log';
import store from '../../store/store';
import { Tnotice } from '../../common/constants';
import { getNoticeSidebar } from '../../common/Fetches';

class AlarmSidebar {
  element: HTMLElement;
  noticeList: Tnotice[];
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('sidebar-container');

    this.noticeList = store.getState().notice;
    this.render();
    store.subscribe(() => {
      const newList = store.getState().notice;
      if (this.noticeList != newList)
        this.generateLogs(newList)
    });
  }
  render() {
    // store.dispatch(await getNoticeSidebar());
    // this.noticeList = store.getState().notice;

    console.log(this.noticeList);

    this.element.innerHTML = `
      <div class = "sidebar-background">
      <div id = 'alarm-sidebar'>
      <div class="sidebar-icon">
      <div id="alarm-sidebar-close" class="circCont"><button class="circle" data-animation="simpleRotate" data-remove="200"></button></div>

      </div>
      <ul class="scale-up-hover-list"></ul>
      </div>
      </div>`;
    // this.generateLogs(this.noticeList);
    this.closeSidebarEvent();
  }
  generateLogs(noticeList: Tnotice[]) {
    const logList = this.element.querySelector('.scale-up-hover-list') as HTMLElement;
    logList.innerHTML = '';
    for (let i = 0; i < noticeList.length; i++) {
      const log = new Log(noticeList[i]);
      logList?.appendChild(log.element);
    }
  }

  closeSidebarEvent() {
    this.element
      .querySelector('#alarm-sidebar-close')
      ?.addEventListener('click', () => {
        this.closeSidebar();
      });
    this.element
      .querySelector('.sidebar-background')
      ?.addEventListener('click', (e) => {
        const target = e.target as Element;
        if (!target.closest('#alarm-sidebar')) this.closeSidebar();
      });
  }
  closeSidebar() {
    document.querySelector('#alarm-sidebar')?.classList.remove('active');

    setTimeout(() => {
      this.element.classList.remove('active');
      document.body?.removeAttribute('class');
    }, 250);
  }
}
export default AlarmSidebar;
