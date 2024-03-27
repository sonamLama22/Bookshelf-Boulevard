import Carousel from "../../components/Carousel";
import Navbar from "../../components/Navbar";
import book1 from "../../assets/book1.png";
import book2 from "../../assets/book2.png";
import book3 from "../../assets/book3.png";
import book4 from "../../assets/book4.png";
import book5 from "../../assets/book5.png";
import book6 from "../../assets/book6.png";

const slides = [book1, book2, book3, book4, book5, book6];

const Home = () => {
  return (
    <div>
      <Navbar />

      <Carousel>
        {slides.map((slide, index) => (
          <img src={slide} key={index} />
        ))}
      </Carousel>
    </div>
  );
};

export default Home;
