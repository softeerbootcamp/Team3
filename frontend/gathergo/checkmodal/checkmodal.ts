const loginCheck = `
    <div class = "loginCheck-modal-container">
        <div class = "loginCheck-modal-background">
            <div class = "loginCheck-modal">
                <div>이 기능은 로그인이 필요합니다."</div>
                <div>로그인 하시겠습니까?"<div>
                <div style = "display : flex; justify-content : space-between">
                    <button>취소</button>
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
                    <button>취소</button>
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
                    <button>취소</button>
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
                    <button>취소</button>
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
                    <button>취소</button>
                    <button>삭제</button>
            </div>
        </div>
    </div>
`;

function insertCheckModal($checkModal : string){
    const modalContainer = document.getElementById('modal-container');
    const checkModal = document.createElement('div')
    checkModal.style.display = "none";
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

insertCheckModal('commentDelete')


function checkModalBackgroundEvent($checkModal : string ,checkModal : HTMLDivElement){ //$checkModal은 그냥 string이고 이름 문자열일 뿐입니다.
    const checkModalBackgroundName = $checkModal + '-modal-background';
    const [checkModalBackground] = document.getElementsByClassName(
      checkModalBackgroundName
    );
    checkModalBackground.addEventListener('click', (e) => {
      if (e.target == checkModalBackground)
        checkModal.style.display = 'none';
    });
}

function commentDeleteEvent(){
    const commentDeleteButton = document.getElementsByClassName('comment-Delete')[0];
    commentDeleteButton.addEventListener('click',(e)=>{
        insertCheckModal('commentDelete');
    })
}

export {loginCheck, joinCheck,gatheringDelete,gatheringEdit,commentDelete}