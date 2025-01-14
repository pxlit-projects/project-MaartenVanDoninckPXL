# Requirements Full Stack JAVA - Backend

## 1. Unit Test alle logica in je services met Mockito, mock je repos
* Falende testen zijn niet ok
* @Ignored testen zijn niet ok
* Testen met pointless assertions zijn niet ok

### Completed

## 2. Test je services met MockMVC en Testcontainers.org

### Completed

### 3. Werk je Use Cases uit volgens de opdracht

### Completed

## 4. Deployment
* Lokaal op je laptop, alles dockerized is geen vereiste

### Completed

## 5. Kennis vragen

### 1. Concepten **DI** (Dependency Injection) en **IoC** (Inversion of Control) grondig kennen, kunnen uitleggen en toepassen **Annotations** kunnen uitleggen en toepassen

**Dependency Injection (DI)**
* Ontwerpprincipe waarbij objecten hun afhankelijkheden niet zelf creëren, maar deze worden aangeleverd door een externe bron (zoals een framework). Dit maakt de code flexibeler, testbaarder en onderhoudbaarder.
* In [PostControllerTests](./backend-java/post-service/src/test/java/be/pxl/services/controller/PostControllerTests.java) wordt DI toegepast door gebruik te maken van @Autowired en @InjectMocks annotaties. Deze annotaties zorgen dat de benodigde afhankelijkheden automatisch worden geïnjecteerd door het Spring framework.

**Inversion of Control (IoC)**
* Breder concept waarbij controle over het aanmaken en beheren van objecten wordt omgedraaid van applicatie naar container of framework. In het geval van Spring wordt de IoC-container gebruikt om objecten te beheren en hun levenscyclus te controleren.

**Annotations**
* **@SpringBootTest:** Wordt gebruikt om Spring Boot context te laden voor integratietests. In dit geval wordt de [PostServiceApp](backend-java\post-service\src\main\java\be\pxl\services\PostServiceApp.java) klasse gebruikt om context te configureren.

* **@AutoConfigureMockMvc:** Configureert automatisch MockMvc instantie die wordt gebruikt om HTTP-verzoeken te simuleren in tests.

* **@Autowired:** Gebruikt om afhankelijkheden automatisch te injecteren. In de code wordt ***MockMvc*** en ***ObjectMapper*** geïnjecteerd.

* **@Mock:** Gebruikt om een ***mock*** object te maken. In de code wordt [IPostService](backend-java\post-service\src\main\java\be\pxl\services\services\IPostService.java) ***gemockt***.

* **@InjectMocks:** Gebruikt om ***mock*** objecten te injecteren in het gespecificeerde object. In de code wordt [PostController](backend-java\post-service\src\main\java\be\pxl\services\controller\PostController.java) ***geïnjecteerd*** met de ***gemockte*** [IPostService](backend-java\post-service\src\main\java\be\pxl\services\services\IPostService.java).

``` java
@SpringBootTest(classes = PostServiceApp.class)
@AutoConfigureMockMvc
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IPostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    // Test methoden...
}
```

### 2. Weten wat het Proxy pattern is en hoe het gebruikt wordt

**Proxy Pattern**
* **Proxy pattern** is ***structureel ontwerppatroon*** dat ***surrogaat*** of ***plaatsvervanger*** biedt voor ander object om de ***toegang*** tot dit object te ***controleren***. Kan worden gebruikt voor verschillende doeleinden, zoals ***lazy loading***, ***toegangsbescherming***, ***logging***, enz.

**Voorbeeld**
* Toegang tot [PostService](backend-java\post-service\src\main\java\be\pxl\services\services\PostService.java) controleren

* **Interface [IPostService](backend-java\post-service\src\main\java\be\pxl\services\services\IPostService.java):** Definieer interface die service-methoden bevat

* **Implementatie [PostServiceImpl](backend-java\post-service\src\main\java\be\pxl\services\services\PostService.java):** Implementeerd daadwerkelijke service

* **Proxy [PostServiceProxy]():** Implementeerd interface en bevat referentie naar de echte service. Controleert toegang tot echte service.

    ``` java
    public interface IPostService {
        List<PostResponse> getDraftPosts();
        List<PostResponse> getDraftPostsByAuthor(String author);
        List<PostResponse> getPendingPosts();
        List<PostResponse> getPendingPostsByAuthor(String author);
        List<PostResponse> getApprovedPosts();
        List<PostResponse> getPostedPosts();
        PostResponse getPostById(Long id);
        int getAmountOfReviewedPostsByAuthor(String author);
        PostResponse updatePost(Long id, PostRequest postRequest);
        PostResponse submitPost(Long id);
    }
    ```
    ``` java
    public class PostServiceImpl implements IPostService {
        // Implementatie van de methoden
        @Override
        public List<PostResponse> getDraftPosts() {
            // Logica voor het ophalen van draft posts
        }

        // Andere methoden...
    }
    ```
    ```java
    public class PostServiceProxy implements IPostService {
        private final IPostService postService;

        public PostServiceProxy(IPostService postService) {
            this.postService = postService;
        }

        @Override
        public List<PostResponse> getDraftPosts() {
            // Toegangscontrole of logging
            System.out.println("Toegang tot getDraftPosts");
            return postService.getDraftPosts();
        }

        // Andere methoden met toegangscontrole of logging...
    }
    ```

### 3. Wat zijn de standaard features van Spring Cloud?

**Spring Cloud**
* ***Framework*** ontworpen om ontwikkelaars te helpen bij bouwen van **gedistribueerde systemen** en **microservices**. Biedt aantal ***standaard features*** die het ***eenvoudiger*** maken om ***robuuste*** en ***schaalbare*** applicaties te ontwikkelen.

**Standaard Features**
* **Service Discovery:** Biedt ondersteuning voor ***service discovery*** met behulp van tools zoals ***Netflix Eureka***. Maakt mogelijk voor ***microservices*** om elkaar te ***vinden*** en met elkaar te ***communiceren*** zonder exacte locaties van de services bekend hoeven te zijn.

* **Configuration Management:** Biedt ***centrale configuratieserver*** die ***configuratie-instellingen*** voor ***alle microservices*** beheert. Maakt het mogelijk om configuraties centraal te beheren en te wijzigen zonder dat services opnieuw moeten worden gedeployed.

* **Routing en Load Balancing:** Biedt ***intelligente routing*** en ***client-side load balancing*** met behulp van tools zoals Netflix Ribbon en Spring Cloud Gateway. Zorgt voor een efficiënte verdeling van verkeer over meerdere instances van service.

* **Messaging:** Biedt ondersteuning voor bouwen van ***event-driven microservices*** met behulp messaging systemen zoals ***RabbitMQ***.

### 4. Wat is het verschil tussen micro services (Spring Cloud) en Spring Boot?

**Spring Boot**
* Vergemakkelijkt ontwikkelen van applicaties door ***configuratie*** te ***vereenvoudigen*** (convention over configuration).
* Biedt ingebouwde webservers zoals Tomcat of Jetty.
* Geschikt voor bouwen ***individuele services*** of applicaties.
  * **Service Discovery** (Eureka)
  * **Configuratiebeheer** (Spring Cloud Config)
  * **Load balancing** (Ribbon)
  * **API Gateway** (Zuul of Gateway)
  * **Circuit Breakers** (Hystrix/Resilience4J)

**Microservices (Spring Cloud)**
* Helpt ***beheren*** van uitdagingen in ***microservices***, zoals

**Belangrijkste verschil**
* **Spring Boot:** Ontwikkelen ***individuele services*** of applicaties.
* **Spring Cloud:** ***Verbinden***, ***beheren*** en ***orkestreren*** ***meerdere services*** in microservices-architectuur.

### 5. Hoe zou je communiceren tussen je componenten?

**Message Queues:** Voor asynchrone communicatie kunnen message queues zoals RabbitMQ worden gebruikt.
* Zoals bij [ReviewService](backend-java\review-service\src\main\java\be\pxl\services\services\ReviewService.java) en [PostService](backend-java\post-service\src\main\java\be\pxl\services\services\PostService.java) waarbij ***ReviewService*** een bericht stuurt naar ***PostService*** om een post te updaten. [QueueService](backend-java\post-service\src\main\java\be\pxl\services\messaging\QueueService.java) vangt het bericht op.

    ```java
    @Service
    @RequiredArgsConstructor
    public class ReviewService implements IReviewService {

        private final ReviewRepository reviewRepository;
        private final RabbitTemplate rabbitTemplate;

        // Andere methoden...

        public void createReview(ReviewRequest reviewRequest) {
            Optional<Review> reviewOptional = reviewRepository.findReviewsByPostId(reviewRequest.getPostId());
            if (reviewOptional.isPresent()) {
                throw new IllegalArgumentException("Review already exists for post with id " + reviewRequest.getPostId());
            }
            Review review = Review.builder()
                    .postId(reviewRequest.getPostId())
                    .author(reviewRequest.getAuthor())
                    .approval(reviewRequest.isApproval())
                    .content(reviewRequest.getContent())
                    .build();
            reviewRepository.save(review);

            ReviewResponse reviewResponse = ReviewResponse.builder()
                    .postId(review.getPostId())
                    .reviewId(review.getId())
                    .approval(review.isApproval())
                    .author(review.getAuthor())
                    .content(review.getContent())
                    .build();

            rabbitTemplate.convertAndSend("post-service-create-queue", reviewResponse);
        }

        @Override
        public void deleteReview(Long reviewId) {
            Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
            if (reviewOptional.isEmpty()) {
                throw new IllegalArgumentException("Review with id " + reviewId + " not found");
            }
            Review review = reviewOptional.get();
            reviewRepository.deleteById(reviewId);

            DeleteReviewResponse deleteReviewResponse = DeleteReviewResponse.builder()
                    .postId(review.getPostId())
                    .build();

            rabbitTemplate.convertAndSend("post-service-delete-queue", deleteReviewResponse);
        }
    }
    ```

    ```java
    @Service
    @RequiredArgsConstructor
    public class QueueService {

        private final IPostService postService;

        @RabbitListener(queues = "post-service-create-queue")
        public void listen(ReviewRequest reviewRequest) {
            postService.updatePostWithReview(reviewRequest);
        }

        @RabbitListener(queues = "post-service-delete-queue")
        public void listen(DeleteReviewResponse deleteReviewResponse) {
            postService.updatePostWithDeletedReview(deleteReviewResponse);
        }
    }
    ```

    ```java
    package be.pxl.services.services;

    import be.pxl.services.domain.Post;
    import be.pxl.services.domain.Status;
    import be.pxl.services.domain.dto.DeleteReviewResponse;
    import be.pxl.services.domain.dto.PostRequest;
    import be.pxl.services.domain.dto.PostResponse;
    import be.pxl.services.domain.dto.ReviewRequest;
    import be.pxl.services.repository.PostRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class PostService implements IPostService {

        private final PostRepository postRepository;

        // Andere methoden...

        @Override
        public void updatePostWithReview(ReviewRequest reviewRequest) {
            Post post = postRepository.findById(reviewRequest.getPostId()).orElseThrow();
            post.setReviewId(reviewRequest.getReviewId());
            if (reviewRequest.isApproval()) {
                post.setStatus(Status.valueOf("APPROVED"));
            } else {
                post.setStatus(Status.valueOf("REJECTED"));
            }
            postRepository.save(post);
        }

        @Override
        public void updatePostWithDeletedReview(DeleteReviewResponse deleteReviewResponse) {
            Post post = postRepository.findById(deleteReviewResponse.getPostId()).orElseThrow();
            post.setReviewId(null);
            post.setStatus(Status.valueOf("DRAFT"));
            postRepository.save(post);
        }
    }
    ```

### 6. Hoe wordt het proxy pattern toegepast in jouw architectuur?

Niet

### 7. Spring Boot, hoe werken annotaties?

In Spring Boot worden annotaties gebruikt om verschillende aspecten van applicatie te ***configureren*** en ***beheren***.

* **@Autowired:** Gebruikt om ***afhankelijkheden*** ***automatisch*** te ***injecteren***. Spring Boot **zoekt** naar geschikte **bean** en **injecteert** deze in het **gemarkeerde veld**, **constructor** of **setter-methode**.
    ```java
    @Service
    public class PostService {
        @Autowired
        private PostRepository postRepository;

        // Methoden...
    }
    ```

* **@Component, @Service, @Repository, @Controller:** Gebruikt om **beans** te markeren die door Spring's component scanning mechanisme moeten worden gedetecteerd en geregistreerd.
    ```java
    @Service
    public class PostService {
        // Methoden...
    }

    @Repository
    public interface PostRepository extends JpaRepository<Post, Long> {
        // Methoden...
    }
    ```

* **@RestController:** Combinatie van ***@Controller*** en ***@ResponseBody***. Gebruikt om **controller** aan te duiden die **RESTful** webservices afhandelt.
    ```java
    @RestController
    @RequestMapping("/api/posts")
    public class PostController {
        @Autowired
        private PostService postService;

        @GetMapping
        public List<Post> getAllPosts() {
            return postService.getAllPosts();
        }
    }
    ```

* **@Entity:** Gebruikt om klasse aan te duiden als ***JPA-entiteit***. Gebruikt in combinatie met ***andere JPA-annotaties*** om mapping van de ***klasse*** naar een ***database-tabel*** te definiëren.
    ```java
    @Entity
    public class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String content;

        // Getters en setters...
    }
    ```
* **@Configuration:** Gebruikt om klasse aan te duiden die **een** of **meer** **Spring beans** definieert. Vaak gebruikt in combinatie met **@Bean** om bean-methoden te markeren.
    ```java
    @Configuration
    public class AppConfig {
        @Bean
        public PostService postService() {
            return new PostService();
        }
    }
    ```

### 8. Waarom pas je messaging toe?

**Zie vraag 5**

### 9. Welke verschillende manieren van messaging kan je toepassen of heb je gebruik in je implementatie?

**Zie vraag 5**

### 10. Op welke problemen stoot als je log4j zou gebruiken zoals in Spring Boot?

* **Configuratiecomplexiteit:** Log4j vereist aparte configuratiebestand (log4j.properties of log4j.xml). Dit kan complexer zijn in vergelijking met eenvoud van de standaard Spring Boot logging configuratie.
  
* **Compatibiliteit:** Spring Boot gebruikt standaard Logback als logging framework. Vervangen van Logback door Log4j kan compatibiliteitsproblemen veroorzaken, vooral als andere bibliotheken afhankelijk zijn van Logback.

### 11. Hoe heb je logging toegepast?

**SLF4J** (Simple Logging Facade for Java) en Logback worden gebruikt voor logging in [PostService](backend-java\post-service\src\main\java\be\pxl\services\services\PostService.java), [ReviewService](backend-java/review-service/src/main/java/be/pxl/services/services/ReviewService.java) en [CommentService](backend-java\comment-service\src\main\java\be\pxl\services\services\CommentService.java).

```java	
private static final Logger logger = LoggerFactory.getLogger(PostService.class);

@Override
public int getAmountOfReviewedPostsByAuthor(String author) {
    logger.info("Fetching the amount of reviewed posts by author: {}", author);
    return postRepository.findAllByStatusInAndAuthor(List.of(Status.valueOf("APPROVED"), Status.valueOf("REJECTED")), author).size();
}

@Override
public PostResponse getPostById(Long id) {
    logger.info("Fetching post with ID: {}", id);
    Post post = postRepository.findById(id).orElseThrow();
    logger.debug("Post found with ID: {}", id);
    return PostResponse.builder()
            .id(post.getId())
            .reviewId(post.getReviewId())
            .title(post.getTitle())
            .content(post.getContent())
            .author(post.getAuthor())
            .status(post.getStatus())
            .category(post.getCategory())
            .createdOn(post.getCreatedOn())
            .build();
}
```

### 12. Heb je je afgevraagd heb ik hier met 'duplication of data' te maken? En zoja waar/wanneer?

Ja, er zijn enkele gevallen in de code waar duplicatie van data kan optreden.

* In [PostService](backend-java\post-service\src\main\java\be\pxl\services\services\PostService.java) wordt review ID en status van post bijgehouden.
* In [ReviewService](backend-java\review-service\src\main\java\be\pxl\services\services\ReviewService.java) wordt review zelf bijgehouden.

### 13. Hoe verwerkt de Gateway requests?

[GatewayServiceApp](backend-java\gateway-service\src\main\java\be\pxl\services\GatewayServiceApp.java) verwerkt requests door gebruik te maken van Spring Cloud Gateway. Wordt geconfigureerd in [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml).

* **Discovery Client:** ***@EnableDiscoveryClient*** annotatie in [GatewayServiceApp](backend-java\gateway-service\src\main\java\be\pxl\services\GatewayServiceApp.java) zorg dat gateway service zichzelf registreert bij een ***Eureka server*** en andere services kan ontdekken.

* **Route Configuration:** In [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml) worden routes gedefinieerd. Elke route heeft een ***id***, ***uri***, ***predicates*** en ***filters***.

* **Request Routing:** Wanneer request binnenkomt, wordt ***pad*** van ***request*** vergeleken met ***Path*** ***predicate*** van elke route. Als er ***match*** is, wordt ***request*** doorgestuurd naar de ***uri*** die route.

* **Rewrite Path Filter:** Herschrijft pad van inkomende request voordat het wordt doorgestuurd naar doelservice.

### 14. Hoe heb je CORS problemen opgelost?

**CORS** (Cross-Origin Resource Sharing) worden opgelost in [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml) door routes toe te voegen in ***globalCors***

```yaml
globalcors:
    corsConfigurations:
        '[/**]':
        allowedOriginPatterns:
            - "http://localhost"
            - "http://localhost:4200"
        allowedMethods:
            - "*"
        allowedHeaders:
            - "*"
        allowCredentials: true
```

* **allowedOriginPatterns:** Toegestane oorsprongen voor CORS-verzoeken.
* **allowedMethods:** Toegestane methoden voor CORS-verzoeken.
* **allowedHeaders:** Toegestane headers voor CORS-verzoeken.
* **allowCredentials:** Geeft aan of browser verzoeken met credentials (cookies, HTTP-authenticatie) mag sturen.

### 15. Heb je load balancing toegepast? Wat is het?

Ja, load balancing is toegepast in de code.
* Load balancing is een techniek die inkomende ***netwerkverkeer*** ***verdeelt*** over ***meerdere servers*** om belasting te spreiden en prestaties en beschikbaarheid van applicaties te verbeteren. 

In [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml) wordt load balancing toegepast door gebruik te maken van de ***lb:// URI*** schema in uri velden van routes. Zorgt ervoor dat ***requests*** **gelijkmatig** worden **verdeeld** over ***beschikbare instances*** van ***services***.

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: post-service
          uri: lb://post-service
          predicates:
            - Path=/post/**
          filters:
            - RewritePath=/post/(?<path>.*), /$\{path}
        - id: review-service
          uri: lb://review-service
          predicates:
            - Path=/review/**
          filters:
            - RewritePath=/review/(?<path>.*), /$\{path}
        - id: comment-service
          uri: lb://comment-service
          predicates:
            - Path=/comment/**
          filters:
            - RewritePath=/comment/(?<path>.*), /$\{path}
```

### 16. Wat bedoelen we met Service registration en Discovery?
Process waar services zichzelf ***registreren*** en ***ontdekken*** in ***gedistribueerd systeem***.

Elke service registreert zichzelf bij ***service registry*** (zoals Eureka) bij het ***opstarten***. Service registry houdt bij welke services ***beschikbaar*** zijn en ***waar*** ze zich bevinden.

**Service Registration**
* Elke service registreerd zichzelf bij ***Eureka*** server bij het ***opstarten***. Dit wordt gedaan met behulp van ***@EnableDiscoveryClient*** annotatie in Spring Boot applicatie.

**Service Discovery**
* De gateway-service gebruikt ***service discovery*** om andere services te ***ontdekken***. Dit wordt gedaan door ***service-id*** te gebruiken in ***uri*** velden van routes in [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml).

### 17. Verklaar Open-Feign?

Declaratieve HTTP-client die wordt gebruikt om HTTP-aanroepen naar andere services te vereenvoudigen. Maakt het mogelijk om REST-clients te maken door middel van interfaces en annotaties, zonder dat je expliciet de HTTP-aanroepen hoeft te coderen.

### 18. Wat zijn de voordelen van Open-Feign?

* **Declaratieve Syntax:** ***HTTP-clients*** definiëren met behulp van ***interfaces*** en ***annotaties***, wat code eenvoudiger en leesbaarder maakt.

* **Integratie met Spring Cloud:** Integreert naadloos met Spring Cloud, waardoor het eenvoudig is om services te ontdekken en te communiceren via Eureka.

* **Automatische Load Balancing:** Ondersteunt ***load balancing*** door gebruik te maken van Ribbon, wat zorgt voor betere verdeling van verkeer over meerdere instances van een service.

* **Herbruikbaarheid:** Door gebruik te maken van interfaces, kun je HTTP-clients eenvoudig hergebruiken in verschillende delen van applicatie.

* **Eenvoudige Foutafhandeling:** Ingebouwde ondersteuning voor foutafhandeling en retries, wat de betrouwbaarheid van je applicatie verhoogt.

### 19. Wat zijn de voordelen van Spring Cloud?

* **Service Discovery:** Met Netflix Eureka kunnen **services** zichzelf **registreren** en andere services **ontdekken**, wat dynamische schaalbaarheid en flexibiliteit mogelijk maakt.

* **Load Balancing:** Ingebouwde **load balancing** met behulp van **Ribbon**, wat zorgt voor gelijkmatige verdeling van verkeer over meerdere instances van een service.

* **Declaratieve HTTP Clients:** Met OpenFeign kun je eenvoudig HTTP-clients maken met behulp van interfaces en annotaties, wat de complexiteit van het maken van HTTP-aanroepen vermindert.

* **Configuratiebeheer:** Spring Cloud Config Server maakt gecentraliseerd configuratiebeheer mogelijk, waardoor configuraties eenvoudig kunnen worden beheerd en gedeeld tussen verschillende services.

* **API Gateway:** Spring Cloud Gateway biedt een manier om API-verkeer te routeren, te filteren en te beheren, wat zorgt voor betere controle en beveiliging van API's.

* **Circuit Breaker:** Met Hystrix kunnen services zichzelf beschermen tegen falende afhankelijkheden door middel van circuit breakers, wat de veerkracht van de applicatie verhoogt.

### 20. Noem de service implementatie die zorgt voor Registration en Discovery?

**Eureka**

* **Service Registration:** Elke service registreert zichzelf bij de Eureka-server. Dit wordt gedaan met de annotatie ***@EnableDiscoveryClient*** in de hoofdapplicatieklasse en configuratie in het **application.properties** bestand.

* **Service Discovery:** De ***gateway-service*** gebruikt de ***Eureka-server*** om andere services te ontdekken. Dit is geconfigureerd in [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml) met de discovery.locator.enabled eigenschap ingesteld op true.

### 21. Wat is het verschil tussen Spring Cloud en Spring Boot?

**Zie vraag 4**

### 22. Wat zijn de meest gebruike Spring Cloud annotaties?

* **@EnableDiscoveryClient:** Hiermee kan een ***service*** zichzelf ***registreren*** bij een **service registry** zoals Eureka en ***andere services ontdekken***.

* **@EnableFeignClients:** Hiermee kunnen ***Feign-clients*** worden ingeschakeld, wat declaratieve REST-clients mogelijk maakt.

* **@SpringBootApplication:** Combinatie van ***@Configuration***, ***@EnableAutoConfiguration***, en   ***@ComponentScan*** en wordt gebruikt om een Spring Boot applicatie te markeren.

* **@FeignClient:** Hiermee kan een ***interface*** worden gedefinieerd als een ***Feign-client*** om ***HTTP-aanroepen*** naar andere services te doen.

### 23. Hoe ga je om met modules in je project setup?

* **Multi-module project:** Project is opgedeeld in ***meerdere modules*** (services, config-service, gateway-service, etc.) om code te ***organiseren*** en ***scheiden*** op basis van ***functionaliteit***.

* **Parent POM:** ***Parent POM*** bevat ***dependencies*** en ***configuraties*** die ***gedeeld*** moeten worden door ***modules***. [Parent POM](backend-java\pom.xml)

* **Module POM:** ***Module POM*** bevat ***dependencies*** en ***configuraties*** die ***specifiek*** zijn voor dat ***module***. [Post Service POM](backend-java\post-service\pom.xml) / [Review Service POM](backend-java\review-service\pom.xml) / [Comment Service POM](backend-java\comment-service\pom.xml) / [Gateway Service POM](backend-java\gateway-service\pom.xml) / [Config Service POM](backend-java\config-service\pom.xml)

### 24. Wat is Hystrix?

**Hystrix** is een ***circuit breaker*** die wordt gebruikt om ***services*** te ***beschermen*** tegen ***falen*** van ***afhankelijkheden***. Het detecteert ***fouten*** en ***latency*** van ***afhankelijkheden*** en schakelt over naar ***fallback***-logica om te voorkomen dat fouten zich verspreiden naar andere delen van de applicatie.

```java
@SpringBootApplication
@EnableCircuitBreaker
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

```java
@Service
public class SomeService {

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public String someMethod() {
        // Logica die mogelijk kan falen
        return "Resultaat";
    }

    public String fallbackMethod() {
        return "Fallback resultaat";
    }
}
```

### 25. Wat is de Pivotal Cloud Foundry (PCF)?

**Pivotal Cloud Foundry (PCF)** is een ***cloud-native platform*** dat wordt gebruikt om ***applicaties*** te ***bouwen***, ***testen***, ***implementeren*** en ***schaalbaar*** te maken. Het biedt ***container***-gebaseerde ***architectuur*** en ***automatisering*** voor ***applicatielevenscyclusbeheer***.

### 26. Welke implementatie heb je gebruikt voor Messaging?

**Kijk vraag 5**

### 27. Heb je bepaalde end-point beveiligd en hoe?

Buiten de standaard beveiliging die Spring Boot biedt, zijn er geen specifieke endpoints beveiligd in de code. Buiten CORS-beveiliging in [gateway-service.yaml](backend-java\config-service\src\main\resources\config\gateway-service.yaml).

### 28. Hoe heb je je microservices getest?

* **Unit Testing:** Alle ***logica*** in ***services*** is ***getest*** met ***Mockito*** met ***testcontainers***. ***Mocks*** zijn gebruikt om ***repos*** te ***mocken***.

### 29. Hoe identificeer je je browser? 1 of andere vorm van security toegepast?

Je kunt de ***browser identificeren*** en een vorm van security toepassen door gebruik te maken van een combinatie van ***Spring Security*** en een ***custom filter*** die de ***User-Agent header*** controleert.

* Maak een custom filter aan om de User-Agent header te controleren:
    ```java
    @Component
    public class UserAgentFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String userAgent = request.getHeader("User-Agent");
            if (userAgent != null && userAgent.contains("Mozilla")) {
                // Browser identified as Mozilla
                filterChain.doFilter(request, response);
            } else {
                // Block request if browser is not identified
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            }
        }
    }
    ```

* Configureer Spring Security om de custom filter te gebruiken:
    ```java
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final UserAgentFilter userAgentFilter;

        public SecurityConfig(UserAgentFilter userAgentFilter) {
            this.userAgentFilter = userAgentFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(userAgentFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
    ```
