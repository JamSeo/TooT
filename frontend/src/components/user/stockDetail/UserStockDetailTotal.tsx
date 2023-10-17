import { useState, useContext } from "react";
import { useQuery } from "react-query";
import { IuserStock } from "../../../interface/IuserStock";
import {
  BuyButton,
  SellButton,
} from "../../stockDetails/stockItemTitle/StockDetailsTitle";
import StockOrderModal from "../../stockDetails/stockItemTitle/StockOrderModal";
import { UserAuthContext } from "../../../App";
import { IstockTheme } from "../../../interface/IstockTradingModal";
import { api } from "../../../utils/api";
import { useNavigate } from "react-router-dom";
import NotFound from "../../../common/notfound/NotFound";

const UserStockDetailTotal = ({ stock }: { stock: IuserStock | undefined }) => {
  const navigate = useNavigate();
  const stockId = stock?.stockId;

  // 보유현금 가져오기
  const userAuthContext = useContext(UserAuthContext);
  const cash = userAuthContext?.userInfo?.cash as number;

  // Access 토큰 가져오기
  const accessToken = userAuthContext?.accessToken as string;

  // 매수/매도 모달 On/Off 트리거
  const [buyModalOpen, setBuyModalOpen] = useState<boolean>(false);
  const [sellModalOpen, setSellModalOpen] = useState<boolean>(false);

  // 종목 정보 가져오기
  const { data, error } = useQuery(["stock-details", stockId], async () => {
    const response = await api.get(`/stock/${stockId}`, {
      headers: { accesstoken: accessToken },
    });
    return response?.data;
  });

  const stockData = data?.data;

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

  // 보유 주식량 저장
  let hold: number = stockData?.hold ? stockData.hold : 0;

  // 보유 주식량이 0이면 매도 버튼 비활성화
  let isDisabled: boolean = !!!hold;

  // 사용자 금융 정보
  const stockTradingInfo = {
    buy: {
      accessToken: accessToken, // Access 토큰
      stockName: stockData?.stockName, // 종목이름
      stockId: stockId, // 종목코드
      currentPrice: stockData?.currentPrice, // 현재시가
      availableQuantity: Math.floor(cash / stockData?.currentPrice), // 주문 가능 수량
    },
    sell: {
      accessToken: accessToken, // Access 토큰
      stockName: stockData?.stockName, // 종목이름
      stockId: stockId, // 종목코드
      currentPrice: stockData?.currentPrice, // 현재시가
      availableQuantity: stockData?.hold, // 주문 가능 수량
    },
  };

  // 매수/매도 모달 테마
  const theme: IstockTheme = {
    buy: { title: "매수", color: "danger", textColor: "text-red-700" },
    sell: { title: "매도", color: "primary", textColor: "text-blue-700" },
  };

  return (
    <div className="font-light bg-lightYellow min-h-0 h-[160px] grid grid-cols-3 grid-rows-3 gap-2.5 p-6 mb-4 rounded-lg border-solid border border-gray-200">
      <div className="col-span-3 row-span-1 text-[24px] font-normal flex justify-between">
        <div>
          <span className="font-light">총 </span>
          {stock?.hold}
          <span className="font-light">주</span>
        </div>
        <div className="flex justify-between items-center gap-2">
          <BuyButton onClick={() => setBuyModalOpen(true)} />
          <SellButton
            onClick={() => setSellModalOpen(true)}
            isDisabled={isDisabled}
          />
          <StockOrderModal
            modalOpen={buyModalOpen}
            setModalOpen={setBuyModalOpen}
            stockTradingInfo={stockTradingInfo.buy}
            theme={theme.buy}
          />
          <StockOrderModal
            modalOpen={sellModalOpen}
            setModalOpen={setSellModalOpen}
            stockTradingInfo={stockTradingInfo.sell}
            theme={theme.sell}
          />
        </div>
      </div>
      <div className="col-span-1 row-span-1 flex items-end text-base justify-between">
        <div className="text-[16px] text-stockGray font-light">
          순수익(평가손익)
        </div>
        <div
          className={`text-[20px] font-light ${
            stock?.profit === 0
              ? "text-stockGray"
              : stock?.profit && stock.profit > 0
              ? "text-stockRed"
              : "text-stockBlue"
          }`}
        >
          {stock?.profit.toLocaleString()}
        </div>
      </div>
      <div className="col-span-1 row-span-1 flex items-end text-base justify-between pl-6">
        <div className="text-[16px] text-stockGray font-light">평균단가</div>
        <div className="text-[20px] font-light text-gray-800">
          {stock?.averagePrice.toLocaleString()}
        </div>
      </div>
      <div className="col-span-1 row-span-1 text-right text-stockGray text-[20px] mt-2.5 font-light">
        평가액
      </div>
      <div className="col-span-1 row-span-1 flex items-end text-base justify-between">
        <div className="text-[16px] text-stockGray font-light">
          수익률(손익률)
        </div>
        <div
          className={`text-[20px] font-light ${
            stock?.profitRate === 0
              ? "text-stockGray"
              : stock?.profitRate && stock.profitRate > 0
              ? "text-stockRed"
              : "text-stockBlue"
          }`}
        >
          {stock?.profitRate}
          <span className="font-light text-stockGray"> %</span>
        </div>
      </div>
      <div className="col-span-1 row-span-1 flex items-end text-base justify-between pl-6">
        <div className="text-[16px] text-stockGray font-light">현재가</div>
        <div className="text-[20px] font-light text-gray-800">
          {stock?.currentPrice.toLocaleString()}
        </div>
      </div>
      <div className="flex justify-end items-center col-span-1 row-span-1 text-right text-[32px]">
        <span className="text-gray-800">
          {stock?.totalPrice.toLocaleString()}
        </span>
        <span className="text-stockGray text-[28px] ml-2.5">원</span>
      </div>
    </div>
  );
};

export default UserStockDetailTotal;
