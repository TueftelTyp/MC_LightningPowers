# Lightning Powers 1.0.7

Korrekturversuch fuer Minecraft 26.3: registriert den Blitzableiter-Helm als echte Humanoid-Ruestung mit einem ArmorMaterial, traegt ihn in Kampf sowie Suche ein und verwendet den vorhandenen 3D-Kopf-Renderer weiterhin. Das Rezept enthaelt vier Kupferbloecke.

Mit Java 25 und Gradle `gradle clean build` ausfuehren. Die JAR aus `build/libs` in den mods-Ordner kopieren, dabei alle alten Versionen der Lightning-Powers-JAR entfernen. Minecraft danach neu starten. Der Build konnte in dieser Umgebung nicht ausgefuehrt werden (nur Java 21 vorhanden).

Ist der Helm danach weiterhin unsichtbar, bitte den vollstaendigen neuesten `latest.log` mitsenden; der Renderer wird dann anhand der konkreten Fehlermeldung geprueft.

Originalfunktionen und GUI befinden sich weiterhin in `src/main`. Die Datei `src/main/resources/data/lightningpowers/recipe/lightning_helmet.json` darf fuer andere Rezepte angepasst werden.
