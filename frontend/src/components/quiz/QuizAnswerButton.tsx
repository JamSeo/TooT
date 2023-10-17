const QuizAnswerButton = ({index, word, sendUserAnswer, isDisabled}: {index:number, word:string, sendUserAnswer: (word:string) => void, isDisabled:boolean}) => {

    const handleQuizAnswerButtonClick = () => {
        sendUserAnswer(word);
    };

    return(
        <button disabled={isDisabled} onClick={handleQuizAnswerButtonClick}
         className={`text-[18px] bg-white rounded-full p-4 border border-solid border-gray-200 
         ${isDisabled ? "" : "transition ease-in-out hover:bg-stockBlue hover:text-white cursor-pointer"}`}>
            {index}. {word}
        </button>
    );
};

export default QuizAnswerButton;