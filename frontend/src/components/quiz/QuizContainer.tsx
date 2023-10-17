import { useContext, useEffect, useState } from "react";
import Quiz from "./Quiz";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { pushBotBubble } from "../../utils/chat/chat";
import QuizEnd from "./QuizEnd";
import { api } from "../../utils/api";
import { UserAuthContext } from "../../App";
import { Helmet } from "react-helmet";

const QuizContainer = () => {
  const [isSolved, setIsSolved] = useState<boolean | null>(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  useEffect(() => {
    if(accessToken){
      api
        .get("/quiz/today", {
          headers: {
            accesstoken: accessToken,
          },
        })
        .then(({ data }) => {
          console.log(data);
          setIsSolved(data.data);
        });
    }
  }, [accessToken]);

  useEffect(() => {
    if(isSolved === false){
      const message = "오늘의 주식 용어 데일리 퀴즈에 이미 도전하셨습니다.\n내일 다시 도전해주세요!";
      pushBotBubble(message, dispatch);
      setTimeout(() => {
        navigate("/");
      }, 5000);
    } else if(isSolved === true){
      const message = "오늘의 주식 용어 데일리 퀴즈를 진행합니다.";
      pushBotBubble(message, dispatch);
    }
    //eslint-disable-next-line
  }, [isSolved]);

  return(
  <div className="w-full h-full p-8 min-h-0 flex justify-center items-center bg-white">
    <Helmet>
        <title>{"TooT - 주식 용어 퀴즈"}</title>
    </Helmet>
    {(isSolved === false) ? <QuizEnd /> : null}
    {(isSolved === true) ? <Quiz /> : null}
  </div>
  );
};
export default QuizContainer;