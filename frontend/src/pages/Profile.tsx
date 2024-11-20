import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { Card, CardContent, CardHeader } from '@/components/ui/card'
import { useAuth } from '@/context/AuthContext'
import { Separator } from '@/components/ui/separator'
import useFetchAllUserComments from '@/hooks/useFetchAllUserComments'
import Comment from '@/components/Comment'

function Profile() {
  const { username } = useAuth()
  const description = 'Welcome to your profile page'
  const comments = useFetchAllUserComments(username || '')

  return (
    <div>
      <div className="mt-6 flex flex-col items-center space-y-4 sm:flex-row sm:justify-center sm:space-x-4 sm:space-y-0">
        <Avatar className="h-20 w-20">
          <AvatarFallback className="bg-primary text-4xl font-bold">
            {username?.charAt(0).toUpperCase()}
          </AvatarFallback>
        </Avatar>

        <div className="space-y-2 text-center sm:text-left">
          <h1 className="text-2xl font-bold">{username}</h1>
          <p>{description}</p>
        </div>
      </div>
      <div className="mx-auto mt-6 w-full max-w-4xl">
        <Card>
          <CardHeader>
            <h2 className="text-xl font-bold">Comments</h2>
          </CardHeader>
          <Separator className="mb-3" />
          <CardContent>
            <div className="space-y-4">
              {comments.map((comment, index) => (
                <>
                  <Separator className="my-4" key={index} />
                  <Comment key={index} comment={comment} />
                </>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}

export default Profile
