import React from 'react'
import { Link } from 'react-router-dom'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Separator } from '@/components/ui/separator'
import Comment from './Comment'
import { AppRoutes } from '@/enums/AppRoutes'
import useCommentsWithVideoTitle from '@/hooks/useCommentsWithVideoTitle'

interface CommentsWithVideoTitleProps {
  username: string
}

const CommentsWithVideoTitle: React.FC<CommentsWithVideoTitleProps> = ({
  username,
}) => {
  const { videoDetails, groupedComments, handleDeletedComment, hasComments } =
    useCommentsWithVideoTitle(username)

  return (
    <div className="mx-auto mt-6 w-full max-w-4xl">
      <Card>
        <CardHeader>
          <h2 className="text-xl font-bold">Comments</h2>
        </CardHeader>
        <Separator className="mb-3" />
        <CardContent>
          {hasComments ? (
            <div className="space-y-2">
              {Object.keys(groupedComments).map((videoId) => (
                <Card className="border-none shadow-none" key={videoId}>
                  <CardHeader>
                    <Link to={AppRoutes.VIDEO_DETAILS + '?id=' + videoId}>
                      <CardTitle className="text-xl font-semibold text-primary hover:text-primary/80">
                        {videoDetails[videoId]?.title}
                      </CardTitle>
                    </Link>
                    <CardDescription>
                      {videoDetails[videoId]?.username}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    {groupedComments[videoId].map((comment, index) => (
                      <Comment
                        key={index}
                        comment={comment}
                        onDelete={() =>
                          handleDeletedComment(videoId, comment.commentId)
                        }
                      />
                    ))}
                  </CardContent>
                </Card>
              ))}
            </div>
          ) : (
            <div className="mt-5 flex justify-center">
              <p className="text-sm">{username} has no comments to show</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}

export default CommentsWithVideoTitle
