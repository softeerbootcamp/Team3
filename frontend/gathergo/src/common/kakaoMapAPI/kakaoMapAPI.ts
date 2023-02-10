/* eslint-disable @typescript-eslint/no-explicit-any */
declare global {
  interface Window {
    kakao: any;
  }
}

const geocoder = new window.kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다
export default function formMapGenerator(
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
