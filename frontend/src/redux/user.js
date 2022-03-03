import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  info: null,
  isLoggedIn: false,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  // The `reducers` field lets us define reducers and generate associated actions
  reducers: {
    LOG_IN: (state, action) => {
      state.info = action.payload;
      state.isLoggedIn = true;
    },
    LOG_OUT: (state) => {
      state.info = null;
      state.isLoggedIn = false;
    },
  },
});

export const { LOG_IN, LOG_OUT } = userSlice.actions;

export default userSlice.reducer;
