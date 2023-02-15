
import { fetchLogout } from "../common/Fetches";
import profileLayout from "../components/profile/profileLayout"
import HeaderDefault from "../components/header/headerDefault";
// import { userLogout } from "../store/actions";
import store from "../store/store";
import { changeProfileTab } from "../store/actions";

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
    const headerDefault = new HeaderDefault('profile');
    this.$container.appendChild(headerDefault.element);

    const profilePageLayout = new profileLayout()
    this.$container.appendChild(profilePageLayout.element)
    // this.$container.querySelector<HTMLButtonElement>('#logoutbtn')?.addEventListener(('click'),async ()=>{
    // // logOut();
    // store.dispatch(await fetchLogout())
    // })


    this.addTabButtonEvent()
  }

  addTabButtonEvent(){
    const tabMainDom = document.querySelector('#profile-tabMain')
    tabMainDom?.addEventListener('click',()=>{
      store.dispatch(changeProfileTab(0));
    })
    const tabEditDom = document.querySelector('#profile-tabEdit')
    tabEditDom?.addEventListener('click',()=>{
      store.dispatch(changeProfileTab(1));
    })
    const tabLogoutDom = document.querySelector('#profile-logout')
    tabLogoutDom?.addEventListener('click',()=>{
      store.dispatch(changeProfileTab(2));
    })
  }

}

export default Profile;
