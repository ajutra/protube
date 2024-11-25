import React from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { useEditVideo } from '@/hooks/useEditVideo'

interface EditVideoFormProps {
  video: any
  onSave: () => void
}

const EditVideoForm: React.FC<EditVideoFormProps> = ({ video, onSave }) => {
  const {
    title,
    description,
    tags,
    categories,
    setTitle,
    setDescription,
    setTags,
    setCategories,
    handleSave,
  } = useEditVideo(video, onSave)

  return (
    <div className="space-y-4">
      <Label htmlFor="title">Title</Label>
      <Input
        id="title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      <Label htmlFor="description">Description</Label>
      <Input
        id="description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <Label htmlFor="tags">Tags</Label>
      <Input id="tags" value={tags} onChange={(e) => setTags(e.target.value)} />

      <Label htmlFor="categories">Categories</Label>
      <Input
        id="categories"
        value={categories}
        onChange={(e) => setCategories(e.target.value)}
      />

      <Button onClick={handleSave}>Save</Button>
    </div>
  )
}

export default EditVideoForm
