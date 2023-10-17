import { ReactNode } from "react";
import TopStocks from "./topTrandingStocks/TopStocks";
import FavoriteItemsCarousel from "./favoriteStocks/FavoriteItemsCarousel";
import TotalRanking from "./totalRanking/TotalRanking";
import { Tooltip } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Helmet } from "react-helmet";

const Home: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className="h-full">
      <Helmet>
        <meta charSet="utf-8" />
        <meta
          name="description"
          content="주린이들을 위한 모의 주식 투자 트레이닝 서비스 TooT입니다!"
        />
        <meta
          name="keywords"
          content="주린이, 모의 주식 투자, 주식 트레이닝, 모의 주식, 주식 게임, 모의 주식 게임, 주식 투자, 주식, TooT, toot, TOOT, 주식 초보, 초보, 코스피, 코스피32, 주식 단어 퀴즈, 주식 용어 퀴즈, 투투, 투자 트레이닝"
        />
        <meta name="author" content="SSAFY 9기 특화팀 A709 real_final" />
        <meta property="og:url" content="https://too-t.com/" />
        <meta property="og:title" content="TooT 투자 트레이닝 서비스" />
        <meta property="og:type" content="website" />
        <meta
          property="og:image"
          content="	https://too-t.com/static/media/money_quiz.60aab1366cf0b87cc7c1.jpg"
        />
        <meta
          property="og:description"
          content="주린이들을 위한 모의 주식 투자 서비스 TooT!"
        />
        <title>{"TooT - 투자 트레이닝"}</title>
      </Helmet>
      {/* 좋아요 종목 캐러셀 */}
      <div className="h-1/5">
        <FavoriteItemsCarousel />
      </div>
      <div className="h-2/5 flex px-8 pt-6">
        {/* 거래량 순위 */}
        <Container>
          <Title>
            실시간 거래량 순위
            <span className="text-sm text-slate-600 ml-2">
              한국투자증권 기준
            </span>
          </Title>
          <Content>
            <TopStocks />
          </Content>
        </Container>
      </div>
      <div className="h-2/5 flex px-8 pb-6 gap-12">
        {/* 퀴즈 */}
        <Container>
          <Tooltip
            placement="top"
            title={
              <div className="p-2">
                https://www.freepik.com/free-vector/businessman-with-money_1076116.htm#page=3&query=money%20quiz&position=21&from_view=search&track=ais
              </div>
            }
          >
            <div className="text-lg font-bold mb-2">데일리 퀴즈</div>
          </Tooltip>
          <Content>
            <div
              className="w-full text-2xl maple text-[#2e3252] flex flex-col items-end justify-end rounded-xl h-full p-4 bg-cover bg-center bg-moneyquiz cursor-pointer"
              onClick={() => navigate("/quiz")}
            >
              <div>데일리 퀴즈 풀고</div>
              <div>시드머니 1만원 받기!</div>
            </div>
          </Content>
        </Container>
        {/* 전체랭킹 */}
        <Container>
          <Title>전체 랭킹</Title>
          <Content className="min-h-0 overflow-y-auto">
            <TotalRanking />
          </Content>
        </Container>
      </div>
    </div>
  );
};

export default Home;

interface Icontainer {
  children: ReactNode;
  className?: string;
}

const Container = (props: Icontainer) => {
  const { children } = props;
  return (
    <div className="col-span-1 row-span-1 w-full h-full flex flex-col flex-1 overflow-hidden">
      {children}
    </div>
  );
};

const Title = (props: Icontainer) => {
  const { children, className } = props;
  const combinedClassName = "text-lg font-bold mb-2 " + (className || "");
  return <h2 className={combinedClassName}>{children}</h2>;
};

const Content = (props: Icontainer) => {
  const { children, className } = props;
  const combinedClassName = "flex-grow mb-4 " + (className || "");
  return <div className={combinedClassName}>{children}</div>;
};
