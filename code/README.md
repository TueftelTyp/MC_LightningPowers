# Lightning Powers v1.0.8 (Minecraft 26.3 / Fabric)

Basis: funktionierender Stand v1.0.7 (Item, Crafting, Creative-Inventar, 3D-Helm, Blitzkraefte, Mod Menu).

## Neu: Schutz vor brennendem Spieler

Mit aktiviertem **Spieler waehrend Aufladung vor Feuer schuetzen** (`preventPlayerFire`: true) wird eine Entzuendung beim Blitzeinschlag sofort geloescht; waehrend der aktiven Aufladung wird der Brandstatus anschliessend auf jedem Server-Tick geloescht. Dies gilt nur fuer Spieler, die den Blitzableiter-Helm tragen und deren Mod-Funktionen aktiviert sind. Bei erneutem Blitz waehrend Cooldown wird der Brandstatus ebenfalls geloescht. Optional ueber Mod Menu > Kampf und Helm oder `config/lightningpowers.json` steuerbar. Bestehende Config-Dateien erhalten durch Gson die neue Standardoption automatisch, wenn sie beim Laden fehlen (der Konstruktor-Standardwert ist true).

**Hinweis:** Blitz-induzierte Feuerbloecke in der Umgebung werden nicht entfernt. Der neue Schalter regelt die Entzuendung des *Spielers*, nicht den Brandschutz der Welt. Das normale Feuer-Resistance-Effektverhalten bleibt einstellbar.

## Bauen

Java 25; `gradle clean build` (oder bestehendes `build.bat`) auf Windows ausfuehren. Alte Mod-JAR aus `mods` entfernen, nur die neue JAR aus `build/libs` kopieren und das Spiel neu starten.

## Test

1. Helm aufsetzen und mit `/summon minecraft:lightning_bolt ~ ~ ~` einen Blitz ausloesen.
2. Aufladung und Lebenspunkte kontrollieren; Charakter sollte nicht brennen.
3. Blitz waehrend aktiver Aufladung wiederholen.
4. Mod Menu > Kampf und Helm > Spieler waehrend Aufladung vor Feuer schuetzen ausschalten und Verhalten vergleichen.
5. Wenn Feuerbloecke neben dem Spieler entstehen, ist das weiterhin Vanilla-Verhalten.

Build konnte hier nicht ausgefuehrt werden, da Java 25/Gradle nicht installiert sind. Die JSON-Dateien sind syntaktisch geprueft.
