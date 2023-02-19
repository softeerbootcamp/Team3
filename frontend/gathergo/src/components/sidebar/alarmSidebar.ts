import Log from '../sidebar/log'
class AlarmSidebar {
    element: HTMLElement;
    constructor() {
      this.element = document.createElement('div');
      this.element.classList.add('sidebar-container')

      this.render();
      // store.subscribe(() => this.render());
    }
    render() {
        this.element.innerHTML=`
        <div class = "sidebar-background">
        <div id = 'alarm-sidebar'>
        <div class="sidebar-icon">
        <div id="alarm-sidebar-close" class="circCont"><button class="circle" data-animation="simpleRotate" data-remove="200"></button></div>

        </div>
        <ul class="scale-up-hover-list"></ul>
        </div>
        </div>`
        this.generateLogs();
        this.closeSidebarEvent();
    }
    generateLogs(){
      const logList = this.element.querySelector('.scale-up-hover-list');
      for( let i = 0 ; i<15 ; i++){
        const log = new Log();
        logList?.appendChild(log.element)
      }
    }
    
closeSidebarEvent(){
    this.element.querySelector('#alarm-sidebar-close')?.addEventListener('click',()=>{
        this.closeSidebar();
    })
    this.element.querySelector('.sidebar-background')?.addEventListener('click',(e)=>{
        const target = e.target as Element;
        if(!target.closest('#alarm-sidebar'))
            this.closeSidebar();
    })
  }
  closeSidebar(){
    document.querySelector('#alarm-sidebar')?.classList.remove('active');

    setTimeout(() => {
        
    this.element.classList.remove('active')
    document.body?.removeAttribute('class');
    }, 250);
    
     
    }
}
export default AlarmSidebar;