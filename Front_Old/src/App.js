import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Test from "./test";

const router = createBrowserRouter([
  {
    path: "/",
    element: <p>Home</p>,
  },
  {
    path: "/test",
    element: <Test />,
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
