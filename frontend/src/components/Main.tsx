import { useEffect, useState } from "react";
import SearchBar from "./main-components/SearchBar";
import Sort from "./main-components/Sort";
import { CardT } from "../types";
import Card from "./main-components/Card";

const Main = () => {

  const [cardDetails, setCardDetails] = useState<CardT[]>([]);


  useEffect(() => {
    fetch('/courses.json')
      .then(res => res.json())
      .then(res => {
        console.log(res)

        setCardDetails(res)
      })
      .catch(err => console.error(err))


  }, [])
  return (
    <main className="w-[90%]">
      <div className="flex flex-col w-full gap-1">
        <SearchBar />
        <Sort />
      </div>
      <div className="grid min-[1024px]:grid-cols-2 min-[1150px]:grid-cols-3 grid-cols-1 gap-5">
        {cardDetails.map((course, index) => <Card {...course} key={index} />)}

      </div>
    </main>
  );
}

export default Main;
