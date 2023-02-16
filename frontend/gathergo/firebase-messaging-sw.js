importScripts("https://www.gstatic.com/firebasejs/7.15.5/firebase-app.js")
importScripts("https://www.gstatic.com/firebasejs/7.15.5/firebase-messaging.js")

const firebaseConfig = {
    apiKey: "AIzaSyB0i2yNngpONM61QCU3uG0ev52GsA1rHxo",
    authDomain: "gather-go-ff1e0.firebaseapp.com",
    projectId: "gather-go-ff1e0",
    storageBucket: "gather-go-ff1e0.appspot.com",
    messagingSenderId: "436089704325",
    appId: "1:436089704325:web:a1c312b3113b5e558cfeec",
    measurementId: "G-44DYH3Z29Q"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

self.addEventListener('activate', async () => {
    console.log('service worker activate')

    // listen/subscribe to remote(fcm) push messages
    await subscribe();
})

self.addEventListener('push', async (event) => {
    console.log(event.data.text());
    const data = event.data.text()
    if(data) {
        event.waitUntil(
            self.registration.showNotification("알림이 도착했습니다", {
                body: "body",
                tag: Date.now()
            })
        );

    }
})

async function subscribe () {  // 유저 식별 토큰 전달
    let pushSubscription = self.registration.pushManager.getSubscription();

    if (pushSubscription) {
        console.log('subscribe - already subscribed');
        return;
    }

    try {
        const vapidPublicKey = 'BE0QGkzGcjC_xclXL9lp_wGSkOLrZ6WVF5GneUs4C1P0TxwS6uRJkp8Yv6TJM_KueAa8WGjsLNuikqIRM3a_Rew';
        console.log('subscribe', { vapidPublicKey });

        const registration = self.registration;

        if (!registration) {
            alert('subscribe - service worker is not registered');
            return;
        }

        const subscription = await registration.pushManager.subscribe({
            applicationServerKey: vapidPublicKey,
            userVisibleOnly: true,
        });

    } catch (error) {
        console.error('subscribe', { error });
    }
}

