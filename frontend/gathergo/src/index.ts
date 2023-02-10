// import { combineReducers } from 'redux';

// async function getData() {
//   const response = await fetch('http://3.34.153.254:8080/regions');
//   const jsonData = await response.json();
//   return jsonData;
// }
// getData().then((element) => {
//   console.log(element);
// });

// fetch('http://3.34.153.254/login', {
//   method: 'POST',
//   //   credentials: 'include',
//   headers: {
//     'Content-Type': 'application/json',
//   },
//   body: JSON.stringify({
//     userId: 'logData.id',
//     password: 't',
//   }),
// }).then((res) => {
//   console.log(res);
// });

async function fetchRegions() {
  await fetch('http://3.34.153.254/regions')
    .then((response) => response.json())
    .then((json) => {
      console.log(json);
      document.body.innerHTML = JSON.stringify(json);
    });
}
fetchRegions();
