import { useQuery } from "react-query";
import { api } from "../../../utils/api";

import StockCard, { IstockCardData } from "../../../common/card/StockCard";
import Carousel from "../../../common/carousel/Carousel";

import Button from "@mui/joy/Button";
import { useContext } from "react";
import { UserAuthContext } from "../../../App";
import CustomCircularProgress from "../../../common/circularProgress/CustomCircularProgress";
import { useNavigate } from "react-router-dom";

/** 관심 종목 캐러셀 */
const FavoriteItemsCarousel: React.FC = () => {
  // Access 토큰 가져오기
  const userInfo = useContext(UserAuthContext);
  const accessToken = userInfo?.accessToken;

  // 좋아요 종목 목록 가져오기
  const { data, isLoading, error } = useQuery(
    "favorite-stocks",
    async () => {
      try {
        const response = await api.get("/stock/interest/show", {
          headers: {
            accesstoken: accessToken,
          },
        });
        return response?.data;
      } catch {
        console.error("위치: FavoriteItemsCarousel.tsx, 좋아요 목록 요청 실패");
      }
    },
    { retry: 0 }
  );

  if (isLoading) return <CustomCircularProgress />;
  // 로그인 X
  if (
    error ||
    data?.error?.code === "NOT_LOGINED" ||
    data?.error?.message === "1000"
  ) {
    return <NotLogin />;
  }
  // 로그인 O, 좋아요 목록 X
  else if (data?.data === null || !data?.data.length) {
    return <NoFavoriteStocks />;
  }

  // 좋아요 카드 만들기
  const favoriteStocks = data?.data;
  const items = favoriteStocks?.map((favoriteStock: IstockCardData) => (
    <StockCard item={favoriteStock} size="small" />
  ));

  return (
    <div className="h-full flex px-6 py-4 items-center bg-slate-200 rounded-t-lg">
      <div className="h-full flex grow flex-1 overflow-hidden">
        <Carousel items={items} />
      </div>
    </div>
  );
};

export default FavoriteItemsCarousel;

/** 관심 종목이 없을 때 컴포넌트 */
const NoFavoriteStocks: React.FC = () => {
  const navigate = useNavigate();
  return (
    <div className="h-full px-6 py-4 bg-slate-200 rounded-t-lg">
      <div className="w-full h-full flex flex-col items-center justify-center gap-2">
        <p className="text-gray-500">현재 목록에 관심종목이 없습니다.</p>
        <p className="text-gray-500">종목을 추가해보세요.</p>
        <Button
          size="sm"
          variant="solid"
          color="danger"
          onClick={() => navigate("/stock/all")}
        >
          종목 추가
        </Button>
      </div>
    </div>
  );
};

/** 로그인 하지 않았을 때 컴포넌트 */
const NotLogin: React.FC = () => {
  return (
    <div className="h-full px-6 py-4 bg-slate-200 rounded-t-lg">
      <div className="w-full h-full flex flex-col items-center justify-center gap-2">
        <p className="text-gray-500">로그인 후 사용해주시기 바랍니다.</p>
        <p className="text-gray-500">관심종목을 추가해보세요.</p>
      </div>
    </div>
  );
};
