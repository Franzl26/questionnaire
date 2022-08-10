Readme zu mini "Karteikarten-Programm"

Questionaire Fenster:
Shortcuts sind in eckigen Klammern angegeben.

	Open: 				gespeicherte Fragen-Datei öffnen
	Select Question:		öffnet Fenster mit einer Liste aller Fragen.
					Anlicken um zur Frage zu springen.
	Edit Questions:		Öffnet Fenster um Fragen zu löschen/hinzuzufügen
	Set default File Path:	öffnet File-Selector um auszuwählen, welche Fragendatei beim Start automatisch geladen wird
	Help/Readme:			öffnet dieses Readme
	show Solution:		Toggle Button, gibt an ob beim Anzeigen einer neuen Frage auch direkt die Antwort aufgedeckt werden soll
	Solve [S]:			Zeigt die Anwtwort zur aktiven Frage
	Next [N]/Previous [P]:	Zeigt nächste/vorherige Frage
	random Question [R]:		wählt eine zufällige Frage aus (erst nachdem alle Fragen durch sind wiederholen sie sich)
	Forward [F]:			abhänig von "show Solution" entweder wie "Next"(Y) oder abwechselnd neue Frage anzeigen und danach Antwort aufdecken(N)
					- zur Vereinfachung beim durchgehen (nur ein Knopf statt N und S)

Question Edit Fenster:
Alle Fragen sind Toggle-Buttons und können ausgewählt werden.
	Add from Save:	fügt Fragen aus einer gespeicherten Fragen-Datei dazu
	Add from Text:	fügt Fragen auser einer .txt Datei hinzu
	Save:			Speichert alle Fragen in einer Fragen-Datei
	Delete Selected:	löscht alle ausgewählten Fragen
	Delete All:		löscht alle Fragen
	Add Questions:	öffnet ein Fenster um Fragen mit Antworten hinzuzufügen
	Cancel:		abbrechen, Änderungen werden nicht übernommen
	Confirm Changes:	alle Änderungen werden übernommen und können nicht rückgängig gemacht werden


Zu Beachten für .txt-Dateien zum Umwandeln:
- Datei besteht aus Frage-Antwort-Blöcken
- Block beginnt mit einer Zeile für Frage und danach beliebig viele Antwort Zeilen (ohne Leerzeile dazwischen!!)
- Leerzeile beendet Fragenblock
- Kommentare können mit ## am Anfang der Zeile eingefügt werden