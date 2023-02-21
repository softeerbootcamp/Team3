class AddressForm {
  element: HTMLElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('form-address');
    this.render();
  }
  render() {
    this.element.innerHTML = `<h4 class="leading-text">
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
            </div>`;
  }
}
export default AddressForm;
