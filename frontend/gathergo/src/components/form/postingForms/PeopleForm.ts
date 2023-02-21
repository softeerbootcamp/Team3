class PeopleForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-people');
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <h4 class="leading-text">
            <strong>모집 인원</strong>
        </h4>
        <div class="form-input">
            <input class="form-control form-control-lg" type="number" min = "1" placeholder="모집 인원을 설정해주세요." id="inputLarge">
        </div>`;
  }
}
export default PeopleForm;
