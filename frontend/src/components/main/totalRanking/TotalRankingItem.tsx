import Avatar from "@mui/joy/Avatar";
import { getStockStyle } from "../../../utils/getStockStyle";

type UserType = {
  name: string;
  profileImage: string;
  netProfit: number;
  bankruptcyNo: number;
};

interface ItotalRankingItem {
  user: UserType;
  index: number;
}

const TotalRankingItem: React.FC<ItotalRankingItem> = ({ user, index }) => {
  // 랭킹 색 결정
  let indexColor = "text-black ";
  if (0 <= index && index < 3) {
    indexColor = ["text-first ", "text-second ", "text-third "][index];
  }

  const { profileImage, name, netProfit, bankruptcyNo } = user;
  const { textColor } = getStockStyle(netProfit);

  return (
    <tr className="w-full">
      <td align="left" className={indexColor + "align-middle"}>
        {index + 1}
      </td>
      <td align="center" className="align-middle">
        <Avatar src={profileImage} size="md" />
      </td>
      <td align="center" className="align-middle">
        {name}
      </td>
      <td align="right" className={"align-middle " + textColor}>
        {(netProfit > 0 ? "+" : "") + netProfit.toLocaleString()}
        <span className="text-stockGray">원</span>
      </td>
      <td align="right" className="align-middle text-stockGray">
        파산 {bankruptcyNo}회
      </td>
    </tr>
  );
};

export default TotalRankingItem;
