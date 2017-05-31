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



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : out-of-container-test-start
