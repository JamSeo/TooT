import ChatContainer from "./components/container/ChatContainer";
import MainContainer from "./components/container/MainContainer";
import UserContainer from "./components/container/UserContainer";

const Grid = () => {
  return (
    <div className="min-h-0 h-full grid grid-cols-4 gap-6 p-6 grid-rows-3">
      <MainContainer />
      <UserContainer />
      <ChatContainer />
    </div>
  );
};

export default Grid;