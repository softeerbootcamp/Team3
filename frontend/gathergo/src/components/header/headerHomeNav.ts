class HeaderHomeNav {
  islogin: boolean;
  element: HTMLElement;
  constructor(islogin = false) {
    this.islogin = islogin;
    this.element = document.createElement('ul');
    this.element.classList.add('nav-link-wrapper', 'me-auto');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    if (this.islogin) {
      this.element.classList.add('login');
      this.element.innerHTML = `
            <li class="nav-item">
              <a class="nav-link" href="./post" data-hover="모임 만들기">
                <span>모임 만들기</span>
              </a>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item">
              <a class="nav-link alarm" href="/" data-hover="알람">
                <span>알람</span>
              </a>
              <span class="dot unread"></span>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item profile-icon">
              <a class="nav-link" href="" data-hover="내 프로필">
                <span>내 프로필</span>
              </a>
            </li>`;
    } else {
      this.element.classList.add('logout');
      this.element.innerHTML = `
            <li class="nav-item">
              <a class="nav-link" href="/login" data-hover="로그인">
                <span>로그인</span>
              </a>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item profile-icon">
              <a class="nav-link" href="/login/signup" data-hover="회원가입">
                <span>회원가입</span>
              </a>
            </li>`;
    }
  }
}
export default HeaderHomeNav;
