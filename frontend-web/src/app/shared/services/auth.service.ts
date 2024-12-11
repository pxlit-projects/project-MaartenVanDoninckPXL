import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  router: Router = inject(Router);
  private currentUser: { userName: string, role: string } | null = null;

  login(userName: string, role: string): void {
    this.currentUser = { userName, role };
    this.router.navigate(['/']);
  }

  logout(): void {
    this.currentUser = null;
    this.router.navigate(['/login']);
  }

  getUser(): { userName: string, role: string } | null {
    return this.currentUser;
  }

  isLoggedIn(): boolean {
    return !!this.currentUser;
  }

  hasRole(role: string): boolean {
    return this.currentUser?.role === role;
  }
}
