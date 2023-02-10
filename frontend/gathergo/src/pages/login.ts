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
    this.$container.innerHTML = `
            <main class="Login-page">
              로그인 페이지
            </main>
          `;
  }
}

export default Login;
