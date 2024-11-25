import React, { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import  Spinner from '@/components/Spinner';

interface CommentInputProps {
  onConfirm: (commentText: string) => void;
  confirmButtonLabel: string;
  onCancel: () => void;
  loading: boolean;
}

const CommentInput: React.FC<CommentInputProps> = ({
  onConfirm,
  confirmButtonLabel,
  onCancel,
  loading,
}) => {
  const [inputValue, setInputValue] = useState('');
  const [showButtons, setShowButtons] = useState(false);

  const handleConfirm = () => {
    onConfirm(inputValue);
    setInputValue('');
    setShowButtons(false);
  };

  return (
    <div className="w-full space-y-5">
      <Input
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
        className="flex-grow rounded-none border-x-0 border-t-0 focus:border-b-2 focus:border-b-primary focus:outline-none focus-visible:ring-0"
        type="text"
        placeholder="Leave your comment..."
        onFocus={() => setShowButtons(true)}
      />
      {showButtons && (
        <div className="flex justify-end space-x-2">
          <Button
            className="rounded-full"
            variant="ghost"
            onClick={() => {
              setInputValue('');
              setShowButtons(false);
              onCancel();
            }}
          >
            Cancel
          </Button>
          <Button
            className="rounded-full"
            disabled={inputValue.trim() === '' || loading}
            onClick={handleConfirm}
          >
            {loading ? <Spinner /> : confirmButtonLabel}
          </Button>
        </div>
      )}
    </div>
  );
};

export default CommentInput;
