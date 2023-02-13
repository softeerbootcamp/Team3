class ErrorModal {
    element: HTMLElement;
    message:string|undefined
    constructor(message:string|undefined) {
      this.message=message;
      this.element = document.createElement('div');
      this.render();
      // store.subscribe(() => this.render());
    }
    render(){
      this.element.innerHTML = `<div class = "loginCheck-modal-container">
      <div class = "loginCheck-modal-background">
          <div class = "loginCheck-modal">
              <div>이 기능은 로그인이 필요합니다."</div>
              <div>로그인 하시겠습니까?"<div>
              <div style = "display : flex; justify-content : space-between">
                  <button id = "check-modal-cancel">취소</button>
                  <button>로그인</button>
              </div>
          </div>
      </div>
  </div>`
    }
}
export default ErrorModal