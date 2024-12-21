import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  router: Router = inject(Router);
  private currentUser: { userName: string, role: string } | null = null;
  private readonly STORAGE_KEY = 'currentUser';

  constructor() {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const storedUser = localStorage.getItem(this.STORAGE_KEY);
    if (storedUser) {
      this.currentUser = JSON.parse(storedUser);
    }
  }

  private saveUserToStorage(): void {
    if (this.currentUser) {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.currentUser));
    } else {
      localStorage.removeItem(this.STORAGE_KEY);
    }
  }

  login(userName: string, role: string): void {
    this.currentUser = { userName, role };
    this.saveUserToStorage();
    this.router.navigate(['/']);
  }

  logout(): void {
    this.currentUser = null;
    this.saveUserToStorage();
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
