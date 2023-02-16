import { timeDiff } from '../../common/commonFunctions';
import { Tcomment } from '../../common/constants';
import { setModal } from '../../store/actions';
import store from '../../store/store';

class MyComment {
  element: HTMLDivElement;
  commentsData: Tcomment;
  constructor(commentData: Tcomment) {
    this.element = document.createElement('div');
    this.element.classList.add('feed-comment');
    this.commentsData = commentData;
    this.render();
  }
  render() {
    console.log(this.commentsData.isMyComment);
    this.element.innerHTML = `<strong class="comment-user-id">${
      this.commentsData.userId
    }</strong>
    <span class="comment-content">${this.commentsData.content}</span>
    <span class="comment-date text-muted">${timeDiff(
      this.commentsData.date
    )}</span>
    ${
      this.commentsData.isMyComment
        ? `<div class="circCont"><button class="circle" data-commentuuid="${this.commentsData.uuid}" data-animation="simpleRotate" data-remove="200"></button></div>`
        : ``
    }
    `;
    this.closeEvent();
  }
  closeEvent() {
    const closeBtn = this.element.querySelector<HTMLElement>('.circle');
    closeBtn?.addEventListener('click', () => {
     store.dispatch( setModal('DELETE_COMMENT',closeBtn.dataset.commentuuid))
    //  console.log(closeBtn.dataset.commentuuid);
      
    });
  }
}
export default MyComment;
