import DropdownCategory from '../../dropdown/DropdownCategory';

class CategoryForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-category');
    this.render();
  }
  render() {
    this.element.innerHTML = `<h4 class="leading-text">
        <strong>상세 관심사</strong>
    </h4>`;
    this.element.appendChild(this.genertateInputCategoryDropdonw());
  }
  genertateInputCategoryDropdonw() {
    const dropdown = document.createElement('div');
    dropdown.classList.add(
      'form-input',
      'nav-item',
      'dropdown',
      'category',
      'form-control-lg'
    );
    const dropDownCategory = new DropdownCategory();

    dropdown?.appendChild(dropDownCategory.toggleElement);
    dropdown?.appendChild(dropDownCategory.itemsElemnt);

    return dropdown;
  }
}
export default CategoryForm;
