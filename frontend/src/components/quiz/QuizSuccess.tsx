import Confetti from "react-confetti";

const QuizSuccess = () => {
  return (
    <div className="mt-8 flex flex-col justify-around items-center h-[60%] text-[32px]">
      <Confetti className="w-full h-full" />
      <div>정답입니다!</div>
      <div>추가 시드머니 💰💸 10,000원을 💸💰 지급해드립니다!</div>
      <div>🔥 내일도 다시 도전해주세요! 🔥</div>
      <div className="mt-16 text-[20px]">잠시 후 메인페이지로 이동합니다...</div>
    </div>
  );
};

export default QuizSuccess;