class profileMain {
  element: HTMLDivElement;
  constructor() {
    this.element = document.createElement('div');
    this.render();
  }
  render() {
    this.element.innerHTML = `
    안녕
`;
  }
}
export default profileMain;
