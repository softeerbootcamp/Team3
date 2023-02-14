class HeaderSearchSticky {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('search-container');
    this.render();
  }
  render() {
    this.element.innerHTML = `
        <img class="search-container-logo" src="./assets/Logo/LogoIconChar-theme.svg" alt="">
      <div class="navbar-divider"></div>
      <input type="text" class="search-input keyword-input-sticky" placeholder="Search">
      <button type="button" class="btn btn-outline-primary">Search</button>
        `;
  }
  //header search바 event
  searchBarEvent(headerelement: HTMLElement | null) {
    const searchContainer = this.element;
    window.onscroll = function () {
      if (
        headerelement != null &&
        headerelement.getBoundingClientRect().bottom <= 0
      ) {
        searchContainer?.classList.add('show');
      } else {
        searchContainer?.classList.remove('show');
      }
    };
  }
}
export default HeaderSearchSticky;
