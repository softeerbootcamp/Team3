import {
  deleteComment,
  fetchCloseMeeting,
  fetchJoin,
  fetchJoinCancel,
  fetchLogout,
} from '../../common/Fetches';
import Navigate from '../../common/utils/navigate';
import { setModal } from '../../store/actions';
import store from '../../store/store';

class NotiModal {
  element: HTMLElement;
  $container: HTMLElement | null;
  navigate: Navigate;
  type: string;
  message: string;
  need2btns: boolean;
  btnMessage: string;
  constructor(
    $container: HTMLElement | null,
    navigate: Navigate,
    type: string
  ) {
    this.$container = $container;
    this.navigate = navigate;
    this.type = type;
    this.message = this.setMessage(type);
    this.need2btns = this.setNeed2btns(type);
    this.btnMessage = this.setBtnMessage(type);
    this.element = document.createElement('div');
    this.render();
  }
  render() {
    if (this.$container === null) return;
    this.element.innerHTML = `<div class = "noti-modal-container">
            <div class = "noti-modal-background">
                <div class = "noti-modal">
                    <div class = "noti-modal-header">알림</div>
                        <div class = "noti-modal-message">${this.message}</div>
                        <div style = "display : flex; justify-content : end;">
                            <button type="button" class="btn btn-primary noti-modal-mainbtn">
                              ${this.btnMessage}
                           </button>
                            ${
                              this.need2btns
                                ? `<div class = "noti-modal-button-divider"></div>
                            <button type="button" class="btn btn-outline-secondary noti-modal-cancel">취소</button>`
                                : ``
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
    this.$container.appendChild(this.element);
    this.cancelBtnEvent();
    this.modalBgEvent();
    this.mainBtnEvent();
  }
  cancelBtnEvent() {
    const cancelButton = this.$container?.querySelector('.noti-modal-cancel');
    cancelButton?.addEventListener('click', () => {
      this.modalClose();
    });
  }
  modalBgEvent() {
    const modalBg = document.querySelector('.noti-modal-background');
    modalBg?.addEventListener('click', (e) => {
      if (e.target == modalBg) this.modalClose();
    });
  }

  mainBtnEvent() {
    const modalButton = this.$container?.querySelector('.noti-modal-mainbtn');
    modalButton?.addEventListener(
      'click',
      this.mainBtnCallback(this.type).bind(this)
    );
  }
  mainBtnCallback(type: string) {
    switch (type) {
      case 'SIGNUP_SUCCESS':
        return () => {
          history.replaceState(store.getState(), '', '/login?action=login');
          this.modalClose();

          document.body?.removeAttribute('class');
          document
            .querySelector('.login-content-signin')
            ?.classList.remove('ng-hide');
          document
            .querySelector('.login-content-signup')
            ?.classList.add('ng-hide');
          document
            .querySelector('.login-switcher-signin')
            ?.classList.add('ng-hide');
          document
            .querySelector('.login-switcher-signup')
            ?.classList.remove('ng-hide');
        };
      case 'LOGOUT':
        return async () => {
          this.modalClose();
          store.dispatch(await fetchLogout());
        };
      case 'NEED_LOGIN':
        return () => {
          this.navigate.to('/login');

          document.body?.removeAttribute('class');
        };
      case 'EDIT_MEETING':
        return () => {
          const queryString = new URLSearchParams(window.location.search);
          const feed = queryString.get('feed');
          this.navigate.to(`/post?feed=${feed}`);

          document.body?.removeAttribute('class');
        };

      case 'POSTING_SUCCESS':
        return () => {
          this.navigate.to('/');
        };
      case 'POSTING_EDIT_SUCCESS':
        return () => {
          this.navigate.to('/');
        };
      case 'DELETE_COMMENT':
        return async () => {
          store.dispatch(
            await deleteComment(
              store.getState().readingCard?.uuid,
              store.getState().deleteCommentuuid
            )
          );
          this.modalClose();
          store.dispatch(setModal('DELETE_COMMENT_SUCCESS'));
        };
      case 'JOIN':
        return async () => {
          this.modalClose();
          store.dispatch(await fetchJoin(store.getState().readingCard?.uuid));
          const isError = store.getState().error;

          if (!isError) store.dispatch(setModal('JOIN_SUCCESS'));
        };
      case 'JOIN_CANCEL':
        return async () => {
          store.dispatch(
            await fetchJoinCancel(store.getState().readingCard?.uuid)
          );
          this.modalClose();
          store.dispatch(setModal('JOIN_CANCEL_SUCCESS'));
        };
      case 'CLOSE_MEETING':
        return async () => {
          await fetchCloseMeeting(store.getState().readingCard?.uuid);
          this.modalClose();
          this.navigate.to('/');

          document.body?.removeAttribute('class');
        };
      case 'ERROR':
        return () => {
          this.modalClose();
        };
      default: {
        return this.modalClose;
      }
    }
  }

  modalClose() {
    if (this.type == 'POSTING_SUCCESS' || this.type == 'POSTING_EDIT_SUCCESS')
      this.navigate.to('/');
    store.dispatch(setModal(''));
    this.element.remove();
  }
  setMessage(type: string): string {
    switch (type) {
      case 'LOGOUT':
        return '로그아웃 하시겠습니까?';
      case 'INPUT_CATEGORY':
        return '카테고리를 선택해주세요';
      case 'INPUT_TITLE':
        return '만남 이름을 입력해주세요';
      case 'INPUT_TIME':
        return '시간을 설정해주세요';
      case 'INPUT_TIME_BEFORE':
        return '만남 시간은 현재 시간보다 이전일 수 없습니다.';
      case 'INPUT_LOCATION':
        return '만남 장소를 설정해주세요';
      case 'INPUT_PEOPLE':
        return '모집 인원을 설정해주세요';
      case 'INPUT_DETAIL':
        return '상세 내용을 입력해주세요';
      case 'NEED_LOGIN':
        return `로그인이 필요합니다.  로그인 하시겠습니까?`;
      case 'JOIN':
        return '만남에 참가하시겠습니까?';
      case 'JOIN_SUCCESS':
        return '참가에 성공했습니다. 감사합니다.';
      case 'EDIT_MEETING':
        return '만남을 수정하시겠습니까?';
      case 'CLOSE_MEETING':
        return '만남 모집을 마감합니다.<br> 다른 사람들은 더 이상 확인할 수 없습니다.';
      case 'DELETE_COMMENT':
        return '댓글을 삭제하시겠습니까?';
      case 'DELETE_COMMENT_SUCCESS':
        return '댓글이 삭제되었습니다.';
      case 'SIGNUP_SUCCESS':
        return '회원가입이 성공했습니다. 다시 로그인 해주세요.';
      case 'JOIN_CANCEL':
        return '이미 참가 신청한 만남입니다.<br>참가를 취소하시겠습니까?';
      case 'JOIN_CANCEL_SUCCESS':
        return '참가가 취소되었습니다.';
      case 'POSTING_SUCCESS':
        return '만남이 성공적으로 생성되었습니다. 감사합니다.';
      case 'POSTING_EDIT_SUCCESS':
        return '만남이 성공적으로 수정되었습니다. 감사합니다.';
      case 'ERROR': {
        if (store.getState().error !== null)
          return store.getState().error?.message as string;
        const message = '알 수 없는 에러가 발생했습니다.';
        return message;
      }
      default:
        return '새로 고침을 해주세요';
    }
  }
  setNeed2btns(type: string): boolean {
    switch (type) {
      case 'NEED_LOGIN':
        return true;
      case 'LOGOUT':
        return true;
      case 'JOIN':
        return true;
      case 'EDIT_MEETING':
        return true;
      case 'CLOSE_MEETING':
        return true;
      case 'DELETE_COMMENT':
        return true;
      case 'JOIN_CANCEL':
        return true;
      default:
        return false;
    }
  }
  setBtnMessage(type: string): string {
    switch (type) {
      case 'NEED_LOGIN':
        return '로그인';
      case 'LOGOUT':
        return '로그아웃';
      case 'JOIN':
        return '참가';
      case 'EDIT_MEETING':
        return '수정';
      case 'CLOSE_MEETING':
        return '마감하기';
      case 'DELETE_COMMENT':
        return '삭제';
      case 'JOIN_CANCEL':
        return '참가 취소';
      default:
        return '확인';
    }
  }
}
export default NotiModal;
