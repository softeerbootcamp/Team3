import profileSidebar from './profileSidebar'
import profileMain from './profileMain'
import Navigate from '../../common/utils/navigate';

class profileLayout {
  element: HTMLDivElement;
  navigate : Navigate;
  constructor(navigate : Navigate) {
    this.navigate = navigate;
    this.element = document.createElement('div');
    this.element.classList.add('profile-layout')
    this.render();
  }
  render() {
    const Sidebar = new profileSidebar()
    this.element.appendChild(Sidebar.element);
   
    const Main = new profileMain(this.navigate);
    this.element.appendChild(Main.element)
  }
}
export default profileLayout;
