import QuizAnswerButton from "./QuizAnswerButton";

const QuizContent = ({answerIndex, quizList, isFetching, getUserAnswer, isDisabled}: {
    answerIndex:number, quizList:{word:string, meaning:string}[], isFetching:boolean, getUserAnswer:(word:string) => void, isDisabled:boolean}) => {
    return (
        isFetching ? <div className="mt-16 text-[36px]">데일리 퀴즈를 가져오는 중</div> 
        : (<>
            <div className="leading-8 mt-4 font-light w-full h-[60%] p-4 text-[18px] rounded-lg bg-white flex items-center justify-center text-stockBlue border border-solid border-gray-200 whitespace-pre-line break-keep">
                {quizList[answerIndex]?.meaning.replaceAll(".", ".\n")}
            </div>
            <div className="w-[90%] mt-6 flex justify-between">
                {quizList.map((items, index) => <QuizAnswerButton key={index} index={index + 1} word={items.word} sendUserAnswer={getUserAnswer} isDisabled={isDisabled} />)}
            </div>
            <div className="flex justify-center w-[80%] text-stockRed mt-6">새로고침을 하실 경우 데일리 퀴즈를 풀 수 있는 기회가 사라지니 주의해주세요!</div>
        </>)
    );
};

export default QuizContent;