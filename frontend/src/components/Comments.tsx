import React from 'react'
import { Comment } from '../model/Comment'
import { Card, CardHeader, CardContent, CardTitle } from './ui/card'
import { Separator } from './ui/separator'

const Comments: React.FC<{ comments: Comment[] }> = ({ comments }) => {
  return (
    <Card className="mt-4 p-4 shadow-lg">
      <CardContent>
        <CardTitle className="border-b pb-2 text-left text-2xl font-bold">
          {comments.length} COMMENTS
        </CardTitle>
        <Separator />
        {comments.length > 0 ? (
          comments.map((comment, index) => (
            <div key={index} className="mb-4">
              <div className="flex items-start">
                <div className="flex-1">
                  <CardHeader className="text-left">
                    <CardTitle className="text-base font-semibold">
                      {comment.username}
                    </CardTitle>
                  </CardHeader>
                  <CardContent className="text-left">
                    <p className="text-sm">{comment.text}</p>
                  </CardContent>
                </div>
              </div>
              {index < comments.length - 1 && <Separator className="my-4" />}
            </div>
          ))
        ) : (
          <p className="text-sm">No comments available</p>
        )}
      </CardContent>
    </Card>
  )
}

export default Comments
