import React from 'react'
import { Tabs, TabsList, TabsTrigger, TabsContent } from '@/components/ui/tabs'
import VideoUploadButton from '@/components/VideoUploadButton'

const ProfileTabs: React.FC = () => {
  return (
    <Tabs defaultValue="videos">
      <TabsList>
        <TabsTrigger value="videos">My Videos</TabsTrigger>
        <TabsTrigger value="upload">Upload Video</TabsTrigger>
      </TabsList>
      <TabsContent value="videos">
        {}
        <p>Videos here.</p>
      </TabsContent>
      <TabsContent value="upload">
        <div className="mt-8 flex justify-center">
          <VideoUploadButton />
        </div>
      </TabsContent>
    </Tabs>
  )
}

export default ProfileTabs
