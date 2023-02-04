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
//   {
function dropDownEvent() {
    document.addEventListener('click', function (e) {
        var target = e.target;
        //다른 화면 터치시 모든 dropDown 닫기 event
        var dropDown = target === null || target === void 0 ? void 0 : target.closest('.nav-item.dropdown');
        if (dropDown === null) {
            dropDownCloseEvent();
            return;
        }
        //드롭다운 토글 선택시 열고 닫기 event
        var dropDownToggle = dropDown.querySelector('.dropdown-toggle');
        if (dropDownToggle === null)
            return;
        if (dropDownToggle === null || dropDownToggle === void 0 ? void 0 : dropDownToggle.classList.contains('show'))
            dropDownClose(dropDown);
        else
            dropDownOpen(dropDown);
        //item 선택
        var dropDownItem = target === null || target === void 0 ? void 0 : target.closest('.dropdown-item');
        if (dropDownItem) {
            dropDownToggle.innerHTML = dropDownItem === null || dropDownItem === void 0 ? void 0 : dropDownItem.innerHTML;
        }
    });
}
function dropDownCloseEvent() {
    document
        .querySelectorAll('.nav-item.dropdown')
        .forEach(function (element) {
        dropDownClose(element);
        // dropDownOpen(element);
    });
}
function dropDownOpen(dropDownElement) {
    var toggleElement = dropDownElement.querySelector('.dropdown-toggle');
    if (toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.classList.contains('show'))
        return;
    toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.classList.add('show');
    toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.setAttribute('aria-expanded', 'true');
    var menuElement = dropDownElement.querySelector('.dropdown-menu');
    if (menuElement == null)
        return;
    // menuElement.dataset['bs-popper'] = 'static';
    menuElement.classList.add('show');
    menuElement.setAttribute('data-bs-popper', 'static');
    // setDataset['bs-popper'] = 'static';
}
function dropDownClose(dropDownElement) {
    var toggleElement = dropDownElement.querySelector('.dropdown-toggle');
    if (!(toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.classList.contains('show')))
        return;
    toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.classList.remove('show'); //add()
    toggleElement === null || toggleElement === void 0 ? void 0 : toggleElement.setAttribute('aria-expanded', 'false'); //true
    var menuElement = dropDownElement.querySelector('.dropdown-menu');
    menuElement === null || menuElement === void 0 ? void 0 : menuElement.classList.remove('show');
    // delete menuElement?.dataset['bs-popper'];
    menuElement === null || menuElement === void 0 ? void 0 : menuElement.removeAttribute('data-bs-popper');
    // menuElement?.setAttribute('data-bs-popper', '');
    // const v = delete menuElement?.dataset['bs-popper'];
    // console.log(v);
}
//header search바
function searchBarEvent() {
    var header = document.querySelector('.header');
    var searchContainer = document.querySelector('.search-container');
    window.onscroll = function () {
        if (header !== null && header.getBoundingClientRect().bottom <= 0) {
            searchContainer === null || searchContainer === void 0 ? void 0 : searchContainer.classList.add('show');
        }
        else {
            searchContainer === null || searchContainer === void 0 ? void 0 : searchContainer.classList.remove('show');
        }
    };
}
var regionSi = [
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
