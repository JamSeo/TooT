import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    stockName: null,
    stockId: null,
    share: null,
    tradeType: null,
};

const stockSlice = createSlice({
    name: "stock",
    initialState,
    reducers:{
        set: (state, action) => {
            state.stockName = action.payload.stockName;
            state.stockId = action.payload.stockId;
            state.share = action.payload.share;
            state.tradeType = action.payload.tradeType;
        },
        reset: (state) => {
            state.stockName = null;
            state.stockId = null;
            state.share = null;
            state.tradeType = null;
        }
    }
});

export const { set, reset } = stockSlice.actions;
export default stockSlice.reducer;