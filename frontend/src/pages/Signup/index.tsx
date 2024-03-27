import { Formik, ErrorMessage, FormikValues, FormikHelpers } from "formik";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import "../../components/Form/Form.css";
import InputField from "../../components/Form/InputField/index";
import SubmitButton from "../../components/Form/SubmitButton";
import { useAppDispatch, useAppSelector } from "../../redux/hooks/hooks";
import { useEffect, useState } from "react";
import { clearMessage } from "../../redux/slices/message";
import { register } from "../../redux/slices/auth";

interface FormValues {
  username: string;
  email: string;
  password: string;
}

const SignupForm: React.FC = () => {
  const [successful, setSuccessful] = useState(false);

  const { message } = useAppSelector((state) => state.message);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(clearMessage());
  }, [dispatch]);

  const initialValues: FormValues = {
    username: "",
    email: "",
    password: "",
  };

  const passwordRule = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;

  const validationSchema = Yup.object().shape({
    username: Yup.string()
      .required("Name is required")
      .min(2, "Name must be minimum 2 characters long")
      .max(100, "Name must not be more than 100 characters"),
    email: Yup.string().email("Invalid email").required("Email is required"),
    password: Yup.string()
      .min(6, "Password must be at least 6 characters long")
      .required("Password is required")
      .matches(passwordRule, { message: "Please create a stronger password" }),
  });

  const navigate = useNavigate();

  const handleSubmit = (formValue: FormValues) => {
    const { username, email, password } = formValue;

    setSuccessful(false);

    dispatch(register({ username, email, password }))
      .unwrap()
      .then(() => {
        setSuccessful(true);
      })
      .catch(() => {
        setSuccessful(false);
      });
  };

  if (successful) {
    navigate("/login");
  }

  return (
    <div className="min-h-screen flex items-center justify-center ">
      <div className="bg-slate-50 w-11/12 max-w-xl p-8 rounded-2xl shadow-lg ">
        <div className="flex justify-center flex-col items-center pb-10">
          <h1 className="font-bold text-2xl">Create an account</h1>
          <p className="font-light">Please enter your details to register.</p>
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
                  htmlFor="username"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Username
                </label>
                <InputField
                  name="username"
                  value={values.username}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={errors.username && touched.username ? "error" : ""}
                  type="text"
                  id="name"
                  placeholder="Enter your name"
                />
                <ErrorMessage name="name" component="div" className="error" />
              </div>
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

              <SubmitButton text="Sign Up" disabled={isSubmitting} />
            </form>
          )}
        </Formik>
      </div>
      {message && (
        <div className="form-group">
          <div
            className={
              successful ? "alert alert-success" : "alert alert-danger"
            }
            role="alert"
          >
            {message}
          </div>
        </div>
      )}
    </div>
  );
};

export default SignupForm;
