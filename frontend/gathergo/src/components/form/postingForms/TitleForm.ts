class TitleForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-title');
    this.render();
  }
  render() {
    this.element.innerHTML = `<h4 class="leading-text">
        <strong>만남 이름</strong>
    </h4>
    <div class="form-input">
        <input class="form-control form-control-lg" type="text" placeholder="만남 이름을 설정해주세요." id="inputLarge"
        value="">
    </div>`;
  }
}
export default TitleForm;
