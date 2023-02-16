import { TuserInfo } from '../../common/constants';
import store from '../../store/store';
import { changeProfileTab, changeUserIntroduction } from '../../store/actions';
import { changeUserProfileImg, changeUserProfileIntroduction } from '../../common/Fetches';

class tabEditMain {
  element: HTMLDivElement;
  userEditInfo: TuserInfo;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('tabcontent');
    this.element.classList.add('profile');
    this.element.id = 'profile-fix';

    this.userEditInfo = store.getState().userInfo;
    store.subscribe(() => {
      this.render();
    });
    this.render();

    store.subscribe(()=>{
        this.render;
    })
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
                    <img class = "profile-fix-image" id = "profile-fix-image" src = ${this.userEditInfo.profilePath} alt = "USER">
                    <div class = "image-upload">
                        <input type = "file" accept = ".png" id = "profile-file-input" style = "margin : 0 0 0 2.5rem;">
                        <!-- <label for="file-input">
                            <img class = "profile-fix-image-button" src = "../../assets/profileEdit.svg">
                        </label>
                        <input style="display:none" id = "file-input" type = "file"/> -->
                    </div>
                </div>
            </div>
            <div class = "profile-textarea" id = "userid">
                <div class = "profile-textarea-text" style = "width : 6.25rem">아이디</div>
                <input type = "text" id = "profile-id-edit" value = ${this.userEditInfo.userId} class = "form-control" readonly>
            </div>
            <div class = "profile-textarea" id = "name">
                <div class = "profile-textarea-text" style = "width : 6.25rem">이름</div>
                <input type = "text" value = ${this.userEditInfo.userName} class = "form-control" readonly>
            </div>

            <div class = "profile-textarea" id = "email">
                <div class = "profile-textarea-text" style = "width : 6.25rem">이메일</div>
                <input type = "text" id = "profile-email-edit" value = ${this.userEditInfo.email} class = "form-control" readonly>
            </div>
            <div class = "profile-textarea" id = "sentence">
                <div class = "profile-textarea-text" style = "width : 6.25rem; height : 6.25rem;">
                    한 줄 소개
                </div>
                <textarea id = "profile-desc-edit" value = "hihi" class = "form-control" style = "height : 6.25rem">${this.userEditInfo.introduction}</textarea>
            </div>
            <div class = "profile-button">
                <button type = "button" id = "profile-edit-button" class = "btn btn-primary register-button">
                    변경내용 저장
                </button>
                <div style = "width : 5%"></div>
                <button type = "button" id = "profile-edit-cancel-button" class = "btn btn-outline-danger register-button">
                    취소
                </button>
            </div>
        </div>
    </div>`;

    this.profileEditButton();
    this.profileEditCancelButton();
    this.addEventProfileEdit();
  }

  profileEditCancelButton() {
    const profileCancel = this.element.querySelector(
      '#profile-edit-cancel-button'
    ) as HTMLElement;
    profileCancel.addEventListener('click', () => {
      store.dispatch(changeProfileTab(0));
    });
  }
  profileEditButton() {
    const profileEdit = this.element.querySelector('#profile-edit-button')
    const descEdit = this.element.querySelector('#profile-desc-edit') as HTMLTextAreaElement;
    profileEdit?.addEventListener('click',async()=>{
        const profileFileEdit = this.element.querySelector(
            '#profile-fix-image'
            ) as HTMLImageElement;
        store.dispatch(
          await changeUserProfileImg(
            profileFileEdit.src,
            this.userEditInfo.uuid
          )
        );
        store.dispatch(
            await changeUserProfileIntroduction(descEdit.value,this.userEditInfo.uuid)
        )
        store.dispatch(changeProfileTab(0))
    })

  }
    async addEventProfileEdit(){
    const profileFileEdit = this.element.querySelector('#profile-file-input')
    profileFileEdit?.addEventListener('change',()=>{
        const value = profileFileEdit as HTMLInputElement;
    if(value.files && value.files[0]){
        const reader = new FileReader();
        reader.onload = function(e){
            const imageDom = document.getElementById('profile-fix-image') as HTMLImageElement;
            imageDom.src = e.target?.result as string;
        }
        reader.readAsDataURL(value.files[0])
        }
    })
}
}
export default tabEditMain;
