import Avatar from "@mui/joy/Avatar";
import { getStockStyle } from "../../utils/getStockStyle";

const RankingItem = ({
  size,
  user,
  index,
}: {
  user: {
    name: string;
    profileImage: string;
    bankruptcyNo: number;
    netProfit: number;
  };
  index: number;
  size: string;
}) => {
  const { name, profileImage, bankruptcyNo, netProfit } = user;
  const { textColor } = getStockStyle(netProfit);
  // 랭킹 색 결정
  let indexColor = "text-black ";
  if (0 <= index && index < 3) {
    indexColor = ["text-first ", "text-second ", "text-third "][index];
  }
  return (
    <tr className="w-full">
      <td
        align="center"
        className={`${
          size === "small" ? "text-[24px]" : "text-[52px]"
        } align-middle ${indexColor}`}
      >
        {index + 1}
      </td>
      <td align="center" className="align-middle">
        <Avatar
          src={profileImage}
          sx={{
            "--Avatar-size": "55px",
          }}
        />
      </td>
      <td
        align="center"
        className={`${
          size === "small" ? "text-[16px]" : "text-[28px]"
        } align-middle`}
      >
        {name}
      </td>
      <td
        align="right"
        className={`${
          size === "small" ? "text-[16px]" : "text-[28px]"
        } align-middle ${textColor}`}
      >
        {(netProfit > 0 ? "+" : "") + netProfit.toLocaleString("ko-KR")}
        <span className="text-stockGray"> 원</span>
      </td>
      <td
        align="right"
        className={`${
          size === "small" ? "text-[12px]" : "text-[20px]"
        } align-middle text-stockGray`}
      >
        파산 {bankruptcyNo}회
      </td>
    </tr>
  );
};

export default RankingItem;
