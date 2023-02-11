class HeaderDefault {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('headerDefault');
    this.render();
    // store.subscribe(() => this.render());
  }
  render(){
    this.element.innerHTML = `
    <nav class="header navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-lg">
            <img class="navbar-home-Logo" src="../../assets/Logo/LogoIconChar-Black.svg" alt="GatherGo" />
            <div class="container-fluid">
                <ul class="nav-link-wrapper login me-auto show">
                    <li class="nav-item">
                        <a class="nav-link cancel" href="#" data-hover="취소">
                            <span>취소</span>
                        </a>
                    </li>
                    <li class="nav-item divider"></li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-hover="알람">
                            <span>알람</span>
                        </a>
                        <span class="dot unread"></span>
                    </li>
                    <li class="nav-item divider"></li>
                    <li class="nav-item profile-icon">
                        <a class="nav-link" href="#" data-hover="내 프로필">
                            <span>내 프로필</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    `
  }
}
export default HeaderDefault;