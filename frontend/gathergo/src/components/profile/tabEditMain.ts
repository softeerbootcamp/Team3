import { TuserInfo } from '../../common/constants';
import store from '../../store/store';

class tabEditMain {
  element: HTMLDivElement;
    userEditInfo : TuserInfo;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('tabcontent');
    this.element.classList.add('profile');
    this.element.id = 'profile-fix';

    this.userEditInfo = store.getState().userInfo;
    store.subscribe(()=>{
        this.render();
    })
    this.render();
  }
  render() {
    this.element.innerHTML = `    
    <div class = "profile-userInfo">
        <h2>
            <strong>프로필 편집</strong>
        </h2>
    </div>
    <div class = "profile-fix-main">
        <div class = "profile-context">
            <div class = "profile-img-name">
                <div class = "image-button">
                    <img class = "profile-fix-image" id = "profile-fix-image" src = "../../assets/userProfileImg.jpeg" alt = "USER">
                    <div class = "image-upload">
                        <input type = "file" accept = ".png" id = "file-input" style = "margin : 0 0 0 2.5rem;" onchange="addEventProfileEdit(this)">
                        <!-- <label for="file-input">
                            <img class = "profile-fix-image-button" src = "../../assets/profileEdit.svg">
                        </label>
                        <input style="display:none" id = "file-input" type = "file"/> -->
                    </div>
                </div>
            </div>
            <div class = "profile-textarea" id = "name">
                <div class = "profile-textarea-text" style = "width : 6.25rem">이름</div>
                <input type = "text" value = ${this.userEditInfo.userName} class = "form-control" readonly>
            </div>
            <div class = "profile-textarea" id = "nickname">
                <div class = "profile-textarea-text" style = "width : 6.25rem">아이디</div>
                <input type = "text" value = ${this.userEditInfo.userId} class = "form-control" readonly>
            </div>
            <div class = "profile-textarea" id = "email">
                <div class = "profile-textarea-text" style = "width : 6.25rem">이메일</div>
                <input type = "text" value = ${this.userEditInfo.email} class = "form-control" readonly>
            </div>
            <div class = "profile-textarea" id = "sentence">
                <div class = "profile-textarea-text" style = "width : 6.25rem; height : 6.25rem;">
                    한 줄 소개
                </div>
                <textarea onkeydown="resize()" onkeyup="resize()" value = "hihi" class = "form-control" style = "height : 6.25rem">${this.userEditInfo.userDesc}</textarea>
            </div>
            <div class = "profile-button">
                <button type = "button" class = "btn btn-primary register-button">
                    변경내용 저장
                </button>
                <div style = "width : 5%"></div>
                <button type = "button" id = "profileCancelButton" class = "btn btn-outline-danger register-button">
                    취소
                </button>
            </div>
        </div>
    </div>`;
  }
}
export default tabEditMain;
