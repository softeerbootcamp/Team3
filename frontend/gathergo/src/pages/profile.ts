import { userLogout } from "../store/actions";
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
    this.$container.innerHTML = `
          <main class="profile-page">
            프로필페이지
          </main>
          <button id = "logoutbtn">logout</button>
        `;
       this.$container.querySelector<HTMLButtonElement>('#logoutbtn')?.addEventListener(('click'),()=>{
        // logOut();
        store.dispatch(userLogout())
       })
  }
}

export default Profile;
