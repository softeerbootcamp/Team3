/* eslint-disable no-undef */


  const firebaseConfig = {
    apiKey: "AIzaSyB0i2yNngpONM61QCU3uG0ev52GsA1rHxo",
    authDomain: "gather-go-ff1e0.firebaseapp.com",
    projectId: "gather-go-ff1e0",
    storageBucket: "gather-go-ff1e0.appspot.com",
    messagingSenderId: "436089704325",
    appId: "1:436089704325:web:a1c312b3113b5e558cfeec",
    measurementId: "G-44DYH3Z29Q"
  };

  const tokenStore = {
    token: null
  }

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

  // 메시지 기능 활성화를 알림
  const messaging = firebase.messaging();

  // RequestPermission 첫 어플 시작 시 알림 허용 or 불허를 사용자에게 안내합니다.
  function requestForPermission() {
    messaging.requestPermission()   // 권한 확인
            .then(function() {
              return messaging.getToken();  // 토큰 정보 존재하면
            })
            .then(async function(token) {
              console.log("requestForPermission", token);
              tokenStore.token = token;
            }).catch((err) => {
    console.log('Unable to get permission to notify.', err);
  });
  }
  function postSubscription (token, $articleuuid) {
    console.log('postSubscription', { token });

    if (!token) {
      return 'postSubscription - subscription cannot be empty';
    }

    // TODO: 참여하기 누르면 동작, 매 번 동작 안하게 변경
    const response = fetch('https://gathergo.kro.kr/api/subscription', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        deviceToken: token,
        articleId: $articleuuid,
      })
    });
    console.log(`Firebase response : + ${response}`)
    return response;
  }

  function deleteSubscription (token, $articleuuid) {
  console.log('postSubscription', { token });

  if (!token) {
    return 'postSubscription - subscription cannot be empty';
  }

  // TODO: 참여하기 누르면 동작, 매 번 동작 안하게 변경
  const response = fetch('https://gathergo.kro.kr/api/subscription', {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      deviceToken: token,
      articleId: $articleuuid,
    })
  });
  console.log(`Firebase response : + ${response}`)
  return response;
}

  async function registerServiceWorker () {  // 백그라운드 데몬 등록
    await navigator.serviceWorker.register('../firebase-messaging-sw.js');
  }

  const fireBaseFetch = async () => {
    requestForPermission();
    await registerServiceWorker();
  }

  fireBaseFetch()
  console.log(tokenStore)
  function returnTokenStore(){
    return tokenStore;
  }

  export {postSubscription,deleteSubscription,returnTokenStore}