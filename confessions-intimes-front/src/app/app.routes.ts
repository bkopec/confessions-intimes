import { Routes } from '@angular/router';
import { TopicListComponent } from './features/topic-list/topic-list';
import { CreateTopicComponent } from './features/create-topic/create-topic';
import { TopicDetailComponent } from './features/topic-detail/topic-detail';

export const routes: Routes = [
  { path: '', component: TopicListComponent },
  { path: 'create-topic', component: CreateTopicComponent },
  { path: 'topic/:id', component: TopicDetailComponent },
  { path: '**', redirectTo: '' } 
];