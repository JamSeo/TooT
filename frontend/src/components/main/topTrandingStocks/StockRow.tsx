import { getStockStyle } from "../../../utils/getStockStyle";

type RowType = {
  rank: number;
  stockId: string;
  stockName: string;
  price: number;
  rateDifference: number;
};

type RowsType = {
  row: RowType;
  onClick: () => void;
};

/** 거래량 순위 요소 */
const StockRow: React.FC<RowsType> = ({ row, onClick }) => {
  const { stockName, rank, price, rateDifference } = row;
  const { textColor, icon } = getStockStyle(rateDifference as number);
  return (
    <tr
      key={stockName}
      onClick={onClick}
      className="hover:cursor-pointer hover:bg-slate-100"
    >
      <td align="left" className="w-1/12 align-middle">
        <span className="text-slate-500">{rank}</span>
      </td>
      <td align="left" className="w-3/12 align-middle">
        {stockName}
      </td>
      <td align="right" className={textColor + " w-4/12 align-middle"}>
        {price.toLocaleString()}
      </td>
      <td align="right" className="w-4/12 align-middle">
        <p className={textColor + " items-center justify-end"}>
          <span className="text-[10px]">{icon}</span>
          &nbsp;
          {Math.abs(rateDifference).toFixed(2)}%
        </p>
      </td>
    </tr>
  );
};

export default StockRow;
