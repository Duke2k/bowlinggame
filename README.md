**Punkteberechnung Bowling-Game**
Die Anwendung ist ein SpringBoot-Container mit REST-Endpunkt.
Aufruf im Browser:
    http://localhost:8080/totalScore?rawScore=5,5,10,-,10,-,5,5,8,0,8,0,7,2,7,2,10,-,9,1,5
Es werden also mindestens 20, höchstens 21 Werte (für den Bonuswurf bei Strike oder Spare am Ende) erwartet.
Der Rückgabewert ist dann die Errechnete Punktzahl, im obigen Beispiel also 162.
Das perfekte Spiel, also nur Strikes, liefert 300 Punkte.
Ein Bindestrich signalisiert, dass der 2. Wurf eines Frames nach einem Strike nicht geworfen wurde.