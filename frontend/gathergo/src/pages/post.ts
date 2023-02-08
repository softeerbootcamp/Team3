class Post {
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
          <main class="post-page">
            게시물 등록페이지
          </main>
        `;
  }
}

export default Post;
