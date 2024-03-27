import { Formik, ErrorMessage, FormikValues, FormikHelpers } from "formik";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import "../../components/Form/Form.css";
import InputField from "../../components/Form/InputField/index";
import SubmitButton from "../../components/Form/SubmitButton";

interface FormValues {
  email: string;
  password: string;
}

const Login: React.FC = () => {
  const initialValues: FormValues = {
    email: "",
    password: "",
  };

  const validationSchema = Yup.object().shape({
    email: Yup.string().email("Invalid email").required("Email is required"),
    password: Yup.string()
      .min(6, "Password must be at least 6 characters long")
      .required("Password is required"),
  });

  const navigate = useNavigate();

  const handleSubmit = (
    values: FormikValues,
    { setSubmitting }: FormikHelpers<FormValues>
  ) => {
    console.log(values);
    navigate("/profile");
    setSubmitting(false);
  };

  return (
    <div className="min-h-screen flex items-center justify-center ">
      <div className="bg-slate-50 w-11/12 max-w-xl p-10 rounded-2xl shadow-lg ">
        <div className="flex justify-center flex-col items-center pb-10">
          <h1 className="font-bold text-2xl">Welcome Back</h1>
          <p className="font-light">Please enter your details.</p>
        </div>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({
            isSubmitting,
            errors,
            touched,
            values,
            handleChange,
            handleBlur,
            handleSubmit,
          }) => (
            <form className="max-w-sm mx-auto" onSubmit={handleSubmit}>
              <div className="mb-5">
                <label
                  htmlFor="email"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Email
                </label>

                <InputField
                  name="email"
                  value={values.email}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={errors.password && touched.password ? "error" : ""}
                  type="email"
                  id="email"
                  placeholder="Enter your email"
                />
                <ErrorMessage name="email" component="div" className="error" />
              </div>
              <div className="mb-5">
                <label
                  htmlFor="password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Password
                </label>

                <InputField
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={errors.password && touched.password ? "error" : ""}
                  type="password"
                  id="password"
                  placeholder="Enter your password"
                />
                <ErrorMessage
                  name="password"
                  component="div"
                  className="error"
                />
              </div>

              <SubmitButton text="Login" disabled={isSubmitting} />
            </form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default Login;
