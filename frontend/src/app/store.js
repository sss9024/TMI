import { configureStore } from "@reduxjs/toolkit";
import userReducer from "../redux/user";
import { persistStore, persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage"; // defaults to localStorage for web

export const store = configureStore({
  reducer: {
    user: persistReducer(
      {
        key: "user",
        storage,
      },
      userReducer
    ),
  },
});

export const persistor = persistStore(store);

export default { store, persistor };
