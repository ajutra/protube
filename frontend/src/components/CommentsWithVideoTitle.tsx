import React from 'react'
import { Separator } from '@/components/ui/separator'
import Comment from '@/components/Comment'
import useFetchAllUserComments from '@/hooks/useFetchAllUserComments'
import useGetVideoDetailsForComments from '@/hooks/useGetVideoDetailsForComments'
import { Link } from 'react-router-dom'
import { AppRoutes } from '@/enums/AppRoutes'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'

interface CommentsWithVideoTitleProps {
  username: string
}

const CommentsWithVideoTitle: React.FC<CommentsWithVideoTitleProps> = ({
  username,
}) => {
  const comments = useFetchAllUserComments(username || '')
  const { videoDetails, groupedComments } =
    useGetVideoDetailsForComments(comments)

  return (
    <div className="mx-auto mt-6 w-full max-w-4xl">
      <Card>
        <CardHeader>
          <h2 className="text-xl font-bold">Comments</h2>
        </CardHeader>
        <Separator className="mb-3" />
        <CardContent>
          <div className="space-y-2">
            {Object.keys(groupedComments).map((videoId) => (
              <Card className='border-none shadow-none' key={videoId}>
                <CardHeader>
                  <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + videoId}>
                    <CardTitle className='font-semibold text-xl text-primary hover:text-primary/80'>{videoDetails[videoId]?.title}</CardTitle>
                  </Link>
                  <CardDescription>
                    {videoDetails[videoId]?.username}
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  {groupedComments[videoId].map((comment, index) => (
                    <Comment key={index} comment={comment} />
                  ))}
                </CardContent>
              </Card>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  )
}

export default CommentsWithVideoTitle
