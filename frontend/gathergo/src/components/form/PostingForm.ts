import { getKeyByValue, getKoreanTimeString } from '../../common/commonFunctions';
import { category } from '../../common/constants';
import { fetchEditCard, fetchPostCard } from '../../common/Fetches';
import { setModal } from '../../store/actions';
import store from '../../store/store';
import AddressForm from './postingForms/AddressForm';
import CategoryForm from './postingForms/CategoryForm';
import ContentForm from './postingForms/ContentForm';
import DateTimeForm from './postingForms/DateTimeForm';
import MapForm from './postingForms/MapForm';
import PeopleForm from './postingForms/PeopleForm';
import TitleForm from './postingForms/TitleForm';

class PostingForm {
  element: HTMLElement;
  formBtn: HTMLButtonElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-wrapper');
    this.formBtn = document.createElement('button');
    this.render();
    this.buttonEvent();
  }
  render() {
    // const editPostData = feed? store.getState().readingCard:null;
    const titleForm = new TitleForm();
    this.element.appendChild(titleForm.element);

    const categoryForm = new CategoryForm();
    this.element.appendChild(categoryForm.element);

    const dateTimeForm = new DateTimeForm();
    this.element.appendChild(dateTimeForm.element);

    const addressForm = new AddressForm();
    this.element.appendChild(addressForm.element);

    const mapForm = new MapForm(store.getState().readingCard?.location);
    this.element.appendChild(mapForm.element);

    const peopleForm = new PeopleForm();
    this.element.appendChild(peopleForm.element);

    const contentForm = new ContentForm();
    this.element.appendChild(contentForm.element);

    this.element.appendChild(this.formBtn);

    const queryString = new URLSearchParams(window.location.search);
    const feed = queryString.get('feed');
    if (feed) {
      const postCardData = store.getState().readingCard;
      if (!postCardData) return;
      const inputs = this.element.querySelectorAll('input');
      const categoryinput = this.element.querySelector<HTMLDivElement>(
        '.nav-link.dropdown-toggle.category'
      );
      if (!categoryinput) return;
      const content = this.element.querySelector('textarea');
      if (!content) return;
      inputs[0].value = postCardData?.title;
      categoryinput.innerHTML = category[postCardData.categoryId].toString();

      inputs[1].value = `${
        postCardData.meetingDay.getMonth() + 1
      }/${postCardData.meetingDay.getDate()}/${postCardData.meetingDay.getFullYear()}`;

      inputs[2].value = String(
        Math.floor((postCardData.meetingDay.getTime() / (1000 * 60 * 60)) % 24)
      ).padStart(2, '0');
      inputs[2].value += ':';
      inputs[2].value += String(
        Math.floor((postCardData.meetingDay.getTime() / (1000 * 60)) % 60)
      ).padStart(2, '0');

      inputs[3].value = postCardData.location;
      inputs[4].value = postCardData.locationDetail;
      inputs[6].value = postCardData.total.toString();
      content.value = postCardData.content;
    }
  }
  buttonEvent() {
    this.formBtn.classList.add(
      'feed-create-button',
      'btn',
      'btn-primary',
      'btn-lg',
      'form-control-lg'
    );
    this.formBtn.innerHTML = `<strong>모임
      만들기</strong>`;

    this.formBtn.addEventListener('click', this.postMeeting.bind(this));
  }
  async postMeeting() {
    const inputs = this.element.querySelectorAll('input');
    const content = this.element.querySelector('textarea');

    const date = inputs[1].value;
    const time = inputs[2].value;
    const meetingDay = new Date(date);
    const ms =
      Number(time.split(':')[0]) * 60 * 60 * 1000 +
      Number(time.split(':')[1]) * 60 * 1000;
    meetingDay.setTime(meetingDay.getTime() + ms);
    const categoryDropdown = this.element.querySelector(
      '.dropdown-toggle.category'
    );
    console.log((meetingDay));
    console.log((meetingDay.toISOString()));
    console.log(getKoreanTimeString(meetingDay));
    
    

    const categoryKey = getKeyByValue(category, categoryDropdown?.innerHTML);
    if (inputs[0].value.length === 0) {
      store.dispatch(setModal('INPUT_TITLE'));
      return;
    }
    if (getKeyByValue(category, categoryDropdown?.innerHTML) === '0') {
      store.dispatch(setModal('INPUT_CATEGORY'));
      return;
    }
    try {
      meetingDay.toISOString();
    } catch (error) {
      return store.dispatch(setModal('INPUT_TIME'));
    }
    if(meetingDay.getTime() < new Date().getTime()){
      store.dispatch(setModal('INPUT_TIME_BEFORE'))
      return;
    }
    if (inputs[3].value.length === 0 || inputs[4].value.length === 0) {
      store.dispatch(setModal('INPUT_LOCATION'));
      return;
    }
    if (inputs[6].value.length === 0) {
      store.dispatch(setModal('INPUT_PEOPLE'));
      return;
    }
    if (content === null || content.value.length === 0) {
      store.dispatch(setModal('INPUT_DETAIL'));
      return;
    }

    const newPostData = {
      title: inputs[0].value,
      total: Number(inputs[6].value),
      categoryId: Number(categoryKey),
      meetingDay: getKoreanTimeString(meetingDay),//meetingDay.toISOString(),
      content: content?.value,
      location: inputs[3].value,
      locationDetail: inputs[4].value,
    };
    
    const queryString = new URLSearchParams(window.location.search);
    const feed = queryString.get('feed');
    if (feed) {
      store.dispatch(await fetchEditCard(newPostData, feed));
    } else {
      store.dispatch(await fetchPostCard(newPostData));
    }
    console.log(newPostData);
    return;
  }
}
export default PostingForm;
