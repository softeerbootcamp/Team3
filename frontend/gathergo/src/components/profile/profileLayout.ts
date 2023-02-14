import profileSidebar from './profileSidebar'
import profileMain from './profileMain'

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
   
    const Main = new profileMain();
    this.element.appendChild(Main.element)
  }
}
export default profileLayout;
