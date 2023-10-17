import React, { useRef, useState, useEffect } from "react";
import { api } from "../../../utils/api";

import CustomModal from "../../../common/modal/CustomModal";

import Button from "@mui/joy/Button";
import Typography from "@mui/joy/Typography";
import Box from "@mui/joy/Box";
import Input from "@mui/joy/Input";

import {
  IstockOrderModal,
  IpercentageButton,
} from "../../../interface/IstockTradingModal";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { reset } from "../../../store/slices/stockSlice";

const StockOrderModal: React.FC<IstockOrderModal> = ({
  modalOpen,
  setModalOpen,
  stockTradingInfo,
  theme,
}) => {
  const dispatch = useDispatch();
  // 사용자 금융 정보
  const { accessToken, stockId, currentPrice, availableQuantity } =
    stockTradingInfo;

  // 주문 수량 입력창 값
  const inputRef = useRef<HTMLInputElement | null>(null);

  // 총 주문 금액
  const [totalOrderAmount, setTotalOrderAmount] = useState(currentPrice);

  // 주문 수량
  const [orderQuantity, setOrderQuantity] = useState(1);

  // 주문 수량이 달라질 때마다 총 주문 금액도 달라짐
  useEffect(() => {
    if (theme.title === "매도") {
      setTotalOrderAmount(Math.round(orderQuantity * currentPrice * 0.99685)); // 수수료 + 세금
    } else {
      setTotalOrderAmount(orderQuantity * currentPrice);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderQuantity, currentPrice]);

  // 주문 퍼센티지 버튼 클릭 시 주문 수량과 총 주문 금액이 달라짐
  const handlePercentageClick = (percentage: number) => {
    const quantity = Math.floor(availableQuantity * (percentage / 100));
    setOrderQuantity(quantity);
    if (inputRef.current) {
      inputRef.current.value = String(quantity);
    }
  };

  // 챗봇으로 넘어왔을 경우 주문 수량 default 변경
  const chatQuantity = useSelector((state: RootState) => state.stock.share);

  useEffect(() => {
    if (
      chatQuantity !== null &&
      chatQuantity !== undefined &&
      chatQuantity > 0
    ) {
      if (chatQuantity > availableQuantity) {
        setOrderQuantity(availableQuantity);
        // 주문 수량이 변경되면 inputRef의 값을 업데이트
      } else {
        setOrderQuantity(chatQuantity);
        // 주문 수량이 변경되면 inputRef의 값을 업데이트
      }
      // 모달이 열릴 때, input 값을 1로 초기화
    } else if (modalOpen) {
      setOrderQuantity(1);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [chatQuantity, modalOpen]);

  // input 값과 주문수량을 일치화
  useEffect(() => {
    if (inputRef.current) inputRef.current.value = String(orderQuantity);
  }, [orderQuantity]);

  // 매수/매도 요청 함수
  const onClickAction = async (action: string, orderQuantity: number) => {
    // 매수 API
    let actionAPI = "/stock/buy";
    // 매도 API
    if (action === "매도") actionAPI = "/stock/sell";
    // 신청 가능여부?
    if (orderQuantity > availableQuantity || orderQuantity <= 0) {
      alert("요청하신 수량을 확인해주시기 바랍니다.");
      return;
    }
    // 매수/매도 요청
    try {
      const response = await api.post(
        actionAPI,
        { stockId: stockId, count: orderQuantity },
        { headers: { accesstoken: accessToken } }
      );

      if (response.data.data > 0) {
        alert(`${response.data.data}주 ${action}가 성공적으로 체결되었습니다.`);
      } else {
        alert("체결에 실패했습니다. 주문 수량을 확인해주세요.");
      }
      return window.location.reload();
    } catch {
      alert("주문 전송이 실패하였습니다. 잠시 후 다시 시도해주세요.");
      console.error("위치: StockOrderModal.tsx, 주문 실패");
    } finally {
      reset();
    }
  };

  /** 취소 요청 */
  const onClickCancel = async () => {
    dispatch(reset());
    return setModalOpen(false);
  };

  return (
    <CustomModal open={modalOpen} setOpen={setModalOpen}>
      <div className="h-full flex flex-col items-center justify-between">
        {/* 모달 제목 */}
        <Box sx={{ display: "flex", gap: 0.5, flexWrap: "wrap" }}>
          <Typography
            level="title-lg"
            color={theme.color}
            children={theme.title}
          />
          <Typography level="title-lg" color="neutral" children="주문" />
        </Box>
        <div className="w-full">
          <div className="flex flex-col gap-2 mb-4">
            {/* 주문가능수량 */}
            <div className="flex justify-between">
              <p className="text-sm text-neutral-500 font-bold">주문수량</p>
              <div className="flex gap-1">
                <p className="text-sm text-neutral-400 font-bold">주문가능</p>
                <p className={"text-sm font-bold " + theme.textColor}>
                  {availableQuantity}
                </p>
                <p className="text-sm text-neutral-400 font-bold">주</p>
              </div>
            </div>
            {/* 주문수량 입력창 */}
            <Input
              type="number"
              endDecorator={"주"}
              defaultValue={orderQuantity}
              onChange={(e) => setOrderQuantity(Number(e.target.value))}
              slotProps={{
                input: {
                  ref: inputRef,
                  min: 1,
                  max: availableQuantity,
                },
              }}
            />
            {/* 주문 퍼센티지 버튼 */}
            <div className="flex justify-between">
              <PercentageButton value={10} onClick={handlePercentageClick} />
              <PercentageButton value={25} onClick={handlePercentageClick} />
              <PercentageButton value={50} onClick={handlePercentageClick} />
              <PercentageButton value={100} onClick={handlePercentageClick} />
            </div>
          </div>
          <div className="flex flex-col gap-1">
            {/* 주문단가 */}
            <OrderDetails
              label="주문단가"
              title={theme.title}
              value={currentPrice}
            />
            {/* 총 주문 금액 */}
            <OrderDetails
              label="총 주문금액"
              title={theme.title}
              value={totalOrderAmount}
            />
          </div>
        </div>
        <Box sx={{ display: "flex", gap: 2.5, flexWrap: "wrap" }}>
          {/* 취소 버튼 */}
          <Button
            // onClick={() => setModalOpen(false)}
            onClick={() => onClickCancel()}
            color={theme.color}
            variant="soft"
            size="md"
            className="w-36"
          >
            취소
          </Button>
          {/* 매수/매도 버튼 */}
          <Button
            onClick={() => onClickAction(theme.title, orderQuantity)}
            color={theme.color}
            variant="solid"
            size="md"
            className="w-36"
          >
            {theme.title}(Enter)
          </Button>
        </Box>
      </div>
    </CustomModal>
  );
};

export default StockOrderModal;

/** 퍼센티지 버튼 UI */
const PercentageButton: React.FC<IpercentageButton> = ({ value, onClick }) => {
  return (
    <Button
      className="w-[70px]"
      variant="soft"
      color="neutral"
      onClick={() => onClick(value)}
    >
      {value}%
    </Button>
  );
};

interface IorderDetails {
  label: string;
  title: string;
  value: number;
}

const OrderDetails: React.FC<IorderDetails> = ({ label, title, value }) => {
  return (
    <div className="flex justify-between items-center">
      <p className="text-sm text-neutral-500 font-bold">
        {label}
        {title === "매도" && label === "총 주문금액" && (
          <span className="text-xs text-blue-500 ml-2">
            수수료 발생(0.315%)
          </span>
        )}
      </p>
      <div className="flex items-center">
        <p className="text-lg text-black font-semibold mr-1">
          {value.toLocaleString()}
        </p>
        <p className="text-sm text-neutral-400 font-bold">원</p>
      </div>
    </div>
  );
};
