// import { useLocation, useParams } from "react-router-dom";
import { useParams } from "react-router-dom";
import Title from "../../../common/etc/Title";

import UserStockDetailTotal from "./UserStockDetailTotal";
import UserStockTrade from "../UserStockTrade";
import { useContext, useState } from "react";
import { UserAuthContext } from "../../../App";
import { api } from "../../../utils/api";
import { useQuery } from "react-query";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import { IuserStock } from "../../../interface/IuserStock";
import UserNoItem from "../UserNoItem";
import { IuserTrade } from "../../../interface/IuserTrade";
import { Helmet } from "react-helmet";
import { useNavigate } from "react-router-dom";
import stockIdToName from "../../../utils/chat/stockIdToName";

const UserStockDetail = () => {
  const navigate = useNavigate();
  const { stockId } = useParams<{ stockId: string }>();
  const stockName = stockId ? stockIdToName[stockId] : "";
  const [userDetailTotal, setUserDetailTotal] = useState<IuserStock | undefined>();
  const [userDetailTrade, setUserDetailTrade] = useState<any>([]);

  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  const { isLoading: isTotalLoading } = useQuery("user-stock-detail-total", async () => {
    const response = await api.get(`/stock/my/${stockId}`, {
      headers: {
        accesstoken: accessToken, 
      },
    });
    setUserDetailTotal(response.data.data);
  });

  const { isLoading: isTradeLoading } = useQuery("user-stock-detail-trade", async () => {
    const response = await api.get(`/stock/execution/${stockId}`, {
      headers: {
        accesstoken: accessToken,
      },
    });
    setUserDetailTrade(response.data.data);
  });

  return(
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{`TooT - 내 ${stockName}}`}</title>
      </Helmet>
      <Title className="cursor-pointer" onClick={() => navigate(`/stock/${stockId}`)} title={`보유 주식 - ${stockName}`} />
      { (isTotalLoading || isTradeLoading) ? 
      <CustomCircularProgress /> : 
      ((userDetailTotal && userDetailTrade) ? 
      <div className="w-full h-[90%] min-h-0">
        <UserStockDetailTotal stock={userDetailTotal} />
        <div className="w-full h-[65%] min-h-0 overflow-y-scroll no-scrollbar">
          {userDetailTrade?.map((item:IuserTrade, index:number) => (
            <UserStockTrade key={index} index={index} trade={item} isName={true} />
          ))}
        </div>
      </div> : 
      <UserNoItem itemName="해당 보유 주식" />)}
    </div>
  );
};
export default UserStockDetail;
