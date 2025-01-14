import { Routes } from '@angular/router';
import { PostListComponent } from "./core/posts/post-list/post-list.component";
import { AddPostComponent } from "./core/posts/add-post/add-post.component";
import { PostDraftListComponent } from './core/posts/post-draft-list/post-draft-list.component';
import { LoginComponent } from './core/auth/login/login.component';
import { EditPostComponent } from './core/posts/edit-post/edit-post.component';
import { AuthGuard } from './core/auth/auth-guard';
import { PostPendingListComponent } from './core/posts/post-pending-list/post-pending-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'posts', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'posts', component: PostListComponent },
  {
    path: 'pending',
    component: PostPendingListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['redacteur', 'hoofdredacteur'] }
  },
  {
    path: 'draft',
    component: PostDraftListComponent,
    canActivate: [AuthGuard],
    data: { roles: ['redacteur', 'hoofdredacteur'] }
  },
  {
    path: 'add',
    component: AddPostComponent,
    canActivate: [AuthGuard],
    data: { roles: ['redacteur', 'hoofdredacteur'] }
  },
  {
    path: 'edit/:id',
    component: EditPostComponent,
    canActivate: [AuthGuard],
    data: { roles: ['redacteur', 'hoofdredacteur'] }
  }
];
