
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

    this.addHostLinkEvent()
    this.addJoinLinkEvent()
    this.addTabButtonEvent()
  }

  addHostLinkEvent(){
    document.querySelectorAll('tr').forEach((element)=>{
      element.addEventListener('click',()=>{
        console.log("hi")
      })
    })
  }

  addJoinLinkEvent(){
    document.querySelectorAll('.profile-user-join').forEach((element) => {
      element.addEventListener('click', () => {
        console.log('hi2');
      });
    });
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
  }
}

export default Profile;
