export interface IuserInfo {
  id: string;
  seedMoney: number;
  cash: number;
  profileImage: string;
  name: string;
  bankruptcyNo: number;
  lastQuizDate: null | string;
  joinAt: string;
  totalValue: number;
}

export interface Idata {
  success: boolean;
  data: IuserInfo;
  error: null | string; // 에러 메시지의 형식을 모르므로 null 또는 문자열로 추정합니다.
}

export interface IuserAuthContext {
  accessToken: string;
  userInfo: IuserInfo;
}
