import { inject, Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    authService: AuthService = inject(AuthService);
    router: Router = inject(Router);

  canActivate(route: ActivatedRouteSnapshot, _state: RouterStateSnapshot): boolean {
    const roles = route.data['roles'] as Array<string>;
    if (this.authService.isLoggedIn() && roles.includes(this.authService.getUser()?.role || '')) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
