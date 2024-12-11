import { Component, inject } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  authService: AuthService = inject(AuthService);
  fb: FormBuilder = inject(FormBuilder);

  loginForm = this.fb.group({
    username: ['', Validators.required],
    role: ['redacteur', Validators.required]
  });

  login(): void {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username')?.value;
      const role = this.loginForm.get('role')?.value;
      this.authService.login(username!, role!);
    }
  }

  isFormInvalid() {
    return this.loginForm.invalid;
  }
}
