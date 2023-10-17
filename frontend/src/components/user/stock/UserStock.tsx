import UserStockItem from "./UserStockItem";
import Title from "../../../common/etc/Title";
import { useContext, useState } from "react";
import { UserAuthContext } from "../../../App";
import { useQuery } from "react-query";
import { api } from "../../../utils/api";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import UserNoItem from "../UserNoItem";
import { Helmet } from "react-helmet";

const UserStock = () => {
  
  const [userStockList, setUserStockList] = useState([]);
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;


  const { isLoading, isError } = useQuery("user-stock", async () => {
    const response = await api.get("/stock/my", {
      headers: {
        accesstoken: accessToken,
      },
    });
    setUserStockList(response.data.data);
  });

  if (isLoading || isError) {
    return <CustomCircularProgress />;
  }

  return (
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{"TooT - 내 보유 주식"}</title>
      </Helmet>
      <Title title="보유 주식" />
      <div className="h-[90%] no-scrollbar overflow-y-auto">
        {userStockList.length < 1 ? <UserNoItem itemName="보유 주식" /> : userStockList?.map((item, index) => (
          <UserStockItem key={index} stock={item} />
        ))}
      </div>
    </div>
  );
};
export default UserStock;
