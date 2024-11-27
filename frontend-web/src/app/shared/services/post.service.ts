import {inject, Injectable} from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/post.model";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private api: string = environment.apiUrl + 'post/api/post';
  http: HttpClient = inject(HttpClient);

  getPosts() {
    return this.http.get<Post[]>(this.api);
  }

  addPost(post: Post) {
    return this.http.post<Post>(this.api, post);
  }
}
