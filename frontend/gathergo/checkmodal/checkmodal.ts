const loginCheck = `
    <div class = "loginCheck-modal-container">
        <div class = "loginCheck-modal-background">
            <div class = "loginCheck-modal">
                <input type="text" value="이 기능은 로그인이 필요합니다." readonly>
                <input type="text" value="로그인 하시겠습니까?." readonly>
                <div style = "display : flex; justify-content : space-between">
                    <button>로그인</button>
                    <button>취소</button>
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
                    <button>참가</button>
                    <button>취소</button>
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
                    <button>수정</button>
                    <button>취소</button>
            </div>
        </div>
    </div>
`;
const gatheringDelete = `
    <div class = "gatheringDelete-modal-container">
        <div class = "gatheringDelete-modal-background">
            <div class = "gatheringDelete-modal">
                <input type="text" value="모임을 삭제 하시겠습니까?." readonly>
                <div style = "display : flex; justify-content : space-between">
                    <button>로그인</button>
                    <button>취소</button>
            </div>
        </div>
    </div>
`;

const commentDelete = `
    <div class = "commentDelete-modal-container">
        <div class = "commentDelete-modal-background">
            <div class = "commentDelete-modal">
                <input type="text" value="댓글을 삭제 하시겠습니까?." readonly>
                <div style = "display : flex; justify-content : space-between">
                    <button>삭제</button>
                    <button>취소</button>
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
}

insertCheckModal('commentDelete')

export {loginCheck, joinCheck,gatheringDelete,gatheringEdit,commentDelete}