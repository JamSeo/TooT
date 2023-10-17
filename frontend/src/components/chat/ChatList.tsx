import { useEffect, useRef } from "react";
import UserBubble from "./bubble/UserBubble";
import type { Ibubble } from "../../interface/Ibubble";
import ChatbotBubble from "./bubble/ChatbotBubble";

const ChatList = ({bubbles}: {bubbles: Ibubble[]}) => {

  // NOTE: 메세지가 채팅에 올라올 때 스크롤이 최신 메세지를 보여주도록
  const scrollEndRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    if (scrollEndRef.current) {
      scrollEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [bubbles]);

  return (
    <div className="max-h-full min-w-full w-full h-full flex flex-col no-scrollbar overflow-y-auto">
      {bubbles.map((bubble, index) => (
      bubble.speaker ? <UserBubble key={index} text={bubble.message} /> : <ChatbotBubble key={index} type={bubble.type} text={bubble.message} url={bubble.url} />))}
      <div ref={scrollEndRef}></div>
    </div>
  );
};

export default ChatList;