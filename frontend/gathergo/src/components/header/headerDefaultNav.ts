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
              <a class="nav-link" href="./post" data-hover="모임 만들기">
                <span>모임 만들기</span>
              </a>
            </li>`;
      default:
        return '';
    }
  }
}
export default HeaderDefaultNav;
