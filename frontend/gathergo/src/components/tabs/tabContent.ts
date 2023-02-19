class TabContent{
    element: HTMLElement;
    tab1Ele: HTMLElement;
    tab2Ele: HTMLElement;
    constructor(tab1Ele:HTMLElement,tab2Ele:HTMLElement) {
      this.element = document.createElement('div');
      this.element.classList.add('tab-content');
      this.element.id = 'nav-tab-content';
      this.tab1Ele = tab1Ele;
      this.tab2Ele = tab2Ele;
      this.render();
    }
    render() {
        this.tab1Ele.classList.add('fade');
        this.tab2Ele.classList.add('fade');
        this.element.appendChild(this.tab1Ele)
        this.element.appendChild(this.tab2Ele)
    }
}
export default TabContent;