# Workshop Spring MVC<br><br><i class="fa fa-external-link fa-2x" aria-hidden="true"></i>HATEOAS

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   **[HATEOAS](#/8)**



## HATEOAS

*   Hypermedia as the engine of application state
*   Permet de découvrir les actions futures/possibles
    *   Naviguer vers une autre ressource
    *   Avoir le détail d’une ressource
    *   Récupérer d’autres représentations d’une ressource



## HATEOAS : exemple

```json
[
  {
    "firstname":"Joe",
    "lastname":"Dalton",
    "links":[
      {
        "rel":"self",
        "href":"http://localhost:8080/hateoas/zen-contact/contacts/1"
      }
    ]
  }, ...
]
```



## Spring HATEOAS

*   Une librairie proposant un support HATEOAS   
*   S’intègre avec Spring MVC
*   En cours de développement !



## `Link`

```java
Link link = new Link(
  "http://localhost:8080/hateoas/zen-contact/contacts/1",
  Link.REL_SELF
);
```

*   `Link` ajoutés aux ressources...
*   ... puis sérialisés en JSON, XML...



## Ressources avec des liens

*   Rajouter un support pour `Link` dans ses ressources...   
*   ... ou utiliser `ResourceSupport`

```java
public class ShortContact extends ResourceSupport {

  private String firstname,lastname;
  (...) // getters and setters
}
...
ShortContact resource = new ShortContact();
resource.add(new Link("http://localhost/contacts/1"));
```



## Intégration avec Spring MVC

```java
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/contacts")
public class ContactController {
  ...
  // dans une méthode
  // lien à partir de l’URL du contrôleur, ajout d’un identifiant
  Link detail = linkTo(ContactController.class)
    .slash(contact.getId())
    .withSelfRel();

}
```



## Lien sur une méthode de contrôleur


```java
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RequestMapping(method=RequestMethod.GET)
@ResponseBody
public List<ShortContact> contacts() {

  // fait référence à la méthode du contrôleur
  Link detail = linkTo(
    methodOn(ContactController.class).contact(contact.getId())
  ).withSelfRel();

}

@RequestMapping(value="/{id}", method=RequestMethod.GET)
public ResponseEntity<Contact> contact(@PathVariable Long id) { }
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : spring-mvc-hateoas-start
