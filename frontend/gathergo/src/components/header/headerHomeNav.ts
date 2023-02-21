import { setModal } from "../../store/actions";
import store from "../../store/store";

class HeaderHomeNav {
  // islogin: boolean;
  element: HTMLElement;
  sessionIdState: string|null;
  constructor(/*islogin = false*/) {
    // this.islogin = islogin;
    this.sessionIdState = store.getState().sessionId;
    this.element = document.createElement('ul');
    this.element.classList.add('nav-link-wrapper', 'me-auto');
    this.render();
    store.subscribe(() => {
      const newState = store.getState().sessionId;
      if (this.sessionIdState !== newState) {
        this.sessionIdState = newState;
        if (this.sessionIdState !== null) this.render();
      }
    });
  }
  render() {
    // console.log(store.getState().sessionId)
    if (this.sessionIdState !="") {
      // console.log("store.getState()")
      this.element.classList.add('login');
      this.element.innerHTML = `
            <li class="nav-item">
              <a class="nav-link" href="./post" data-hover="모임 만들기">
                <span>모임 만들기</span>
              </a>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item">
              <div id="sidebar-tigger" class="nav-link alarm"  data-hover="알람">
                <span>알람</span>
              </div>
              <span class="dot unread"></span>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item profile-icon">
              <a class="nav-link" href="/profile" data-hover="내 프로필">
                <span>내 프로필</span>
              </a>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item profile-icon">
              <div id="nav-home-logout" class="nav-link cancel" data-hover="로그아웃">
                <span>로그아웃</span>
              </div>
            </li>`;
    } else {
      this.element.classList.add('logout');
      this.element.innerHTML = `
            <li class="nav-item">
              <a class="nav-link" href="/login?action=login" data-hover="로그인">
                <span>로그인</span>
              </a>
            </li>
            <li class="nav-item divider"></li>
            <li class="nav-item">
              <a class="nav-link" href="/login?action=signup" data-hover="회원가입">
                <span>회원가입</span>
              </a>
            </li>`;
    }
    this.logoutEvent();
  }
  logoutEvent(){
    this.element.querySelector('#nav-home-logout')?.addEventListener('click',()=>{
      console.log('ljkljkl');
      store.dispatch(setModal('LOGOUT'))
    })
  }
}
export default HeaderHomeNav;
