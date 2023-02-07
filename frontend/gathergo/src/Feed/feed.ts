function showMapEvent() {
  const mapToggle = document.querySelector<HTMLDivElement>('.map-toggle');
  const map = document.querySelector<HTMLDivElement>('.form-map');
  if (!mapToggle || !map) return;
  mapToggle?.addEventListener('click', () => showMap(map));
}
function showMap(map: HTMLDivElement) {
  map.classList.add('show');
}
export default showMapEvent;
