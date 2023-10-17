import { createContext, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import Grid from "./Grid";
import Nav from "./common/nav/Nav";
import CustomCircularProgress from "./common/circularProgress/CustomCircularProgress";
import { fetchUserAuthData } from "./utils/fetchUserAuthData";
import { IuserAuthContext } from "./interface/IauthUserContext";
import { HelmetProvider } from "react-helmet-async";
import { pushBotBubble } from "./utils/chat/chat";

/** 유저정보 & 토큰관리용 ContextAPI */
export const UserAuthContext = createContext<IuserAuthContext | undefined>(
  undefined
);

function App() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [isMobile, setIsMobile] = useState(false);

  const handleResize = () => {
    if (window.innerWidth < 768 || window.innerWidth <= window.innerHeight) {
      setIsMobile(true);
    } else {
      setIsMobile(false);
    }
  };

  useEffect(() => {
    handleResize();
    window.addEventListener("resize", handleResize);
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  useEffect(() => {
    const isVisited = localStorage.getItem("isVisited");
    if (!isVisited) {
      localStorage.setItem("isVisited", "true");
      pushBotBubble(
        `모의 주식 투자 트레이닝 서비스
      TooT에 오신 것을 환영합니다!
      TooT은 챗봇을 통해 다양한 상호작용을 제공합니다.
      
      다음은 사용 가능한 챗봇 명령어입니다.

      KOSPI 32 주식 조회
      - ex) 전체 주식 볼래
      - ex) 삼성전자 볼래

      사용자 주식 조회
      - ex) 내 주식
      - ex) 내 삼성전자
      - ex) 내 거래 내역
      - ex) 내 파산 기록

      주식 거래
      - ex) 삼성전자 5주 매수
      - ex) 삼성전자 5주 매도

      퀴즈 풀기
      - ex) 데일리 퀴즈 풀기

      랭킹 조회
      - ex) 전체 랭킹 보기
      - ex) 친구 랭킹 보기

      페이지 이동
      - ex) 메인 페이지 이동
      - ex) 튜토리얼 이동
      `,
        dispatch
      );
      navigate("/tutorials");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // 유저정보 & Access 토큰 요청하기
  const { data: contextData, isLoading } = useQuery(
    "user-auth-data",
    fetchUserAuthData,
    {
      refetchOnWindowFocus: false, // 다른 탭에서 다시 페이지 접근 시 refetch 취소
      retry: 0, // 오류로 인한 refetch 횟수 제한
    }
  );

  return (
    <HelmetProvider>
      <UserAuthContext.Provider value={contextData}>
        {!isMobile ? (
          <div className="App w-screen max-h-screen h-screen flex flex-col bg-background">
            <Nav />
            {isLoading ? <CustomCircularProgress /> : <Grid />}
          </div>
        ) : null}
        {isMobile ? (
          <div className="w-screen h-screen flex items-center justify-center">
            <span>PC 화면으로 접속해주세요!</span>
          </div>
        ) : null}
      </UserAuthContext.Provider>
    </HelmetProvider>
  );
}

export default App;
