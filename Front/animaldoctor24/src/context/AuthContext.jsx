import { createContext, useContext, useEffect, useState } from 'react';

const AuthContext = createContext();

export function AuthContextProvider({ children }) {
    const [user, setUser] = useState();

    useEffect(() => {
    }, []);

    return (
        <AuthContext.Provider
            value={{ user, uid: user && user.uid}}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuthContext() {
    return useContext(AuthContext);
}
