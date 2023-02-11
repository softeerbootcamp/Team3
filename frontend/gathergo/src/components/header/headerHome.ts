// import {store} from '../store/store';

import DropdownCategory from '../dropdown/DropdownCategory';
import DropdownRegion from '../dropdown/DropdownRegion';
import HeaderHomeNav from './headerHomeNav';
import HeaderSearchSticky from './HeaderSearchSticky';

// import { $ } from '../common/utils/querySelctor';
class HeaderHome {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('headerhome');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.element.innerHTML = `
    <nav class="header navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-lg">
        <div class="navbar-brand-wrapper">
          <img class="navbar-brand" src="./assets/Logo/GatherGoLogo.svg" alt="GatherGo" />
        </div>
        <div id="header-nav" class="container-fluid"></div>
      </div>
      <ul class="navbar-nav navbar-filter me-auto">
        <li id="dropdown-region" class="nav-item dropdown region"></li>
        <li id="dropdown-category" class="nav-item dropdown category"></li>
        <form class="d-flex">
          <input class="form-control me-sm-2" type="search" placeholder="Search" />
          <button class="btn btn-primary my-2 my-sm-0" type="submit">
            Search
          </button>
        </form>
      </ul>
    </nav>
    `;
    // this.element.addEventListener('click', (e) => {
    //   console.log('lj');
    //   //   const target = e.target as Element;
    //   //   const aElement = target?.closest('a') as HTMLAnchorElement;
    //   //   if (!(aElement instanceof HTMLAnchorElement)) return;
    //   //   e.preventDefault();
    //   history.pushState(
    //     { data: 'pushpush' },
    //     'title을 pushState로',
    //     '/pushpush'
    //   );
    // });
    const headerHomeNav = new HeaderHomeNav(true);
    this.element.querySelector('#header-nav')?.appendChild(headerHomeNav.element);

    const dropdownRegion = new DropdownRegion();
    const dropdownRegionLI = this.element.querySelector('#dropdown-region');
    dropdownRegionLI?.appendChild(dropdownRegion.toggleElement);
    dropdownRegionLI?.appendChild(dropdownRegion.itemsElemnt);

    const dropDownCategory = new DropdownCategory();
    const dropDownCategoryLI = this.element.querySelector('#dropdown-category');
    dropDownCategoryLI?.appendChild(dropDownCategory.toggleElement);
    dropDownCategoryLI?.appendChild(dropDownCategory.itemsElemnt);

    const searchSticky = new HeaderSearchSticky();
    searchSticky.searchBarEvent(this.element.querySelector('.header'));
    this.element.appendChild(searchSticky.element);
  }
}

export default HeaderHome;
