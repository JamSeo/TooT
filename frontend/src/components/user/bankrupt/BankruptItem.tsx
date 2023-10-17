import { useNavigate } from "react-router-dom";
import { IuserBankrupt } from "../../../interface/IuserBankrupt";

const BankruptItem = ({bankrupt}: {bankrupt:IuserBankrupt}) => {
  const navigate = useNavigate();

  const handleBankruptClick = () => {
    navigate(bankrupt.bankruptcyNo.toString());
  };

  return (
    <div onClick={handleBankruptClick} className="cursor-pointer text-stockGray font-extralight h-14 pl-4 pr-4 pt-2 pb-2 mb-4 rounded-lg border border-solid border-gray-200 grid grid-cols-10 grid-rows-1">
      <div className="col-span-1 flex items-center text-gray-800 font-light text-lg">{bankrupt.bankruptcyNo + 1}회차</div>
      <div className="col-span-1 flex items-center">{bankrupt.bankruptAt}</div>
      <div className="col-span-1 flex items-center"></div>
      <div className="col-span-1 flex items-center">최종 평가액</div>
      <div className="col-span-2 flex items-center justify-end text-gray-800 font-light text-lg">
        {bankrupt.lastTotalAsset}
        <span className="text-stockGray ml-1">원</span>
      </div>
      <div className="col-span-1 flex items-center justify-end">순수익</div>
      <div className="col-span-1 flex items-center justify-end font-light text-stockBlue text-lg">
        {bankrupt.netIncome}
        <span className="text-stockGray ml-1">원</span>
      </div>
      <div className="col-span-1 flex items-center justify-end">손익률</div>
      <div className="col-span-1 flex items-center justify-end font-light text-stockBlue text-lg">
        {bankrupt.roi}
        <span className="text-stockGray ml-1">%</span>
      </div>
    </div>
  );
}
export default BankruptItem;
