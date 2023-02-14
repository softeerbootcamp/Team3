
import { fetchLogout } from "../common/Fetches";
import { HeaderDefault } from '../components/header/headerDefault';
// import { userLogout } from "../store/actions";
import store from "../store/store";

class Profile {
  $container: HTMLElement;
  constructor($container: HTMLElement) {
    this.$container = $container;
    this.render();
  }
  setState = () => {
    this.render();
  };

  render() {
    if (!this.$container) return;
    const headerDefault = new HeaderDefault('login');
    this.$container.appendChild(headerDefault.element);

    this.$container.innerHTML = `
          <main class="profile-page">
            프로필페이지
          </main>
          <button id = "logoutbtn">logout</button>
        `;
       this.$container.querySelector<HTMLButtonElement>('#logoutbtn')?.addEventListener(('click'),async ()=>{
        // logOut();
        store.dispatch(await fetchLogout())
       })
  }
}

export default Profile;
