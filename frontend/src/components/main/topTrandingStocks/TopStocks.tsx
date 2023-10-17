import React from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../../utils/api";
import { useQuery } from "react-query";
import { useAutoAnimate } from "@formkit/auto-animate/react";

import StockRow from "./StockRow";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";

type rowType = {
  rank: number;
  stockId: string;
  stockName: string;
  price: number;
  rateDifference: number;
};

/** 거래량 순위 */
const TopStocks: React.FC = () => {
  const navigate = useNavigate();
  const [animationParent] = useAutoAnimate();

  // 거래량 순위 요청하기
  const fetchStockRank = async () => {
    const response = await api.get("/stock/rank");
    return response?.data?.data;
  };

  const {
    data: rows,
    isLoading,
    isError,
  } = useQuery("stock-rank", fetchStockRank, {
    refetchInterval: 5000, // 5초마다 요청
  });

  if (isLoading || isError) {
    console.error("위치: TopStocks.tsx, 거래량 순위 불러오기 실패");
    return <CustomCircularProgress />;
  }

  // 랭킹 5위씩 끊어서 저장하기
  const rowTop5 = rows?.slice(0, 5);
  const rowTop10 = rows?.slice(5);

  return (
    <div className="w-full h-full flex gap-x-12">
      <table className="w-full h-full overflow-hidden">
        <tbody ref={animationParent}>
          {rowTop5?.map((row: rowType) => (
            <StockRow
              key={row.stockId}
              row={row}
              onClick={() => navigate(`/stock/${row.stockId}`)}
            />
          ))}
        </tbody>
      </table>
      <table className="w-full h-full overflow-hidden">
        <tbody ref={animationParent}>
          {rowTop10?.map((row: rowType) => (
            <StockRow
              key={row.stockId}
              row={row}
              onClick={() => navigate(`/stock/${row.stockId}`)}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TopStocks;
