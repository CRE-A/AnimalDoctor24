import React, {useState} from 'react';
import Nav from "../components/Nav";

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Here you can perform your authentication logic
        console.log('Logging in with:', { username, password });
        // For demonstration purposes, let's clear the form fields after submission
        setUsername('');
        setPassword('');
    };
    return (
        <>
            <section className="relative flex flex-col items-center bg-white w-[393px] h-[782px] ">
                <button className="w-[126px] h-[49px] rounded-[27px] bg-yellow-300 font-bold text-white text-lg mt-[50px]">LOGIN</button>
                <h1 className="font-bold relative top-[15px] text-lg">Animal Doctor 24 로그인하기!</h1>
                <div className="relative bottom-[30px]">
                    <img src="https://loosedrawing.com/assets/illustrations/png/593.png" className="w-[316px] h-[316px]"/>
                </div>
                <form onSubmit={handleSubmit} className="relative bottom-[70px]">
                    <div>
                        <input
                            className="w-[342px] h-[54px] border-2 mb-[20px]"
                            type="text"
                            id="username"
                            value={username}
                            onChange={handleUsernameChange}
                            required
                        />
                    </div>
                    <div>
                        <input
                            className="w-[342px] h-[54px] border-2 mb-[20px]"
                            type="password"
                            id="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    <button type="submit" className="w-[342px] h-[76px] bg-yellow-300 font-bold text-white text-lg">로그인</button>
                </form>
            </section>
        </>

    ); ;
}
