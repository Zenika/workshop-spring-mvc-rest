# Workshop Spring MVC<br><br><i class="fa fa-link" aria-hidden="true"></i>REST

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   **[REST](#/5)**
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## Spring MVC & REST

*   Spring MVC fournit un support REST
*   Même modèle de programmation que pour les applications web
*   Annotations et mécanismes supplémentaires
*   Principale différence : plus de vue
*   Spring MVC
    *   désérialise le contenu des requêtes
    *   sérialise le contenu des réponses



## Activer le support REST

*   Utiliser le starter web de Spring Boot
*   Inclut Spring MVC, sérialisation JSON, etc

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



## Récupérer une ressource (HTTP)

*   Requête

```console
GET /contacts/1 HTTP/1.1
Accept: application/json
Host: localhost:8080
```

*   Réponse

```console
HTTP/1.1 200 OK
Content-Type: application/json
{"id":1,"firstname":"Joe","lastname":"Dalton","age":37}
```



## Récupérer une ressource (Contrôleur)

```java
@RequestMapping(value="/contacts/{id}", method=RequestMethod.GET)
public Contact contact(@PathVariable Long id) {
  return contactRepository.findOne(id);
}
```



## Récupérer une ressource (Contrôleur)

```java
@RequestMapping(
  value="/contacts/{id}", // URL
  method=RequestMethod.GET) // opération
public Contact contact(
    @PathVariable Long id) { // àextraire de l'URL
  return contactRepository.findOne(id);
}
```



## Récupérer une ressource (client)

```java
RestTemplate tpl = new RestTemplate();
Contact contact = tpl.getForObject(
  "http://localhost:8080/contacts/{id}",
  Contact.class,
  id
);
```



## Récupérer des ressources (HTTP)

*   Requête

```console
GET /contacts HTTP/1.1
Accept: application/json
Host: localhost:8080
```

*   Réponse

```console
HTTP/1.1 200 OK
Content-Type: application/json
[
 {"id":1,"firstname":"Joe","lastname":"Dalton","age":37},
 {"id":2,"firstname":"William","lastname":"Dalton","age":35},
 {"id":3,"firstname":"Jack","lastname":"Dalton","age":33},
 {"id":4,"firstname":"Averell","lastname":"Dalton","age":31}
]
```



## Récupérer des ressources (Contrôleur)

```java
@RequestMapping(value="/contacts",method=RequestMethod.GET)
public List<Contact> contacts() {
  return contactRepository.findAll();
}
```



## Récupérer des ressources (client)

```java
RestTemplate tpl = new RestTemplate();
Contact [] contacts = tpl.getForObject(
  "http://localhost:8080/contacts",
  Contact[].class
);
```



## Créer une ressource (HTTP)

*   Requête

```console
POST /contacts HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
{"id":null,"firstname":"Oncle","lastname":"Picsou","age":100}
```

*   Réponse

```console
HTTP/1.1 201 Created
Location: http://localhost:8080/contacts/130
Content-Length: 0
```



## Créer une ressource (Contrôleur)

```java
@RequestMapping(value="/contacts",method=RequestMethod.POST)
public ResponseEntity<Void> create(@RequestBody Contact contact,
                          UriComponentsBuilder uriComponentsBuilder) {
  contactRepository.save(contact);
  URI location = uriComponentsBuilder
    .pathSegment("contacts","{id}")
    .build()
    .expand(contact.getId())
    .encode()
    .toUri();
  return ResponseEntity.created(location).build();
}
```



## Créer une ressource (Contrôleur)

*   Mieux : utilisation du ServletUriComponentsBuilder


```java
@RequestMapping(value="/contacts",method=RequestMethod.POST)
public ResponseEntity<Void> create(@RequestBody Contact contact) {
  contactRepository.save(contact);
  URI location = ServletUriComponentsBuilder
    .fromCurrentRequestUri()
    .path("contacts/{id}")
    .buildAndExpand(contact.getId())
    .toUri();
  return ResponseEntity.created(location).build();
}
```



## Créer une ressource (Contrôleur)

```java
@RequestMapping(value="/contacts",method=RequestMethod.POST)
public ResponseEntity<Void> create(
    @RequestBody Contact contact // extraire du corps de la requête
    ) {
  (...)
  return ResponseEntity.created( // code réponse
    location                     // URL de la ressource créée
  ).build();
}
```



## Créer une ressource (client)

```java
Contact contact = new Contact();
contact.setFirstname("Oncle");
contact.setLastname("Picsou");
contact.setAge(100);
RestTemplate tpl = new RestTemplate();
URI location = tpl.postForLocation(
  "http://localhost:8080/contacts",
contact
);
```



## Modifier une ressource (HTTP)

*   Requête

```console
PUT /contacts/130 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Host: localhost:8080
{"id":130,"firstname":"Oncle","lastname":"Picsou","age":90}
```

*   Réponse

```console
HTTP/1.1 204 No Content
Content-Length: 0
```



## Modifier une ressource (Contrôleur)

```java
@RequestMapping(value="/contacts/{id}",method=RequestMethod.PUT)
@ResponseStatus(HttpStatus.NO_CONTENT) //code réponse
public void update(@RequestBody Contact contact) {
  contactRepository.save(contact);
}
```



## Modifier une ressource (client)

```java
RestTemplate tpl = new RestTemplate(); // création donne l’URI
URI location = tpl.postForLocation(...); // modification
contact.setAge(90);
tpl.put(location,contact);
```



## Supprimer une ressource (HTTP)

*   Requête

```console
DELETE /contacts/130 HTTP/1.1
Host: localhost:8080

```

*   Réponse

```console
HTTP/1.1 204 No Content
Content-Length: 0
```



## Supprimer une ressource (Contrôleur)

```java
@RequestMapping(value="/contacts/{id}", method=RequestMethod.DELETE)
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable Long id) {
  contactRepository.delete(id);
}
```



## Supprimer une ressource (client)

```java
RestTemplate tpl = new RestTemplate(); // création donne l’URI
URI location = tpl.postForLocation(...); // suppression
tpl.delete(location);
```



## Gestion des erreurs

*   Ex. : demande d’une ressource qui n’existe pas
*   Le serveur doit retourner une erreur 404
*   Comment faire avec Spring MVC, côtés serveur et client ?



## Gestion des erreurs, côté serveur, solution 1

```java
@RequestMapping(value="/contacts/{id}", method=RequestMethod.GET)
public Contact contact(@PathVariable Long id) {
  Contact contact = contactRepository.findOne(id);
  if(contact == null) {
    throw new EmptyResultDataAccessException(1);
  }
  return contact;
}

@ExceptionHandler(EmptyResultDataAccessException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public void notFound() { }
```



## Gestion des erreurs, côté serveur, solution 2

*   Retourner une instance de `ResponseEntity`

```java
@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
public ResponseEntity<Contact> contact(@PathVariable Long id) {
  Contact contact = contactRepository.findOne(id);
  ResponseEntity<Contact> response = new ResponseEntity<Contact>(
contact,
    contact == null ? HttpStatus.NOT_FOUND : HttpStatus.OK
  );
  return response;
}
```



## Gestion des erreurs, côté client

```java
try {
  Contact contact = tpl.getForObject(location, Contact.class);
} catch (HttpStatusCodeException e) {
  // e.getStatusCode() == HttpStatus.NOT FOUND
}
```

*   Stratégie de gestion des exceptions configurable   
*   Propriété errorHandler du RestTemplate



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : spring-mvc-rest-start
