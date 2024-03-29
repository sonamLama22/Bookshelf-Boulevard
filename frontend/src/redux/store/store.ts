import { configureStore } from "@reduxjs/toolkit";
import { adminApi } from "../../services/Auth/apiSlice";
import authReducer from "../slices/auth";
import messageReducer from "../slices/message";

const reducer = {
  auth: authReducer,
  message: messageReducer,
  [adminApi.reducerPath]: adminApi.reducer,
};

export const store = configureStore({
  reducer: reducer,
  devTools: true,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(adminApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
