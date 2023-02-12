import { formMapGenerator, showMapEvent } from "../common/kakaoMapAPI/kakaoMapAPI";
import PostingForm from "../components/form/postingForm";
import HeaderDefault from "../components/header/headerDefault";

class Post {
  $container: HTMLElement;
  constructor($container: HTMLElement) {
    this.$container = $container;
    this.render();
  }

  render() {

    if (!this.$container) return;
    const header = new HeaderDefault('post');
    this.$container.appendChild(header.element);

    const form = new PostingForm();
    this.$container.appendChild(form.element);
    formMapGenerator();
    showMapEvent();
    return;
  }
}

export default Post;
