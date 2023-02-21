class MapForm {
  element: HTMLElement;
  location: string;
  constructor(location = '현대자동차본사') {
    this.location = location;
    this.element = document.createElement('div');
    this.element.id = 'form-map-wrapper';
    this.element.classList.add('show');
    this.render();
  }
  render() {
    this.element.innerHTML = `<div id="form-map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
        <div id="menu_wrap" class="bg_white">
            <div class="option">
                <div>
                    <form action="#" onsubmit="return false;">
                        키워드 : <input class="form-control form-control-sm" type="text" value="${this.location}" id="keyword"
                            size="15">
                        <button type="submit" id="keywordBtn" class="btn btn-primary">검색하기</button>
                    </form>
                </div>
            </div>
            <hr>
            <ul id="placesList"></ul>
            <div id="pagination"></div>
        </div>`;
  }
}

export default MapForm;
