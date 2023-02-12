
import { Datepicker } from 'vanillajs-datepicker';
// import fg from '../common/fg.timepicker-master/fg.timepicker.js';
import fg from '../../../common/fg.timepicker-master/fg.timepicker.js';

class DateTimeForm {
    element: HTMLElement;
    constructor() {
      this.element = document.createElement('div');
      this.element.classList.add('form-date-time','calendar-toggle');
      this.render();
    this.datePickerEvent();
    this.timePickerEvent();
    this.timePickerGenerate();
    }
    render(){
        this.element.innerHTML =
        `<h4 class="leading-text">
        <strong>만남 시간</strong>
    </h4>
    <div class="form-input">
        <div class="form-input-date-time">
            <div class="form-input-date">
                <input class="form-control form-control-lg" id="readOnlyDateInput" type="datetime"
                    placeholder="만남 날짜를 설정해주세요." readonly value="">
            </div>
            <div class="divider"></div>
            <div class="form-input-time">
                <input class="form-control form-control-lg" id="readOnlyTimeInput" type="datetime"
                    placeholder="만남 시간을 설정해주세요." readonly value="">
                <div id="timePicker">
                </div>
            </div>
        </div>
    </div>`
    }
    datePickerEvent(){
        const dateInput = this.element.querySelector<HTMLElement>('#readOnlyDateInput')
        if(!dateInput) return
        new Datepicker(dateInput);
    }
     timePickerGenerate() {
        new fg.Timepicker({
          bindContainer: this.element.querySelector('#timePicker'),
          bindInput: this.element.querySelector('#readOnlyTimeInput'),
        });
      }
    timePickerEvent() {
        const timePicker = this.element.querySelector<HTMLElement>('#timePicker');
        if (timePicker === null) return;
        document.addEventListener('mousedown', (e) => {
          const target = e.target as Element;
          const readOnlyTimeInput = target?.closest('#readOnlyTimeInput');
          const formInputTime = target?.closest('.form-input-time');
      
          if (readOnlyTimeInput) {
            this.timePickerOpen(timePicker);
            return;
          }
          if (timePicker.classList.contains('show') && formInputTime === null)
            this.timePickerClose(timePicker);
        });
      }
       timePickerOpen(timePicker: HTMLElement) {
        timePicker.classList.add('show');
      }
       timePickerClose(timePicker: HTMLElement) {
        timePicker.classList.remove('show');
      }
}
export default DateTimeForm