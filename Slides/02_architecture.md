# Workshop Spring MVC<br><br><i class="fa fa-wrench" aria-hidden="true"></i>Architecture

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   **[Architecture](#/2)**
*   [Application Web](#/3)
*   [Principes de REST](#/4)
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## Beans d'infrastructure

*   La DispatcherServlet coordonne des beans d’infrastructure   
*   Valeurs par défaut, tout est surchargeable
*   Changer ces beans change le comportement de Spring MVC



## Beans d’infrastructure principaux

*   Ils sont trois
*   HandlerMapping, HandlerAdapter, ViewResolver
*   Ils participent au pattern MVC



## Beans d’infrastructure principaux

```java
// dans la DispatcherServlet (pseudo-code)
// quel contrôleur pour cette requête ?
Object handler = handlerMapping.getHandler(request);
// appeler la bonne méthode du contrôleur, avec les bons paramètres
ModelAndView mav = handlerAdapter.handle(request,response,handler); // trouver la vue à rendre
View view = viewResolver.resolveViewName(
  mav.getViewName(),request.getLocale()
);
// effectuer le rendu
view.render(mav.getModel(),request,response);
```



## Chaîne de beans d’infrastructure

*   Les beans d’infrastructure sont généralement organisés en chaîne
    *   Le cas pour HandlerMapping, HandlerAdapter, ViewResolver
*   Ils sont consultés, le premier qui répond gagne   
    *   Répondre = retourner autre chose que null



## Configuration par défaut

*   DispatcherServlet.properties    
    *   dans spring-webmvc.jar
    *   package org.springframework.web.servlet

```java
org.springframework.web.servlet.LocaleResolver=\
  org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
org.springframework.web.servlet.ThemeResolver=\
  org.springframework.web.servlet.theme.FixedThemeResolver
org.springframework.web.servlet.HandlerMapping=\
  org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
  org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping
org.springframework.web.servlet.HandlerAdapter=\
  org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
  org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
  org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter
(...)
```



## Surcharger un bean d'infrastructure

*   Déclarer un bean du type correspondant
*   Remplace la configuration par défaut
*   Ex. : déclarer un HandlerAdapter remplace les 3 par défaut

```xml
<bean class="o.s.web.servlet.view.InternalResourceViewResolver">
  <property name="prefix" value="/WEB-INF/views" />
  <property name="suffix" value=".jsp" />
</bean>

```



<!-- .slide: class="page-questions" -->
