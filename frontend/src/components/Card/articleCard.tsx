import { ReactNode } from "react";

interface Props {
  children: ReactNode;
}

function ArticleCard({ children }: Props) {
  return (
    <div className="mx-auto px-6 lg:px-8 rounded-xl overflow-hidden shadow-[rgba(13,_38,_76,_0.19)_0px_9px_20px] mb-10 w-[700px] h-[200px] bg-white flex flex-col justify-between p-4">
      {children}
    </div>
  );
}

export default ArticleCard;
