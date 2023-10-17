import { Ibubble } from "../../interface/Ibubble";
import { createSlice } from "@reduxjs/toolkit";

const bubbleList = localStorage.getItem("chat-list");
const initialBubbles: Ibubble[] = bubbleList ? JSON.parse(bubbleList) : [];

const initialState = {
    bubbles: initialBubbles,
};

const bubbleSlice = createSlice({
    name: "bubbles",
    initialState,
    reducers:{
        add: (state, action) => {
            state.bubbles = [...state.bubbles, action.payload];
            localStorage.setItem("chat-list", JSON.stringify(state.bubbles));
        },
        reset: (state) => {
            state.bubbles = [];
        },
    }
});

export const { add } = bubbleSlice.actions;
export default bubbleSlice.reducer;