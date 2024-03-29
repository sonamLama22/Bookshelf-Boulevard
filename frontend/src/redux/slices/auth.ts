import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import { setMessage } from "./message";

import AuthService from "../../services/Auth/authService";

const userString = localStorage.getItem("user");
const user = userString ? JSON.parse(userString) : null;

interface User {
  username: string;
  email: string;
  password: string;
  role: string;
  token: string;
}

interface RegisterPayload {
  username: string;
  email: string;
  password: string;
}

interface LoginPayload {
  email: string;
  password: string;
}

export const register = createAsyncThunk(
  "auth/register",
  async (payload: RegisterPayload, thunkAPI) => {
    try {
      const response = await AuthService.register(
        payload.username,
        payload.email,
        payload.password
      );
      thunkAPI.dispatch(setMessage(response.data.message));
      return response.data;
    } catch (error: any) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      thunkAPI.dispatch(setMessage(message));
      return thunkAPI.rejectWithValue();
    }
  }
);

export const login = createAsyncThunk(
  "auth/login",
  async ({ email, password }: LoginPayload, thunkAPI) => {
    try {
      const data = await AuthService.login(email, password);
      return { user: data };
    } catch (error: any) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      thunkAPI.dispatch(setMessage(message));
      return thunkAPI.rejectWithValue();
    }
  }
);

export const logout = createAsyncThunk("auth/logout", async () => {
  await AuthService.logout();
});

interface AuthState {
  isLoggedIn: boolean;
  user: User | null;
}

const initialState: AuthState = {
  isLoggedIn: !!user,
  user: user || null,
};

// const authSlice = createSlice({
//   name: "auth",
//   initialState,
//   reducers: {},
//   extraReducers: (builder) => {
//     builder
//       .addCase(register.fulfilled, (state) => {
//         state.isLoggedIn = false;
//       })
//       .addCase(register.rejected, (state) => {
//         state.isLoggedIn = false;
//       })
//       .addCase(login.fulfilled, (state, action: PayloadAction<User>) => {
//         state.isLoggedIn = true;
//         state.user = action.payload;
//       })
//       .addCase(login.rejected, (state) => {
//         state.isLoggedIn = false;
//         state.user = null;
//       })
//       .addCase(logout.fulfilled, (state) => {
//         state.isLoggedIn = false;
//         state.user = null;
//       });
//   },
// });

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(register.fulfilled, (state) => {
        state.isLoggedIn = false;
      })
      .addCase(
        login.fulfilled,
        (state, action: PayloadAction<{ user: User; token: string }>) => {
          state.isLoggedIn = true;
          state.user = action.payload.user;
        }
      )
      .addMatcher(
        (action) =>
          [register.rejected, login.rejected, logout.fulfilled].includes(
            action.type
          ),
        (state) => {
          state.isLoggedIn = false;
          state.user = null;
        }
      );
  },
});

const { reducer } = authSlice;
export default reducer;
