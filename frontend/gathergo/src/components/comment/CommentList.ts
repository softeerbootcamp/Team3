import { Tcomment } from '../../common/constants';
import store from '../../store/store';
import MyComment from './MyComment';
class CommentList {
  element: HTMLDivElement;
  commentsState: Tcomment[];
  constructor() {
    this.commentsState = store.getState().comments;
    this.element = document.createElement('div');
    this.element.classList.add('feed-comment-container');
    this.render();
    store.subscribe(() => {
      const newState = store.getState().comments;
      if (this.commentsState !== newState) {
        this.commentsState = newState;
        this.render();
      }
    });
  }
  render() {
    this.element.innerHTML = '';
    this.generateComments();
  }
  generateComments() {
    this.commentsState.forEach((commentData: Tcomment) => {
      const comment = new MyComment(commentData);
      this.element.appendChild(comment.element);
      comment.element.addEventListener('contextmenu', () => {
        // this.openCardModal();
        // store.dispatch(readCard(cardData.id));
      });
    });
  }
  //   openCardModal() {
  //     const modalContainer =
  //       document.querySelector<HTMLElement>('#modal-container');
  //     modalContainer?.removeAttribute('class');
  //     modalContainer?.classList.add('modal-animation');
  //     document.body.classList.add('modal-active');
  //   }
}
export default CommentList;
