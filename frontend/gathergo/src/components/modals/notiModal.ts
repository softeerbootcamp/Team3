import { setModal } from "../../store/actions";
import store from "../../store/store";

class NotiModal {
  element: HTMLElement;
$container:HTMLElement | null

  type: string;
  message: string;
  btnMessage: string;
  constructor($container:HTMLElement |null, type: string) {
    this.$container =$container;
    this.type = type;
    this.message = this.setMessage(type);
    this.btnMessage = this.setBtnMessage(type);
    this.element = document.createElement('div');
    this.render();
    // store.subscribe(() => this.render());
  }
  render() {
    if(this.$container === null) return
    this.element.innerHTML = `<div class = "noti-modal-container">
            <div class = "noti-modal-background">
                <div class = "noti-modal">
                    <div class = "noti-modal-header">알림</div>
                        <div class = "noti-modal-message">${this.message}</div>
                        <div style = "display : flex; justify-content : end;">
                            <button type="button" class="btn btn-primary">${this.btnMessage}</button>
                            <div class = "noti-modal-button-divider"></div>
                            <button type="button" class="btn btn-outline-secondary noti-modal-cancel">취소</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
        this.$container.appendChild(this.element)
        this.cancelBtnEvent();
        this.modalBgEvent();
  }
  cancelBtnEvent(){
    const cancelButton = this.$container?.querySelector('.noti-modal-cancel')
    cancelButton?.addEventListener('click',()=>{
      this.modalClose();
    })
  }
  modalBgEvent(){
    const modalBg = document.querySelector('.noti-modal-background');
    modalBg?.addEventListener('click', (e) => {
      if (e.target == modalBg)
        this.modalClose();
    });
}
modalClose(){
    store.dispatch(setModal(""))
    this.element.remove();
}
  setMessage(type: string): string {
    switch (type) {
      case 'NEED_LOGIN':
        return `로그인이 필요합니다.   로그인 하시겠습니까?`;
      case 'JOIN':
        return '만남에 참가하시겠습니까?';

      case 'EDIT_MEETING':
        return '만남을 수정하시겠습니까?';

      case 'DELETE_MEETING':
        return '만남을 삭제하시겠습니까?';

      case 'DELETE_COMMENT':
        return '댓글을 삭제하시겠습니까?';

      case 'CANCEL_JOIN':
        return '만남 참가를 취소하시겠습니까?';
      default:
        return 'default';
    }
  }
  setBtnMessage(type: string): string {
    switch (type) {
      case 'NEED_LOGIN':
        return '로그인';
      case 'JOIN':
        return '참가';

      case 'EDIT_MEETING':
        return '수정';

      case 'DELETE_MEETING':
        return '삭제';

      case 'DELETE_COMMENT':
        return '삭제';

      case 'CANCEL_JOIN':
        return '참가 취소';
      default:
        return 'default';
    }
  }
}
export default NotiModal;
