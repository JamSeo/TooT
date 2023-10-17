import axios from "axios";
import { pushBotBubble } from "./chat";
import { setRespondingFalse } from "../../store/slices/chatInputSlice";
import { reset } from "../../store/slices/stockSlice";

/* 900: chatGPT 패스 & 에러 */
export const code900 = async (code:number, userMessage: string, dispatch: any) => {
  switch(code){
    case 901:
      pushBotBubble("잠시만 기다려주세요...", dispatch);
      // TODO: chatGPT에게 bubble 전달
      await axios.post("https://too-t.com/express/chatgpt", {
        sendData: userMessage,
      }).then((res) => {
        pushBotBubble(res.data.chatResponse.choices[0].message.content, dispatch);
      }).catch((err) => {
        console.log(err);
      }).finally(() => {
        dispatch(setRespondingFalse());
      })
      break;
    case 902:
      dispatch(reset());
      localStorage.setItem("chat-list", JSON.stringify([]));
      window.location.reload();
      break;
    case 999:
      dispatch(setRespondingFalse());
      break;
  };
};
