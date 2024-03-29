export interface Genre {
  genreId: number;
  genreName: string;
}

export interface Book {
  bookId?: number;
  title: string;
  author: string;
  price: number;
  genre?: Genre | null;
  description: string;
  fileName?: string;
  fileType?: string;
  copiesAvailable: number;
  data?: File;
}
