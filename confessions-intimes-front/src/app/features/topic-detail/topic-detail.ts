import { Component, OnInit, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BoardService } from '../../services/board.service';
import { Message, CreateMessageDto } from '../../models/message.model';
import { Topic } from '../../models/topic.model';
import { FormsModule } from '@angular/forms';
import { Page } from '../../models/page.model';

@Component({
  selector: 'app-topic-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './topic-detail.html',
  styleUrls: ['./topic-detail.css'],
  changeDetection: ChangeDetectionStrategy.OnPush 
})
export class TopicDetailComponent implements OnInit {
  topic = signal<Topic | undefined>(undefined);
  messagesPage = signal<Page<Message>>({
    content: [],
    pageable: {
      pageNumber: 0,
      pageSize: 0,
      sort: { empty: true, sorted: false, unsorted: true },
      offset: 0,
      paged: false,
      unpaged: true
    },
    last: true,
    totalPages: 0,
    totalElements: 0,
    size: 0,
    number: 0,
    sort: { empty: true, sorted: false, unsorted: true },
    first: true,
    numberOfElements: 0,
    empty: true
  });
  newMessage: CreateMessageDto = { content: '', authorName: '' };
  topicId!: number;

  currentMessagePage = signal(0);
  messagePageSize = 10;
  messageSort = 'createdAt,asc';

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.topicId = +id;
        this.loadTopicDetails();
        this.loadMessages();
      }
    });
  }

  loadTopicDetails(): void {
    this.boardService.getTopicById(this.topicId).subscribe({
      next: (data) => this.topic.set(data),
      error: (error) => {
        console.error('Error loading topic:', error);
        this.router.navigate(['/']);
      }
    });
  }

  loadMessages(): void {
    this.boardService.getMessagesInTopic(this.topicId, this.currentMessagePage(), this.messagePageSize, this.messageSort).subscribe({
      next: (data) => this.messagesPage.set(data),
      error: (error) => {
        console.error('Error loading messages:', error);
      }
    });
  }

  goToMessagePage(pageNumber: number): void {
    if (pageNumber >= 0 && pageNumber < this.messagesPage().totalPages) {
      this.currentMessagePage.set(pageNumber);
      this.loadMessages();
    }
  }

  addReply(): void {
    if (this.topicId && this.newMessage.content && this.newMessage.authorName) {
      this.boardService.addReply(this.topicId, this.newMessage).subscribe({
        next: () => {
          this.newMessage = { content: '', authorName: '' }; 
          this.loadTopicDetails(); 
          this.loadMessages(); 
        },
        error: (error) => {
          console.error('Error adding reply:', error);
        }
      });
    }
  }

  trackMessageById(index: number, message: Message): number {
    return message.id; // Return a unique identifier for each message
  }
}