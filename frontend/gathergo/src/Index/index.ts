// import {} from "./store.ts";
// document.addEventListener('click', (e: Event) => {
//   const target = e.target as Element;
//   const profileIcon = target?.closest('.profile-icon');
//   const sidebarBg = target?.closest('#sidebar-bg');
//   if (profileIcon) {
//     sidebarActiveEvent();
//     dropDownCloseEvent();
//   }
//   if (sidebarBg) {
//     sidebarCloseEvent();
//   }
// });

// function sidebarActiveEvent() {
//   document.querySelector<HTMLDivElement>('#sidebar')?.classList.add('active');
//   //TODO: 모든 dropDown 닫아주기
//   // https://jess2.xyz/css/fade-in-out/
// }
// function sidebarCloseEvent() {
//   document
//     .querySelector<HTMLDivElement>('#sidebar')
//     ?.classList.remove('active');
// }
function dropDownEvent() {
  document.addEventListener('click', (e: Event) => {
    const target = e.target as Element;

    //다른 화면 터치시 모든 dropDown 닫기 event
    const dropDown = target?.closest('.nav-item.dropdown') as HTMLLIElement;
    if (dropDown === null) {
      dropDownCloseEvent();
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
    if (dropDownItem) dropDownToggle.innerHTML = dropDownItem?.innerHTML;
  });
}

function dropDownCloseEvent() {
  document
    .querySelectorAll<HTMLLIElement>('.nav-item.dropdown')
    .forEach((element) => {
      dropDownClose(element);
      // dropDownOpen(element);
    });
}

function dropDownOpen(dropDownElement: HTMLLIElement) {
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

const regionSi: string[] = [
  '서울특별시',
  '부산광역시',
  '대구광역시',
  '인천광역시',
  '광주광역시',
  '대전광역시',
  '울산광역시',
  '세종특별자치시',
  '경기도',
  '강원도',
  '충청북도',
  '충청남도',
  '전라북도',
  '전라남도',
  '경상북도',
  '경상남도',
  '제주특별자치도',
];
searchBarEvent();
dropDownEvent();
