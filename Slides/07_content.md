# Workshop Spring MVC<br><br><i class="fa fa-files-o" aria-hidden="true"></i>Négociation de contenu

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   **[Négociation de contenu](#/7)**
*   [HATEOAS](#/8)



## Représentation

*   En REST, aucun format n’est imposé
    *   En SOAP, XML est imposé
*   Le client et le serveur négocient le format   
*   Tout se passe avec deux entêtes
    *   Accept : dans la requête, ce que le client comprend
    *   Content-Type : dans la réponse, ce que le serveur renvoie



## `Accept` et `Content-Type`

*   Requête

```console
GET /contacts/1 HTTP/1.1
Accept: application/xml, text/xml, application/*+xml, application/json
Host: localhost:8080
```

*   Réponse

```console
HTTP/1.1 200 OK
Content-Type: application/xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<contact>
  <age>37</age>
  <firstname>Joe</firstname>
  <id>1</id>
  <lastname>Dalton</lastname>
</contact>
```



## Dans Spring MVC

*   Spring MVC gère de façon transverse
    *   La négociation de contenu
    *   La désérialisation/sérialisation de la requête/réponse
*   Conséquences pour le dévelopeur :
    *   Un même contrôleur peut retourner plusieurs types de contenu
    *   Travail sur des objets de domaine



## Désérialisation / sérialisation

```java
// désérialiser le corps de la requête pour obtenir un Contact
@RequestMapping(value="/contacts", method=RequestMethod.POST)
@ResponseStatus(HttpStatus.CREATED)
public void create(@RequestBody Contact contact,
                   HttpServletRequest request,
                   HttpServletResponse response) {
  (...)
}

// sérialiser le Contact retourné
// puis le mettre dans le corps de la réponse
@RequestMapping(value="/contacts/{id}",method=RequestMethod.GET)
public Contact contact(@PathVariable Long id) {
  return contactRepository.findOne(id);
}
```



## Désérialisation / sérialisation

*   idem avec le `RestTemplate`

```java
// désérialiser le corps de la réponse pour obtenir un Contact
Contact contact = tpl.getForObject(
  "http://localhost:8080/contacts/1",
  Contact.class,
  id
);

// sérialiser le Contact à créer
// puis le mettre dans le corps de la requête
Contact contact = new Contact();
URI location = tpl.postForLocation(
  "http://localhost:8080/contacts",
  contact
);
```



## Désérialisation / sérialisation, qui ?

*   Des HttpMessageConverters
*   Autour du contrôleur (dans le HandlerAdapter)   
*   Dans le RestTemplate



## `HttpMessageConverter`

```java
public interface HttpMessageConverter<T> {

  boolean canRead(Class<?> clazz, MediaType mediaType);

  boolean canWrite(Class<?> clazz, MediaType mediaType);

  List<MediaType> getSupportedMediaTypes();

  // désérialisation (représentation vers objet)
  T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException;

  // sérialisation (objet vers représentation)
  void write(T t, MediaType contentType,
             HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException;

}
```



## `HttpMessageConverter` dans Spring MVC

*   Spring MVC connait
    *   Le format (`Accept` ou `Content-Type`)
    *   Le type Java attendu (en paramètre ou en retour)
*   Spring MVC consulte ses `HttpMessageConverters`   
*   Il utilise celui qui peut faire la conversion



## `HttpMessageConverter` disponibles

*   JAXB2 (XML), Jackson (JSON), Atom, RSS, Spring OXM (XML)
*   Formulaire HTML, byte[], etc.
*   Automatiquement enregistrés (si librairie tierce présente)



## Déclarer un `HttpMessageConverter` côté serveur

```java
@Configuration
public static class ContentNegociationConfiguration
              extends WebMvcConfigurerAdapter {

  @Override
  public void configureMessageConverters(
                    List<HttpMessageConverter<?>> converters) {
    converters.add(new CsvHttpMessageConverter());
  }

}
```



## Déclarer un `HttpMessageConverter` côté client

```java
RestTemplate tpl = new RestTemplate();
List<HttpMessageConverter<?>> convs =
  new ArrayList<HttpMessageConverter<?>>();
convs.add(new MappingJacksonHttpMessageConverter());
tpl.setMessageConverters(convs);
```



## Intercepteurs

*   Spring MVC propose des intercepteurs, cotés client et serveur   
*   Pratique pour des traitements transverses ou systématiques



## Intercepteur côté serveur

```java
public interface HandlerInterceptor {

  boolean preHandle(HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler) throws Exception;

  void postHandle(HttpServletRequest request,
                  HttpServletResponse response,
                  Object handler, ModelAndView modelAndView)
             throws Exception;

  void afterCompletion(HttpServletRequest request,
                       HttpServletResponse response,
                       Object handler, Exception ex)
                            throws Exception;
}
```



## Déclarer un intercepteur côté serveur

```java
@Configuration
public static class ContentNegociationConfiguration
              extends WebMvcConfigurerAdapter {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogHandlerInterceptor());
  }

}
```



## Intercepteur côté client

```java
public interface ClientHttpRequestInterceptor {
  ClientHttpResponse intercept(HttpRequest request, byte[] body,
                               ClientHttpRequestExecution execution)
                        throws IOException;
}
```



## Déclarer un intercepteur coté client

```java
RestTemplate tpl = new RestTemplate();
List<ClientHttpRequestInterceptor> interceptors =
  new ArrayList<ClientHttpRequestInterceptor>();
interceptors.add(new LogClientHttpRequestInterceptor());
tpl.setInterceptors(interceptors);
```



<!-- .slide: class="page-questions" -->



## <i class="fa fa-pencil-square-o" aria-hidden="true"></i> TP : spring-mvc-content-negotiation-start
