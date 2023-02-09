class Post {
  $container: HTMLElement;
  constructor($container: HTMLElement) {
    this.$container = $container;
    this.render();
  }
  setState = () => {
    this.render();
  };

  render() {
    this.$container.innerHTML = `
          
    <nav class="header navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-lg">
        <img class="navbar-home-Logo" src="../../assets/Logo/LogoIconChar-Black.svg" alt="GatherGo" />
        <div class="container-fluid">
            <ul class="nav-link-wrapper login me-auto show">
                <li class="nav-item">
                    <a class="nav-link cancel" href="" data-hover="취소">
                        <span>취소</span>
                    </a>
                </li>
                <li class="nav-item divider"></li>
                <li class="nav-item">
                    <a class="nav-link" href="" data-hover="알람">
                        <span>알람</span>
                    </a>
                    <span class="dot unread"></span>
                </li>
                <li class="nav-item divider"></li>
                <li class="nav-item profile-icon">
                    <a class="nav-link" href="" data-hover="내 프로필">
                        <span>내 프로필</span>
                    </a>
                </li>
            </ul>
            <ul class="nav-link-wrapper logout me-auto ">
                <li class="nav-item">
                    <a class="nav-link" href="" data-hover="로그인">
                        <span>로그인</span>
                    </a>
                </li>
                <li class="nav-item divider"></li>
                <li class="nav-item profile-icon">
                    <a class="nav-link" href="" data-hover="회원가입">
                        <span>회원가입</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="form-wrapper">
    <div class="form-title">
        <h4 class="leading-text">
            <strong>만남 이름</strong>
        </h4>
        <div class="form-input">
            <input class="form-control form-control-lg" type="text" placeholder="만남 이름을 설정해주세요." id="inputLarge">
        </div>
    </div>
    <div class="form-category">
        <h4 class="leading-text">
            <strong>상세 관심사</strong>
        </h4>
        <div class="form-input nav-item dropdown category form-control-lg">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button"
                aria-haspopup="true" aria-expanded="false">카테고리를 선택하세요</a>
            <div class="dropdown-menu">
                <div class="dropdown-item-wrapper category">
                    <div class="dropdown-item category" data-category-title="축구">
                        <img style="width: 25px;" src="../../assets/category/icons/1.png" alt="">
                        <span>아웃도어/여행</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/2.png" alt="">
                        <span>운동/스포츠</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/3.png" alt="">
                        <span>인문학/책/글</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/4.png" alt="">
                        <span>업종/직무</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/5.png" alt="">
                        <span>외국/언어</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/6.png" alt="">
                        <span>문화/공연/축제</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/7.png" alt="">
                        <span>음악/악기</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/8.png" alt="">
                        <span>패션/뷰티</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/9.png" alt="">
                        <span>공예/만들기</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/10.png" alt="">
                        <span>댄스/무용</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/11.png" alt="">
                        <span>봉사활동</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/12.png" alt="">
                        <span>사교/인맥</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/13.png" alt="">
                        <span>차/오토바이</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/14.png" alt="">
                        <span>사진/영상</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/15.png" alt="">
                        <span>야구관람</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/16.png" alt="">
                        <span>게임/오락</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/17.png" alt="">
                        <span>요리/제조</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/18.png" alt="">
                        <span>반려동물</span>
                    </div>
                    <div class="dropdown-item category">
                        <img style="width: 25px;" src="../../assets/category/icons/19.png" alt="">
                        <span>자유주제</span>
                    </div>
                </div>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">카테고리를 선택하세요</a>
            </div>
        </div>
    </div>
    <div class="form-date-time calendar-toggle">
        <h4 class="leading-text">
            <strong>만남 시간</strong>
        </h4>
        <div class="form-input">
            <div class="form-input-date-time">
                <div class="form-input-date">
                    <input class="form-control form-control-lg" id="readOnlyDateInput" type="datetime"
                        placeholder="만남 날짜를 설정해주세요." readonly value="">
                </div>
                <div class="divider"></div>
                <div class="form-input-time">
                    <input class="form-control form-control-lg" id="readOnlyTimeInput" type="datetime"
                        placeholder="만남 시간을 설정해주세요." readonly value="">
                    <div id="timePicker">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-address">
        <h4 class="leading-text">
            <strong>만남 장소</strong>
        </h4>
        <div class="form-input">
            <div class="form-input-map">
                <div class="form-input-map-doromyeong">
                    <input class="form-control form-control-lg" id="addressReadOnlyInput" type="text"
                        placeholder="지도에서 만남 장소를 설정해주세요." readOnly value="">
                    <div id="form-map-icon" class="map-toggle">
                        <img src="../../assets/Icons/mapIcon.png" alt="">
                    </div>
                </div><input class="form-control form-control-lg" id="detailAddressInput" type="text"
                    placeholder="상세 주소를 설정해주세요." value="">
            </div>

        </div>
    </div>
    <div id="form-map-wrapper" class="show">
        <div id="form-map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
        <div id="menu_wrap" class="bg_white">
            <div class="option">
                <div>
                    <form action="#" onsubmit="return false;">
                        키워드 : <input class="form-control form-control-sm" type="text" value="코드스쿼드" id="keyword"
                            size="15">
                        <button type="submit" id="keywordBtn" class="btn btn-primary">검색하기</button>
                    </form>
                </div>
            </div>
            <hr>
            <ul id="placesList"></ul>
            <div id="pagination"></div>
        </div>
    </div>
    <div class="form-people">
        <h4 class="leading-text">
            <strong>모집 인원</strong>
        </h4>
        <div class="form-input">
            <input class="form-control form-control-lg" type="number" placeholder="모집 인원을 설정해주세요." id="inputLarge">
        </div>
    </div>
    <div class="form-content">
        <h4 class="leading-text">
            <strong>만남 상세 설명</strong>
        </h4>
        <div class="form-input">
            <textarea class="form-control form-control-lg" id="feedTextarea" rows="3" spellcheck="true"
                style="height: 277px;" placeholder="만남에 대해서 설명해주세요."></textarea>
        </div>
    </div>
    <button type="button" class="feed-create-button btn btn-primary btn-lg form-control-lg"><strong>모임
            만들기</strong></button>

</div>
<script type="text/javascript"
    src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e025d257c750a5635c774ac925157964&libraries=services"></script>
        `;
  }
}

export default Post;
