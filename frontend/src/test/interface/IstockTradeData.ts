export interface IstockTradeData{
  code: string,
  name: string,
  userId: string,
  date: string,
  type: boolean, // 매수가 true
  share: number,
  tradePrice: number,
  totalPrice: number,
};