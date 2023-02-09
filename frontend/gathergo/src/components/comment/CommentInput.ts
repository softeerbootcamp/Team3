class CommentInput {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('comment-input');
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <div class="form-group">
          <input class="form-control form-control-lg" type="text" placeholder="댓글 달기.." id="inputLarge">
        </div>
        <button type="button" class="btn btn-info disabled comment-send">게시</button>`;
  }
}
export default CommentInput;
