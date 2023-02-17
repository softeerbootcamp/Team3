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
}
export default CommentList;
