document.addEventListener('click', (e: Event) =>{
    const target = e.target as Element;
    const profileIcon = target?.closest('.profile-icon')
    const sidebarBg = target?.closest('#sidebar-bg')
    if (profileIcon) {
        sidebarActiveEvent();
        dropDownCloseEvent();
    }
    if (sidebarBg) {
        sidebarCloseEvent();
    }
})

function sidebarActiveEvent() {
    document.querySelector<HTMLDivElement>('#sidebar')?.classList.add('active');
    //TODO: 모든 dropDown 닫아주기
    // https://jess2.xyz/css/fade-in-out/
}
function sidebarCloseEvent() {
    document.querySelector<HTMLDivElement>('#sidebar')?.classList.remove('active');
}

function dropDownCloseEvent(){
    document.querySelectorAll<HTMLLIElement>('.nav-item.dropdown').forEach( (element) =>dropDownClose(element))
}
function dropDownClose(dropDownElement:HTMLLIElement){
    
    const toggleElement = dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-toggle');
    if(!toggleElement?.classList.contains('show')) return
    console.log(toggleElement)
    toggleElement?.classList.remove('show'); //add()
    toggleElement?.setAttribute('aria-expanded', 'false'); //true

    const menuElement =  dropDownElement.querySelector<HTMLAnchorElement>('.dropdown-menu');
    menuElement?.classList.remove('show');
    delete menuElement?.dataset['bs-toggle'] 
}