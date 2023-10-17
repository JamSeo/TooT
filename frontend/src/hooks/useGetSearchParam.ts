import { useSearchParams } from "react-router-dom";

/** URI parameter에서 함수에 입력된 key 값을 반환 */
export const useGetSearchParam = (key: string) => {
  const [searchParam] = useSearchParams();
  const value = searchParam.get(key);

  return value;
};
