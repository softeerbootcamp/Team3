import { regionSi } from '../../common/constants';

class DropdownRegion {
  toggleElement: HTMLAnchorElement;
  itemsElemnt: HTMLElement;
  constructor() {
    this.toggleElement = document.createElement('a');
    this.itemsElemnt = document.createElement('div');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    this.toggleElement.classList.add('nav-link', 'dropdown-toggle', 'region');
    // this.toggleElement.dataset['bs-toggle'] = 'dropdown';
    this.toggleElement.href = '#';
    this.toggleElement.role = 'button';
    this.toggleElement.ariaHasPopup = 'true';
    this.toggleElement.ariaExpanded = 'false';
    this.toggleElement.innerHTML = '지역을 선택하세요';

    this.itemsElemnt.classList.add('dropdown-menu');
    this.generateDropDownItems();

    // this.toggleElement.addEventListener('click', this.handleToggle.bind(this));
    // this.itemsElemnt.addEventListener('click', this.handleToggle.bind(this));
    document.addEventListener('click', (e) => {
      const target = e.target as Element;
      const dropDown = target?.closest(
        '.dropdown-toggle.region'
      ) as HTMLAnchorElement;
      if (dropDown === null) this.dropDownClose();
      else this.handleToggle();
    });

    Array.from(this.itemsElemnt.children).forEach((item) => {
      item.addEventListener('click', () => {
        this.toggleElement.innerHTML = item.innerHTML;
      });
    });
  }
  generateDropDownItems() {
    for (const key in regionSi) {
      const item = document.createElement('a');
      item.classList.add('dropdown-item');
      item.href = '#'; //key
      item.innerHTML = regionSi[key];
      this.itemsElemnt.appendChild(item);
    }

    this.itemsElemnt.innerHTML += `<div class="dropdown-divider"></div>`;

    const defaultItem = document.createElement('a');
    defaultItem.classList.add('dropdown-item');
    defaultItem.href = '#'; //key
    defaultItem.innerHTML = '지역을 선택하세요';
    this.itemsElemnt.appendChild(defaultItem);
  }
  handleToggle() {
    if (this.toggleElement.classList.contains('show')) this.dropDownClose();
    else this.dropDownOpen();
  }
  dropDownOpen() {
    this.toggleElement.classList.add('show');
    this.toggleElement.setAttribute('aria-expanded', 'true');

    this.itemsElemnt.classList.add('show');
    this.itemsElemnt.setAttribute('data-bs-popper', 'static');
  }
  dropDownClose() {
    this.toggleElement.classList.remove('show');
    this.toggleElement.setAttribute('aria-expanded', 'false');

    this.itemsElemnt.classList.remove('show');
    this.itemsElemnt.removeAttribute('data-bs-popper');
  }
}
export default DropdownRegion;