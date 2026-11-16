# Lightning Powers v1.0.9 (Minecraft 26.3 / Fabric)

Basis: funktionierender Stand v1.0.7 (Item, Crafting, Creative-Inventar, 3D-Helm, Blitzkraefte, Mod Menu).

## Neu: Schutz vor brennendem Spieler

Mit aktiviertem **Spieler waehrend Aufladung vor Feuer schuetzen** (`preventPlayerFire`: true) wird eine Entzuendung beim Blitzeinschlag sofort geloescht; waehrend der aktiven Aufladung wird der Brandstatus anschliessend auf jedem Server-Tick geloescht. Dies gilt nur fuer Spieler, die den Blitzableiter-Helm tragen und deren Mod-Funktionen aktiviert sind. Bei erneutem Blitz waehrend Cooldown wird der Brandstatus ebenfalls geloescht. Optional ueber Mod Menu > Kampf und Helm oder `config/lightningpowers.json` steuerbar. Bestehende Config-Dateien erhalten durch Gson die neue Standardoption automatisch, wenn sie beim Laden fehlen (der Konstruktor-Standardwert ist true).

**v1.0.9:** Nach einem Treffer werden fuer ca. 0,6 Sekunden (12 Server-Ticks) gewoehnliche Feuerbloecke im 3x3-Bereich um die Fuesse des Helmtraegers entfernt. Das verhindert die erneute Entzuendung durch unmittelbar vom Blitz erzeugtes Feuer. Andere Terrainbloecke und entferntere Feuer werden nicht veraendert. Der bestehende Schalter `preventPlayerFire` steuert diese Funktion mit.

## Bauen

Java 25; `gradle clean build` (oder bestehendes `build.bat`) auf Windows ausfuehren. Alte Mod-JAR aus `mods` entfernen, nur die neue JAR aus `build/libs` kopieren und das Spiel neu starten.

## Test

1. Helm aufsetzen und mit `/summon minecraft:lightning_bolt ~ ~ ~` einen Blitz ausloesen.
2. Aufladung und Lebenspunkte kontrollieren; Charakter sollte nicht brennen.
3. Blitz waehrend aktiver Aufladung wiederholen.
4. Mod Menu > Kampf und Helm > Spieler waehrend Aufladung vor Feuer schuetzen ausschalten und Verhalten vergleichen.
5. Nach dem Blitzeinschlag duerfen im direkten 3x3-Bereich um den Spieler keine Feuerbloecke verbleiben; weiter entfernt ist Feuer weiterhin moeglich.

Build konnte hier nicht ausgefuehrt werden, da Java 25/Gradle nicht installiert sind. Die JSON-Dateien sind syntaktisch geprueft.
