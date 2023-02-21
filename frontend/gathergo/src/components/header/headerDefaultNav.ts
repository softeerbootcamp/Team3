import AlarmSidebar from "../sidebar/alarmSidebar";

class HeaderDefaultNav {
  type: string;
  element: HTMLElement;
  constructor(type: string) {
    this.type = type;
    this.element = document.createElement('ul');
    this.element.classList.add('nav-link-wrapper', 'me-auto');
    this.render();
  }
  render() {
    this.element.innerHTML = this.genertateNav(this.type);
    this.sidebarEvent();

  }
  genertateNav(type: string) {
    switch (type) {
      case 'post':
        return `
              <li class="nav-item">
                <a class="nav-link cancel" href="/" data-hover="취소">
                  <span>취소</span>
                </a>
              </li>
              <li class="nav-item divider"></li>
              <li class="nav-item profile-icon">
                <a class="nav-link" href="/profile" data-hover="내 프로필">
                  <span>내 프로필</span>
                </a>
              </li>`;
      case 'login':
        return `<li class="nav-item">
        <a class="nav-link cancel" href="/" data-hover="취소">
            <span>취소</span>
        </a>
    </li>
      `;
      case 'profile':
        return `<li class="nav-item">
        <a class="nav-link cancel" href="/" data-hover="취소">
          <span>취소</span>
        </a>
      </li>
      <li class="nav-item divider"></li>
      <li class="nav-item">
        <div id="sidebar-tigger" class="nav-link alarm"  data-hover="알람">
          <span>알람</span>
        </div>
        <span class="dot unread"></span>
      </li>`;
      default:
        return '';
    }
  }
  sidebarEvent(){
    const sidebar = new AlarmSidebar();

    // this.element.appendChild(sidebar.element)
  }
}
export default HeaderDefaultNav;
