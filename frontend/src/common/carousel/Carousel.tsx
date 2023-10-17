import React from "react";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";

interface Icarousel {
  items: React.ReactNode[];
}

const Carousel: React.FC<Icarousel> = ({ items }) => {
  return (
    <AliceCarousel
      autoWidth
      mouseTracking
      disableDotsControls
      disableButtonsControls
      items={items}
    />
  );
};

export default Carousel;
