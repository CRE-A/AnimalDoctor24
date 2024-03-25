import logo from './logo.svg';
import { Outlet } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { AuthContextProvider } from './context/AuthContext';
import Nav from "./components/Nav";

const queryClient = new QueryClient();
function App() {
  return (
      <QueryClientProvider client={queryClient}>
        <AuthContextProvider>
            <div>
                <Nav/>
                <Outlet />
            </div >
        </AuthContextProvider>
      </QueryClientProvider>
  );
}

export default App;
