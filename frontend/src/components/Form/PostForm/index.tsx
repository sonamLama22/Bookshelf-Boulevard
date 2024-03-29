import React, { useState } from "react";
import { Formik, Form, Field } from "formik";
import { useAddBookMutation } from "../../../services/Auth/apiSlice"; // Import your RTK Query mutation hook
import { Book } from "../../../types/book.types"; // Import the Book interface
import SubmitButton from "../SubmitButton";
import InputField from "../InputField";

const initialValues: Book = {
  title: "",
  author: "",
  price: 0,
  description: "",
  copiesAvailable: 0,
  genre: { genreId: 0, genreName: "" }, // Add initial values for genre
  data: undefined, // Add initial value for image
};

const PostForm: React.FC = () => {
  //   const [addBook, { isLoading }] = useAddBookMutation();
  const [addBook] = useAddBookMutation({
    // Use prepareHeaders to set the Content-Type header to 'multipart/form-data'
    prepareHeaders(headers) {
      headers.set("Content-Type", "multipart/form-data");
      return headers;
    },
  });
  const [file, setFile] = useState<File | undefined>();

  const handleSubmit = async (values: Book) => {
    try {
      const formData = new FormData();

      console.log("file", file);

      if (typeof file === "undefined") return;
      formData.append("file", file);
      formData.append("data", JSON.stringify(values)); // Convert the Book object to JSON string and append it as 'data' part

      await addBook(formData).unwrap(); // Perform the POST request with form data
      console.log("Book added successfully");
    } catch (error) {
      console.error("Error adding book:", error);
    }
  };

  function handleOnChange(e: React.FormEvent<HTMLInputElement>) {
    const target = e.target as HTMLInputElement & {
      files: FileList;
    };
    console.log(target.files);
    setFile(target.files[0]);
  }

  return (
    <Formik initialValues={initialValues} onSubmit={handleSubmit}>
      {({ values }) => (
        <div>
          <div className="flex justify-center items-center font-bold text-teal-700 p-5">
            Add a new Book
          </div>
          <Form className="max-w-sm mx-auto">
            <div className="mb-2">
              <label
                htmlFor="title"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Title:
              </label>
              <InputField type="text" id="title" name="title" />
            </div>
            <div className="mb-2">
              <label
                htmlFor="author"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Author:
              </label>
              <InputField type="text" id="author" name="author" />
            </div>
            <div className="mb-2">
              <label
                htmlFor="price"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Price:
              </label>
              <InputField type="number" id="price" name="price" />
            </div>
            <div className="mb-2">
              <label
                htmlFor="description"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Description:
              </label>
              <Field
                as="textarea"
                id="description"
                name="description"
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>
            <div className="mb-2">
              <label
                htmlFor="copiesAvailable"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Copies Available:
              </label>
              <InputField
                type="number"
                id="copiesAvailable"
                name="copiesAvailable"
              />
            </div>
            <div className="mb-2">
              <label
                htmlFor="genre.genreId"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Genre ID:
              </label>
              <InputField
                type="number"
                id="genre.genreId"
                name="genre.genreId"
              />
            </div>
            <div className="mb-2">
              <label
                htmlFor="genre.genreName"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Genre Name:
              </label>
              <InputField
                type="text"
                id="genre.genreName"
                name="genre.genreName"
              />
            </div>
            <div className="mb-2">
              <label
                htmlFor="data"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Image:
              </label>
              <Field
                type="file"
                id="data"
                name="data"
                onChange={handleOnChange}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>
            <SubmitButton text="Add" />
          </Form>
        </div>
      )}
    </Formik>
  );
};

export default PostForm;
