import { Route, Routes } from "react-router-dom";
import Home from "./components/main/Home";
import NotFound from "./common/notfound/NotFound";
import Example from "./components/main/Example";
import RankingContainer from "./components/ranking/RankingContainer";
import StockDetails from "./components/stockDetails/StockDetails";
import UserStock from "./components/user/stock/UserStock";
import UserStockDetail from "./components/user/stockDetail/UserStockDetail";
import Bankrupt from "./components/user/bankrupt/Bankrupt";
import BankruptDetail from "./components/user/bankrupt/BankruptDetail";
import UserTrade from "./components/user/trade/UserTrade";
import QuizContainer from "./components/quiz/QuizContainer";
import CustomCircularProgress from "./common/circularProgress/CustomCircularProgress";
import MainUserContainer from "./components/user/MainUserContainer";
import AuthRouterGuard from "./AuthRouterGuard";
import StockAll from "./components/stock/StockAll";
import Tutorials from "./components/tutorials/Tutorials";

const Routers = () => {
  return (
    <Routes>
      {/* 비인가유저 접근 가능 페이지 */}
      <Route path="/" element={<Home />}></Route>
      <Route path="/loading" element={<CustomCircularProgress />}></Route>
      <Route path="*" element={<NotFound />}></Route>
      <Route path="/tutorials" element={<Tutorials />}></Route>

      {/* 비인가유저 접근 가능 페이지 */}
      <Route path="/" element={<AuthRouterGuard />}>
        <Route path="/stock/all" element={<StockAll />} />
        <Route path="/stock/:stockId" element={<StockDetails />}></Route>
        {/* TODO: /user 대신 /:userID 사용 예정, user/bankrupt/:userID 대신 :userId/bankrupt */}
        <Route path="/user/:userId" element={<MainUserContainer />}>
          <Route path="stock" element={<UserStock />}></Route>
          {/* TODO: stockId로 바꾸기 */}
          <Route path="stock/:stockId" element={<UserStockDetail />}></Route>
          <Route path="bankrupt" element={<Bankrupt />}></Route>
          <Route
            path="bankrupt/:bankruptcyNo"
            element={<BankruptDetail />}
          ></Route>
          <Route path="trade" element={<UserTrade />}></Route>
        </Route>
        <Route path="/ranking" element={<Example />}>
          <Route
            path="total"
            element={<RankingContainer size="big" title="전체 랭킹" />}
          ></Route>
          <Route
            path="friend"
            element={<RankingContainer size="big" title="친구 랭킹" />}
          ></Route>
        </Route>
        <Route path="/quiz" element={<QuizContainer />}></Route>
      </Route>
    </Routes>
  );
};

export default Routers;
