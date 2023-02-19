import TabContent from "./tabContent";

class TabHome {
    element: HTMLElement;
    tabContent: TabContent;
    constructor(tabContent:TabContent) {
      this.element = document.createElement('nav');
      this.tabContent = tabContent;
      this.render();
    }
    render() {
      this.element.innerHTML = `<div class="nav nav-tabs" id="nav-tab" role="tablist">
      <a class="nav-item nav-link tab-underline active" id="nav-tab1" data-toggle="tab" role="tab" aria-controls="nav-home" aria-selected="true">최신 게시물</a>
      <div class="divider"></div>
      <a class="nav-item nav-link tab-underline" id="nav-tab2" data-toggle="tab" role="tab" aria-controls="nav-profile" aria-selected="false">마감 임박 게시물</a>
    </div>`;
    this.element.after(this.tabContent.element)
    this.tabContent.tab1Ele.classList.add('show')
    this.tab1Event();
    this.tab2Event();
    }
    tab1Event(){
        const tab1 = this.element.querySelector('#nav-tab1');
        tab1?.addEventListener('click',()=>{
            tab1.classList.add('active')
            this.element.querySelector('#nav-tab2')?.classList.remove('active')
            this.tabContent.tab1Ele.classList.add('show')
            this.tabContent.tab2Ele.classList.remove('show')
        })
    }
    tab2Event(){
        const tab2 = this.element.querySelector('#nav-tab2');
        tab2?.addEventListener('click',()=>{
            tab2.classList.add('active')
            this.element.querySelector('#nav-tab1')?.classList.remove('active')
            this.tabContent.tab2Ele.classList.add('show')
            this.tabContent.tab1Ele.classList.remove('show')
        })
    }
}
export default TabHome;