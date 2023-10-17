import { ItemOverviewHeader } from "./StockInformationTabs";
import { IstockItem } from "../../../interface/IstockDetails";
import { Tooltip } from "@mui/joy";
import { ReactNode } from "react";

/** 상세조회 페이지 종목요약 */
const ItemSummary: React.FC<{ stockId: string; stockItem: IstockItem }> = ({
  stockId,
  stockItem,
}) => {
  return (
    <div className="mb-3">
      {/* 종목 타이틀(로고,이름,코드) */}
      <div className="h-3/12">
        <ItemOverviewHeader stockId={stockId} stockItem={stockItem} />
      </div>
      {/* 간단정보 */}
      <div className="h-5/12">
        <ItemData data={stockItem} />
      </div>
    </div>
  );
};

export default ItemSummary;

/** 간단 정보 컴포넌트 */
const ItemData: React.FC<{ data: IstockItem }> = ({ data }) => {
  const { totalPrice, totalStock, industryClass, wics, per, pbr } = data;
  return (
    <div className="mb-3">
      <div className="grid grid-rows-4 grid-cols-2 gap-3 mb-3">
        <ItemDataElement
          title="시가총액"
          element={totalPrice}
          description={
            <>
              <p>☝🏼 주식 한 주의 가격을 전체 주식수로 곱한 값으로,</p>
              <p className="mb-2">
                <span className="text-rose-600">회사의 크기나 규모</span>를
                나타내는 지표로 사용됩니다.
              </p>
              <p className="text-xs">
                예) 회사의 주식 한 주의 가격이 10,000원이고, 주식수가
                1,000,000주라면,
              </p>
              <p className="text-xs">
                시가총액은{" "}
                <span className="text-indigo-600">
                  10,000원 x 1,000,000주 = 1,000억원
                </span>
                입니다.
              </p>
            </>
          }
        />
        <ItemDataElement
          title="주식수"
          element={totalStock.toLocaleString() + "주"}
          description={
            <>
              <p className="mb-2">
                ☝🏼 회사가 발행한
                <span className="text-rose-600"> 주식의 총 개수</span>입니다.
              </p>
              <p className="text-xs">
                예: 회사 A가 1,000주의 주식을 발행했다면, 주식수는
                1,000주입니다.
              </p>
            </>
          }
        />
        <ItemDataElement
          title="산업군"
          element={industryClass}
          description={
            <>
              <p className="mb-2">
                ☝🏼 <span className="text-rose-600">비슷한 종류의 비즈니스</span>
                를 하는 회사들의 그룹을 의미합니다.
              </p>
              <p className="text-xs">
                예) 자동차 제조 회사, 음식점, IT 기업 등
              </p>
            </>
          }
        />
        <ItemDataElement
          title="세부산업군"
          element={wics}
          description={
            <>
              <p className="mb-2">
                ☝🏼 산업군 내에서
                <span className="text-rose-600"> 더욱 세부적인 분류</span>
                입니다.
              </p>
              <p className="text-xs">
                예) "IT 산업" 내에서 SW 개발, HW 제조, 클라우드 서비스 등
              </p>
            </>
          }
        />
        <ItemDataElement
          title="52주 최저"
          element={data.min52.toLocaleString()}
          description={
            <p>
              ☝🏼 <span className="text-indigo-600">지난 1년</span> 동안 주식이
              거래된 <span className="text-indigo-600"> 가장 낮은 가격</span>
              입니다.
            </p>
          }
        />
        <ItemDataElement
          title="52주 최고"
          element={data.max52.toLocaleString()}
          description={
            <p>
              ☝🏼 <span className="text-rose-600">지난 1년</span>동안 주식이
              거래된
              <span className="text-rose-600"> 가장 높은 가격</span>
              입니다.
            </p>
          }
        />
        <ItemDataElement
          title="PER(배)"
          element={per}
          description={
            <>
              <p>☝🏼 주가 수익 비율로,</p>
              <p>
                <span className="text-indigo-600">낮은 PER</span>는 주식이
                상대적으로
                <span className="text-indigo-600"> 저평가</span>
                되었다는 것을,
              </p>
              <p>
                <span className="text-rose-600">높은 PER</span>은 주식이
                상대적으로
                <span className="text-rose-600"> 고평가</span>
                되었다는 것을 나타냅니다.
              </p>
            </>
          }
        />
        <ItemDataElement
          title="PBR(배)"
          element={pbr}
          description={
            <>
              <p>☝🏼 주가 자산 비율로,</p>
              <p>
                <span className="text-indigo-600">1보다 낮으면</span>
                주식이 자기자본 대비
                <span className="text-indigo-600"> 저평가</span>
                되었다는 것을,
              </p>
              <p>
                <span className="text-rose-600">1보다 높으면</span>
                주식이
                <span className="text-rose-600"> 고평가</span>
                되었다는 것을 나타냅니다.
              </p>
            </>
          }
        />
      </div>
    </div>
  );
};

/** 간단 정보 요소 */
const ItemDataElement: React.FC<{
  title: string;
  element: string | number | null;
  description?: ReactNode;
}> = ({ title, element, description }) => {
  let displayValue = element;
  // 글자 색 설정
  let textColor =
    title === "52주 최저"
      ? "text-blue-600"
      : title === "52주 최고"
      ? "text-red-600"
      : "text-black";

  // 시가총액 조 단위 설정
  if (title === "시가총액") {
    const value = parseInt(element as string);
    const 조단위 = Math.floor(value / 10000);
    const 억단위 = value % 10000;

    if (조단위 > 0) {
      displayValue = `${조단위}조 ${억단위.toLocaleString()}억`;
    } else if (억단위 > 1000) {
      displayValue = `${억단위.toLocaleString()}억`;
    } else {
      displayValue = `${억단위}억`;
    }
  }

  return (
    <div>
      <Tooltip title={description} variant="outlined">
        <h3 className="text-sm text-neutral-500">{title}</h3>
      </Tooltip>
      <p className={"text-sm " + textColor}>{displayValue}</p>
    </div>
  );
};
