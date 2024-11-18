import { useAuth } from '@/context/AuthContext'
import { AppRoutes } from '@/enums/AppRoutes'
import { Link } from 'react-router-dom'

function Profile() {
  const { username, logout } = useAuth()
  return (
    <div>
      <div className="container mt-5 pt-5">
        <h1>User Profile</h1>
        <p className="fs-2">{username}</p>
        <Link to={AppRoutes.HOME}>
          <button onClick={logout} className="btn btn-primary">
            Log Out
          </button>
        </Link>
      </div>
    </div>
  )
}

export default Profile
