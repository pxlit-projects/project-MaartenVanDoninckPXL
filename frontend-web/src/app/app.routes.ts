import { Routes } from '@angular/router';
import { PostListComponent } from "./core/posts/post-list/post-list.component";
import { AddPostComponent } from "./core/posts/add-post/add-post.component";
import { PostDraftListComponent } from './core/posts/post-draft-list/post-draft-list.component';
import { LoginComponent } from './core/auth/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'posts', pathMatch: 'full' },
  { path: 'posts', component: PostListComponent },
  { path: 'add', component: AddPostComponent },
  { path: 'draft', component: PostDraftListComponent },
  { path: 'login', component: LoginComponent }
];
