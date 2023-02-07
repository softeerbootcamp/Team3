import showMapEvent from '../Feed/feed';
import { Datepicker } from 'vanillajs-datepicker';
import moment from 'moment';
import fg from '../common/fg.timepicker-master/fg.timepicker.js';

function dropDownEvent() {
  document.addEventListener('click', (e: Event) => {
    const target = e.target as Element;

    //다른 화면 터치시 모든 dropDown 닫기 event
    const dropDown = target?.closest('.nav-item.dropdown') as HTMLLIElement;
    if (dropDown === null) {
      dropDownCloseAll();
      return;
    }

    //드롭다운 토글 선택시 열고 닫기 event
    const dropDownToggle =
      dropDown.querySelector<HTMLAnchorElement>('.dropdown-toggle');
    if (dropDownToggle === null) return;
    if (dropDownToggle?.classList.contains('show')) dropDownClose(dropDown);
    else dropDownOpen(dropDown);

    //item 선택
    const dropDownItem = target?.closest('.dropdown-item') as HTMLLIElement;
    if (dropDownItem === null) return;

    if (dropDownItem.classList.contains('category')) {
      const index: number = getElementIndex(dropDownItem);
      dropDownToggle.innerHTML = category[index + 1];
    } else dropDownToggle.innerHTML = dropDownItem?.innerHTML;
  });
}

function dropDownCloseAll() {
  document
    .querySelectorAll<HTMLLIElement>('.nav-item.dropdown')
    .forEach((element) => dropDownClose(element));
}

function dropDownOpen(dropDownElement: HTMLLIElement) {
  dropDownCloseAll();
  const toggleElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-toggle');
  if (toggleElement?.classList.contains('show')) return;
  toggleElement?.classList.add('show');
  toggleElement?.setAttribute('aria-expanded', 'true');

  const menuElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-menu');
  if (menuElement == null) return;
  menuElement.classList.add('show');
  menuElement.setAttribute('data-bs-popper', 'static');
}

function dropDownClose(dropDownElement: HTMLLIElement) {
  const toggleElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-toggle');
  if (!toggleElement?.classList.contains('show')) return;
  toggleElement?.classList.remove('show');
  toggleElement?.setAttribute('aria-expanded', 'false');

  const menuElement =
    dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-menu');
  menuElement?.classList.remove('show');
  menuElement?.removeAttribute('data-bs-popper');
}

//header search바 event
function searchBarEvent() {
  const header = document.querySelector<HTMLDivElement>('.header');
  const searchContainer =
    document.querySelector<HTMLDivElement>('.search-container');

  window.onscroll = function () {
    if (header !== null && header.getBoundingClientRect().bottom <= 0) {
      searchContainer?.classList.add('show');
    } else {
      searchContainer?.classList.remove('show');
    }
  };
}
type dropDownType = {
  [key: number]: string;
};
const regionSi: dropDownType = {
  1: '서울특별시',
  2: '부산광역시',
  3: '대구광역시',
  4: '인천광역시',
  5: '광주광역시',
  6: '대전광역시',
  7: '울산광역시',
  8: '세종특별자치시',
  9: '경기도',
  10: '강원도',
  11: '충청북도',
  12: '충청남도',
  13: '전라북도',
  14: '전라남도',
  15: '경상북도',
  16: '경상남도',
  17: '제주특별자치도',
  0: '지역을 선택하세요',
};
const category: dropDownType = {
  1: '아웃도어 / 여행',
  2: '운동 / 스포츠',
  3: '인문학 / 책/ 글',
  4: '업종 / 직무',
  5: '외국 / 언어',
  6: '문화 / 공연 / 축제',
  7: '음악 / 악기',
  8: '패션 / 뷰티',
  9: '공예 / 만들기',
  10: '댄스 / 무용',
  11: '봉사활동',
  12: '사교 / 인맥',
  13: '차 / 오토바이',
  14: '사진 / 영상',
  15: '야구관람',
  16: '게임 / 오락',
  17: '요리 / 제조',
  18: '반려동물',
  19: '자유주제',
  0: '카테고리를 선택하세요',
};

const getElementIndex = (element: HTMLElement) => {
  let index = 0;
  let sibling = element.previousElementSibling;
  while (sibling) {
    index++;
    sibling = sibling.previousElementSibling;
  }
  return index;
};

function cardReadModalEvent() {
  document.querySelectorAll<HTMLLIElement>('.card').forEach((element) => {
    element.addEventListener('click', (e) => {
      const target = e.target as Element;
      if (target?.closest('.card')) {
        // $("#modal-container").removeAttr("class").addClass(buttonId);
        const modalContainer =
          document.querySelector<HTMLElement>('#modal-container');
        modalContainer?.removeAttribute('class');
        modalContainer?.classList.add('modal-animation');
        document.body.classList.add('modal-active');
      }
    });
  });
}

function closeModalEvent() {
  const modalContainer =
    document.querySelector<HTMLElement>('#modal-container');
  modalContainer?.addEventListener('click', (e) => {
    const target = e.target as Element;
    if (target.id === 'modal-background') closeModal(modalContainer);
    const closeIcon = target.closest('#modal-close-icon');
    if (closeIcon) closeModal(modalContainer);
  });
}

function closeModal(modalContainer: HTMLElement) {
  modalContainer.classList.add('out');
  document.body.classList.remove('modal-active');
}

function datePickerEvent() {
  document
    .querySelectorAll<HTMLElement>('#readOnlyDateInput')
    .forEach((elem) => {
      const datepicker = new Datepicker(elem);
      // elem.addEventListener('click', () => {
      // const calendarInput = document.querySelector<HTMLInputElement>(
      //   '#readOnlyCalendarInput'
      // );
      // if (!calendarInput) return;
      // const formattedDate = moment(datepicker.getDate().toString()).format(
      //   'YYYY-MM-DD a HH:mm:ss'
      // );
      // calendarInput.value = formattedDate;
      // });
    });
}

function timePickerGenerate() {
  function logEvent(log: string) {
    // console.log(log);
    // const timeInput =
    //   document.querySelector<HTMLInputElement>('readOnlyTimeInput');
    // if (timeInput === null) return;
    // timeInput.value = log;
  }
  const e1TP = new fg.Timepicker({
    bindContainer: document.getElementById('timePicker'),
    bindInput: document.getElementById('readOnlyTimeInput'),

    onTimeChange: function () {
      logEvent('onTimeChange, new time formatted : ' + e1TP.getFormattedTime());
    },
    onHourChange: function () {
      logEvent('onHourChange, new hour : ' + e1TP.getHour().toString());
    },
    onMinuteChange: function () {
      logEvent('onMinuteChange, new minute : ' + e1TP.getMinute().toString());
    },
    onShow: function () {
      logEvent('onShow triggered');
    },
  });
}
function timePickerEvent() {
  const timePicker = document.querySelector<HTMLDivElement>('#timePicker');
  if (timePicker === null) return;
  document.addEventListener('click', (e) => {
    const target = e.target as Element;
    const readOnlyTimeInput = target?.closest('#readOnlyTimeInput');
    const formInputTime = target?.closest('.form-input-time');

    if (readOnlyTimeInput) {
      timePickerOpen(timePicker);
      return;
    }
    if (timePicker.classList.contains('show') && formInputTime === null)
      timePickerClose(timePicker);
  });
}
function timePickerOpen(timePicker: HTMLElement) {
  timePicker.classList.add('show');
}
function timePickerClose(timePicker: HTMLElement) {
  timePicker.classList.remove('show');
}
searchBarEvent();
dropDownEvent();
cardReadModalEvent();
closeModalEvent();
showMapEvent();
datePickerEvent();
timePickerGenerate();
timePickerEvent();
