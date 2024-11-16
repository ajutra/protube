// src/components/__tests__/VideoCard.test.tsx
import { render, fireEvent, waitFor } from '@testing-library/react'
import '@testing-library/jest-dom'
import VideoCard from '../VideoCard'
import { VideoPreviewData } from '../../model/VideoPreviewData'

jest.mock('../../utils/Env', () => ({
    getEnv: () => ({
        MEDIA_BASE_URL: 'http://mockedurl.com',
    }),
}))

const mockVideoData: VideoPreviewData = {
    videoFileName: 'test-video.mp4',
    thumbnailFileName: 'test-thumbnail.jpg',
    title: 'Test Video',
    username: 'testuser',
}

test('renders VideoCard and handles hover', async () => {
    const { getByAltText, getByRole } = render(<VideoCard {...mockVideoData} />)

    const thumbnail = getByAltText('test-thumbnail.jpg')
    expect(thumbnail).toHaveAttribute(
        'src',
        'http://mockedurl.com/test-thumbnail.jpg'
    )

    fireEvent.mouseEnter(thumbnail)

    await waitFor(() => {
        const video = getByRole('video')
        expect(video).toBeInTheDocument()
        expect(video).toHaveAttribute(
            'src',
            'http://mockedurl.com/test-video.mp4'
        )
        expect(video).toHaveAttribute('autoPlay')
        expect(video).toHaveAttribute('loop')
    })

    fireEvent.mouseLeave(thumbnail)

    expect(thumbnail).toHaveAttribute(
        'src',
        'http://mockedurl.com/test-thumbnail.jpg'
    )
})
