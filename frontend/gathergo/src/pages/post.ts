import { formMapGenerator, showMapEvent } from "../common/kakaoMapAPI/kakaoMapAPI";
import Navigate from "../common/utils/navigate";
import PostingForm from "../components/form/postingForm";
import HeaderDefault from "../components/header/headerDefault";
import { checkLogin } from "../store/actions";
import store from "../store/store";

class Post {
  $container: HTMLElement;
  navigate: Navigate;
  constructor($container: HTMLElement, navigate:Navigate) {
    this.$container = $container;
    this.navigate = navigate;
    this.render();
  }

  render() {window.scrollTo(0, 0);
    store.dispatch(checkLogin(document.cookie))
    if(!store.getState().sessionId) {
      this.navigate.to('/login')
      return
    }
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
