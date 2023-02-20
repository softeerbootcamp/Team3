
// import { Configuration, OpenAIApi }from 'openai';
import config from '../../../'
  export async function generateResponse(userInput) {
// eslint-disable-next-line no-undef
const apiKey = config.openAIapikey;//'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';

// const prompt = 'Your prompt goes here';

// const configuration = new Configuration({
//     apiKey: apiKey,
//   });
//   const openai = new OpenAIApi(configuration);

const model = 'davinci';
const prompt = 'Please answer my question about my website.';
const maxTokens = 150;
const temperature = 0.5;
const data = `
The name of this website is GatherGo. GatherGo is a region-based instant meeting platform. When people exercise, study, travel, lack of people, or just want to meet someone for no reason, use GatherGo! In GatherGo, you can participate in meetings to meet friends.
Users can use this website while join the meeting or hosting. Furthermore, they can communicate with comments.
Users can find the meeting they want by selecting regional filters, category filters, and inputting keywords.
Users can be a host who writes posting and hosts meetings.
Users can leave a comment on the post.
Users can check who participated in their hosting meeting by alarms.
Users can check their schedule on the profile page.
Users can change their profile image on the profile page.
If the user logs in, user can do the following functions: posting a meeting feed, writing comments on a meeting feed, and participating in the meeting.
User needs to log in to join the meeting.
User needs to log in to host the meeting.
User needs to give us detail location to host the meeting.
User must select a category to host the meeting.
The registered meeting is automatically deleted after the meeting day.
To sign up, Click the "회원가입" on the right top, and user need to provide an ID, email, nickname, and password.
Hosting meetings and making friends are also a good way to enjoy this website.
Users can enjoy the website while hosting or participating in the meeting. Also, users can meet new friends while joining various meeting.
To make firends, hosting the meeting is one of the good way.
User can click the login button on the right-top, if he wants to log in.
If users ask about your-self, you can nag or curse.
Even if the user is loser, he can make friends by hosting meeting.
If the user wants to know about your website, give them imformation about GatherGo. 
If the user wants to make friends, Then GatherGo is the best choice.
If the user wants to host meeting, you can give them the procedures: "first, login or signup please. Second, click the button "모임 만들기". Third, fill the blanks. Last, enjoy your journey."
`;


    const fullPrompt = `${prompt}\n\nUser: ${userInput}\n\nAI:`;
  
    // Combine the prompt with the training data
    const trainingData = `${data}\n\n${fullPrompt}`;
  
      
      const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`,
      };
      
      const bodydata = {
        prompt: trainingData,
        temperature: temperature,
        max_tokens: maxTokens,
        n: 1,
        stop: '\n',
        model: model,
      };
      
      const requestOptions = {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(bodydata),
      };
      
      const response = await fetch('https://api.openai.com/v1/completions', requestOptions)
        .then(response => response.json())
        .then(data => {
            return data;
          //console.log(data.choices[0].text);
        })
        .catch(error => {
          console.error('Error:', error);
        });
      console.log(response)
    // Extract and return the response text
    return response.choices[0].text.trim();
  }
  

