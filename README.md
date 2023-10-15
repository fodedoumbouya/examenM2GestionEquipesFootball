# Devoir de l'exam :blush:

## :bellhop_bell::bellhop_bell: Getting Started :bellhop_bell::bellhop_bell:

1. :hammer_and_wrench:Environnement de développement :hammer_and_wrench:	
    - Java (stable, 11)
    - JDK (zulu, 1.8)
    - macOS (M1, 12.5)

2. Exécutez la commande suivante sur le terminal pour cloner le projet .

```bash
git clone https://github.com/fodedoumbouya/examenM2GestionEquipesFootball.git
```

3. Séquence d'excusion des services 
	- 1. Manager 		:one:
	- 2. TeamService	:two:
	- 3. PlayerService	:three:
	- 4. MatchService	:four:
	- 5. StatsService	:five:

## :pushpin::pushpin: Projets Ports :pushpin::pushpin:
Port | Default  | Description
--- | --- | --- |
Manager | ``` 8761 ``` | Default Port du Service Manager 
TeamService | ```8011 ``` | Default Port du Service Team
PlayerService | ```8012``` | Default Port du Service Player
MatchService | ```8013``` | Default Port du Service Match
StatsService | ```8014``` | Default Port du Service Stats 


## :flashlight::flashlight: Projets Paths :flashlight::flashlight:

Url | Service  | Description
--- | --- | --- |
http://localhost:8761/ | Manager | Interface pour Deskboard  
http://localhost:8761/admin/ | Manager | Interface pour Deskboard de l'admin  
http://localhost:8011/swagger-ui.html | TeamService | Interface API du Service Team 
http://localhost:8012/swagger-ui.html | PlayerService | Interface API du Service Player 
http://localhost:8013/swagger-ui.html | MatchService | Interface API du Service Match
http://localhost:8014/swagger-ui.html | StatsService | Interface API du Service Stats 
```/actuator``` | TeamService/PlayerService/MatchService/StatsService | Interface pour Actuator
```/prometheus``` | TeamService/PlayerService/MatchService/StatsService | Interface pour Prometheus

## :stethoscope::stethoscope: Status Verification :stethoscope::stethoscope:

Si vous aviez suivi exactement les etapes precendents alors vous pouviez voir ses services sur ```http://localhost:8761/``` et ```http://localhost:8761/admin/```

![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163081123027497032/deskboard.png?ex=653e46b4&is=652bd1b4&hm=2dc819fede7e850c01f21bca838b2506d793e71f56d607d2fd1a9efd4e108a05&?raw=true "Deskboard View ")
![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163081122540949504/deskboardAdmin.png?ex=653e46b4&is=652bd1b4&hm=df38900f145167c37ef1b87d74bc3e062419c4391768f642d9b6cb1c3dd44cf0&?raw=true "Admin View ")

Actuator ```/actuator```
![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163120868373844018/image.png?ex=653e6bb8&is=652bf6b8&hm=4244bb7ac14d06a2ad0fac4056d7dc5945bcec18d57b69bc3bac0922a42f6de3&?raw=true "actuator View ")


Actuator ```/prometheus```
![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163120957901250570/image.png?ex=653e6bce&is=652bf6ce&hm=640979ec2192423cb9856857e03d88d0a16e3c981f38f1196b1dd56b9809353b&?raw=true "prometheus View ")

## :open_file_folder::open_file_folder: Projects Details :open_file_folder::open_file_folder:

### :round_pushpin: Project TeamService :round_pushpin:
#### :bangbang: Je donne un exemple detaillé d'un project sur les 5 mais les informations de tous ses Projets sont disponible sur ```/swagger-ui.html``` 
Les endpoints du projet :mag_right:

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/teams/{teamId}`                             | Renvoie les détails d'une équipe par son identifiant.|
| `GET`    | `/teams/teamsExist/{teamId}`| Renvoie existant d'une equipe.   
| `POST`   | `/teams`|Permet d'ajouter une nouvelle équipe|
| `PUT`    | `/teams/{teamId}`|Met à jour les informations d'une équipe existante.|
| `DELETE` | `/teams/{teamId}`| Supprime une équipe par son identifiant |

### :sound::sound: Response :sound::sound:
`GET`    | `/teams/1`    
``` js
{
    "id": 1,
    "name": "Team A",
    "players": [
        {
            "id": 1,
            "name": "player A",
            "playerTeam": {
                "id": 1,
                "teamName": "Team A"
            }
        },
        {
            "id": 2,
            "name": "player B",
            "playerTeam": {
                "id": 1,
                "teamName": "Team A"
            }
        }
    ]
}
 ```
`GET`    | `/teams/teamsExist/1`    

``` js 
{
    "data": "Team A",
    "msg": "Team  exist",
    "valid": true
}

```

`POST`| `/teams` \
Body
``` 
{
    "id": 8,
    "name": "Team A",
    "players": [
        {
            "id": 1,
            "name": "Player 1",
            "playerTeam": {
                "id": 1,
                "teamName": "Team A"
            }
        }
    ]
}
```
Response \
``` Success ```

`DELETE` | `/teams/1` 

Response \
``` Success ```

### :x::x: Erreur :x::x:
Les erreur possible dans les request \
`GET`    | `/teams/10`    
Si l'id du team n'existe pas
```js 
{
    "id": -1,
    "name": "N/A",
    "players": []
}
```

`POST`| `/teams` \
Si le body n'est pas correcte alors vous veriez cet message suivante
```  
Fail Your body should be like
{id = 1, name = name, players:[ id = 1, name = name, playerTeam: [id=1,teamName=TeamName]} 
```


Si les informations ne pas pas correctes dans le body de la request alors vous receverai les messages suivants

```ID already exist```

```
The Player with Id 1 and team id 1 has wrong team name 
```
```The Player with Id 1 Doesn't exist```

```The Player with Id 1 has a wrong team id = 10```

`PUT`    | `/teams/1`
Si le body n'est pas correcte alors vous veriez cet message suivante
```  
Fail Your body should be like
{id = 1, name = name, players:[ id = 1, name = name, playerTeam: []} 
```

Si les informations ne pas pas correctes dans le body de la request alors vous receverai les messages suivants

```ID already exist```

```The url ID doesn't match with the body ID```

:bangbang: :bangbang: Si l'un des Service fait appel à un autre mais cette derrière est eteinte alors vous receverai un message qui details le problème :bangbang: :bangbang:

Par example le Service Team appel le Service Player mais PlayerService n'est pas disponible alors on vera un message comme ça 
![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163095140320153691/image.png?ex=653e53c2&is=652bdec2&hm=3b89e7ca4f11246013e7641a416fdb616e504eca87256d90a9a9ffe5c10a7543&&hm=df38900f145167c37ef1b87d74bc3e062419c4391768f642d9b6cb1c3dd44cf0&?raw=true "Service Down !!")

### :test_tube::test_tube: Testé l'API :test_tube ::test_tube:
vous pouvez utiliser le fichier ```request.json``` dans postman directement en l'important and la structure sera:

![Alt text](https://cdn.discordapp.com/attachments/1163081078156820481/1163125973814882448/image.png?ex=653e7079&is=652bfb79&hm=ebeb0b585c0f1fee7ba70048983fc0f742965e15d49e2eecf591e3ef6fefee7a&?raw=true "Request")

## MERCI :blush: