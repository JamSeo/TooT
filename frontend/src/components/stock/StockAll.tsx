import { useContext, useState } from "react";
import Title from "../../common/etc/Title";
import { api } from "../../utils/api";
import { UserAuthContext } from "../../App";
import CustomCircularProgress from "../../common/circularProgress/CustomCircularProgress";
import { useQuery } from "react-query";
import StockCard from "../../common/card/StockCard";
import { Helmet } from "react-helmet";

const StockAll = () => {
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;
  const [stockList, setStockList] = useState([]);

  const { isLoading, isError } = useQuery("stock-all", async () => {
    const response = await api.get("/stock/showall", {
      headers: {
        accesstoken: accessToken,
      },
    });
    setStockList(response.data.data);
    console.log(response.data.data);
  });

  if (isLoading || isError) {
    return <CustomCircularProgress />;
  }

  return (
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{"TooT - KOSPI 32"}</title>
      </Helmet>
      <Title title="전체 주식" />
      <div className="w-full h-[90%] min-h-0 overflow-y-auto no-scrollbar">
        <div className="w-full min-h-0 grid grid-cols-3 gap-4 no-scrollbar place-items-center">
          {stockList.map((item, index) => (
            <StockCard size="medium" item={item} key={index} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default StockAll;
