import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { Book } from "../../types/book.types";
import { RootState } from "../../redux/store/store";

const API_URL = "http://localhost:8080/api";

export const adminApi = createApi({
  reducerPath: "adminApi",
  baseQuery: fetchBaseQuery({
    baseUrl: API_URL,

    prepareHeaders: (headers, { getState }) => {
      const token = (getState() as RootState).auth.user?.token;
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  }),
  tagTypes: ["Book"],

  endpoints: (builder) => ({
    getAllBooks: builder.query<Book[], void>({
      // retrieve all books
      query: () => `/getAllBooks`,
      providesTags: ["Book"],
    }),

    addBook: builder.mutation<{}, Book>({
      query: (book) => {
        const formData = new FormData();
        formData.append("title", book.title);
        formData.append("author", book.author);
        formData.append("price", book.price.toString());
        if (book.genre) {
          formData.append("genre", JSON.stringify(book.genre));
        }
        formData.append("description", book.description);
        formData.append("copiesAvailable", book.copiesAvailable.toString());
        if (book.data) {
          formData.append("data", book.data);
        }
        return {
          url: "/admin/addBook",
          method: "POST",
          body: formData,
        };
      },
      invalidatesTags: ["Book"],
    }),

    deleteBook: builder.mutation<void, string>({
      query: (id) => ({
        url: `/admin/deleteBook/${id}`,
        method: "DELETE",
      }),
      invalidatesTags: ["Book"],
    }),
  }),
});

export const {
  useGetAllBooksQuery,
  useAddBookMutation,
  useDeleteBookMutation,
} = adminApi;
