import { useState } from "react";
import { ItemOverviewHeader } from "./StockInformationTabs";
import { IstockItem } from "../../../interface/IstockDetails";

/** 상세조회 페이지 재무정보 */
const CompanyOverview: React.FC<{
  stockId: string;
  stockItem: IstockItem;
}> = ({ stockId, stockItem }) => {
  const overview = stockItem?.outline;
  const [showMore, setShowMore] = useState(false);

  // 내용을 문장으로 분할
  const sentences = overview
    .split(".")
    .filter((sentence) => sentence.trim() !== "");
  const firstSentence = sentences[0];

  return (
    <>
      {/* 종목 타이틀(로고,이름,코드) */}
      <div className="h-3/12">
        <ItemOverviewHeader stockId={stockId} stockItem={stockItem} />
      </div>
      <h2 className="text-md font-bold mb-2">기업개요</h2>
      <p className="text-sm mb-2">{firstSentence}.</p>
      {showMore &&
        sentences.map((sentence, index) => (
          <p key={index} className="text-sm mb-2">
            {sentence}.
          </p>
        ))}
      <button onClick={() => setShowMore(!showMore)}>
        <p className="text-sm text-gray-600">
          {showMore ? "간략히" : "더보기"}
        </p>
      </button>
    </>
  );
};

export default CompanyOverview;
