import ReactApexChart from "react-apexcharts";
import {
  IminChartData,
  IdateChartData,
} from "../../../interface/IstockDetails";

/** 분봉 x축: "HHmmss" 데이터를 "HH시 mm분"으로 parsing */
const parseMinTime = (timeStr: string) => {
  const hour = timeStr.substring(0, 2);
  const minute = timeStr.substring(2, 4);
  return `${parseInt(hour, 10)}시 ${parseInt(minute, 10)}분`;
};

/** 일,주봉 x축: "YYYYMMDD" 데이터를 "YYYY.MM.DD(day)"로 parsing */
const parseDateTime = (timeStr: string) => {
  const year = timeStr.substring(0, 4);
  const month = timeStr.substring(4, 6);
  const date = timeStr.substring(6, 8);

  // 요일 구하기
  const yearNum = parseInt(year, 10);
  const monthNum = parseInt(month, 10) - 1; // JavaScript의 월은 0부터 시작
  const dateNum = parseInt(date, 10);
  const dayDate = new Date(yearNum, monthNum, dateNum);
  const options: Intl.DateTimeFormatOptions = {
    weekday: "long",
    timeZone: "Asia/Seoul",
  };
  const day = new Intl.DateTimeFormat("ko-KR", options)
    .format(dayDate)
    .substring(0, 1);

  return `${year}.${month}.${date}(${day})`;
};

/** 분봉 데이터 parsing 함수 */
const parseDataForMin = (data: IminChartData[]) => {
  return {
    times: data.map((item) => parseMinTime(item.time)).reverse(),
    prices: data.map((item) => parseInt(item.price)).reverse(),
    amounts: data.map((item) => parseInt(item.amount)).reverse(),
  };
};

/** 일,주봉 데이터 parsing 함수 */
const parseDataForDate = (data: IdateChartData[]) => {
  return {
    times: data.map((item) => parseDateTime(item.date)).reverse(),
    prices: data.map((item) => parseInt(item.endPrice)).reverse(),
    amounts: data.map((item) => parseInt(item.amount)).reverse(),
  };
};

/** 주식 차트 UI */
const ApexChart: React.FC<{
  title: "min" | "day" | "week";
  data: IminChartData[] | IdateChartData[];
}> = ({ title, data }) => {
  // 데이터 parsing
  const { times, prices, amounts } =
    title === "min"
      ? parseDataForMin(data as IminChartData[])
      : parseDataForDate(data as IdateChartData[]);

  // 분봉, 일봉, 주봉 별로 그래프 색 설정
  const colors: {
    min: string;
    day: string;
    week: string;
  } = {
    min: "#FF5B5B", // 분홍
    day: "#74C69D", // 초록
    week: "#3498DB", // 파랑
  };

  const sharedChartOptions = {
    chart: {
      group: "stock-charts", // 동일한 그룹 이름으로 두 차트를 그룹화
      toolbar: {
        show: true, // Line 그래프의 도구 모듈 활성화
      },
    },
    colors: [colors[title]],
    xaxis: {
      categories: times,
    },
    yaxis: {
      opposite: true,
    },
  };

  const priceOptions = {
    ...sharedChartOptions,
    chart: {
      ...sharedChartOptions.chart,
    },
    stroke: {
      curve: "smooth" as const, // 선을 곡선 형태로 연결
      width: 3, // 선의 두께
    },
    grid: {
      padding: {
        top: 5,
        bottom: -25,
      },
    },
    xaxis: {
      ...sharedChartOptions.xaxis,
      labels: {
        show: false, // Line 그래프의 x축 라벨 숨김
      },
      axisTicks: {
        show: false, // Line 그래프의 x축 눈금 숨김
      },
    },
  };

  const amountOptions = {
    ...sharedChartOptions,
    chart: {
      ...sharedChartOptions.chart,
      toolbar: {
        show: false, // Bar 그래프의 도구 모듈 비활성화
      },
    },
    grid: {
      padding: {
        top: -20,
        bottom: -5,
      },
    },
    dataLabels: {
      enabled: false, // 거래량 데이터 값 숨김
    },
    xaxis: {
      ...sharedChartOptions.xaxis,
      labels: {
        show: true,
        hideOverlappingLabels: true,
        trim: false,
        rotate: 0,
        rotateAlways: false, // 항상 회전하는 것을 방지
        // 10개 간격으로 보여줌
        formatter: function (value: string) {
          const index = times.indexOf(value);
          return index % 10 === 0 ? value : "";
        },
      },
      axisTicks: {
        show: true, // x축 눈금 보여줌
      },
    },
  };

  const chartName = title === "min" ? "시가" : "종가";

  return (
    <div className="h-full">
      <ReactApexChart
        options={priceOptions}
        series={[{ name: chartName, data: prices }]}
        height="66%"
        type="line"
      />
      <ReactApexChart
        options={amountOptions}
        series={[{ name: "거래량", data: amounts }]}
        height="34%"
        type="bar"
      />
    </div>
  );
};

export default ApexChart;
