import { useDraggable } from "react-use-draggable-scroll";
import { Tooltip } from "@mui/material";
import { useRef } from "react";
import { moveUrlTo } from "../../utils/chat/guideButtonUtils";
import GuideButton from "./GuideButton";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {faAddressBook, faClipboardQuestion, faHouseChimneyCrack, faMagnifyingGlassChart, faTrashCan, faTrophy, faUser, faUserFriends} from "@fortawesome/free-solid-svg-icons";
import { useDispatch } from "react-redux";
import { reset } from "../../store/slices/stockSlice";
import { faArrowAltCircleRight, faQuestionCircle } from "@fortawesome/free-regular-svg-icons";

const Guide = () => {
  const userInfoString = localStorage.getItem("userInfo");
  const userInfo = userInfoString ? JSON.parse(userInfoString) : null;
  const userId = userInfo ? userInfo.id : null;
  const dispatch = useDispatch();

  const ref = useRef<HTMLDivElement>() as React.MutableRefObject<HTMLInputElement>;
  const {events} = useDraggable(ref);

  const handleChatDelete = () => {
    dispatch(reset());
    localStorage.setItem("chat-list", JSON.stringify([]));
    window.location.reload();
  }

  return (
    <div className="w-full flex items-center justify-between">
      <div
        className="mb-2 mt-1 w-full flex whitespace-nowrap overflow-x-scroll no-scrollbar cursor-grab"
        {...events}
        ref={ref}
      >
        <GuideButton
          buttonName="전체 주식"
          isIcon={true}
          icon={faMagnifyingGlassChart}
          iconColor="text-stockRed"
          url="/stock/all"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="내 주식"
          url={`/user/${userId}/stock`}
          isIcon={true}
          icon={faUser}
          iconColor="text-stockBlue"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="전체 랭킹"
          isIcon={true}
          icon={faTrophy}
          iconColor="text-first"
          url="/ranking/total"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="친구 랭킹"
          isIcon={true}
          icon={faUserFriends}
          iconColor="text-[#FF9A9A]"
          url="/ranking/friend"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="데일리 퀴즈"
          isIcon={true}
          icon={faClipboardQuestion}
          iconColor="text-[#1EC492]"
          url="/quiz"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="내 거래"
          url={`/user/${userId}/trade`}
          isIcon={true}
          icon={faAddressBook}
          iconColor="text-[#c242f5]"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="내 파산"
          url={`/user/${userId}/bankrupt`}
          isIcon={true}
          icon={faHouseChimneyCrack}
          iconColor="text-black"
          moveUrlTo={moveUrlTo}
        />
        <GuideButton
          buttonName="채팅 삭제"
          isIcon={true}
          icon={faTrashCan}
          iconColor="text-stockGray"
          onClick={handleChatDelete}
        />
        <GuideButton
          buttonName="튜토리얼"
          url="/tutorials"
          isIcon={true}
          icon={faQuestionCircle}
          iconColor="text-[#008080]"
          moveUrlTo={moveUrlTo}
        />
      </div>
      <Tooltip title={`버튼을 잡아당기면 더 많은 버튼들이 나타납니다!`}>
        <div className="w-[10%] flex items-center justify-center">
          <FontAwesomeIcon icon={faArrowAltCircleRight} className="text-[#1E2731]" />
        </div>
      </Tooltip>
    </div>
  );
};

export default Guide;
