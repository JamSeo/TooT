import { IuserBankrupt } from "../../../interface/IuserBankrupt";

const BankruptDetailTotal = ({bankruptTotal}: {bankruptTotal: IuserBankrupt | undefined}) => {
  return(
    <div className="font-light text-stockGray min-h-0 h-[120px] gap-y-2 gap-x-6 p-4 mb-4 rounded-lg grid grid-cols-6 grid-rows-2 bg-lightYellow border border-solid border-gray-200">
      <div className="col-span-1 row-span-1 flex items-center">총순수익(평가손익)</div>
      <div className="col-span-1 row-span-1 flex items-center justify-end text-stockBlue text-[20px]">{bankruptTotal?.netIncome.toLocaleString()}</div>
      <div className="col-span-1 row-span-1 flex items-center justify-center">평가액</div>
      <div className="col-span-1 row-span-1 flex items-center justify-end">
        <span className=" text-gray-800 text-[20px]">{bankruptTotal?.lastSeedMoney?.toLocaleString()}</span>
        <span className="text-[18px] ml-1">원</span>
      </div>
      <div className="col-span-2 row-span-1 flex items-center justify-end">총자산</div>
      <div className="col-span-1 row-span-1 flex items-center">총수익률(손익률)</div>
      <div className="col-span-1 row-span-1 flex items-center justify-end">
        <span className=" text-stockBlue text-[20px]">{bankruptTotal?.roi}</span>
        <span className="text-[18px] ml-1">%</span>
      </div>
      <div className="col-span-1 row-span-1 flex items-center justify-center">예수금</div>
      <div className="col-span-1 row-span-1 flex items-center justify-end">
        <span className=" text-gray-800 text-[20px]">{bankruptTotal?.lastCash?.toLocaleString()}</span>
        <span className="text-[18px] ml-1">원</span>
      </div>
      <div className="col-span-2 row-span-1 flex items-center justify-end text-[32px]">
        <span className="text-gray-800">{bankruptTotal?.lastTotalAsset.toLocaleString()}</span>
        <span className="text-stockGray text-[28px] ml-2.5">원</span>
      </div>
    </div>
  );
};

export default BankruptDetailTotal;