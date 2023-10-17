export interface IstockItem {
  stockName: string;
  imageUrl: string;
  interested: boolean;
  industryClass: string | null;
  wics: string;
  outline: string;
  currentPrice: number;
  max52: number;
  min52: number;
  totalPrice: string;
  totalStock: number;
  minCandle: IminChartData[] | IdateChartData[];
  dayCandle: IminChartData[] | IdateChartData[];
  weekCandle: IminChartData[] | IdateChartData[];
  per: string;
  pbr: string;
  priceDifference: number;
  rateDifference: number;
}

export interface IminChartData {
  time: string;
  price: string;
  amount: string;
}

export interface IdateChartData {
  date: string;
  startPrice: string;
  endPrice: string;
  bestPrice: string;
  worstPrice: string;
  amount: string;
}
