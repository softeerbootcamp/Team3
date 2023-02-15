import { Tcomment } from '../../common/constants';
import store from '../../store/store';
import MyComment from './MyComment';
class CommentList {
  element: HTMLDivElement;
  commentsState: Tcomment[];
  constructor() {
    this.commentsState = store.getState().comments;
    console.log(this.commentsState)
    this.element = document.createElement('div');
    this.element.classList.add('feed-comment-container');
    this.render();
    store.subscribe(() => {
      console.log(store.getState().comments)
      const newState = store.getState().comments;
      if (this.commentsState !== newState) {
        this.commentsState = newState;
        this.render();
      }
    });
  }
  render() {
    this.element.innerHTML = '';
    console.log(this.element)
    this.generateComments();
  }
  generateComments() {
    this.commentsState.forEach((commentData: Tcomment) => {
      const comment = new MyComment(commentData);
      this.element.appendChild(comment.element);
    });
  }
  // TODO:마우스 우클릭시
  //   openCardModal() {
  //     const modalContainer =
  //       document.querySelector<HTMLElement>('#modal-container');
  //     modalContainer?.removeAttribute('class');
  //     modalContainer?.classList.add('modal-animation');
  //     document.body.classList.add('modal-active');
  //   }
}
export default CommentList;
