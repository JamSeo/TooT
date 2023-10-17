import CompanyOverview from "./CompanyOverview";
import ItemSummary from "./ItemSummary";

import { IstockItem } from "../../../interface/IstockDetails";

import Tabs from "@mui/joy/Tabs";
import TabList from "@mui/joy/TabList";
import Tab, { tabClasses } from "@mui/joy/Tab";
import TabPanel from "@mui/joy/TabPanel";
import Avatar from "@mui/joy/Avatar";

/** 상세조회 페이지 종목요약 & 재무정보 */
const StockInfoTabs: React.FC<{ stockId: string; stockItem: IstockItem }> = ({
  stockId,
  stockItem,
}) => {
  return (
    <Tabs
      variant="outlined"
      aria-label="stock-info-tabs"
      defaultValue={0}
      sx={{
        borderRadius: "lg",
        boxShadow: "sm",
        overflow: "auto",
        height: "100%",
      }}
    >
      <TabList
        disableUnderline
        tabFlex={1}
        sx={{
          [`& .${tabClasses.root}`]: {
            fontSize: "sm",
            fontWeight: "lg",
            [`&[aria-selected="true"]`]: {
              color: "primary.500",
              bgcolor: "background.surface",
            },
            [`&.${tabClasses.focusVisible}`]: {
              outlineOffset: "-4px",
            },
          },
        }}
      >
        <Tab disableIndicator variant="soft" sx={{ flexGrow: 1 }}>
          종목요약
        </Tab>
        <Tab disableIndicator variant="soft" sx={{ flexGrow: 1 }}>
          기업개요
        </Tab>
      </TabList>
      {/* 종목요약 */}
      <TabPanel value={0} sx={{ overflowY: "auto" }} className="no-scrollbar">
        <ItemSummary stockId={stockId} stockItem={stockItem} />
      </TabPanel>
      {/* 기업개요 */}
      <TabPanel value={1} sx={{ overflowY: "auto" }} className="no-scrollbar">
        <CompanyOverview stockId={stockId} stockItem={stockItem} />
      </TabPanel>
    </Tabs>
  );
};

export default StockInfoTabs;

/** 종목 타이틀 */
export const ItemOverviewHeader: React.FC<{
  stockId: string;
  stockItem: IstockItem;
}> = ({ stockId, stockItem }) => {
  return (
    <div className="mb-3">
      <div className="flex items-center gap-2 mb-3">
        <Avatar alt="회사로고" src={stockItem.imageUrl} size="sm" />
        <div>
          <h2 className="text-md font-bold">{stockItem.stockName}</h2>
          <p className="text-xs text-gray-500">코스피32 {stockId}</p>
        </div>
      </div>
      <hr />
    </div>
  );
};
