import { getKeyByValue } from '../../common/commonFunctions';
import { category } from '../../common/constants';
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
    const titleForm = new TitleForm();
    this.element.appendChild(titleForm.element);

    const categoryForm = new CategoryForm();
    this.element.appendChild(categoryForm.element);

    const dateTimeForm = new DateTimeForm();
    this.element.appendChild(dateTimeForm.element);

    const addressForm = new AddressForm();
    this.element.appendChild(addressForm.element);

    const mapForm = new MapForm();
    this.element.appendChild(mapForm.element);

    const peopleForm = new PeopleForm();
    this.element.appendChild(peopleForm.element);

    const contentForm = new ContentForm();
    this.element.appendChild(contentForm.element);

    this.element.appendChild(this.formBtn);
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
  postMeeting() {
    const inputs = this.element.querySelectorAll('input');
    const content = this.element.querySelector('textarea');

    const date = inputs[1].value 
    const time =  inputs[2].value;
    const meetingDay = new Date(date)
    const ms = Number(time.split(':')[0]) * 60 * 60 * 1000 + Number(time.split(':')[1]) * 60 * 1000;
    meetingDay.setTime(meetingDay.getTime() + ms);
    const categoryDropdown = this.element.querySelector('.dropdown-toggle.category');

    const newPostData = {
      userId: "inputs[0].value",
      title: inputs[0].value,
      total: Number(inputs[6].value),
      categoryId: getKeyByValue(category,categoryDropdown?.innerHTML),
      // regionId: number,
      meetingDay: meetingDay.toISOString(),
      content: content?.value,
      location: inputs[3].value,
      locationDetail: inputs[4].value,
    };
    //store.dispatch(~~action~~)
    console.log(newPostData);
  }
}
export default PostingForm;