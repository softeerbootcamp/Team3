class Profile {
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
          <main class="profile-page">
            프로필페이지
          </main>
        `;
  }
}

export default Profile;
