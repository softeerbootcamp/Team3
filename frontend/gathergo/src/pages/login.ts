import LoginForm from '../components/form/LoginForm';
import HeaderDefault from '../components/header/headerDefault';

class Login {
  $container: HTMLElement;
  constructor($container: HTMLElement) {
    this.$container = $container;
    this.render();
  }
  setState = () => {
    this.render();
  };

  render() {
    if (!this.$container) return;
    const headerDefault = new HeaderDefault('login');
    this.$container.appendChild(headerDefault.element);

    const loginForm = new LoginForm();
    const queryString = new URLSearchParams(window.location.search);
    const action = queryString.get('action');
    if (action === 'signup') {
      loginForm.showSignUp();
    }
    this.$container.appendChild(loginForm.element);
  }
}

export default Login;
