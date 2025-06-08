export interface Topic {
    id: number;
    subject: string;
    posterName: string;
    createdAt: string; 
    lastPostAt: string; 
    repliesCount: number;
  }
  
  export interface CreateTopicDto {
    subject: string;
    posterName: string;
    content: string;
  }