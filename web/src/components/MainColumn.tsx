import type {PropsWithChildren} from "react"
import Header from "./Header.tsx"
import SearchBar from "./SearchBar.tsx"

const MainColumn = ({children}: PropsWithChildren) => {
  return <>
    <div className={"main-column"}>
      <Header />
      <SearchBar />
      {children}
    </div>
  </>
}

export default MainColumn
