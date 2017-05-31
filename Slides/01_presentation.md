# Workshop Spring MVC<br><br><i class="fa fa-leaf" aria-hidden="true"></i>Présentation

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   **[Presentation](#/1)**
*   [Architecture](#/2)
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## Spring MVC en deux mots

*   Framework pour applications web “classiques”   
*   Support pour web services REST
    *   Support serveur et client (RestTemplate)   
*   Dépendances : Spring et API Servlet
*   Très flexible (beaucoup de points d’extension)   
*   Base technique pour d’autres frameworks
    *   Spring Web Flow, Grails



## DispatcherServlet

*   Le coeur de Spring MVC
*   Coordonne des composants d’infrastructure
*   Appelle les contrôleurs applicatifs



## Contrôleur "Hello World"

*   Les contrôleurs sont des POJO annotés

```java
@RestController // indique à Spring que c’est un contrôleur
public class HelloWorldController {
  @RequestMapping("/hello") // quel URL ?
  public String hello() {
    return "Hello World!";
  }
}
```



## Les contrôleurs sont des beans Spring

*   Il faut bien déclarer les contrôleurs dans Spring   
*   Une solution est le component scanning
    *   Déclarations en XML ou en Java fonctionnent aussi



## Comment démarrer ?

*   Utiliser Spring Boot
*   Gère les dépendances, la DispatcherServlet, etc.

```java
@SpringBootApplication // gère notamment le component scanning
public class SpringMvcOverviewApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringMvcOverviewApplication.class, args);
  }
}
```



<!-- .slide: class="page-questions" -->
