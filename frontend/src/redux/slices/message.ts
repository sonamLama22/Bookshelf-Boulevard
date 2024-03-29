import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// Define the state slice interface
interface MessageState {
  message: string | null; // Assuming message is of type string
}

// Define the initial state
const initialState: MessageState = {
  message: null,
};

const messageSlice = createSlice({
  name: "message",
  initialState,
  reducers: {
    setMessage: (state, action: PayloadAction<string>) => {
      return { message: action.payload };
    },
    clearMessage: () => {
      return { message: "" };
    },
  },
});

const { reducer, actions } = messageSlice;

export const { setMessage, clearMessage } = actions;
export default reducer;
