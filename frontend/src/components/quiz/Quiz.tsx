import { useContext, useEffect, useState } from "react";
import { useQuery } from "react-query";
import QuizHeader from "./QuizHeader";
import QuizContent from "./QuizContent";
import { useDispatch } from "react-redux";
import { Ibubble } from "../../interface/Ibubble";
import { add } from "../../store/slices/bubbleSlice";
import QuizSuccess from "./QuizSuccess";
import QuizFail from "./QuizFail";
import { useNavigate } from "react-router-dom";
import { api } from "../../utils/api";
import { UserAuthContext } from "../../App";

const Quiz = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  const [isDisabled, setIsDisabled] = useState(false);
  const [isAnswer, setIsAnswer] = useState<boolean|null>(null);
  const [quizList, setQuizList] = useState<{word: string, meaning: string}[]>([]);
  const [answer, setAnswer] = useState("");
  const [answerIndex, setAnwserIndex] = useState(-1);
  const {isFetching} = useQuery<any>("daily-quiz", async () => {
    await api.get("/quiz/", {
      headers: {
        accesstoken: accessToken,
      },
    }).then(({data}) => {
      console.log(data);
      setQuizList(data.data.quizList);
      setAnswer(data.data.answerString);
      setAnwserIndex(data.data.answerIndex);
    });
  }, {
    staleTime: Infinity,
  });

  const getUserAnswer = async (word:string) => {
    const newBubble: Ibubble = {
      message: "",
      speaker: false,
    };
    await setIsDisabled(true);
    if(answer === word){
      await api.patch("/quiz/", {
        data: null,
      }, {
        headers: {
          accesstoken: accessToken,
        }
      }).then((res) => {
        console.log(res);
      }).catch((err) => {
        console.log(err);
      });
      await setIsAnswer(true);
      newBubble.message = "정답입니다! 시드머니 10,000원을 지급해드립니다."
    }
    else {
      await setIsAnswer(false);
      newBubble.message = "오답입니다! 내일 다시 도전해주세요!";
    }
    await dispatch(add(newBubble));
  };
  
  useEffect(() => {
    if(isAnswer === true || isAnswer === false){
      setTimeout(() => {
        navigate("/");
      }, 5000);
    }
    // eslint-disable-next-line
  }, [isAnswer]);

  return (
    <div className="w-full h-full rounded-lg flex flex-col items-center">
      <QuizHeader />
      { isDisabled ? 
          ( isAnswer ? <QuizSuccess /> : <QuizFail /> )
        : <>
        <QuizContent
          answerIndex={answerIndex}
          quizList={quizList}
          isFetching={isFetching}
          getUserAnswer={getUserAnswer}
          isDisabled={isDisabled}
        />
      </>
    }
    </div>
  );
};
 
export default Quiz; 