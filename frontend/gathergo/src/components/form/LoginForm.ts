
import { fetchLogin, fetchSignup } from "../../common/Fetches";
import store from "../../store/store";

class LoginForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('login-page');
    this.render();
  }
  render() {
    this.element.innerHTML = `

        <div class="login-content login-content-signin" ng-hide="showSignIn">
            <div>
                <h2>로그인</h2>
                <form class="wrapper-box" role="form">
                    <input type="id" ng-model="id" class="form-control" placeholder="ID" required>
                    <input type="password" ng-model="password" class="form-control form-control-password"
                        placeholder="Password" required>
                    <button id="btn-submit-signin" type="button" >로그인</button>
                </form>
            </div>
        </div>

        <div class="login-content login-content-signup ng-hide" ng-show="showSignIn">
            <div>
                <h2>회원가입</h2>
                <form class="wrapper-box" role="form" ng-submit="register()">
                    <input type="text" ng-model="id" class="form-control form-control-userid" placeholder="ID" required>
                    <input type="text" ng-model="username" class="form-control form-control-username"
                        placeholder="Username" required>
                    <input type="password" ng-model="password" class="form-control form-control-password"
                        placeholder="Password" required>
                    <input type="email" ng-model="email" class="form-control form-control-email"
                        placeholder="Email address" required>
                    <button id="btn-submit-signup" type="button" >회원가입</button>
                </form>
            </div>
        </div>

        <div class="login-switcher">
            <div class="login-switcher-signin" ng-show="showSignIn">
                <h3>계정이 있으신가요?</h3>
                <button class="login-switcher-button" id="login-button-signin">로그인</button>
            </div>
            <div class="login-switcher-signup" ng-hide="showSignIn">
                <h3>계정이 없으신가요?</h3>
                <button class="login-switcher-button" id="login-button-signup">회원가입</button>
            </div>
        </div>`;
    this.switchButtonEvent();
    this.subminButtonEvent();
  }
  subminButtonEvent(){
    const signinBtn = this.element.querySelector('#btn-submit-signin')
    signinBtn?.addEventListener('click',this.submitSignin.bind(this))
    const signupBtn = this.element.querySelector('#btn-submit-signup')
    signupBtn?.addEventListener('click',this.submitSignup.bind(this))
  }
  async submitSignin(){
    const inputs =this.element.querySelectorAll('input');
    const loginData = {
        "userId": inputs[0].value,
        "password": inputs[1].value,
      };
      console.log(loginData)
      store.dispatch(await fetchLogin(loginData));
  }

  async submitSignup(){
    const inputs =this.element.querySelectorAll('input');
    const signupData = {
        "userId": inputs[2].value,
        "userName": inputs[3].value,
        "password": inputs[4].value,
        "email": inputs[5].value,
      };
      console.log(signupData)
      store.dispatch(await fetchSignup(signupData));
  }
  switchButtonEvent() {
    const btns = this.element.querySelectorAll('.login-switcher-button');
    btns.forEach((btn) => {
      if (btn.id === 'login-button-signup')
        btn.addEventListener('click', this.showSignUp.bind(this));
      if (btn.id === 'login-button-signin')
        btn.addEventListener('click', this.showSignIn.bind(this));
    });
  }
  showSignIn() {
    history.replaceState(store.getState(), "", '/login?action=login');
    
    this.element
      .querySelector('.login-content-signin')
      ?.classList.remove('ng-hide');
    this.element
      .querySelector('.login-content-signup')
      ?.classList.add('ng-hide');
    this.element
      .querySelector('.login-switcher-signin')
      ?.classList.add('ng-hide');
    this.element
      .querySelector('.login-switcher-signup')
      ?.classList.remove('ng-hide');
  }
  showSignUp() {
    history.replaceState(store.getState(), "", '/login?action=signup');
    this.element
      .querySelector('.login-content-signin')
      ?.classList.add('ng-hide');
    this.element
      .querySelector('.login-content-signup')
      ?.classList.remove('ng-hide');
    this.element
      .querySelector('.login-switcher-signin')
      ?.classList.remove('ng-hide');
    this.element
      .querySelector('.login-switcher-signup')
      ?.classList.add('ng-hide');
  }
}
export default LoginForm;
