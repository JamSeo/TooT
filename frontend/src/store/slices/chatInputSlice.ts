import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isChatbotResponding: false,
};

const chatInputSlice = createSlice({
    name: "isChatbotResponding",
    initialState,
    reducers:{
        toggle: (state) => {
            state.isChatbotResponding = !state.isChatbotResponding;
        },
        setRespondingTrue: (state) => {
          state.isChatbotResponding = true;
        },
        setRespondingFalse: (state) => {
          state.isChatbotResponding = false;
        },
    }
});

export const { toggle, setRespondingTrue, setRespondingFalse } = chatInputSlice.actions;
export default chatInputSlice.reducer;