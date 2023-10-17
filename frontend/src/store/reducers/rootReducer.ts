import { combineReducers } from "redux";
import bubbleReducer from "../slices/bubbleSlice";
import stockReducer from "../slices/stockSlice";
import chatInputReducer from "../slices/chatInputSlice";

const rootReducer = combineReducers({
    bubbles: bubbleReducer,
    stock: stockReducer,
    chatbotResponding: chatInputReducer,
});

export default rootReducer;