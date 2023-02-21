import { chatBotText } from '../../common/constants';
import { generateResponse } from './chatBotOpenAI.js';
class ChatBot {
  element: HTMLElement;
  $container: HTMLElement | null;
  constructor($container: HTMLElement | null) {
    this.$container = $container;
    this.element = document.createElement('div');
    this.render();
  }

  render() {
    if (this.$container === null) return;
    this.element.classList.add('main-card', 'collapsed');
    this.element.id = 'chatbot';
    this.element.innerHTML = `<button id="chatbot_toggle">
        <svg xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 24 24" fill="currentColor"><path d="M0 0h24v24H0V0z" fill="none"/><path d="M15 4v7H5.17l-.59.59-.58.58V4h11m1-2H3c-.55 0-1 .45-1 1v14l4-4h10c.55 0 1-.45 1-1V3c0-.55-.45-1-1-1zm5 4h-2v9H6v2c0 .55.45 1 1 1h11l4 4V7c0-.55-.45-1-1-1z"/></svg>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" style="display:none"><path d="M0 0h24v24H0V0z" fill="none"/><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"/></svg>
    </svg>
      </button>
      <div class="main-title">
        <div>
          <svg viewBox="0 0 640 512" title="robot">
            <path fill="currentColor" d="M32,224H64V416H32A31.96166,31.96166,0,0,1,0,384V256A31.96166,31.96166,0,0,1,32,224Zm512-48V448a64.06328,64.06328,0,0,1-64,64H160a64.06328,64.06328,0,0,1-64-64V176a79.974,79.974,0,0,1,80-80H288V32a32,32,0,0,1,64,0V96H464A79.974,79.974,0,0,1,544,176ZM264,256a40,40,0,1,0-40,40A39.997,39.997,0,0,0,264,256Zm-8,128H192v32h64Zm96,0H288v32h64ZM456,256a40,40,0,1,0-40,40A39.997,39.997,0,0,0,456,256Zm-8,128H384v32h64ZM640,256V384a31.96166,31.96166,0,0,1-32,32H576V224h32A31.96166,31.96166,0,0,1,640,256Z" />
          </svg>
        </div>
        <span>GatherGO Chatbot</span>
    
      </div>
      <div class="chat-area" id="message-box">
      </div>
      <div class="line"></div>
      <div class="input-div">
        <input class="input-message" name="message" type="text" id="message" placeholder="Type your message ..." />
        <button class="input-send"">
          <svg style="width:24px;height:24px">
            <path d="M2,21L23,12L2,3V10L17,12L2,14V21Z" />
          </svg>
        </button>
      </div>`;

    this.$container.appendChild(this.element);
    this.chatBotEvent();
    this.sendEvent();
    console.log(generateResponse('what I need for posting?'));
  }
  chatBotEvent() {
    this.enterEvent();
    this.toggleEvent();
  }
  sendEvent() {
    const sendBtn = document.querySelector('.input-send');
    if (!sendBtn) return;
    sendBtn.addEventListener('click', () => {
      this.send();
      console.log('ljkl');
    });
  }
  enterEvent() {
    const message = this.element.querySelector<HTMLInputElement>('#message');
    message?.addEventListener('keyup', (e) => {
      if (e.key == 'Enter') {
        this.send();
      }
    });
  }
  toggleEvent() {
    const chatbotToggle =
      this.element.querySelector<HTMLButtonElement>('#chatbot_toggle');
    if (!chatbotToggle) return;
    const icon1 = chatbotToggle.children[0] as HTMLElement;
    const icon2 = chatbotToggle.children[1] as HTMLElement;
    chatbotToggle.onclick = () => {
      if (this.element.classList.contains('collapsed')) {
        this.element.classList.remove('collapsed');
        icon1.style.display = 'none';
        icon2.style.display = '';
        setTimeout(this.addResponseMsg, 1000, chatBotText('DEFAULT')); 
      } else {
        this.element.classList.add('collapsed');
        icon2.style.display = 'none';
        icon1.style.display = '';
      }
    };
  }

  async send() {
    const msg = this.element.querySelector<HTMLInputElement>('#message')?.value;
    if (!msg) return;
    this.addMsg(msg);
    this.addResponseMsg(await generateResponse(msg));
  }
  addMsg(msg: string) {
    const div = document.createElement('div');
    div.innerHTML =
      "<span style='flex-grow:1'></span><div class='chat-message-sent'>" +
      msg +
      '</div>';
    div.className = 'chat-message-div';
    const messageBox = this.element.querySelector<HTMLElement>('#message-box');
    if (!messageBox) return;
    messageBox.appendChild(div);
    const message = this.element.querySelector<HTMLInputElement>('#message');
    if (!message) return;
    message.value = '';
    messageBox.scrollTop = messageBox.scrollHeight;
  }
  addResponseMsg(msg: string) {
    const div = document.createElement('div');
    div.innerHTML = "<div class='chat-message-received'>" + msg + '</div>';
    div.className = 'chat-message-div';
    const messageBox = document.querySelector('#message-box');
    if (!messageBox) return;
    messageBox.appendChild(div);
    messageBox.scrollTop = messageBox.scrollHeight;
  }
}
export default ChatBot;
