import { Component, OnInit, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 
import { BoardService } from '../../services/board.service';
import { Topic } from '../../models/topic.model';
import { Page } from '../../models/page.model';

@Component({
  selector: 'app-topic-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './topic-list.html',
  styleUrls: ['./topic-list.css'],
  changeDetection: ChangeDetectionStrategy.OnPush 
})
export class TopicListComponent implements OnInit {
  topicsPage = signal<Page<Topic>>({
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
  currentPage = signal(0);
  pageSize = 10;
  sort = 'lastPostAt,desc';

  constructor(private boardService: BoardService) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.boardService.getTopics(this.currentPage(), this.pageSize, this.sort).subscribe({
      next: (data) => this.topicsPage.set(data),
      error: (error) => {
        console.error('Error loading topics:', error);
      },
      complete: () => {
        console.log(this.topicsPage());
      }
    });
  }

  goToPage(pageNumber: number): void {
    if (pageNumber >= 0 && pageNumber < this.topicsPage().totalPages) {
      this.currentPage.set(pageNumber);
      this.loadTopics();
    }
  }

  trackTopicById(index: number, topic: Topic): number {
    return topic.id; // Return a unique identifier for each topic
  }
}