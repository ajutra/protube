import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { useAuth } from '@/context/AuthContext'
import CommentsWithVideoTitle from '@/components/CommentsWithVideoTitle'
import UserVideos from '@/components/UserVideos'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/ui/tabs'

function Profile() {
  const { username } = useAuth()
  const description = 'Welcome to your profile page'

  return (
    <div className="mt-14">
      <div className="m-6 flex flex-col items-center space-y-4 sm:flex-row sm:justify-center sm:space-x-4 sm:space-y-0">
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
        <Tabs defaultValue="comments">
          <TabsList>
            <TabsTrigger value="comments">Comments</TabsTrigger>
            <TabsTrigger value="videos">Videos</TabsTrigger>
          </TabsList>
          <TabsContent value="comments">
            <CommentsWithVideoTitle username={username || ''} />
          </TabsContent>
          <TabsContent value="videos">
            <UserVideos username={username || ''} />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  )
}

export default Profile
