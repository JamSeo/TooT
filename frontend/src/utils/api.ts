import axios from "axios";

// 카카오 Redirect URI
// const REDIRECT_URI = "http://localhost:3000/loading";
const REDIRECT_URI = "https://too-t.com/loading";

// 서버 API URI
// const REACT_APP_SERVER = "http://localhost:8080";
const REACT_APP_SERVER = "https://too-t.com/api";

/** 서버로 ajax 요청 */
const api = axios.create({
  baseURL: REACT_APP_SERVER,
  withCredentials: true, // 쿠키와 인증 정보를 포함하여 요청
});

export { api, REDIRECT_URI, REACT_APP_SERVER };
