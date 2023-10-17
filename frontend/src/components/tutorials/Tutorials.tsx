import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleLeft } from "@fortawesome/free-regular-svg-icons";
import { useNavigate } from "react-router-dom";

import bankrupt from "../../assets/gifs/bankrupt.gif";
import buysell from "../../assets/gifs/buysell.gif";
import chatbot from "../../assets/gifs/chatbot.gif";
import daily_quiz from "../../assets/gifs/daily_quiz.gif";
import favourites from "../../assets/gifs/favourites.gif";
import login from "../../assets/gifs/login.gif";
import my_stock from "../../assets/gifs/my_stock.gif";
import ranking_all from "../../assets/gifs/ranking_all.gif";
import ranking_friend from "../../assets/gifs/ranking_friend.gif";
import stock_all from "../../assets/gifs/stock_all.gif";
import stock_detail from "../../assets/gifs/stock_detail.gif";
import trade from "../../assets/gifs/trade.gif";

interface Itab {
  fileName: string;
  index: number;
  value: number;
  file: any;
}

const Tutorials = () => {
  const navigate = useNavigate();
  const [value, setValue] = useState(1);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    console.log(newValue);
    setValue(newValue);
  };

  return (
    <div className="w-full h-full p-6 min-h-0 flex flex-col items-center">
      <div className="w-full flex items-center justify-between mb-6">
        <div className="flex items-center w-[80%]">
          <div className="w-[20%] text-[28px] text-gray-800 font-normal">
            튜토리얼
          </div>
          <Box className="max-w-[80%]">
            <Tabs
              value={value}
              onChange={handleChange}
              variant="scrollable"
              scrollButtons="auto"
              allowScrollButtonsMobile
            >
              <Tab label="로그인" />
              <Tab label="전체 주식" />
              <Tab label="관심 주식" />
              <Tab label="상세 주식" />
              <Tab label="매도 매수" />
              <Tab label="내 주식" />
              <Tab label="내 거래" />
              <Tab label="내 파산" />
              <Tab label="데일리 퀴즈" />
              <Tab label="전체 랭킹" />
              <Tab label="친구 랭킹" />
              <Tab label="챗봇" />
            </Tabs>
          </Box>
        </div>
        <FontAwesomeIcon
          icon={faArrowAltCircleLeft}
          onClick={() => navigate(-1)}
          className="cursor-pointer text-[#1E2731] w-[36px] h-[36px]"
        />
      </div>
      <CustomTab fileName="로그인" index={0} value={value} file={login} />
      <CustomTab
        fileName="전체 주식"
        index={1}
        value={value}
        file={stock_all}
      />
      <CustomTab
        fileName="관심 주식"
        index={2}
        value={value}
        file={favourites}
      />
      <CustomTab
        fileName="상세 주식"
        index={3}
        value={value}
        file={stock_detail}
      />
      <CustomTab fileName="매도 매수" index={4} value={value} file={buysell} />
      <CustomTab fileName="내 주식" index={5} value={value} file={my_stock} />
      <CustomTab fileName="내 거래" index={6} value={value} file={trade} />
      <CustomTab fileName="내 파산" index={7} value={value} file={bankrupt} />
      <CustomTab
        fileName="데일리 퀴즈"
        index={8}
        value={value}
        file={daily_quiz}
      />
      <CustomTab
        fileName="전체 랭킹"
        index={9}
        value={value}
        file={ranking_all}
      />
      <CustomTab
        fileName="친구 랭킹"
        index={10}
        value={value}
        file={ranking_friend}
      />
      <CustomTab fileName="챗봇" index={11} value={value} file={chatbot} />
    </div>
  );
};

const CustomTab = (tab: Itab) => {
  const { fileName, index, value, file } = tab;
  if (value === index) {
    return (
      <div
        role="tabpanel"
        id={String(index)}
        className="w-full flex justify-center items-center h-[90%]"
      >
        <img src={file} alt={fileName} className="h-[100%]" />
      </div>
    );
  } else {
    return null;
  }
};

export default Tutorials;
