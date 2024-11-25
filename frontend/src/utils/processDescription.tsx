import React from 'react'

const processDescription = (description: string) => {
  const urlRegex = /(https?:\/\/[^\s]+)/g
  let lineCount = 0

  const processedDescription = description
    .split('\n\n')
    .map((paragraph, paragraphIndex) => {
      const lines = paragraph.split('\n').map((line, lineIndex) => {
        lineCount += 1
        const parts = line.split(urlRegex).map((part, partIndex) => {
          if (urlRegex.test(part)) {
            return (
              <a
                key={partIndex}
                href={part}
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-500 underline"
              >
                {part}
              </a>
            )
          }
          return part
        })
        return (
          <React.Fragment key={lineIndex}>
            {parts}
            {lineIndex < paragraph.split('\n').length - 1 && <br />}
          </React.Fragment>
        )
      })
      return (
        <p key={paragraphIndex} className="mb-4">
          {lines}
        </p>
      )
    })

  return { processedDescription, lineCount }
}

export default processDescription
