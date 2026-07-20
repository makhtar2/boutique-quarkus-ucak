# Guide de Démarrage Quarkus & MySQL (Boutique UCAK)

Bienvenue dans ce guide conçu pour vous guider pas à pas dans l'apprentissage de **Quarkus** et la configuration de la base de données **MySQL** pour votre projet [boutique-quarkus](.).

Voici le plan d'apprentissage structuré étape par étape sous forme de guides markdown interactifs. Cliquez sur chaque lien pour accéder à l'étape correspondante :

---

## 🗺️ Plan d'Apprentissage Étape par Étape

### 🧰 [Installation : de la base de données au lancement en mode dev](./docs/installation_base_de_donnees.md)
*   À faire en premier si vous clonez/forkez ce projet pour la première fois.
*   Créer la base MySQL (Docker ou installation locale) et lancer `./mvnw quarkus:dev`.
*   Dépannage des erreurs de connexion les plus courantes.

### 🚀 [Étape 1 : Introduction à Quarkus](./docs/etape1_introduction_quarkus.md)
*   Qu'est-ce que Quarkus et pourquoi est-il si rapide (Supersonic Subatomic Java) ?
*   Structure générale du projet [boutique-quarkus](.).
*   Fonctionnement du Hot Reload (Live Reload).

### ⚙️ [Étape 2 : Configuration de MySQL et Hibernate](./docs/etape2_configuration_mysql.md)
*   Explication des dépendances JDBC MySQL et Hibernate ORM.
*   Configuration détaillée du fichier [application.properties](./src/main/resources/application.properties) pour la base de données `boutiqueucak`.
*   Fonctionnalité "Dev Services" de Quarkus avec Docker.

### 💾 [Étape 3 : Création d'Entités JPA](./docs/etape3_creation_entites.md)
*   Qu'est-ce qu'une entité JPA/Hibernate.
*   Analyse détaillée de la classe [Produit.java](./src/main/java/sn/edu/ucak/dar/Produit.java).
*   Les annotations indispensables (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`).

### 🌐 [Étape 4 : Création d'une API REST](./docs/etape4_api_rest.md)
*   Mise en place de routes API REST avec Jakarta REST (JAX-RS).
*   Injection de l'EntityManager avec `@Inject`.
*   Gestion des transactions pour l'écriture avec `@Transactional`.
*   Analyse détaillée de la ressource [ProduitResource.java](./src/main/java/sn/edu/ucak/dar/ProduitResource.java).

### 🧪 [Étape 5 : Lancement et Tests](./docs/etape5_lancement_dev_mode.md)
*   Démarrage de l'application en mode développement (`./mvnw quarkus:dev`).
*   Requêtes de test concrètes (GET, POST, PUT, DELETE) avec `curl`.
*   Exploration de la Dev UI Quarkus.
*   Compilation pour la production et compilation native.

### ⛓️ [Étape 6 : Entités JPA Avancées et Relations complexes](./docs/etape6_entites_avancees.md)
*   Intégration de Lombok dans le compilateur Quarkus.
*   Relations One-to-One, One-to-Many et Many-to-One entre vos 7 entités (`Client`, `Carte`, `Marque`, `Produit`, `Stock`, `Facture`, `LigneArticle`).
*   Mise à jour du script d'import SQL avec contraintes d'intégrité référentielle.

### 🌐 [Étape 7 : Implémentation des Contrôleurs REST CRUD](./docs/etape7_autres_ressources.md)
*   Création des ressources d'API REST pour gérer les clients, marques, stocks et factures.
*   Intégration des contraintes et relations (liaison automatique des clients/produits lors d'une facture, génération de carte de fidélité).
*   Visualisation et tests dans le Swagger UI interactif.

---

## 🛠️ Résumé des modifications apportées au projet
1.  **Configuration MySQL** : Écrite dans [application.properties](./src/main/resources/application.properties).
2.  **Entité Produit** : Créée dans [Produit.java](./src/main/java/sn/edu/ucak/dar/Produit.java).
3.  **Contrôleur REST** : Créé dans [ProduitResource.java](./src/main/java/sn/edu/ucak/dar/ProduitResource.java).
4.  **Initialisation de la base** : Mise à jour dans [import.sql](./src/main/resources/import.sql).

N'hésitez pas à poser des questions à chaque étape si des notions restent floues. Bonne découverte de Quarkus !
