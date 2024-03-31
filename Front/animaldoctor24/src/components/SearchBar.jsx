import React, { useEffect, useState } from 'react';
import { BsSearch } from 'react-icons/bs';
import { useNavigate, useParams } from 'react-router-dom';

export default function SearchBar() {
    const { keyword } = useParams();
    const navigate = useNavigate();
    const [text, setText] = useState('');
    const handleSubmit = (e) => {
        e.preventDefault();
        navigate(`/hospital/${text}`);
    };

    useEffect(() => setText(keyword || ''), [keyword]);

    return (
        <div>
            <form onSubmit={handleSubmit}  className="border-[2px] border-yellow-500 rounded px-3 py-2">
                <input
                    className="w-[280px] h-[40px] focus:outline-none "
                    type='text'
                    placeholder='찾고있는 동물병원이 있나요?'
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                />
                <button>
                    <BsSearch />
                </button>
            </form>
        </div>
    );
}
