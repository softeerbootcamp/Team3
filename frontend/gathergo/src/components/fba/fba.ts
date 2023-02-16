class Fba {
    element: HTMLElement;
    $container: HTMLElement | null;
    constructor($container: HTMLElement | null,) {
      
        this.$container = $container;
      this.element = document.createElement('div');
      this.render();
      // store.subscribe(() => this.render());
    }
  
    render(){
        
    if (this.$container === null) return;
        this.element.id = 'fba'
        this.element.innerHTML = '<img src="./assets/Icons/chevronupIcon.svg" alt=""/>'

        this.$container.appendChild(this.element);
        this.scrollEvent();
    }
    scrollEvent(){
        // ===== Scroll to Top ==== 
        window.addEventListener('scroll', () => {
            if (window.scrollY >= 50) {// If page is scrolled more than 50px
                this.element.style.display = 'flex';
            } else {
                this.element.style.display = 'none';
            }
          });
          
          this.element.addEventListener('click', () => {
            window.scrollTo({ top: 0, behavior: 'smooth' });
          });
       
    }
}
export default Fba