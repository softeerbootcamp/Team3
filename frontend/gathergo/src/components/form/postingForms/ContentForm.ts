class ContentForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-content');
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <h4 class="leading-text">
                <strong>만남 상세 설명</strong>
            </h4>
            <div class="form-input">
                <textarea class="form-control form-control-lg" id="feedTextarea" rows="3" spellcheck="true"
                    style="height: 277px;" placeholder="만남에 대해서 설명해주세요."></textarea>
            </div>`;
  }
}
export default ContentForm;
