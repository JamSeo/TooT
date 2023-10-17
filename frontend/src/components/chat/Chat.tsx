import ChatList from "./ChatList";
import Guide from "./Guide";
import Input from "./input/Input";
import { useSelector } from "react-redux";
import { RootState } from "../../store";

const Chat = () => {
  const bubbles = useSelector((state:RootState) => state.bubbles.bubbles);
  return (
  <div className="w-full h-full flex flex-col justify-between items-stretch p-2.5 rounded-3xl border-8 border-solid border-blue-950 bg-white">
    <ChatList bubbles={bubbles} />
    <Guide />
    <Input />
  </div>);
};

export default Chat;