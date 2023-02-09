function noticeSidebar() {
  const toggleBtn = document.querySelector('#alarm-button');
  const sidebar = document.querySelector('.notice-sidebar');
  const sidebarBackground = document.querySelector('.notice-background');

  toggleBtn?.setAttribute('style', 'cursor : pointer');

  toggleBtn?.addEventListener('click', function () {
    sidebarBackground?.classList.toggle('is-closed');
  });
  sidebarBackground?.addEventListener('click', (e) => {
    if (e.target != sidebar) {
      e.stopPropagation();
      sidebarBackground.classList.toggle('is-closed');
    }
  });
}
noticeSidebar();
