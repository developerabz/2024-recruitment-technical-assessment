import { useState } from "react"

const Header = () => {
  const blueColor = "md:text-6xl text-4xl font-extrabold text-[#1479f2]"

  const [titleColor, setTitleColor] = useState(blueColor)

  const redColor = "md:text-6xl text-4xl font-extrabold text-red-500"

  const handleColorChange = () => {
    setTitleColor(prevColor => prevColor === blueColor ? redColor : blueColor)
  }
  return (
    <header className="w-[90%]">

      <p>DevSoc presents</p>
      <h1 className={titleColor}
        onClick={handleColorChange}>unilectives</h1>
      <p className="font-extrabold">Your one-stop shop for UNSW course and elective reviews.</p>
    </header >
  )
}

export default Header
