# Workshop Spring MVC<br><br><i class="fa fa-globe" aria-hidden="true"></i>Application Web

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   **[Application Web](#/3)**
*   [Principes de REST](#/4)
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## Spring MVC et les applications Web

*   1 modèle de programmation, plusieurs types d’applications   
*   Application web “classique” : vue générée coté serveur
    *   Spring MVC = équivalent de Struts, JSF, Wicket, etc.
*   Application web REST : ne gère pas la vue
    *   Spring MVC ne retourne que des données
    *   Présentation gérée coté client (ex. : JQuery)



## Contrôleur application Web

*   Remplit un modèle de données
*   Indique quelle vue doit être rendue



## Contrôleur application Web

```java
@Controller
public class ContactController {

  @RequestMapping("/contact")
  public String contact(Model model) { // demande un Model vide
    Contact contact = (...); // chargement depuis la BD
    model.addAttribute(contact); // pour la vue
    return "/WEB-INF/views/contact.jsp"; // la vue à utiliser
  }

}
```



## La vue

*   Plusieurs technologies supportées : JSP, FreeMarker, Thymeleaf, Groovy Markup Templates, ...  
*   JSP très couramment utilisé

```html
<html>
  <head>
    <title>Spring MVC</title>
  </head>
  <body>
  ${contact.id} <br />
  ${contact.firstname} <br />
  ${contact.lastname} <br />
  </body>
</html>
```



## Couplage contrôleur/vue

*   Le contrôleur connait le chemin de la vue
*   C’est un couplage fort (chemin + technologie)

```java
@RequestMapping("/contact")
public String contact(Model model) {
  (...)
  return "/WEB-INF/views/contact.jsp";
}
```



## Découplage contrôleur/vue

*   Le contrôleur peut utiliser un nom logique   
*   Couplage lâche
*   Il faut déclarer un ViewResolver

```java
@RequestMapping("/contact")
public String contact(Model model) {
  (...)
  return "contact";
}
```



## Découplage contrôleur/vue

*   Utiliser InternalResourceViewResolser
*   Configurer un préfixe et un suffixe
*   Ils “décoreront” le nom retourné par le contrôleur

```xml
<bean class="o.s.web.servlet.view.InternalResourceViewResolver">
  <property name="prefix" value="/WEB-INF/views/" />
  <property name="suffix" value=".jsp" />
</bean>
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : spring-mvc-overview-start
