# Workshop Spring MVC<br><br><i class="fa fa-tachometer" aria-hidden="true"></i>Tests d'intégration hors-conteneur

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   [REST](#/5)
*   **[Tests d'intégration hors-conteneur](#/6)**
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## Principes

*   Tester toute la stack Spring MVC
    *   validation, (dé)sérialisation, codes réponse, etc.
*   Sans utiliser un conteneur web   
    *   Pas de requête HTTP



## Pourquoi ?

*   Tester toute la couche web   
    *   Cas de base et cas limites
*   Utiliser des mock pour les services métiers   
    *   Plus facile pour simuler tous les cas



## Configuration du test

*   Ajouter `@WebAppConfiguration`
*   Injecter le contexte Spring

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ContactControllerTest {

  @Autowired
  WebApplicationContext ctx;

  ...
}
```



## Configuration dans le test

*   Classe interne statique, détectée automatiquement

```java
public class ContactControllerTest {

  ...

  @Configuration
  @EnableWebMvc
  @ComponentScan(basePackageClasses = ContactController.class)
  public static class TestConfiguration {

    @Bean
    public ContactRepository contactRepository() {
      return mock(ContactRepository.class);
    }

  }

}
```



## Initialisation de `MockMvc`

```java
public class ContactControllerTest {

  @Autowired
  WebApplicationContext ctx; // le contexte Spring

  @Autowired ContactRepository repo; // une dépendance mockée

  MockMvc mockMvc; // Spring MVC, version mock

  @Before public void setUp() {
    this.mockMvc = webAppContextSetup(ctx) // méthode statique
      .build();                            // Spring MVC test
    reset(repo);                           // méthode statique Mockito
  }
  ...
}
```



## FLuent API, le prix à payer

```java
import static o.m.Mockito.*;
import static o.s.test.web.servlet.request.MockMvcRequestBuilders.*;
import static o.s.test.web.servlet.result.MockMvcResultMatchers.*;
import static o.s.test.web.servlet.setup.MockMvcBuilders.*;
import static o.s.test.web.servlet.result.MockMvcResultHandlers.*;
```



## Configuration du test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration @WebAppConfiguration
public class ContactControllerTest {
  @Autowired WebApplicationContext ctx;
  @Autowired ContactRepository repo;
  MockMvc mockMvc;

  @Before public void setUp() {
    this.mockMvc = webAppContextSetup(ctx).build(); reset(repo);
  }
  ...

  @Configuration
  @EnableWebMvc
  @ComponentScan(basePackageClasses = ContactController.class)
  public static class TestConfiguration {

    @Bean
    public ContactRepository contactRepository() {
      return mock(ContactRepository.class);
    }

  }
}
```



## Méthode à tester

```java
@RestController
public class ContactController {

  @Autowired ContactRepository contactRepository;

  @RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
  public Contact contact(@PathVariable Long id) {
    Contact contact = contactRepository.findOne(id);
    if(contact == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return contact;
  }

}
```



## Lancer une requête

```java
@Test
public void contactExists() throws Exception {
  Long id = 1L;
  when(repo.findOne(id)).thenReturn(new Contact(id,"John","Doe",33));
  mockMvc.perform(get("/contacts/{id}", id))
}
```



## Voir ce qui se passe

```java
mockMvc.perform(get("/contacts/{id}", id)).andDo(print());
```

```console
...
MockHttpServletResponse:
          Status = 200
  Error message = null
        Headers = {Content-Type=[application/json;charset=UTF-8]}
   Content type = application/json;charset=UTF-8
           Body = {"id":1,"firstname":"John","lastname":"Doe","age":33}
  Forwarded URL = null
 Redirected URL = null
        Cookies = []
...
```



## Tester un GET

```java
@Test
public void contactExists() throws Exception {
  Long id = 1L;
  when(repo.findOne(id)).thenReturn(new Contact(id,"John","Doe",33));
  mockMvc.perform(get("/contacts/{id}", id))
    .andExpect(status().isOk())
    .andExpect(jsonPath("id").value(1))
    .andExpect(jsonPath("firstname").value("John"))
    .andExpect(jsonPath("lastname").value("Doe"))
    .andExpect(jsonPath("age").value(33));
}
```



## Si la ressource n'existe pas

```java
@RestController
public class ContactController {

  @Autowired ContactRepository contactRepository;

  @RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
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
}
```



## Tester un GET qui retourne 404

```java
@Test
public void contactDoesNotExists() throws Exception {
  Long id = 1L;
  when(repo.findOne(1L)).thenReturn(null);
  mockMvc.perform(get("/contacts/{id}", id))
         .andExpect(status().isNotFound());
}
```



## Un GET qui retourne un tableau

```json
[
  {"id":1,"firstname":"John","lastname":"Doe","age":33},
  {"id":2,"firstname":"Jane","lastname":"Doe","age":30}
]
```

```java
@Test public void contacts() throws Exception {
  when(repo.findAll()).thenReturn(Arrays.asList(
    new Contact(1L,"John","Doe",33),
    new Contact(2L,"Jane","Doe",30)
  ));
  mockMvc.perform(get("/contacts"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].id").value(1))
    .andExpect(jsonPath("$[0].firstname").value("John"))
    .andExpect(jsonPath("$[0].lastname").value("Doe"))
    .andExpect(jsonPath("$[1].id").value(2))
    .andExpect(jsonPath("$[1].firstname").value("Jane"))
    .andExpect(jsonPath("$[1].lastname").value("Doe"));
}
```



## Une méthode POST avec un corps

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



## Tester une méthode POST avec un corps

```java
@Test public void create() throws Exception {
  Contact toBeCreated = new Contact(1L,"John","Doe",33);
  when(repo.save(any(Contact.class))).thenReturn(toBeCreated);
  mockMvc.perform(
    post("/contacts")
      .content(
        "{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}"
      )
      .contentType(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(header().string(
       "Location","http://localhost/contacts/1")
    );

    (...)
}
```



## Tester une méthode POST avec un corps (suite)

```java
@Test public void create() throws Exception {
  Contact toBeCreated = new Contact(1L,"John","Doe",33);

  (...)
  // JSON => Contact OK ?
  ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(
    Contact.class
  );
  
  verify(repo).save(contactCaptor.capture());
  Contact captured = contactCaptor.getValue();
  Assert.assertEquals(toBeCreated.getFirstname(),
    captured.getFirstname());
  Assert.assertEquals(toBeCreated.getLastname(),
    captured.getLastname());
  Assert.assertEquals(toBeCreated.getAge(),
    captured.getAge());
}
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
