# Installation : de la base de données au lancement en mode dev

Ce guide explique comment préparer votre environnement local pour faire tourner **boutique-quarkus**, étape par étape, jusqu'au `./mvnw quarkus:dev`.

Le projet est configuré pour se connecter à une base MySQL nommée `boutiqueucak`, accessible sur `localhost:3306`, avec l'utilisateur `root` sans mot de passe (voir [application.properties](../src/main/resources/application.properties)). Deux façons de l'obtenir : **Docker (recommandé)** ou **MySQL installé nativement**.

---

## Option A — MySQL via Docker (recommandé)

Prérequis : [Docker](https://docs.docker.com/get-docker/) installé et lancé.

1. Vérifiez qu'aucun autre service n'écoute déjà sur le port 3306 :
   ```shell script
   sudo ss -tlnp | grep 3306
   ```
   Si quelque chose répond, soit vous l'arrêtez, soit vous adaptez le port dans les commandes ci-dessous et dans `application.properties`.

2. Démarrez un conteneur MySQL dédié au projet, avec mot de passe root vide (comme attendu par la config) :
   ```shell script
   docker run -d \
     --name mysql_boutiqueucak \
     -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
     -e MYSQL_DATABASE=boutiqueucak \
     -p 3306:3306 \
     mysql:8
   ```

3. Attendez quelques secondes que MySQL soit prêt, puis vérifiez :
   ```shell script
   docker logs -f mysql_boutiqueucak
   ```
   (Ctrl+C pour quitter les logs une fois la ligne `ready for connections` affichée.)

4. Pour l'arrêter/relancer plus tard sans le recréer :
   ```shell script
   docker stop mysql_boutiqueucak
   docker start mysql_boutiqueucak
   ```

La base `boutiqueucak` sera de toute façon créée automatiquement au premier démarrage de l'application grâce à `createDatabaseIfNotExist=true` dans l'URL JDBC — l'option `-e MYSQL_DATABASE=boutiqueucak` ci-dessus est juste une garantie supplémentaire.

---

## Option B — MySQL installé directement sur la machine

Si vous préférez ne pas utiliser Docker :

1. Installez MySQL Server (ex. sous Ubuntu/Debian) :
   ```shell script
   sudo apt update
   sudo apt install mysql-server
   sudo systemctl enable --now mysql
   ```

2. Autorisez la connexion `root` sans mot de passe en local (uniquement pour un environnement de développement, jamais en production) :
   ```shell script
   sudo mysql -u root
   ```
   Puis dans le prompt MySQL :
   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';
   FLUSH PRIVILEGES;
   ```

3. Créez la base (optionnel, elle sera créée automatiquement sinon) :
   ```sql
   CREATE DATABASE IF NOT EXISTS boutiqueucak;
   ```

---

## Option C — Sans rien installer : Dev Services de Quarkus

Si vous n'avez ni MySQL ni envie d'en gérer un manuellement, Quarkus peut démarrer et arrêter un MySQL jetable tout seul via Docker, à condition d'avoir Docker installé et lancé.

Il suffit de **commenter** ces 3 lignes dans [application.properties](../src/main/resources/application.properties) :

```properties
#quarkus.datasource.username=root
#quarkus.datasource.password=
#quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/boutiqueucak?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
```

Au prochain `./mvnw quarkus:dev`, Quarkus détecte l'absence d'URL JDBC et lance automatiquement un conteneur MySQL temporaire (les logs affichent `Dev Services for default datasource ... started`). Le conteneur est détruit à l'arrêt de l'application — **aucune donnée n'est conservée d'un lancement à l'autre**.

⚠️ **Ne committez pas ce changement** : les autres membres du groupe utilisent la config habituelle (Option A ou B). Cette option sert uniquement à des tests ponctuels sur votre poste.

---

## Lancer l'application

Une fois la base disponible (Option A, B ou C), à la racine du projet :

```shell script
./mvnw quarkus:dev
```

Vous devriez voir dans les logs :
- La création des tables (`schema-management.strategy=drop-and-create`, donc les tables sont recréées à chaque démarrage).
- L'insertion des données de test définies dans [import.sql](../src/main/resources/import.sql) (marques, produits, stocks, clients avec leur carte de fidélité).
- La ligne finale : `Listening on: http://localhost:8080`.

## Vérifier que tout fonctionne

- Interface graphique : ouvrez **http://localhost:8080/q/swagger-ui/** dans votre navigateur pour tester les routes visuellement.
- En ligne de commande :
  ```shell script
  curl http://localhost:8080/marques
  curl http://localhost:8080/produits
  curl http://localhost:8080/clients
  curl http://localhost:8080/stocks
  ```
  Chaque commande doit renvoyer un tableau JSON avec les données de démo.

## Problèmes courants

| Symptôme | Cause probable | Solution |
|---|---|---|
| `Communications link failure` / `Connection refused` au démarrage | MySQL n'est pas lancé ou pas encore prêt | Vérifiez `docker ps` (Option A) ou `systemctl status mysql` (Option B), attendez quelques secondes et relancez |
| `Access denied for user 'root'@...` | Mot de passe root non vide, ou utilisateur root restreint à `localhost` uniquement | Recréez le conteneur (Option A) ou réappliquez `ALTER USER` (Option B) |
| Port 3306 déjà utilisé | Un autre MySQL tourne déjà sur la machine | `sudo ss -tlnp \| grep 3306` pour l'identifier, puis l'arrêter ou changer de port |
| Les données de test n'apparaissent pas | `import.sql` non exécuté | Vérifiez que `quarkus.hibernate-orm.sql-load-script=import.sql` est bien présent dans `application.properties` |

---

### Lien utile :
*   Retourner à l'index des guides : [README_GUIDE.md](../README_GUIDE.md)
*   Guide de contribution (fork, branches, PR) : [CONTRIBUTING.md](../CONTRIBUTING.md)
