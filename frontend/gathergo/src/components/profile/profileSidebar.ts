class profileSidebar {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('profile-sidebar')
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <div class = "mypage">
            <span class="nav-link"><strong>마이페이지</strong></span>
        </div>
        <div class="tab">
            <button class="tablinks" id = "profile-tabMain">프로필</button>
            <button class="tablinks" id = "profile-tabEdit">프로필 편집</button>
            <button class="tablinks" id = "profile-logout" style = "color : red">로그아웃</button>
        </div>`;
  }
}
export default profileSidebar;
