import React, { useContext } from "react";
import { useQuery } from "react-query";
import { useNavigate, useParams } from "react-router-dom";
import { UserAuthContext } from "../../App";
import { api } from "../../utils/api";

import StockChart from "./stockChart/StockChart";
import StockInformationTabs from "./stockInformationTabs/StockInformationTabs";
import FavoriteItemsCarousel from "../main/favoriteStocks/FavoriteItemsCarousel";
import CustomCircularProgress from "../../common/circularProgress/CustomCircularProgress";
import NotFound from "../../common/notfound/NotFound";
import { StockDetailsTitle } from "./stockItemTitle/StockDetailsTitle";
import { Helmet } from "react-helmet";

/** 주식 상세정보 화면 */
const StockDetails: React.FC = () => {
  const navigate = useNavigate();

  // 주식 ID 읽기
  const { stockId } = useParams<{ stockId: string }>();

  // Access토큰 가져오기
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  // 종목 정보 가져오기
  const { isLoading, data, error } = useQuery(
    ["stock-details", stockId],
    async () => {
      const response = await api.get(`/stock/${stockId}`, {
        headers: { accesstoken: accessToken },
      });
      return response?.data;
    },
    { refetchInterval: 5000 } // 5초마다 요청
  );

  if (isLoading) return <CustomCircularProgress />;

  if (typeof stockId === "undefined") return <NotFound />;

  // 종목코드가 KOSPI 32에 없을 때
  if (
    error ||
    data?.error?.code === "MYSQL_NO_DATA" ||
    data?.error?.message === "994"
  ) {
    alert("올바른 종목 코드로 조회해주시기 바랍니다.");
    navigate("/stock");
    return <NotFound />;
  }

  let stockItem = data?.data;

  return (
    <div className="h-full">
      <Helmet>
        <title>{`TooT - ${stockItem.stockName}`}</title>
      </Helmet>
      {/* 상단: 좋아요 종목 캐러셀 */}
      <div className="h-1/5">
        <FavoriteItemsCarousel />
      </div>
      {/* 하단: 상세 종목 조회 Container */}
      <div className="h-4/5 px-6 pb-4">
        <div className="h-full grid grid-rows-6 grid-cols-3 gap-2">
          {/* 종목명, 로고, 코드, 매수/매도 버튼 */}
          <div className="row-span-1 col-span-3 flex items-center">
            <StockDetailsTitle stockId={stockId} stockItem={stockItem} />
          </div>
          {/* 종목 그래프 차트 */}
          <div className="row-span-5 col-span-2 h-full">
            <StockChart stockItem={stockItem} />
          </div>
          {/* 종목 정보 */}
          <div className="row-span-5 col-span-1 h-full">
            <StockInformationTabs stockId={stockId} stockItem={stockItem} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default StockDetails;
