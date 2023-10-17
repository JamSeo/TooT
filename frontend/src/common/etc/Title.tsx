import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleLeft } from "@fortawesome/free-regular-svg-icons";
import { faTrophy } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const Title = ({title , isTrophy, className, onClick}: {title:string, isTrophy?: boolean, className?: string, onClick?: any}) => {
  const navigate = useNavigate();
  return (
  <div className="w-full flex justify-between items-center mb-6" >
    <div className="flex items-center">
      <div className={"h-[10%] text-[28px] text-gray-800 font-normal" + (onClick ? " cursor-pointer" : "")} onClick={onClick} >{title ? title : ""}</div>
      {isTrophy === true ? <FontAwesomeIcon
          className="ml-2.5 text-first text-[24px]"
          icon={faTrophy}
        /> : null}
    </div>
    <FontAwesomeIcon icon={faArrowAltCircleLeft} onClick={() => navigate(-1)} className={className + " cursor-pointer text-[#1E2731] w-[36px] h-[36px]"} />
  </div>
  );
};

export default Title;
