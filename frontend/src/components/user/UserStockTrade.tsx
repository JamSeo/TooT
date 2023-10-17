import { IuserTrade } from "../../interface/IuserTrade";
import { useNavigate } from "react-router-dom";

const UserStockTrade = ({index, trade, isName}: {index:number, trade:IuserTrade, isName?:boolean}) => {
  const navigate = useNavigate();
  const userInfoString = localStorage.getItem("userInfo");
  const userInfo = userInfoString ? JSON.parse(userInfoString) : null;
  const userId = userInfo?.id;
  
  const handleClick = () => {
    navigate(`/user/${userId}/stock/${trade?.stockId}`);
  };
  
  return (
    <div onClick={handleClick} className="cursor-pointer font-extralight grid grid-cols-9 h-12 grid-rows-1 pl-4 pr-4 pt-2 pb-2 rounded-lg border border-solid mb-4 border-gray-200">
      <div className="col-span-2 flex items-center text-stockGray">
        {trade.dealAt}
      </div>
      <div
        className={`col-span-1 flex items-center font-light ${
          trade.isBought ? "text-stockRed" : "text-stockBlue"
        }`}
      >
        {trade.isBought ? "매수" : "매도"}
      </div>
      {isName ? <div className="col-span-1 flex items-center font-normal">{trade.stockName}</div> : <div className="col-span-1"></div>}
      <div className="col-span-1 flex items-center text-gray-800 font-light text-lg">
        {trade.amount.toLocaleString()}
        <span className="text-stockGray">주</span>
      </div>
      <div className="text-stockGray col-span-1 flex items-center">거래 단가</div>
      <div className="col-span-1 flex items-center text-gray-800 font-light text-lg">{trade.price.toLocaleString()}</div>
      <div className="text-stockGray col-span-1 flex items-center">{trade.isBought ? "매수액" : "매도액"}</div>
      <div className="col-span-1 flex items-center justify-end text-gray-800 font-light text-lg">{trade.totalPrice.toLocaleString()}</div>
    </div>
  );
};
export default UserStockTrade;