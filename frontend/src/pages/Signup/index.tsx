import { Formik, ErrorMessage, FormikValues, FormikHelpers } from "formik";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";
import "../../components/Form/Form.css";
import InputField from "../../components/Form/InputField/index";
import SubmitButton from "../../components/Form/SubmitButton";

interface FormValues {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

const SignupForm: React.FC = () => {
  const initialValues: FormValues = {
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  };

  const passwordRule = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;

  const validationSchema = Yup.object().shape({
    name: Yup.string()
      .required("Name is required")
      .min(2, "Name must be minimum 2 characters long")
      .max(100, "Name must not be more than 100 characters"),
    email: Yup.string().email("Invalid email").required("Email is required"),
    password: Yup.string()
      .min(6, "Password must be at least 6 characters long")
      .required("Password is required")
      .matches(passwordRule, { message: "Please create a stronger password" }),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref("password")], "Passwords must match")
      .required("Confirm password is required"),
  });

  const navigate = useNavigate();

  const handleSubmit = (
    values: FormikValues,
    { setSubmitting }: FormikHelpers<FormValues>
  ) => {
    console.log(values);
    navigate("/login");
    setSubmitting(false);
  };

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
                  htmlFor="name"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Name
                </label>
                <InputField
                  name="name"
                  value={values.name}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={errors.name && touched.name ? "error" : ""}
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
              <div className="mb-5">
                <label
                  htmlFor="repeat-password"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Repeat password
                </label>

                <InputField
                  name="confirmPassword"
                  value={values.confirmPassword}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  className={
                    errors.confirmPassword && touched.confirmPassword
                      ? "error"
                      : ""
                  }
                  type="password"
                  id="repeat-password"
                  placeholder="Confirm your password"
                />
                <ErrorMessage
                  name="confirmPassword"
                  component="div"
                  className="error"
                />
              </div>

              <SubmitButton text="Sign Up" disabled={isSubmitting} />
            </form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default SignupForm;
