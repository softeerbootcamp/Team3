import { getElementIndex } from "../commonFunctions";

/* eslint-disable @typescript-eslint/no-explicit-any */
declare global {
  interface Window {
    kakao: any;
  }
}

const geocoder = new window.kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
export  function modalMapGenerator(
  doromyeong = '서울 강남구 강남대로62길 23'
) {
  geocoder.addressSearch(doromyeong, function (result: any, status: any) {
    // 정상적으로 검색이 완료됐으면
    if (status === window.kakao.maps.services.Status.OK) {
      const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);

      //지도를 담을 영역의 DOM 레퍼런스
      const container = document.getElementById('map-api-readOnly');

      //지도를 생성할 때 필요한 기본 옵션
      const options = {
        center: coords, //지도의 중심좌표.
        level: 3, //지도의 레벨(확대, 축소 정도)
      };

      //지도 생성 및 객체 리턴
      const map = new window.kakao.maps.Map(container, options);

      // 결과값으로 받은 위치를 마커로 표시합니다
      const marker = new window.kakao.maps.Marker({
        map: map,
        position: coords,
      });

      // 인포윈도우로 장소에 대한 설명을 표시합니다
      const infowindow = new window.kakao.maps.InfoWindow({
        content:
          '<div style="width:150px;text-align:center;padding:6px 0;">만남 장소</div>',
      });
      infowindow.open(map, marker);
    }
  });
}

export function formMapGenerator(){

// 마커를 담을 배열입니다
let markers: any[] = [];

const mapContainer = document.getElementById('form-map'), // 지도를 표시할 div
  mapOption = {
    center: new window.kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };

// 지도를 생성합니다
const map = new window.kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
const ps = new window.kakao.maps.services.Places();

// 지도 우측 하단 스케일 위치변경
designMapScale(mapContainer);

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
  const keyword = document.querySelector<HTMLInputElement>('#keyword')?.value;

  if (keyword && !keyword.replace(/^\s+|\s+$/g, '')) {
    alert('키워드를 입력해주세요!');
    return false;
  }

  // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
  ps.keywordSearch(keyword, placesSearchCB);

  placesListClickEvent();
  return true;
}
function placesListClickEvent() {
  const placesList = document.querySelector<HTMLElement>('#placesList');
  placesList?.addEventListener('click', (e) => {
    const target = e.target as Element;
    const placeItem = target?.closest('.item') as HTMLLIElement;
    const _i = getElementIndex(placeItem);

    placeClickSetMap(markers[_i].n);

    placeClickSetInput(placeItem);
  });
}

function placeClickSetMap(coords: { La: number; Ma: number }) {
  // 선택된 장소 위치를 기준으로 지도 범위를 재설정합니다
  map.setCenter(coords);
  map.setLevel(2, { anchor: coords }, { animate: { duration: 500 } });
}
function placeClickSetInput(placeItem: HTMLLIElement) {
  // 선택된 장소의 주소를 input.value에 재설정합니다
  const addressValue =
    placeItem.querySelector<HTMLSpanElement>('.setting-address')?.innerHTML;
  const addressInputEle = document.querySelector<HTMLInputElement>(
    '#addressReadOnlyInput'
  );
  if (addressValue && addressInputEle) addressInputEle.value = addressValue;
}
function markerClickSetInput(adressName: string) {
  const addressInputEle = document.querySelector<HTMLInputElement>(
    '#addressReadOnlyInput'
  );
  if (addressInputEle) addressInputEle.value = adressName;
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
    bounds = new window.kakao.maps.LatLngBounds();

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
    
      window.kakao.maps.event.addListener(marker, 'click', function () {
        placeClickSetMap(marker.n);
        markerClickSetInput(places[i].address_name);
      });
    

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
      '    <span class="setting-address">' +
      places.road_address_name +
      '</span>' +
      '   <span class="jibun gray">' +
      places.address_name +
      '</span>';
  } else {
    itemStr +=
      '<span class="setting-address">' + places.address_name + '</span>';
  }

  itemStr += '<span class="tel">' + places.phone + '</span>' + '</div>';

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

function keywordEvent() {
  const keyword = document.querySelector<HTMLInputElement>('#keyword');
  keyword?.addEventListener('keydown', (e) => {
    if (e.key == 'Enter') {
      searchPlaces();
    }
  });
  const keywordBtn = document.querySelector<HTMLElement>('#keywordBtn');
  keywordBtn?.addEventListener('click', searchPlaces);
}
keywordEvent();

}
export function showMapEvent() {
  const mapToggle = document.querySelector<HTMLDivElement>('.map-toggle');
  const map = document.querySelector<HTMLDivElement>('#form-map-wrapper');
  if (!mapToggle || !map) return;
  mapToggle?.addEventListener('click', () => toggleMap(map));
}
function toggleMap(map: HTMLDivElement) {
  if (!map.classList.contains('show')) showMap(map);
  else hideMap(map);
}
function hideMap(map: HTMLDivElement) {
  map.classList.remove('show');
}
function showMap(map: HTMLDivElement) {
  map.classList.add('show');
}
