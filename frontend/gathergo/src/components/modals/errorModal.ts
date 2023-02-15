class ErrorModal {
  element: HTMLElement;
  message: string | undefined;
  constructor(message: string | undefined) {
    this.message = message;
    this.element = document.createElement('div');
    this.render();
    // store.subscribe(() => this.render());
  }

  checkModalBackgroundEvent($checkModal: string, element: HTMLElement) {
    //$checkModal은 그냥 string이고 이름 문자열일 뿐입니다.
    const checkModalContainerName = $checkModal + '-modal-container';
    const checkModalContainer = element.getElementsByClassName(
      checkModalContainerName
    )[0];
    const checkModalBackgroundName = $checkModal + '-modal-background';
    const checkModalBackground = element.getElementsByClassName(
      checkModalBackgroundName
    )[0];
    checkModalBackground.addEventListener('click', (e) => {
      if (e.target == checkModalBackground) checkModalContainer.remove();
    });
  }

    ErrorModalButton($checkModal: string, element: HTMLElement) {
    //$checkModal은 그냥 string이고 이름 문자열일 뿐입니다.
        const checkModalContainerName = $checkModal + '-modal-container';
        const checkModalContainer = element.getElementsByClassName(
        checkModalContainerName
        )[0];
        const errorModalButtonName = $checkModal + '-modal-button';
        const errorModalButton = element.getElementsByClassName(
            errorModalButtonName
        )[0];
        errorModalButton.addEventListener('click', (e) => {
        if (e.target == errorModalButton) checkModalContainer.remove();
        });
  }

  render() {
    this.element.innerHTML = `<div class = "error-modal-container">
      <div class = "error-modal-background">
          <div class = "error-modal">
            <div class = "error-modal-header">에러!</div>
            <div class = "error-modal-divider"></div>
                <div class = "error-modal-message">${this.message}</div>
                <div style = "display : flex; justify-content : end;">
                    <button class = "error-modal-button">확인</button>
                </div>
              </div>
          </div>
      </div>
  </div>`;

    this.checkModalBackgroundEvent('error', this.element);
    this.ErrorModalButton('error',this.element)
  }
}
export default ErrorModal