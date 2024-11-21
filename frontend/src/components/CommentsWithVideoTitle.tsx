import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Separator } from '@/components/ui/separator'
import useFetchAllUserComments from '@/hooks/useFetchAllUserComments'
import useGetVideoDetailsForComments from '@/hooks/useGetVideoDetailsForComments'
import Comment from './Comment'
import { AppRoutes } from '@/enums/AppRoutes'

interface CommentsWithVideoTitleProps {
  username: string
}

const CommentsWithVideoTitle: React.FC<CommentsWithVideoTitleProps> = ({
  username,
}) => {
  const comments = useFetchAllUserComments(username || '')
  const { videoDetails, groupedComments: initialGroupedComments } =
    useGetVideoDetailsForComments(comments)
  const [groupedComments, setGroupedComments] = useState(initialGroupedComments)

  useEffect(() => {
    setGroupedComments(initialGroupedComments)
  }, [initialGroupedComments])

  const handleDeletedComment = (videoId: string, commentId: string) => {
    setGroupedComments((prevGroupedComments) => {
      const updatedComments = prevGroupedComments[videoId].filter(
        (comment) => comment.commentId !== commentId
      )
      if (updatedComments.length === 0) {
        const { [videoId]: _, ...rest } = prevGroupedComments
        return rest
      }
      return {
        ...prevGroupedComments,
        [videoId]: updatedComments,
      }
    })
  }

  const hasComments = Object.keys(groupedComments).length > 0

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
            <div className="flex justify-center">
              <p>{username} has no comments to show</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}

export default CommentsWithVideoTitle
