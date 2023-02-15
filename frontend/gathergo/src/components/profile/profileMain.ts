import tabProfileMain from './tabProfileMain';
import tabEditMain from './tabEditMain'
import store from '../../store/store'

class profileMain {
  element: HTMLDivElement;
  tabNumber : number;

  constructor() {
    this.element = document.createElement('div');
    this.element.classList.add('profile-main');
    this.tabNumber = store.getState().tabNumber;

    store.subscribe(()=>{
        const newtabNumber = store.getState().tabNumber;

        if(newtabNumber != this.tabNumber){
            console.log(this.tabNumber, newtabNumber);
            this.tabNumber = newtabNumber;
            this.render()
        }
    })
    this.render();
  }
  render() {
    if(this.tabNumber == 0){
        this.element.innerHTML = ''
        const tabProfileMainDom = new tabProfileMain()
        this.element.appendChild(tabProfileMainDom.element);
    }
    else if(this.tabNumber == 1){
        this.element.innerHTML = ''
        const tabProfileEditDom = new tabEditMain()
        this.element.appendChild(tabProfileEditDom.element)
    }

  }
}
export default profileMain;
