import HeaderDefaultNav from "./headerDefaultNav";

class HeaderDefault {
  element: HTMLElement;
  type:string
  constructor(type:string) {
    this.type=type;
    this.element = document.createElement('div');
    this.element.classList.add('headerDefault');
    this.render();
    // store.subscribe(() => this.render());
  }
  render(){
    this.element.innerHTML = `
    <nav class="header navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-lg">
            <a href="/">
            <img class="navbar-home-Logo" src="../../assets/Logo/LogoIconChar-Black.svg" alt="GatherGo" />
            </a>
            <div class="container-fluid"></div>
        </div>
    </nav>
    `
    const headerDefaultNav = new HeaderDefaultNav(this.type);
    this.element.querySelector('.container-fluid')?.appendChild(headerDefaultNav.element)
  }
}
export default HeaderDefault;