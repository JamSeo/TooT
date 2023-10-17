import RankingItem from "./RankingItem";
import { useAutoAnimate } from "@formkit/auto-animate/react";

const Ranking = ({
  size,
  rankingList,
}: {
  size: string;
  rankingList: any[];
}) => {

  const [animationParent] = useAutoAnimate();

  return (
    <div className="w-full h-full min-h-0 no-scrollbar overflow-y-auto">
      <table className="w-full h-full min-h-0 table-auto border-separate border-spacing-x-2 border-spacing-y-8">
        <tbody className="w-full h-full min-h-0" ref={animationParent}>
          {rankingList.map((data, index) => (
            <RankingItem size={size} user={data} index={index} key={index} />
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default Ranking;
