import { useNavigate } from "react-router-dom";
import { IuserStock } from "../../../interface/IuserStock";

const UserStockItem = ({ stock}: { stock:IuserStock}
) => {
  const navigate = useNavigate();
  const handleStockClick = () => {
    navigate(`${stock.stockId}`);
  };

  return (
    <div onClick={handleStockClick} className="min-h-0 font-light grid grid-cols-3 grid-rows-3 gap-2.5 p-4 mb-4 rounded-lg border-solid border border-gray-200 cursor-pointer">
      <div className="col-span-2 row-span-1 text-[24px]">{stock.stockName}</div>
      <div className="col-span-1 row-span-1 text-[20px] text-right text-gray-600">{stock.hold} <span className="font-light text-stockGray">주</span></div>
      <div className="col-span-1 row-span-1 flex text-base justify-between">
        <div className="text-[16px] text-stockGray font-light">순수익(평가손익)</div>
        <div className={`text-[20px] ${stock.profit === 0 ? "text-gray-800" : (stock.profit > 0 ? "text-stockRed" : "text-stockBlue")} font-light`}>{stock.profit.toLocaleString()}</div>
      </div>
      <div className="col-span-1 row-span-1 flex text-base justify-between pl-6">
        <div className="text-[16px] text-stockGray font-light">평균단가</div>
        <div className="text-[20px] font-light text-gray-800">{stock.averagePrice.toLocaleString()}</div>
      </div>
      <div className="col-span-1 row-span-1 text-right text-stockGray text-base font-light">
        평가액
      </div>
      <div className="col-span-1 row-span-1 flex text-base justify-between">
        <div className="text-[16px] text-stockGray font-light">수익률(손익률)</div>
        <div className={`text-[20px] ${stock.profit === 0 ? "text-gray-600" : (stock.profit > 0 ? "text-stockRed" : "text-stockBlue")} font-light`}>
          {stock.profitRate}
          <span className="text-stockGray"> %</span>
        </div>
      </div>
      <div className="col-span-1 row-span-1 flex text-base justify-between pl-6">
        <div className="text-[16px] text-stockGray font-light">현재가</div>
        <div className="text-[20px] font-light text-gray-800">{stock.currentPrice.toLocaleString()}</div>
      </div>
      <div className="col-span-1 row-span-1 text-right text-[24px]">
        <span className="text-gray-800 font-light">{stock.totalPrice.toLocaleString()}</span>
        <span className="text-stockGray font-light"> 원</span>
      </div>
    </div>
  );
};

export default UserStockItem;
