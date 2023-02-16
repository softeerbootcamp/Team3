import { timeDiff } from '../../common/commonFunctions';
import { Tcomment } from '../../common/constants';

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
    console.log(this.commentsData.isMyComment)
    this.element.innerHTML = `<strong class="comment-user-id">${this.commentsData.userId}</strong>
    <span class="comment-content">${this.commentsData.content}</span>
    <span class="comment-date text-muted">${timeDiff( this.commentsData.date)}</span>
    ${this.commentsData.isMyComment?`<div class="circCont"><button class="circle" data-animation="simpleRotate" data-remove="200"></button></div>`:``}
    `;
    this.closeEvent();
  }
  closeEvent(){
    const closeBtn = document.querySelector('.circle');
    closeBtn?.addEventListener('click',()=>{
      if (closeBtn.classList.contains('simpleRotate')) {
        closeBtn.classList.remove('200');
      } else {
        closeBtn.classList.add('simpleRotate');
        // if (typeof '200' !== 'undefined') {
          const el = closeBtn;
          setTimeout(function() {
            el.classList.remove('simpleRotate');
          }, 200);
        // }
      }
    })
  }
}
export default MyComment;
