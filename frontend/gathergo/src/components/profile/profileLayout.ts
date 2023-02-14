import profileSidebar from './profileSidebar'

class profileLayout {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('profile-layout')
    this.render();
  }
  render() {
    const Sidebar = new profileSidebar()
    this.element.appendChild(Sidebar.element);
   
  }
}
export default profileLayout;
