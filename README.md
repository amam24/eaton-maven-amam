# eaton-maven-amam

Descriptif de ce programme:
### écrit par : AMAMERI###

Le programme client représenté par la classe ClientTest envoi régulièrement - toutes les 3 secondes - une donnée numérique au serveur.
Le programme serveur représenté par la classe ServerTest est capable d’écouter plusieurs programmes clients en parallèle pour récupérer les données envoyées. 
Le programme serveur affiche toutes les 3 secondes, dans la console, pour chacun des clients : le nombre de données déjà reçues, les 4 dernières valeurs reçues et la moyenne de l’ensemble des valeurs reçues.

Les deux programmes peuvent etre compilés via un simple « mvn install ».
