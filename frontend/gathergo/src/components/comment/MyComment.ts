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
    // console.log(this.commentsData.isMyComment)
    this.element.innerHTML = `<strong class="comment-user-id">${this.commentsData.userId}</strong>
    <span class="comment-content">${this.commentsData.content}</span>
    <span class="comment-date text-muted">${timeDiff( this.commentsData.date)}</span>`;
    // if(this.commentsData.isMyComment)
    this.element.innerHTML +=`<div>
    <img class="comment-icon-threedot" src="./assets/Icons/threedots.png</>
    </div>
    `;
  }
}
export default MyComment;
