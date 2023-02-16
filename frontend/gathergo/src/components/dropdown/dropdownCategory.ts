import { category, Tfilters } from '../../common/constants';
import { getElementIndex } from '../../common/commonFunctions';
import store from '../../store/store';
import { filterSearch } from '../../store/actions';
import { getArticles } from '../../common/Fetches';
class DropdownCategory {
  filtersState: Tfilters;
  toggleElement: HTMLElement;
  itemsElemnt: HTMLElement;
  constructor() {
    this.filtersState = store.getState().filters;
    this.toggleElement = document.createElement('div');
    this.itemsElemnt = document.createElement('div');
    this.render();
    store.subscribe(() => {
      const newState = store.getState().filters;
      if (this.filtersState !== newState) {
        this.filtersState = newState;
        this.toggleElement.innerHTML = category[this.filtersState.categoryId];
      }
    });
  }
  render() {
    this.toggleElement.classList.add('nav-link', 'dropdown-toggle', 'category');

    this.toggleElement.role = 'button';
    this.toggleElement.ariaHasPopup = 'true';
    this.toggleElement.ariaExpanded = 'false';
    this.toggleElement.innerHTML = category[this.filtersState.categoryId];

    this.itemsElemnt.classList.add('dropdown-menu');
    this.generateDropDownItems();

    document.addEventListener('click', async (e) => {
      const target = e.target as Element;
      const dropDown = target?.closest(
        '.dropdown-toggle.category'
      ) as HTMLAnchorElement;
      if (dropDown === null) this.dropDownClose();
      else this.handleToggle();

      const newcategoryId = this.getClickedItemIndex(target);
      if(newcategoryId!==-1) {
        store.dispatch(filterSearch({...this.filtersState, categoryId:newcategoryId}))

        store.dispatch(await getArticles(store.getState().filters));
      }
    });
  }
  getClickedItemIndex(target:Element):number{
    const dropDownItem = target?.closest('.dropdown-item.category') as HTMLLIElement;
      if (dropDownItem === null) return -1;

      let index: number = getElementIndex(dropDownItem) + 1;
      if (dropDownItem.classList.contains('default-item')) index = 0;

    return index;
  }
  generateDropDownItems() {
    const itemWrapper = document.createElement('div');
    itemWrapper.classList.add('dropdown-item-wrapper', 'category');
    for (const key in category) {
      if (key === '0') continue;
      itemWrapper.innerHTML += `<div class="dropdown-item category">
        <img style="width: 25px;" src="../../../assets/category/icons/${key}.png" alt="">
        <span>${category[key]}</span>
      </div>`;
    }

    this.itemsElemnt.append(itemWrapper);
    this.itemsElemnt.innerHTML += `<div class="dropdown-divider"></div>`;

    const defaultItem = document.createElement('a');
    defaultItem.classList.add('dropdown-item', 'default-item', 'category');

    defaultItem.innerHTML = '카테고리를 선택하세요';
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
export default DropdownCategory;
