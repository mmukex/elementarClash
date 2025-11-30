# **ElementarClash** – Rundenbasiertes Elementar-Strategiespiel
*Ein taktischer Kampf zwischen Feuer, Wasser, Erde und Luft mit dynamischem Terrain und Synergien.*

---

## **Konzept**
**ElementarClash** ist ein **rundenbasiertes Strategiespiel**, in dem vier elementare Fraktionen (**Feuer**, **Wasser**, **Erde**, **Luft**) auf einem **10×10-Raster** mit fünf Geländearten (**Lava**, **Eis**, **Wald**, **Wüste**, **Stein**) gegeneinander antreten. Jede Fraktion verfügt über **einzigartige Einheiten, Fähigkeiten und Spielstile**, die durch **elementare Stärken/Schwächen** und **geländebasierte Effekte** strategische Tiefe erzeugen.

**Feuer** (aggressiv) | **Wasser** (defensiv) | **Erde** (kontrollierend) | **Luft** (mobil)

---

## **Spielmechaniken**

| **Feature**                   | **Details**                                                                                                 |
|-------------------------------|-------------------------------------------------------------------------------------------------------------|
| **Dynamisches Terrain**       | Geländearten beeinflussen Bewegung, Angriff und Verteidigung.                                               |                                                             |
| **Einheiten & Fähigkeiten**   | 3 Einheitentypen pro Fraktion mit **aktiven Fähigkeiten** und **passiven Boni**.                            |
| **Synergien & Konter**        | Einheitenkombinationen und elementare Interaktionen (Feuer schmilzt Eis).                                   |
| **Rundenbasierte Logik**      | 3 Aktionen pro Runde: **Bewegen** (geländeabhängig), **Angreifen**, **Fähigkeiten einsetzen**.              |
| **Dynamische Ereignisse**     | Zufällige Ereignisse wie **Geysire**, **Waldbrände** oder **Erdrutsche** können das Schlachtfeld verändern. |
| **Siegbedingungen**           | Alle Gegner müssen besiegt werden                                                                           |
| **Modular & Erweiterbar**     | Neue Fraktionen, Geländearten oder Fähigkeiten können **ohne Änderungen am Core-Code** hinzugefügt werden.  |

---

## **Fraktionen & Einheiten**

Jede Fraktion verfügt über **3 einzigartige Einheitentypen** mit unterschiedlichen Rollen und Statistiken:

### **Feuer-Fraktion** (Aggressiv)
| Einheit               | LP  | Angriff | Verteidigung | Bewegung | Reichweite | Spezial                                               |
|-----------------------|-----|---------|--------------|----------|------------|-------------------------------------------------------|
| **Inferno-Krieger**   | 100 | 15      | 5            | 3        | 1          | +2 Angriff auf Lava-Gelände                           |
| **Flammen-Bogenschütze** | 70  | 12      | 3            | 4        | 3          | Ignoriert Wald-Verteidigungsbonus                     |
| **Phönix**            | 80  | 10      | 4            | 5        | 1          | Fliegend (ignoriert Gelände), Wiederbelebung 1× (50% LP) |

**Passiver Fraktions-Bonus:** Feuer-Einheiten verursachen +25% Schaden gegen Erde, -25% gegen Wasser. Benachbarte Feuer-Einheiten gewähren einander +1 Angriff.

### **Wasser-Fraktion** (Defensiv)
| Einheit               | LP  | Angriff | Verteidigung | Bewegung | Reichweite | Spezial                                               |
|-----------------------|-----|---------|--------------|----------|------------|-------------------------------------------------------|
| **Gezeiten-Wächter**  | 120 | 10      | 8            | 2        | 1          | +3 Verteidigung auf Eis-Gelände                       |
| **Frost-Magier**      | 60  | 13      | 4            | 3        | 4          | Angriffe verlangsamen Gegner (-1 Bewegung für 2 Runden) |
| **Wellen-Reiter**     | 90  | 11      | 6            | 4        | 1          | Kann durch Wasser/Eis mit Kosten 1 ziehen             |

**Passiver Fraktions-Bonus:** Wasser-Einheiten verursachen +25% Schaden gegen Feuer, -25% gegen Erde. Heilt 5 LP pro Runde auf Eis-Gelände.

### **Erde-Fraktion** (Kontrollierend)
| Einheit               | LP  | Angriff | Verteidigung | Bewegung | Reichweite | Spezial                                           |
|-----------------------|-----|---------|--------------|----------|------------|---------------------------------------------------|
| **Stein-Golem**       | 140 | 8       | 10           | 2        | 1          | Unbeweglich (kann nicht verschoben werden)        |
| **Terra-Schamane**    | 70  | 9       | 5            | 3        | 3          | Kann Stein-Gelände erschaffen (1× pro 3 Runden)  |
| **Erdbeben-Titan**    | 110 | 14      | 7            | 2        | 1          | Angriffe treffen auch benachbarte Gegner          |

**Passiver Fraktions-Bonus:** Erde-Einheiten verursachen +25% Schaden gegen Wasser, -25% gegen Luft. +2 Verteidigung auf Stein-Gelände.

### **Luft-Fraktion** (Mobil)
| Einheit               | LP | Angriff | Verteidigung | Bewegung | Reichweite | Spezial                                             |
|-----------------------|----|---------|--------------|----------|------------|-----------------------------------------------------|
| **Wind-Tänzer**       | 65 | 13      | 3            | 6        | Nahkampf   | Fliegend, kann ohne Gelegenheitsangriffe ausweichen |
| **Sturm-Rufer**       | 75 | 14      | 4            | 5        | 4          | Angriffe stoßen Gegner 1 Feld zurück                |
| **Himmels-Wächter**   | 85 | 11      | 5            | 5        | 2          | Fliegt, gewährt Sicht   |

**Passiver Fraktions-Bonus:** Luft-Einheiten verursachen +25% Schaden gegen Erde, -25% gegen Feuer. Alle Einheiten haben Fliegend (ignorieren Gelände-Bewegungsstrafen).

---

## **Geländearten**

Das 10×10-Schlachtfeld enthält **5 Geländearten**, die jeweils Bewegung, Kampf und Strategie beeinflussen:

| Gelände   | Bewegungskosten                    | Verteidigungsbonus | Fraktions-Effekte                                                                    | Ereignis-Interaktionen                      |
|-----------|------------------------------------|--------------------|-------------------------------------------------------------------------------------|---------------------------------------------|
| **Lava**  | Normal: 2<br>Feuer: 1<br>Wasser: 3 | 0                  | **Feuer:** +2 Angriff<br>**Wasser:** -5 LP/Runde                                     | Wird durch Waldbrand-Ereignis erschaffen    |
| **Eis**   | Normal: 3<br>Wasser: 1<br>Feuer: 2 | +1 Verteidigung    | **Wasser:** +2 Verteidigung, Heilt 5 LP/Runde<br>**Feuer:** Schmilzt zu Wüste nach Bewegung | Wird durch Geysir-Ereignis erschaffen       |
| **Wald**  | Normal: 2<br>Luft: 1 (fliegend)    | +2 Verteidigung    | Blockiert Fernkampfangriffe (Sichtlinie)<br>Feuerangriffe können entzünden (→ Lava) | Waldbrand-Ereignis breitet sich hier aus    |
| **Wüste** | Normal: 1                          | 0                  | Neutrales Gelände, keine Boni                                                        | Standard-Gelände, entsteht durch schmelzendes Eis |
| **Stein** | Normal: 3<br>Erde: 2               | +3 Verteidigung    | **Erde:** +2 Verteidigung<br>Unpassierbar für nicht-fliegende Einheiten wenn gestapelt | Kann nicht durch Ereignisse verändert werden |

**Gelände-Verteilung:** Das Schlachtfeld startet mit zufälligem Gelände (30% Wüste, 20% Wald, 20% Stein, 15% Lava, 15% Eis).

---

## **Design-Pattern-Architektur**

ElementarClash implementiert **10 GoF Design Patterns**, um Modularität, Erweiterbarkeit und saubere Architektur sicherzustellen. Jedes Pattern adressiert direkt spezifische Spielmechaniken:

| #  | **Pattern**                 | **Kategorie** | **Anwendungsfall in ElementarClash**                                                                             | **Warum dieses Pattern?**                                                                                                                                                                                                             |
|----|-----------------------------|---------------|------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | **Factory Method**          | Erzeugung     | Erstellung fraktionsspezifischer Einheiten (FeuerKrieger, WasserHeiler, etc.)                                    | Jede der 4 Fraktionen hat 3 einzigartige Einheitentypen. Factory Method kapselt Erstellungslogik und ermöglicht das Hinzufügen neuer Fraktionen ohne Core-Code-Änderungen.                                                               |
| 2  | **Composite**               | Struktur      | Raster-Hierarchie (Battlefield → Zeilen → Zellen) und Einheitengruppen (Armee vs. einzelne Einheit)             | Das 10×10-Raster enthält 100 Zellen. Composite ermöglicht Operationen sowohl auf einzelnen Zellen als auch auf ganzen Regionen (z.B. "applyForestFire()"). Einheitengruppen ermöglichen Synergien wie "+1 Angriff für benachbarte Feuer-Einheiten". |
| 3  | **Decorator**               | Struktur      | Stapeln temporärer Buffs/Debuffs auf Einheiten                                                                  | Einheiten erhalten dynamische Boni: Gelände-Bonus (Feuer auf Lava: +2 Angriff), Synergien (benachbarte Feuer-Einheiten: +1 Angriff), Fähigkeits-Buffs (Feuersturm: +3 Angriff für 2 Runden). Decorator ermöglicht Stapeln ohne Änderung der Unit-Klasse. |
| 4  | **Strategy**                | Verhalten     | Bewegungsstrategien (Boden, Fliegend für Luft-Einheiten) und Angriffsstrategien (Nahkampf, Fernkampf, Fläche)   | Jede Fraktion hat einzigartige Spielstile: Luft ist "mobil" (andere Bewegung), Feuer ist "aggressiv" (andere Angriffe). Strategy kapselt diese Verhaltensweisen als austauschbare Algorithmen.                                            |
| 5  | **State**                   | Verhalten     | Einheiten-Zustände (Idle, Moving, Attacking, Stunned, Dead) und Spielphasen (Setup, PlayerTurn, EventPhase, GameOver) | Einheiten können nur bestimmte Aktionen in bestimmten Zuständen ausführen (kein Angriff wenn betäubt). Das Spiel hat klare Phasen (3 Aktionen → dynamische Ereignisse → nächster Spieler). State Pattern verwaltet Übergänge sauber.     |
| 6  | **Observer**                | Verhalten     | Event-System für UI-Updates, Synergien und dynamische Ereignisse                                                | Wenn eine Einheit stirbt → UI-Updates, Achievements triggern, Synergien neu berechnen. "Dynamische Ereignisse" wie Waldbrände betreffen mehrere Einheiten. Observer entkoppelt Komponenten (Spiellogik ↔ UI ↔ Events).                   |
| 7  | **Command**                 | Verhalten     | Aktions-System (MoveCommand, AttackCommand, UseAbilityCommand)                                                  | "3 Aktionen pro Runde" erfordert ein flexibles Command-System. Ermöglicht Undo/Redo, KI-Integration (KI erstellt Commands), Replay-System und vollständiges Action-Logging.                                                              |
| 8  | **Chain of Responsibility** | Verhalten     | Schadensberechnung-Pipeline (Basisschaden → Elementar-Modifikator → Gelände → Synergien → Verteidigung)         | Schaden wird beeinflusst durch: Einheiten-Angriff, elementare Vorteile (Feuer vs Eis), Gelände-Effekte (Verteidiger auf Stein: +Verteidigung), Synergien, Ziel-Verteidigung. Jeder Handler fügt seine Modifikation in Sequenz hinzu.    |
| 9  | **Template Method**         | Verhalten     | Fähigkeits-Ausführungs-Framework (alle Fähigkeiten prüfen Cooldown → verbrauchen Ressourcen → wenden Effekt an → benachrichtigen) | Alle Fähigkeiten teilen gemeinsame Schritte (Cooldown-Check, Benachrichtigungen), haben aber unterschiedliche Effekte (Feuersturm vs Heilregen). Template Method definiert das Skelett, Subklassen implementieren `applyEffect()`.       |
| 10 | **Visitor**                 | Verhalten     | Gelände-Effekte auf verschiedene Einheitentypen                                                                  | Lava-Gelände hat unterschiedliche Effekte pro Fraktion: Feuer-Einheiten erhalten +2 Angriff, Wasser-Einheiten nehmen Schaden, Erde/Luft haben keinen Bonus. Visitor vermeidet verschachtelte if-Statements und macht neue Geländetypen leicht erweiterbar. |

Diese Patterns ergeben sich aus den Kernmechaniken von ElementarClashs:
- **4 Fraktionen × 3 Einheitentypen** → Factory Method
- **100 Zellen × 5 Geländetypen** → Composite
- **Stapeleffekte** (Gelände + Synergien + Fähigkeiten) → Decorator, Chain of Responsibility
- **Rundenbasierter Ablauf** → State, Command
- **Dynamische Ereignisse** → Observer
- **Fraktionsspezifisches Verhalten** → Strategy, Visitor

---

## **Benutzeroberfläche**

ElementarClash bietet eine **textbasierte Konsolen-Schnittstelle** mit ASCII-Grafiken für maximale Portabilität und Fokus auf Spiellogik.

### **Console View Example**
```
═══════════════════════════════════════════════════════════════
                   ELEMENTARCLASH - Runde 5
═══════════════════════════════════════════════════════════════
Spieler: FEUER (3 Einheiten)               Gegner: WASSER (3 Einheiten)
Verbleibende Aktionen: 2/3                 Gesamt-LP: 270/270
═══════════════════════════════════════════════════════════════

    0   1   2   3   4   5   6   7   8   9
  ┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐
0 │ F1│ 🔥│ 🌵│ 🌲│ 🌲│ ⛰️ │ 🌵│ 🌵│ ❄️ │ W1│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
1 │ 🔥│ F2│ 🌵│ 🌲│ ⛰️ │ ⛰️ │ 🌵│ ❄️ │ ❄️ │ W2│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
2 │ 🌵│ 🔥│ 🌵│ 🌵│ 🌲│ ⛰️ │ 🌵│ 🌵│ ❄️ │ 💧│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
3 │ 🌵│ 🌵│ 🌵│ ⛰️ │ 🌲│ 🌲│ 🌵│ 🌵│ 🌵│ 💧│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
4 │ 🌲│ 🌲│ ⛰️ │ ⛰️ │ 🌵│ 🌵│ ⛰️ │ 🌵│ 🌵│ 🌵│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
5 │ 🌲│ 🌲│ ⛰️ │ 🌵│ 🌵│ 🌵│ ⛰️ │ 🌵│ 🌵│ 🌵│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
6 │ 🌵│ 🌵│ 🌵│ 🌵│ 🌵│ ⛰️ │ 🌲│ 🌲│ 🌵│ 🌵│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
7 │ 🌵│ 🌵│ ⛰️ │ 🌵│ 🌵│ 🌲│ 🌲│ ⛰️ │ 🌵│ 🌵│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
8 │ 🔥│ F3│ ❄️ │ 🌵│ 🌵│ ⛰️ │ 🌲│ 🌲│ ❄️ │ W3│
  ├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤
9 │ 🔥│ 🔥│ 🌵│ 🌵│ ⛰️ │ ⛰️ │ 🌲│ 🌲│ ❄️ │ 💧│
  └───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘

Legende:
  🔥 Lava  | ❄️  Eis  | 🌲 Wald | 🌵 Wüste | ⛰️  Stein | 💧 Wasser

Einheiten:
  F1 = Inferno-Krieger   (LP: 100/100, ANG: 17, VTD: 5)  [+2 ANG auf Lava]
  F2 = Flammen-Bogenschütze (LP: 70/70, ANG: 12, VTD: 3)  [Reichweite: 3]
  F3 = Phönix            (LP: 100/100, ANG: 10, VTD: 4)  [Fliegend, Wiederbelebung]

  W1 = Gezeiten-Wächter (LP: 120/120, ANG: 10, VTD: 11) [+3 VTD auf Eis]
  W2 = Frost-Magier     (LP: 60/60,   ANG: 13, VTD: 4)  [Reichweite: 4, Verlangsamung]
  W3 = Wellen-Reiter    (LP: 90/90,   ANG: 11, VTD: 6)  [Schnell auf Eis]

Aktionen: [B]ewegen | [A]ngreifen | [F]ähigkeit | [Z]ug beenden | [Q] Beenden
> Einheit auswählen (z.B. F1): _
```

---

## **Technologies**
- **Language:** Java 21
- **Architecture:** Modular, testable, extensible (SOLID principles)
- **Build Tool:** Gradle 8.14 with wrapper
- **Testing:** JUnit 5 (unit tests), 
- **Documentation:** PlantUML (UML diagrams), Javadoc