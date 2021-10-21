# API-2021-Protocol-Design

## Protocol objectives
Le but du protocole est de permettre à un client de se connecter à un serveur et d'envoyer des requêtes, notamment des calculs mathématiques
simples du type "a op b", avec a et b des nombres entiers ou décimaux, et op l'opération à effectuer parmi "+, -, *, /, %, ^".

## Overall behavior
Pour réaliser ce client-serveur, on va utiliser le protocole TCP et utiliser l'adresse localhost avec un port attribuer au client (2424) et un deuxième port pour le serveur (2323). On va initialiser la connexion à l'aide de la commande netcat.
Lors de l'initialisation de la connexion, le serveur va envoyer un message expliquant les requêtes qui peuvent être effectuées. Pour terminer la connexion, le client enverra le message "bye" et le serveur mettera fin à la connection.

## Messages
La syntaxe du message pour effectuer un calcul est "a op b", avec a et b des entiers et op une des opérations parmi "+, -, *, /, %, ^". Le serveur répond par "> resultat" ou en cas d'erreur "> [undefined]". Ces messages seront affichés dans la fenêtre netcat.

## Specific elements
Comme décrit precédement, on peut additionner, soustraire, multiplier et diviser deux entiers. Il faut gérer la division par 0.
On a un socket serveur qui va créer un thread pour chaque nouvelle connexion. Lorsque le serveur reçoit les arguments, il vérifie qu'il a le bon nombre d'arguments. Si ce n'est pas le cas, il envoit une erreur. Si le nombre d'arguments est bon, il va transformer les arguments (nombre) en double. On effectue le calcul avec un switch pour le type d'opération et on retourne la valeur. Pour la division, on retourne une erreur si le deuxième arguments nombre et égal à 0. Si l'argument vaut "bye", on termine la connexion.

## Examples
1. Initialisation de la connexion netcat par l'utilisateur

\> Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.

2. Envoi d'une requête valide

10 + 5

\> 15

3. Envoi d'une requête non valide

10 / 0

\> [undefined]

4. Terminaison de la connexion

bye

[connection closed]

