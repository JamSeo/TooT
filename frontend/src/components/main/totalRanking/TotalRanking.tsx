import { useContext } from "react";
import { useQuery } from "react-query";
import { api } from "../../../utils/api";

import TotalRankingItem from "./TotalRankingItem";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import { UserAuthContext } from "../../../App";
import { useAutoAnimate } from "@formkit/auto-animate/react";

type ListType = {
  name: string;
  profileImage: string;
  netProfit: number;
  bankruptcyNo: number;
};

const TotalRanking: React.FC = () => {
  // Access토큰 가져오기
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken as string;
  const [animationParent] = useAutoAnimate();

  // 전체랭킹 가져오기
  const { data, isLoading, error } = useQuery(
    ["ranking-list"],
    async () => {
      const response = await api.get("/rank/list", {
        headers: { accesstoken: accessToken },
      });
      return response?.data?.data;
    }
    // { refetchInterval: 10000 }
  );
  // // 전체랭킹 저장
  if (isLoading || error) return <CustomCircularProgress />;
  let rankingList = data?.list;

  return (
    <div className="w-full h-full h-min-0 overflow-y-auto no-scrollbar">
      <table className="w-full h-full border-separate border-spacing-2.5">
        <tbody ref={animationParent}>
          {rankingList?.map((list: ListType, index: number) => (
            <TotalRankingItem user={list} index={index} key={index} />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TotalRanking;
