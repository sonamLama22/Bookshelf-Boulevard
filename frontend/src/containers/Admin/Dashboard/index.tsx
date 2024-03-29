import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { v4 as uuidv4 } from "uuid";
import {
  useDeleteBookMutation,
  useGetAllBooksQuery,
} from "../../../services/Auth/apiSlice";
import { FcEditImage } from "react-icons/fc";
import { AiTwotoneDelete } from "react-icons/ai";

const AdminDashboard = () => {
  const { data, error, isLoading, isSuccess, isFetching } =
    useGetAllBooksQuery();
  const [deleteBook] = useDeleteBookMutation();

  if (isLoading) {
    toast.info("loading...");
  }

  if (error) {
    toast.error("something went wronng");
  }

  if (!data) {
    return <div>No data available</div>;
  }

  //   console.log(data);

  const handleDelete = async (id: any) => {
    if (window.confirm("Are you sure that you wanted to delete that user ?")) {
      await deleteBook(id);
      toast.success("Contact Deleted Successfully");
    }
  };

  return (
    <div className="p-5 h-screen bg-gray-100">
      <div className="flex space-x-5 py-5 justify-center">
        <div className="font-bold text-gray-700">
          <Link to={"/addBook"}>
            <button className="bg-green-100 hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow">
              Add a new order
            </button>
          </Link>
        </div>
      </div>
      <div className="overflow-auto rounded-lg shadow ">
        <table className="w-full">
          <thead className="bg-gray-50 border-b-2 border-gray-200">
            <tr>
              <th className="p-3 text-sm font-semibold tracking-wide text-left">
                Id
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Title
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Author
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Genre
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Description
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Price
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Copies Available
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Edit
              </th>
              <th className="p-3 text-sm font-semibold tracking-wide text-left ">
                Delete
              </th>
            </tr>
          </thead>

          <tbody className="divide-y divide-gray-100">
            {data &&
              data.map((item) => {
                return (
                  <tr key={uuidv4()} className="bg-gray-50">
                    <td className="p-3 font-medium text-blue-700 whitespace-nowrap">
                      {item.bookId}.
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap ">
                      {item.title}
                    </td>

                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      {item.author}
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      {item?.genre?.genreName}
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      {item.description}
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      ${item.price}
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      {item.copiesAvailable}
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      <FcEditImage onClick={() => handleEdit(item.bookId)} />
                    </td>
                    <td className="p-3 text-sm text-gray-700 whitespace-nowrap">
                      <AiTwotoneDelete
                        onClick={() => handleDelete(item.bookId)}
                      />
                    </td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminDashboard;
