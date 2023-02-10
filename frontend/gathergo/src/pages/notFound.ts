class NotFound {
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
        <main class="notFoundPage">
          404 NOT FOUND
        </main>
      `;
  }
}

export default NotFound;
