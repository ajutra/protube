import React from 'react';
import Header from './Header';
import useLogOut from '../hooks/useLogOut';

interface ProfileProps {
  username: string;
}

const Profile: React.FC<ProfileProps> = ({ username }) => {
    const LogOut = useLogOut();
    return (
        <div>
        <Header />
        <div className="container mt-5 pt-5">
            <h1>User Profile</h1>
            <p className='fs-2'>{username}</p>
            <button onClick={LogOut} className="btn btn-primary">
            Log Out
            </button>
        </div>
        </div>
    );
};

export default Profile;