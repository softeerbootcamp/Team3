import App from './src/app';
// import './style.css';
import { $ } from './src/common/utils/querySelctor';

window.addEventListener('DOMContentLoaded', (e) => new App($('#app')));
