import React from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { useEditVideo } from '@/hooks/useEditVideo'
import { LucideText } from 'lucide-react'

interface EditVideoFormProps {
  video: any
  onSave: () => void
  onCancel: () => void
}

const EditVideoForm: React.FC<EditVideoFormProps> = ({
  video,
  onSave,
  onCancel,
}) => {
  const { title, description, setTitle, setDescription, handleSave } =
    useEditVideo(video, onSave)

  return (
    <div className="w-full space-y-4 p-6">
      <h2 className="flex w-full justify-center text-3xl font-bold text-primary">
        Edit Video
      </h2>
      <div className="space-y-4">
        <div className="w-full">
          <Label htmlFor="title">
            <LucideText className="mr-2 inline-block pl-2 text-primary" />
            Title
          </Label>
          <Input
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="pl-2"
          />
        </div>

        <div className="w-full">
          <Label htmlFor="description">
            <LucideText className="mr-2 inline-block pl-2 text-primary" />
            Description
          </Label>
          <Textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={10}
            className="pl-2"
          />
        </div>

        <div className="flex justify-end space-x-2">
          <Button variant="outline" onClick={onCancel} className="mt-2 sm:mt-0">
            Cancel
          </Button>
          <Button onClick={handleSave}>Save</Button>
        </div>
      </div>
    </div>
  )
}

export default EditVideoForm
