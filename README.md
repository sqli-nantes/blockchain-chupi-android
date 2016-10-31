# blockchain-xXx-Mobile
Application mobile de décentralisation de la location de véhicule par la blockchain

# scenario
jim veut louer une voiture pour aller de A vers B.
1. Scanner pour trouver une voiture au choix de l'utilisateur:
1.1. via QR code : *apparition d'une fenetre de scan pour QR code*
1.2. via blue touch : 
2. Choix de la voiture
3. Choix de la destination : *apparition d'une carte pour selectionner l'endroit* (désactivé, actuellement : choix préenregistré)
4. Payement de la course 
5. Voyage en cours
6. Validation de la destination
7. Fin du scenario


```
# Architecture fichiers java:

							--------------
							|MainActivity|
							--------------
			|----------------------|----------------------|
	-----------------								----------------
	|LoadingActivity|								|QRScanActivity|
	-----------------								----------------
			|---------------------------------------------|
									|
						---------------------
						|DetectedCarActivity|
						---------------------
									|
					-----------------------------
					|SelectedDestinationActivity|
					-----------------------------
								|
						-----------------
						|SummaryActivity|
						-----------------
								|
						----------------
						|TravelActivity|
						----------------
								|
						-----------------
						|ArrivedActivity|
						-----------------
								|
							-------------
							|EndActivity|
							-------------


