import App from './src/app';
import { $ } from './src/common/utils/querySelctor';

window.addEventListener('DOMContentLoaded', () => new App($('#app')));
