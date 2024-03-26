type Props = {
  text: string;
};

const Button = ({ text }: Props) => {
  return (
    <div>
      <button className="bg-white text-black rounded-full font-medium px-3 py-1 hover:bg-black hover:text-white border border-black">
        {text}
      </button>
    </div>
  );
};

export default Button;
