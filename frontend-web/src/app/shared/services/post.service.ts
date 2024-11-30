import {inject, Injectable} from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/post.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  api: string = environment.apiUrl + '/api/posts';
  http: HttpClient = inject(HttpClient);

  getPosts() {
    return this.http.get<Post[]>(this.api);
  }

  addPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.api, post);
  }
}