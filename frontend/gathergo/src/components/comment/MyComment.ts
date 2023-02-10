import { Tcomment } from '../../common/constants';

class MyComment {
  element: HTMLDivElement;
  commentsData: Tcomment;
  constructor(commentData: Tcomment) {
    this.element = document.createElement('div');
    this.element.classList.add('feed-comment');
    this.commentsData = commentData;
    // this.id = cardData.id;
    // this.categoryId = cardData.categoryId;
    // this.regionId = cardData.regionId;
    // this.curr = cardData.curr;
    // this.total = cardData.total;
    // this.title = cardData.title;
    // this.meetingDay = new Date(cardData.meetingDay);
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.element.innerHTML = `<strong class="comment-user-id">${this.commentsData.userId}</strong>
    <span class="comment-content">${this.commentsData.content}</span>
    <span class="comment-date text-muted">${this.commentsData.date}</span>`;
  }
}
export default MyComment;
