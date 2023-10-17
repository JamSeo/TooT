import { IstockItem } from "../../../interface/IstockDetails";

import ApexChart from "./ApexChart";

import Tabs from "@mui/joy/Tabs";
import TabList from "@mui/joy/TabList";
import Tab, { tabClasses } from "@mui/joy/Tab";
import TabPanel from "@mui/joy/TabPanel";
import Typography from "@mui/joy/Typography";

const StockChart: React.FC<{ stockItem: IstockItem }> = ({ stockItem }) => {
  let minCandle = stockItem?.minCandle; // 분봉
  let dayCandle = stockItem?.dayCandle; //일봉
  let weekCandle = stockItem?.weekCandle; //주봉
  return (
    <Tabs
      aria-label="stock-chart"
      defaultValue={0}
      size="sm"
      sx={{
        bgcolor: "transparent",
        height: "100%",
        marginRight: "12px",
        gap: 1,
      }}
    >
      <TabList
        disableUnderline
        sx={{
          p: 0.5,
          gap: 0.5,
          borderRadius: "xl",
          bgcolor: "background.level1",
          [`& .${tabClasses.root}[aria-selected="true"]`]: {
            boxShadow: "sm",
            bgcolor: "background.surface",
          },
          flex: "flex",
          justifyContent: "right",
        }}
      >
        <Tab disableIndicator>
          <Typography level="title-sm">분</Typography>
        </Tab>
        <Tab disableIndicator>
          <Typography level="title-sm">일</Typography>
        </Tab>
        <Tab disableIndicator>
          <Typography level="title-sm">주</Typography>
        </Tab>
      </TabList>
      <TabPanel value={0} sx={{ padding: "0" }} className="no-scrollbar">
        <ApexChart title="min" data={minCandle} />
      </TabPanel>
      <TabPanel value={1} sx={{ padding: "0" }} className="no-scrollbar">
        <ApexChart title="day" data={dayCandle} />
      </TabPanel>
      <TabPanel value={2} sx={{ padding: "0" }} className="no-scrollbar">
        <ApexChart title="week" data={weekCandle} />
      </TabPanel>
    </Tabs>
  );
};

export default StockChart;
