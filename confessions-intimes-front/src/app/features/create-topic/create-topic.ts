import { Component, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BoardService } from '../../services/board.service';
import { CreateTopicDto } from '../../models/topic.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-topic',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-topic.html',
  styleUrls: ['./create-topic.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateTopicComponent {
  newTopic: CreateTopicDto = { subject: '', posterName: '', content: '' };

  constructor(private boardService: BoardService, public router: Router) {}

  onSubmit(): void {
    this.boardService.createTopic(this.newTopic).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Error creating topic:', error);
      }
    });
  }
}