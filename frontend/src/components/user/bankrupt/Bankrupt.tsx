import { useContext, useState } from "react";
import Title from "../../../common/etc/Title";
import BankruptItem from "./BankruptItem";
import { UserAuthContext } from "../../../App";
import { useQuery } from "react-query";
import { api } from "../../../utils/api";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import { IuserBankrupt } from "../../../interface/IuserBankrupt";
import UserNoItem from "../UserNoItem";
import { Helmet } from "react-helmet";

const Bankrupt = () => {
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;

  const [userBankrupt, setUserBankrupt] = useState<IuserBankrupt[]>([]);

  const { isLoading } = useQuery("user-bankrupt", async () => {
    const response = await api.get("/bankruptcy/all", {
      headers: {
        accesstoken: accessToken,
      },
    });
    setUserBankrupt(response.data.data);
    console.log("파산 내역 api");
    console.log(response);
  });

  return (
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{"TooT - 내 파산 기록"}</title>
      </Helmet>
      <Title title="파산 기록" />
      { isLoading ? <CustomCircularProgress /> : <div className="h-[90%] no-scrollbar overflow-y-auto">
        { !userBankrupt ? <UserNoItem itemName="파산 기록" /> : userBankrupt?.map((item) => (
          <BankruptItem key={item.bankruptcyNo} bankrupt={item} />
        ))}
      </div>}
    </div>
  );
};

export default Bankrupt;
