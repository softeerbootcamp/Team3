function showMapEvent() {
  const mapToggle = document.querySelector<HTMLDivElement>('.map-toggle');
  const map = document.querySelector<HTMLDivElement>('#form-map-wrapper');
  if (!mapToggle || !map) return;
  mapToggle?.addEventListener('click', () => toggleMap(map));
}
function toggleMap(map: HTMLDivElement) {
  if (!map.classList.contains('show')) showMap(map);
  else hideMap(map);
}
function hideMap(map: HTMLDivElement) {
  map.classList.remove('show');
}
function showMap(map: HTMLDivElement) {
  map.classList.add('show');
}
export default showMapEvent;
