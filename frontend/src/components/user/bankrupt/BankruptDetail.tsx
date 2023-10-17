import Title from "../../../common/etc/Title";
import BankruptDetailTotal from "./BankruptDetailTotal";
import UserStockTrade from "../UserStockTrade";
import { useContext, useState } from "react";
import { UserAuthContext } from "../../../App";
import { useQuery } from "react-query";
import { api } from "../../../utils/api";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import { IuserBankrupt } from "../../../interface/IuserBankrupt";
import UserNoItem from "../UserNoItem";
import { useParams } from "react-router-dom";
import { Helmet } from "react-helmet";

const BankruptDetail = () => {
  const { bankruptcyNo } = useParams<{ bankruptcyNo?: string }>();
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  const [userBankruptTotal, setUserBankruptTotal] = useState<IuserBankrupt>();
  const [userBankruptTrade, setUserBankruptTrade] = useState([]);

  const { isLoading:isBankruptTotalLoading } = useQuery("user-bankrupt-detail-total", async () => {
    const response = await api.get(`/bankruptcy/detail/${bankruptcyNo}`, {
      headers: {
        accesstoken: accessToken,
      },
    });
    setUserBankruptTotal(response.data.data);
    console.log("상세 파산 내역 종합 api");
    console.log(response);
  });

  const { isLoading:isBankruptTradeLoading } = useQuery("user-bankrupt-detail-trade", async () => {
    const response = await api.get(`/bankruptcy/detail/${bankruptcyNo}/execution`, {
      headers: {
        accesstoken: accessToken,
      },
    });
    setUserBankruptTrade(response.data.data);
    console.log("상세 파산 내역 거래 api");
    console.log(response);
  });

  return(
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{`TooT - 내 ${bankruptcyNo ? parseInt(bankruptcyNo) + 1 : ""}회차 파산`}</title>
      </Helmet>
      <Title title={`${bankruptcyNo ? parseInt(bankruptcyNo) + 1 : ""}회차 파산 기록`} />
      { (isBankruptTotalLoading || isBankruptTradeLoading) ? 
      <CustomCircularProgress /> : 
      ((userBankruptTotal && userBankruptTrade) ? 
      <div className="w-full h-[90%] min-h-0">
        <BankruptDetailTotal bankruptTotal={userBankruptTotal} />
        <div className="w-full h-[75%] min-h-0 overflow-y-scroll no-scrollbar">
        {userBankruptTrade?.map((item, index) => (
          <UserStockTrade index={index} trade={item} isName={true} />
        ))}
        </div>
      </div> : 
      <UserNoItem itemName="파산 기록" />)}
    </div>
  );
};

export default BankruptDetail;
