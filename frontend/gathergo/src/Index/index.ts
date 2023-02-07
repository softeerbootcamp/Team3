import showMapEvent from '../Feed/feed';
import { Datepicker } from 'vanillajs-datepicker';
// import moment from 'moment';
import fg from '../common/fg.timepicker-master/fg.timepicker.js';
// import formMapGenerator from '../common/kakaoMapAPI/kakaoMapAPI';
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
// const regionSi: dropDownType = {
//   1: '서울특별시',
//   2: '부산광역시',
//   3: '대구광역시',
//   4: '인천광역시',
//   5: '광주광역시',
//   6: '대전광역시',
//   7: '울산광역시',
//   8: '세종특별자치시',
//   9: '경기도',
//   10: '강원도',
//   11: '충청북도',
//   12: '충청남도',
//   13: '전라북도',
//   14: '전라남도',
//   15: '경상북도',
//   16: '경상남도',
//   17: '제주특별자치도',
//   0: '지역을 선택하세요',
// };
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
      new Datepicker(elem);
      // const datepicker = new Datepicker(elem);
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
    console.log(log);
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

declare global {
  interface Window {
    kakao: any;
  }
}

// const geocoder = new window.kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
// ('/frontend/gathergo/src/Feed/create-update.html');
// // eslint-disable-next-line @typescript-eslint/no-explicit-any
// geocoder.addressSearch(
//   '서울시 서초구 강남대로61길 23',
//   function (result: any, status: any) {
//     // 정상적으로 검색이 완료됐으면
//     if (status === window.kakao.maps.services.Status.OK) {
//       const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);

//       const container = document.getElementById('map-api-readOnly'); //지도를 담을 영역의 DOM 레퍼런스

//       //지도를 생성할 때 필요한 기본 옵션
//       const options = {
//         center: coords, //지도의 중심좌표.
//         level: 3, //지도의 레벨(확대, 축소 정도)
//       };

//       const map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

//       // 결과값으로 받은 위치를 마커로 표시합니다
//       const marker = new window.kakao.maps.Marker({
//         map: map,
//         position: coords,
//       });

//       // 인포윈도우로 장소에 대한 설명을 표시합니다
//       const infowindow = new window.kakao.maps.InfoWindow({
//         content:
//           '<div style="width:150px;text-align:center;padding:6px 0;">만남 장소</div>',
//       });
//       infowindow.open(map, marker);
//     }
//   }
// );
// formMapGenerator();

// export default function formMapGenerator() {
// 마커를 담을 배열입니다
let markers: any[] = [];

const mapContainer = document.getElementById('form-map'), // 지도를 표시할 div
  mapOption = {
    center: new window.kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };

// 지도를 생성합니다
const map = new window.kakao.maps.Map(mapContainer, mapOption);

// 지도 우측 하단 스케일 위치변경
designMapScale(mapContainer);

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
const infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });

// 키워드로 장소를 검색합니다
searchPlaces();
// }

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
  console.log('check');
  // eslint-disable-next-line no-debugger
  debugger;
  const keyword = document.querySelector<HTMLInputElement>('#keyword')?.value;

  if (keyword && !keyword.replace(/^\s+|\s+$/g, '')) {
    alert('키워드를 입력해주세요!');
    return false;
  }

  // 장소 검색 객체를 생성합니다
  const ps = new window.kakao.maps.services.Places();
  // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
  ps.keywordSearch(keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data: any, status: any, pagination: any) {
  if (status === window.kakao.maps.services.Status.OK) {
    // 정상적으로 검색이 완료됐으면
    // 검색 목록과 마커를 표출합니다
    displayPlaces(data);

    // 페이지 번호를 표출합니다
    displayPagination(pagination);
  } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
    alert('검색 결과가 존재하지 않습니다.');
    return;
  } else if (status === window.kakao.maps.services.Status.ERROR) {
    alert('검색 결과 중 오류가 발생했습니다.');
    return;
  }
}
// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places: string | any[]) {
  const listEl = document.getElementById('placesList'),
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(),
    bounds = new window.kakao.maps.LatLngBounds(),
    listStr = '';

  // 검색 결과 목록에 추가된 항목들을 제거합니다
  removeAllChildNods(listEl);

  // 지도에 표시되고 있는 마커를 제거합니다
  removeMarker();

  for (let i = 0; i < places.length; i++) {
    // 마커를 생성하고 지도에 표시합니다
    const placePosition = new window.kakao.maps.LatLng(
        places[i].y,
        places[i].x
      ),
      marker = addMarker(placePosition, i),
      itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
    // LatLngBounds 객체에 좌표를 추가합니다
    bounds.extend(placePosition);

    // 마커와 검색결과 항목에 mouseover 했을때
    // 해당 장소에 인포윈도우에 장소명을 표시합니다
    // mouseout 했을 때는 인포윈도우를 닫습니다
    (function (marker, title) {
      window.kakao.maps.event.addListener(marker, 'mouseover', function () {
        displayInfowindow(marker, title);
      });

      window.kakao.maps.event.addListener(marker, 'mouseout', function () {
        infowindow.close();
      });

      itemEl.onmouseover = function () {
        displayInfowindow(marker, title);
      };

      itemEl.onmouseout = function () {
        infowindow.close();
      };
    })(marker, places[i].place_name);

    fragment.appendChild(itemEl);
  }

  // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
  listEl?.appendChild(fragment);
  if (menuEl) menuEl.scrollTop = 0;

  // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
  map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index: number, places: any) {
  const el = document.createElement('li');
  let itemStr =
    '<span class="markerbg marker_' +
    (index + 1) +
    '"></span>' +
    '<div class="info">' +
    '   <h5>' +
    places.place_name +
    '</h5>';

  if (places.road_address_name) {
    itemStr +=
      '    <span>' +
      places.road_address_name +
      '</span>' +
      '   <span class="jibun gray">' +
      places.address_name +
      '</span>';
  } else {
    itemStr += '    <span>' + places.address_name + '</span>';
  }

  itemStr += '  <span class="tel">' + places.phone + '</span>' + '</div>';

  el.innerHTML = itemStr;
  el.className = 'item';

  return el;
}
// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position: any, idx: number) {
  const imageSrc =
      'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
    imageSize = new window.kakao.maps.Size(36, 37), // 마커 이미지의 크기
    imgOptions = {
      spriteSize: new window.kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
      spriteOrigin: new window.kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
      offset: new window.kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    },
    markerImage = new window.kakao.maps.MarkerImage(
      imageSrc,
      imageSize,
      imgOptions
    ),
    marker = new window.kakao.maps.Marker({
      position: position, // 마커의 위치
      image: markerImage,
    });

  marker.setMap(map); // 지도 위에 마커를 표출합니다
  markers.push(marker); // 배열에 생성된 마커를 추가합니다

  return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
  for (let i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination: any) {
  const paginationEl = document.getElementById('pagination');
  if (!paginationEl) return;
  const fragment = document.createDocumentFragment();
  let i;

  // 기존에 추가된 페이지번호를 삭제합니다
  removeAllChildNods(paginationEl);
  // while (paginationEl?.hasChildNodes()) {
  //     if(paginationEl)
  //     paginationEl.removeChild (paginationEl.lastChild);
  // }

  for (i = 1; i <= pagination.last; i++) {
    const el = document.createElement('a');
    el.href = '#';
    el.innerHTML = i.toString();

    if (i === pagination.current) {
      el.className = 'on';
    } else {
      el.onclick = (function (i) {
        return function () {
          pagination.gotoPage(i);
        };
      })(i);
    }

    fragment.appendChild(el);
  }
  paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker: any, title: string) {
  const content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

  infowindow.setContent(content);
  infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el: any) {
  while (el.hasChildNodes()) {
    el.removeChild(el.lastChild);
  }
}

function designMapScale(mapEl: HTMLElement | null) {
  if (!mapEl) return;
  const elements = mapEl.children;
  (elements[1] as HTMLElement).style.cssText =
    'position: absolute; cursor: default; z-index: 1; margin: 0px 6px; height: 19px; line-height: 14px; right: 0px; bottom: 0px; color: rgb(0, 0, 0);';
}
