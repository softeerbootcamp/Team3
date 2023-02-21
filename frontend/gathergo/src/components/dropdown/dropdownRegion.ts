import { getElementIndex } from '../../common/commonFunctions';
import { regionSi, Tfilters } from '../../common/constants';
import { getArticles } from '../../common/Fetches';
import { filterSearch} from '../../store/actions';
import store from '../../store/store';

class DropdownRegion {
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
        this.toggleElement.innerHTML = regionSi[this.filtersState.regionId];
      }
    });
  }
  render() {
    this.toggleElement.classList.add('nav-link', 'dropdown-toggle', 'region');
    
    this.toggleElement.role = 'button';
    this.toggleElement.ariaHasPopup = 'true';
    this.toggleElement.ariaExpanded = 'false';
    this.toggleElement.innerHTML = regionSi[this.filtersState.categoryId];

    this.itemsElemnt.classList.add('dropdown-menu');
    this.generateDropDownItems();

    
    document.addEventListener('click', async (e) => {
      const target = e.target as Element;
      const dropDown = target?.closest(
        '.dropdown-toggle.region'
      ) as HTMLAnchorElement;
      if (dropDown === null) this.dropDownClose();
      else this.handleToggle();
      
      const newregionId = this.getClickedItemIndex(target);
      if(newregionId!==-1) {
        store.dispatch(filterSearch({...this.filtersState, regionId:newregionId}))
        store.dispatch(await getArticles(store.getState().filters));
      }
    });
  }
  getClickedItemIndex(target:Element):number{
    const dropDownItem = target?.closest('.dropdown-item.regionSi') as HTMLLIElement;
    if (dropDownItem === null) return -1;
    let index: number = getElementIndex(dropDownItem) + 1;
    if (dropDownItem.classList.contains('default-item')) index = 0;

    return index;
  }
  generateDropDownItems() {
    for (const key in regionSi) {
      if (key === '0') continue;
      const item = document.createElement('div');
      item.classList.add('dropdown-item','regionSi');

      item.innerHTML = regionSi[key];
      this.itemsElemnt.appendChild(item);
    }

    this.itemsElemnt.innerHTML += `<div class="dropdown-divider"></div>`;

    const defaultItem = document.createElement('div');
    defaultItem.classList.add('dropdown-item','default-item','regionSi');

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
