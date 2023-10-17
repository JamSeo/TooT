import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const SendButton = ({handleSendClick}: {handleSendClick: (e: React.MouseEvent<HTMLButtonElement>) => void}) => {
  return(
    <button className="shrink-0 grow-0 w-7 h-7 mt-1 mb-1 mr-0.1 rounded-full bg-blue-400 text-white focus:outline-none" onClick={handleSendClick}>
      <FontAwesomeIcon icon={faPaperPlane} />
    </button>
  );
};

export default SendButton;