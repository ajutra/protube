import React from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { useEditVideo } from '@/hooks/useEditVideo'

interface EditVideoFormProps {
  video: any
  onSave: () => void
}

const EditVideoForm: React.FC<EditVideoFormProps> = ({ video, onSave }) => {
  const { title, description, setTitle, setDescription, handleSave } =
    useEditVideo(video, onSave)

  return (
    <div className="mx-auto max-w-6xl space-y-6 rounded-lg bg-background p-6 shadow-md">
      <h2 className="text-2xl font-semibold text-foreground">Edit Video</h2>
      <div className="space-y-4">
        <div>
          <Label
            htmlFor="title"
            className="block text-sm font-medium text-foreground"
          >
            Title
          </Label>
          <Input
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="mt-1 block w-full rounded-md border-background shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
          />
        </div>

        <div>
          <Label
            htmlFor="description"
            className="block text-sm font-medium text-foreground"
          >
            Description
          </Label>
          <Textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={4}
            className="mt-1 block w-full rounded-md border-background shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
          />
        </div>

        <div className="flex justify-end">
          <Button
            onClick={handleSave}
            className="hover:bg-primary-dark rounded-md bg-primary px-4 py-2 text-white shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
          >
            Save
          </Button>
        </div>
      </div>
    </div>
  )
}

export default EditVideoForm
