const loginCheck = `
    <div class = "loginCheck-modal-container">
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
    </div>
`;
const joinCheck = `
    <div class = "joinCheck-modal-container">
        <div class = "joinCheck-modal-background">
            <div class = "joinCheck-modal">
                <input type="text" value="참가 하시겠습니까?." readonly>
                <div style = "display : flex; justify-content : space-between">
                    <button id = "check-modal-cancel">취소</button>
                    <button>참가</button>
                </div>
            </div>
        </div>
    </div>
`;
const gatheringEdit = `
    <div class = "gatheringEdit-modal-container">
        <div class = "gatheringEdit-modal-background">
            <div class = "gatheringEdit-modal">
                <input type="text" value="모임을 수정하시겠습니까?." readonly>
                <div style = "display : flex; justify-content : space-between">
                    <button id = "check-modal-cancel">취소</button>
                    <button>수정</button>
            </div>
        </div>
    </div>
`;
const gatheringDelete = `
    <div class = "gatheringDelete-modal-container">
        <div class = "gatheringDelete-modal-background">
            <div class = "gatheringDelete-modal">
                <div>모임을 삭제 하시겠습니까?.</div>
                <div style = "display : flex; justify-content : space-between">
                    <button id = "check-modal-cancel">취소</button>
                    <button>삭제</button>
            </div>
        </div>
    </div>
`;

const commentDelete = `
    <div class = "commentDelete-modal-container">
        <div class = "commentDelete-modal-background">
            <div class = "commentDelete-modal">
                <div>댓글을 삭제 하시겠습니까?</div>
                <div style = "display : flex; justify-content : space-between">
                    <button id = "check-modal-cancel">취소</button>
                    <button>삭제</button>
            </div>
        </div>
    </div>
`;

//insertCheckModal함수는 문자열 $checkModal의 값에 따라 상황에 맞는 모달을 modal-container에 넣어주는 함수입니다.
function insertCheckModal($checkModal : string){
    const modalContainer = document.getElementById('modal-container');
    const checkModal = document.createElement('div')
    switch ($checkModal) {
      case 'loginCheck':
        checkModal.innerHTML = loginCheck;
        break;
      case 'joinCheck':
        checkModal.innerHTML = joinCheck;
        break;
      case 'gatheringDelete':
        checkModal.innerHTML = gatheringDelete;
        break;
      case 'gatheringEdit':
        checkModal.innerHTML = gatheringEdit;
        break;
      case 'commentDelete':
        checkModal.innerHTML = commentDelete;
        break;
    }

    modalContainer?.appendChild(checkModal);
    
    checkModalBackgroundEvent($checkModal,checkModal)
}
//checkModalBackgroundEvent는 확인용 작은 모달들의 백그라운드를 누르면 모달이 내려가는 기능을 담당합니다.
function checkModalBackgroundEvent($checkModal : string ,checkModal : HTMLDivElement){ //$checkModal은 그냥 string이고 이름 문자열일 뿐입니다.
    const checkModalBackgroundName = $checkModal + '-modal-background';
    const [checkModalBackground] = document.getElementsByClassName(
      checkModalBackgroundName
    );
    checkModalBackground.addEventListener('click', (e) => {
      if (e.target == checkModalBackground)
        checkModal.remove();
    });
}

//rightClickDeleteComment함수는 댓글에서 우클릭했을때 삭제 버튼이 나오는 기능을 담당합니다.
function rightClickDeleteComment() {
  const modalRegister = document.getElementsByClassName('feed-comment');
  let element : Element;
  for(element of modalRegister){
    element.addEventListener('contextmenu', (event) => {
    const e = event as MouseEvent;
        e.preventDefault();
        removeAllcontextmenu();
        const x = e.clientX + 'px';
        const y = e.clientY + 'px';
        const div = document.createElement('div');
        div.classList.add('comment-delete');
        div.innerHTML = `<button id = "commentDeleteButton" class="custom-btn btn-1">삭제하기</button>`;
        div.style.position = 'fixed';
        div.style.left = `${x}`;
        div.style.top = `${y}`;
        document.getElementById('modal-container')?.appendChild(div);
    });
  }
}
//removeAllcontextmenu함수는 우클릭으로 삭제버튼이 나오면 마우스를 움직여 다른곳에 클릭이나 우클릭을 할 경우 해당 버튼을 삭제하는 기능입니다.
function removeAllcontextmenu() {
  const contextmenuList = document.getElementsByClassName('comment-delete');
  let element : Element;
  for(element of contextmenuList){
    element.remove();
  }
}

//window에 좌클릭을 할경우 우클릭이 댓글삭제버튼이면 모달을띄우고 아니면 댓글삭제 버튼을 없애는 기능을 담당합니다.
function deleteAllCommentButton(){
    window.addEventListener('click', (e) => {
    const contextmenuList = document.getElementById('commentDeleteButton');
    if (e.target == contextmenuList) {
        insertCheckModal('commentDelete');
    }
    removeAllcontextmenu();
    });
}

deleteAllCommentButton();
rightClickDeleteComment();

export {loginCheck, joinCheck,gatheringDelete,gatheringEdit,commentDelete}
export {insertCheckModal}