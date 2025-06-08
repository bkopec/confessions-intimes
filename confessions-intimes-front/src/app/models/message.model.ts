export interface Message {
    id: number;
    topicId: number;
    content: string;
    authorName: string;
    createdAt: string; 
  }
  
  export interface CreateMessageDto {
    content: string;
    authorName: string;
  }