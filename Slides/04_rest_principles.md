# Workshop Spring MVC<br><br><i class="fa fa-comments" aria-hidden="true"></i>Principes de REST

<!-- .slide: class="page-title" -->



## Sommaire

<!-- .slide: class="toc" -->

*   [Presentation](#/1)
*   [Architecture](#/2)
*   [Application Web](#/3)
*   **[Principes de REST](#/4)**
*   [REST](#/5)
*   [Tests d'intégration hors-conteneur](#/6)
*   [Négociation de contenu](#/7)
*   [HATEOAS](#/8)



## REST

*   Representational State Transfer
*   Un style d’architecture
*   Une façon de faire communiquer des applications
*   Utiliser HTTP comme un protocole applicatif
    *   Pas seulement comme un protocole de transport



## Principes

*   Ressources identifiées   
*   Interface uniforme
*   Sans état
*   Représentation
*   Hypermedia



## Ressources identifiées

*   Tout est ressource, chaque ressource a une addresse   
*   L’adresse est une URI
*   <i class="fa fa-thumbs-o-up" aria-hidden="true"></i> : `http://somehost.com/zen/contacts/1`
*   <i class="fa fa-thumbs-o-down" aria-hidden="true"></i> : `http://somehost.com/zen/contacts?id=1`
    *   L’identifiant fait partie de l’adresse
    *   Il doit être dans l’URL, pas en tant que paramètre



## Interface uniforme

*   Le client effectue des opérations sur une ressource   
*   Opérations disponibles :

| Méthode | Description |
|---------|--------|
| GET | Récupération d'une ressource |
| POST | Créer une ressource |
| PUT | Modifier une ressource |
| DELETE | Supprimer une ressource |
| HEAD | GET, mais sans le contenu, juste les entêtes |
| OPTIONS | Options de communication de la ressource |



## Interface uniforme

*   Entêtes standardisés
    *   type de la requête, type attendu, taille de la réponse, etc.
*   Codes réponse standardisés
    *   200 OK
    *   201 Created
    *   404 Not found
    *   409 Conflict
    *   500 Internal server error
    *   etc.



## Sans état

*   Aucun lien entre deux requêtes...
*   Même si envoyées par le même client
*   Facilite la distribution à grande échelle (“scalability”)   
*   Si état il y a, il est stocké dans une base de données
*   Plus de session !
    *   En théorie...
    *   On peut choisir de ne pas suivre ce principe



## Représentation

*   Pas de format imposé pour représenter les ressources   
*   Formats courants :
    *   XML
    *   JSON
    *   ATOM
*   Possibilité d’utiliser des schémas



## Hypermedia

*   Les ressources ont des liens vers d’autres ressources
*   Exactement comme des pages web
*   Un client peut suivre les liens d’une ressource à l’autre
    *   Un client intelligent...
*   Ex. : lien pour avancer dans un workflow ou l’annuler
*   Généralement, entente entre client et fournisseur du service



<!-- .slide: class="page-questions" -->
