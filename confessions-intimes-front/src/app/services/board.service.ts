import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateTopicDto, Topic } from '../models/topic.model';
import { CreateMessageDto, Message } from '../models/message.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
private apiUrl = 'https://conf-intimes.s2.bkopec.com/api'; 

  constructor(private http: HttpClient) {}


  createTopic(topic: CreateTopicDto): Observable<Topic> {
    return this.http.post<Topic>(`${this.apiUrl}/topics`, topic);
  }

  getTopics(page: number, size: number, sort: string): Observable<Page<Topic>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort); 
    return this.http.get<Page<Topic>>(`${this.apiUrl}/topics`, { params });
  }

  getTopicById(id: number): Observable<Topic> {
    return this.http.get<Topic>(`${this.apiUrl}/topics/${id}`);
  }



  addReply(topicId: number, message: CreateMessageDto): Observable<Message> {
    return this.http.post<Message>(`${this.apiUrl}/topics/${topicId}/messages`, message);
  }

  getMessagesInTopic(topicId: number, page: number, size: number, sort: string): Observable<Page<Message>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort); 
    return this.http.get<Page<Message>>(`${this.apiUrl}/topics/${topicId}/messages`, { params });
  }
}