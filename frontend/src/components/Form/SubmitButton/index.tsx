type Props = {
  text: string;
  disabled: boolean;
};

function SubmitButton({ text, disabled }: Props) {
  return (
    <div>
      <button
        type="submit"
        disabled={disabled}
        className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800
        border border-gray-300 w-full p-2.5 
        "
      >
        {text}
      </button>
    </div>
  );
}

export default SubmitButton;
