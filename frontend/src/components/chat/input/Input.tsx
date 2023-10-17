import { useContext, useState } from "react";
import SendButton from "./SendButton";
import { useDispatch } from "react-redux";
import { getBubble, sendBubble } from "../../../utils/chat/chat";
import { useNavigate } from "react-router-dom";
import { UserAuthContext } from "../../../App";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { setRespondingTrue } from "../../../store/slices/chatInputSlice";

const Input = () => {
  const [inputText, setInputText] = useState<string>("");
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const userAuthContext = useContext(UserAuthContext);

  const isChatbotResponding = useSelector(
    (state: RootState) => state.chatbotResponding.isChatbotResponding
  );

  const handleInputTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputText(e.target.value);
  };

  const handleBubble = async () => {
    await sendBubble(inputText, dispatch);
    await setInputText("");
    await dispatch(setRespondingTrue());
    await getBubble(inputText, dispatch, navigate, userAuthContext);
  };

  const handleKeyDown = async (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      if (inputText === "") return;
      await handleBubble();
    }
  };

  const handleSendClick = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    if (inputText === "") return;
    await handleBubble();
  };

  return (
    <div className="w-full h-9 pl-2.5 pr-1 flex items-center justify-between rounded-full bg-gray-300">
      <input
        type="text"
        disabled={isChatbotResponding}
        autoFocus
        value={inputText}
        onChange={handleInputTextChange}
        className="w-full h-full font-light text-[14px] bg-gray-300 focus:outline-none ml-1.5"
        onKeyDown={handleKeyDown}
      ></input>
      <SendButton handleSendClick={handleSendClick} />
    </div>
  );
};

export default Input;
