import React from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { useEditVideo } from '@/hooks/useEditVideo'
import { Edit3, FileText } from 'lucide-react'

interface EditVideoFormProps {
  video: any
  onSave: () => void
  onCancel: () => void
}

const EditVideoForm: React.FC<EditVideoFormProps> = ({ video, onSave }) => {
  const {
    title,
    description,
    setTitle,
    setDescription,
    handleSave,
    handleCancel,
  } = useEditVideo(video, onSave)

  return (
    <div className="w-full space-y-4 p-6">
      <h2 className="text-3xl font-bold text-primary">Edit Video</h2>
      <div className="space-y-4">
        <div className="w-full">
          <Label
            htmlFor="title"
            className="block text-sm font-medium text-foreground"
          >
            Title
          </Label>
          <div className="relative mt-1">
            <span className="absolute inset-y-0 left-0 flex items-center pl-3">
              <Edit3 className="text-foreground-muted h-5 w-5" />
            </span>
            <Input
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="block w-full rounded-lg border-border pl-10 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>
        </div>

        <div className="w-full">
          <Label
            htmlFor="description"
            className="block text-sm font-medium text-foreground"
          >
            Description
          </Label>
          <div className="relative mt-1">
            <span className="absolute inset-y-0 left-0 flex items-center pl-3">
              <FileText className="text-foreground-muted h-5 w-5" />
            </span>
            <Textarea
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              rows={6}
              className="block w-full rounded-lg border-border pl-10 shadow-sm focus:border-primary focus:ring-primary sm:text-sm"
            />
          </div>
        </div>

        <div className="flex justify-end space-x-2">
          <Button
            onClick={handleCancel}
            className="hover:bg-primary-dark min-w-[100px] rounded-md bg-secondary px-4 py-2 text-background shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
          >
            Cancel
          </Button>
          <Button
            onClick={handleSave}
            className="hover:bg-primary-dark min-w-[100px] rounded-md bg-primary px-4 py-2 text-background shadow-sm focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
          >
            Save
          </Button>
        </div>
      </div>
    </div>
  )
}

export default EditVideoForm
