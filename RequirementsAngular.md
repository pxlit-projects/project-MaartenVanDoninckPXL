# Requirements Full Stack JAVA - Frontend

## Requirements Angular

### Je maakt gebruik van typescript en je volgt hierin zo veel mogelijk de best practices / linting tools
* **Done**

### Je gebruikt standalone components en de nieuwe control flow van Angular 18.
* **Control flow**: @for, @if
* [Navber Component](frontend-web\src\app\core\navbar\navbar.component.html)
* **Done**

### Je voorziet in de applicatie de nodige unit testen. We streven voor de frontend naar een test coverage van 50% op alle onderdelen (statements, branches, functions, lines)

```bash
ng test --code-coverage
```
* **Done**

### Voorzie duidelijke opsplitsing in verschillende components & gebruik hierbij de concepten gezien tijdens de les om te communiceren tussen components.
* **Done**

### Je voorziet in de applicatie de nodige event- en databinding en kiest hiervoor de beste implementatie afhankelijk van het scenario waarin ze gebruikt worden.
1. **Data Binding**
   * Interpolation [post-pending-component.html](frontend-web\src\app\core\posts\post-pending-item\post-pending-item.component.html)
        ```html
        <p class="text-red-600 mt-1">{{rejectionMessage.content}}</p>
        ```
    * Property Binding [navbar.component.html](frontend-web\src\app\core\navbar\navbar.component.html)
        ```html
        <a [routerLink]="['/pending']" routerLinkActive="active-link">
        ```
    * Two-way Binding [post-pending-item.component.html](frontend-web\src\app\core\posts\post-pending-item\post-pending-item.component.html)
        ```ts
        imports: [CommonModule, FormsModule]
        ```

2. **Event Binding**
    * Output Binding [filter.component.ts](frontend-web\src\app\core\posts\filter\filter.component.ts)
        ```ts
        @Output() filterChanged = new EventEmitter<any>();
        ```
    * Event Listener [post-pending-component.html](frontend-web\src\app\core\posts\post-pending-item\post-pending-item.component.html)
        ```html
        <button (click)="approvePost()" class="...">
        ```

3. **Smart Component Pattern**
    * List components like [PostPendingListComponent](frontend-web\src\app\core\posts\post-pending-list\post-pending-list.component.ts) manage data and pass it down to presentational components
    * Child components like [PostPendingItemComponent](frontend-web\src\app\core\posts\post-pending-item\post-pending-item.component.ts) receive data via ***@Input()*** and emit events via ***@Output()***

4. **Service-based State Managemen**
    * Services like [NotificationService](frontend-web\src\app\shared\services\notification.service.ts) use ***RxJS*** Subjects for ***cross-component communication***
        ```ts
        private reviewUpdateSource = new Subject<void>();
        reviewUpdate$ = this.reviewUpdateSource.asObservable();
        ```

* **Done**

### Voor de centralisatie van gegevens en voor het uitvoeren van http requests maak je gebruik van services & dependency injection.

1. **HTTP Services**
   * [PostService](frontend-web\src\app\shared\services\post.service.ts) uses ***HttpClient*** to make HTTP requests
        ```ts
        @Injectable({
            providedIn: 'root'
        })
        export class PostService {
            api: string = environment.apiUrl + '/post/api/posts';
            http: HttpClient = inject(HttpClient);
        }
        ```

2. **State Management Services**
   * [AuthService](frontend-web\src\app\shared\services\auth.service.ts) voor gebruikersauthenticatie
   * [NotificationService](frontend-web\src\app\shared\services\notification.service.ts) voor cross-component notificaties

* **Done**

### Je maakt gebruik van zowel template driven als reactive forms in de applicatie.

1. **Reactive Forms**
   * [AddPostComponent](frontend-web\src\app\core\posts\add-post\add-post.component.ts) uses ***Reactive Forms*** to manage form state
        ```ts
        @Component({
            imports: [ReactiveFormsModule],
        })
        export class AddPostComponent {
            fb: FormBuilder = inject(FormBuilder);
            postForm: FormGroup = this.fb.group({
                title: ['', Validators.required],
                content: ['', Validators.required],
                ...
            });
        }
        ```
2. **Template Driven Forms**
    * [PostPendingItemComponent](frontend-web\src\app\core\posts\post-pending-item\post-pending-item.component.ts) uses ***Template Driven Forms***
        ```ts
        @Component({
            imports: [CommonModule, FormsModule],
        })
        export class PostPendingItemComponent {
            rejectReason: string = '';
        }
        ```

* **Done**

### Je maakt gebruik van de HttpClient van Angular en gebruikt bijgevolg ook de verschillende RxJS operatoren.

1. **HttpClient Usage in Services**
   * [PostService](frontend-web\src\app\shared\services\post.service.ts) gebruikt ***HttpClient*** voor CRUS operaties
        ```ts
        @Injectable({
            providedIn: 'root'
        })
        export class PostService {
            http: HttpClient = inject(HttpClient);
            
            addPost(post: Post): Observable<Post> {
                return this.http.post<Post>(this.api, post).pipe(
                catchError(this.handleError)
                );
            }
        }
        ```

2. **RxJS Operators**
    * [PostService](frontend-web\src\app\shared\services\post.service.ts) gebruikt ***RxJS Operators*** zoals ***pipe*** en ***catchError***
        ```ts
        getCommentsByPostId(postId: number) {
            return this.http.get<Comment[]>(this.api + '/' + postId).pipe(
                catchError(this.handleError)
            );
        }
        ```
        ```ts
        private handleError(error: HttpErrorResponse) {
            return throwError(() => new Error(errorMessage));
        }
        ```

3. **Observable Transformaties**
   * [PostPendingListComponent](frontend-web\src\app\core\posts\post-pending-list\post-pending-list.component.ts) Gebruik van ***firstValueFrom*** voor het omzetten van ***Observables*** naar ***Promises***
        ```ts
        async loadPosts(): Promise<void> {
            try {
                this.posts = await firstValueFrom(this.postService.getPendingPosts());
            } catch (error) {
                console.error(error);
            }
        }
        ```

4. **Subscription Management**
   * [NavbarComponent](frontend-web\src\app\core\navbar\navbar.component.ts) gebruikt subscription management
        ```ts
        private subscription: Subscription;
            ngOnDestroy() {
            if (this.subscription) {
                this.subscription.unsubscribe();
            }
        }
        ```

* **Done**

### Je maakt gebruik van angular routing voor de navigatie in de frontend.
* ### Voorzie de nodige routeguards in de applicatie.

1. **Route Configuration in [app.routes.ts](frontend-web\src\app\app.routes.ts)**
    ```ts
    export const routes: Routes = [
        { path: '', redirectTo: 'posts', pathMatch: 'full' },
        { path: 'login', component: LoginComponent },
        { path: 'posts', component: PostListComponent },
        // ...
    ];
    ```

2. **Route Guards**
   * [AuthGuard](frontend-web\src\app\core\auth\auth-guard.ts) voor het beveiligen van bepaalde routes
        ```ts
        {
            path: 'pending',
            component: PostPendingListComponent,
            canActivate: [AuthGuard],
            data: { roles: ['redacteur', 'hoofdredacteur'] }
        }
        ```

* **Done**

### Je maakt gebruik van componenten uit de Angular material library indien van toepassing. Indien gewenst kan je ook kiezen voor tailwindCSS als UI library.

* **Done**

### De applicatie is docker ready opgebouwd. Dit wil zeggen dat er minstens gewerkt wordt met de environment files voor het build proces & dat er werkende dockerfiles (+ docker-compose) aanwezig zijn.

1. **Environment Files**

    * [environment.ts](frontend-web\src\environments\environment.ts)
        ```ts
        export const environment = {
            apiUrl: 'http://localhost:8084',
        };
        ```
    * [environment.development.ts](frontend-web\src\environments\environment.development.ts)
        ```ts
        export const environment = {
        apiUrl: 'http://localhost:8084',
        }
        ```

2. **Docker Configuratie**
    * [Dockerfile](frontend-web\Dockerfile)
        ```dockerfile
        FROM nginx
        COPY nginx.conf /etc/nginx/nginx.conf
        COPY ./dist/frontend-web/browser /usr/share/nginx/html
        EXPOSE 80
        ```

3. **Angular Configuratie**
   * [angular.json](frontend-web\angular.json) heeft environment configuraties
        ```json
        "fileReplacements": [
            {
                "replace": "src/environments/environment.ts",
                "with": "src/environments/environment.development.ts"
            }
        ]
        ```

4. **Nginx Configuratie**
   * [nginx.conf](frontend-web\nginx.conf) voor reverse proxy configuratie
        ```yaml
        events{}
        http {
            include /etc/nginx/mime.types;

            server {
                listen 80;
                server_name localhost;
                root /usr/share/nginx/html;
                index index.html;

                location / {
                    try_files $uri $uri/ /index.html;
                }
            }
        }
        ```

5. **Docker Compose**
   * [docker-compose.yml](docker-compose.yml) voor het opzetten van de frontend en backend services
        ```yaml
        services:
            frontend:
                build:
                    context: .
                    dockerfile: Dockerfile
                container_name: angular-frontend
                ports:
                    - "80:80"
                environment:
                    - NODE_ENV=production
                volumes:
                    - ./nginx.conf:/etc/nginx/nginx.conf:ro
                networks:
                    - frontend-web

            networks:
                frontend-web:
                    driver: bridge
        ```
        ```bash
        cd frontend-web
        docker-compose up --build
        ```
        http://localhost:80

* **Done**