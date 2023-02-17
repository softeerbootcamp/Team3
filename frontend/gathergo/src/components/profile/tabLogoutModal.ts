
import store from '../../store/store';
import { changeProfileTab } from '../../store/actions';

class tabLogoutModal {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.id = 'logout-modal';

    this.render();
  }
  render() {
    this.element.innerHTML = `
    <div class = "logout-modal-content">
            <h4 style = "margin-top : 2rem">
                <strong>로그아웃 하시겠습니까?</strong></h4> 
            <div class = "logout-modal-button">
                <button type="button" class="btn btn-warning" id = "button-logout">로그아웃</button>
                <div style="width : 2.5rem"></div>
                <button type="button" class="btn btn-outline-danger" id = "button-cancel">취소</button>
            </div>   
        </div>
    </div>`;

    this.logoutModalButton()
  }

    logoutModalButton(){
    const cancelButton = this.element.querySelector('#button-cancel') as HTMLDivElement;
    cancelButton.addEventListener('click',()=>{
        store.dispatch(changeProfileTab(0));
    })
    }
}
export default tabLogoutModal;
