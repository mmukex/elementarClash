# **ElementarClash** – Rundenbasiertes Elementar-Strategiespiel
*Ein taktischer Kampf zwischen Feuer, Wasser, Erde und Luft mit dynamischem Terrain und Synergien.*
---    
## **Konzept**
**ElementarClash** ist ein **rundenbasiertes Strategiespiel**, in dem vier elementare Fraktionen (**Feuer**, **Wasser**, **Erde**, **Luft**) auf einem **10×10-Raster** mit fünf Geländearten (**Lava**, **Eis**, **Wald**, **Wüste**, **Stein**) gegeneinander antreten. Jede Fraktion verfügt über **einzigartige Einheiten, Fähigkeiten und Spielstile**, die durch **elementare Stärken/Schwächen** und **geländebasierte Effekte** strategische Tiefe erzeugen.

**Feuer** (aggressiv) | **Wasser** (defensiv) | **Erde** (kontrollierend) | **Luft** (mobil)
    
---    
## **Spielmechaniken**

| **Feature**                 | **Details**                                                                                                 |
| --------------------------- | ----------------------------------------------------------------------------------------------------------- |
| **Dynamisches Terrain**     | Geländearten beeinflussen Bewegung, Angriff und Verteidigung.                                               |
| **Einheiten & Fähigkeiten** | 3 Einheitentypen pro Fraktion mit **passiven Fähigkeiten** und elementaren Boni.                            |
| **Elementare Interaktionen**      | Terrain-Fraktions-Synergien (Feuer auf Lava: +2 Angriff, Feuer schmilzt Eis zu Wüste).                                   |
| **Rundenbasierte Logik**    | Jede Einheit kann pro Runde: **1× Bewegen** (geländeabhängig), **1× Angreifen**.              |
| **Undo/Redo System**   | Per-Turn Rollback-Fähigkeit für alle Aktionen über Command Pattern. |
| **Siegbedingungen**         | Alle Gegner müssen besiegt werden                                                                           |
| **Modular & Erweiterbar**   | Neue Fraktionen, Geländearten oder Fähigkeiten können **ohne Änderungen am Core-Code** hinzugefügt werden.  |

---    
## **Fraktionen & Einheiten**

Jede Fraktion verfügt über **3 einzigartige Einheitentypen** mit unterschiedlichen Rollen und Statistiken:

### **Feuer-Fraktion** (Aggressiv)

| **Einheit**              | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                                                  |
| ------------------------ | ------ | ----------- | ---------------- | ------------ | -------------- | ------------------------------------------------------------ |
| **Inferno Krieger**      | 100    | 15          | 5                | 3            | 1              | +2 Angriff auf Lava-Gelände                                  |
| **Flammen-Bogenschütze** | 70     | 12          | 3                | 4            | 3              | Ignoriert Wald-Verteidigungsbonus (Fernkampf)                |
| **Phönix**               | 80     | 10          | 4                | 5            | 1              | Fliegend (ignoriert Gelände), Wiederbelebung 1× (50% LP)     |

**Passiver Fraktions-Bonus:** Feuer-Einheiten erhalten +2 Angriff auf Lava-Gelände. Feuer-Einheiten schmelzen Eis-Gelände zu Wüste beim Betreten.
### **Wasser-Fraktion** (Defensiv)

| **Einheit**          | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                                             |
| -------------------- | ------ | ----------- | ---------------- | ------------ | -------------- | ------------------------------------------------------- |
| **Gezeiten-Wächter** | 120    | 10          | 8                | 2            | 1              | +3 Verteidigung auf Eis, +5 LP/Runde Heilung            |
| **Frost-Magier**     | 60     | 13          | 4                | 3            | 4              | Fernkampf-Magier (höchste Reichweite: 4)                |
| **Wellen-Reiter**    | 90     | 11          | 6                | 4            | 1              | Schnelle Bewegung auf Eis (Kosten: 1)                   |

**Passiver Fraktions-Bonus:** Wasser-Einheiten erhalten +3 Verteidigung und +5 LP/Runde Heilung auf Eis-Gelände. Wasser-Einheiten erleiden -5 LP/Runde Schaden auf Lava-Gelände.

### **Erde-Fraktion** (Kontrollierend)

| **Einheit**         | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                                     |
| ------------------- | ------ | ----------- | ---------------- | ------------ | -------------- | ----------------------------------------------- |
| **Stein-Golem**     | 150    | 8           | 10               | 2            | 1              | Massiver Tank (höchste LP & Verteidigung)       |
| **Terra-Schamane**  | 75     | 11          | 5                | 3            | 2              | Fernkampf-Unterstützung (Reichweite: 2)         |
| **Erdbeben-Titan**  | 130    | 14          | 7                | 2            | 1              | Starker Nahkämpfer (höchster Angriff)           |

**Passiver Fraktions-Bonus:** Erde-Einheiten erhalten +2 Verteidigung auf Stein-Gelände. Erde-Einheiten haben reduzierte Bewegungskosten auf Stein (2 statt 3).

### **Luft-Fraktion** (Mobil)

| **Einheit**         | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                         |
| ------------------- | ------ | ----------- | ---------------- | ------------ | -------------- |------------------------------------------|
| **Wind-Tänzer**     | 70     | 12          | 3                | 6            | 1              | Fliegend, höchste Mobilität (6 Bewegung) |
| **Sturm-Rufer**     | 65     | 14          | 2                | 4            | 3              | Fliegend, Fernkampf (Reichweite: 3)      |
| **Himmels-Wächter** | 85     | 10          | 5                | 5            | 2              | Fliegend, ausgewogener Verteidiger       |

**Passiver Fraktions-Bonus:** Alle Luft-Einheiten haben die Fliegend-Fähigkeit (ignorieren Gelände-Bewegungskosten, können über Units fliegen). Luft-Einheiten erhalten +1 Verteidigung auf Wald-Gelände.

Jede Fraktion hat einen Fraktionsboni bzw. Fraktionsmali ggü. genau einer anderen Fraktion. Diese werden innerhalb der Chain of Responsibility angewendet (Siehe Design-Pattern Architektur)

| Angreifer \ Verteidiger | 🔥 Feuer | 💧 Wasser | 🪨 Erde  | 💨 Luft  |
|------------------------|----------|-----|----------|----------|
| **🔥 Feuer**           | 0        | **-25%** | **+25%** | 0        |
| **💧 Wasser**          | **+25%** | 0   | **-25%** | 0        |
| **🪨 Erde**            | 0        | **+25%** | 0        | **-25%** |
| **💨 Luft**             | **-25%** | 0   | **+25%** | 0        |

Fraktionsboni / -mali bei Angriffen




---    
## **Geländearten**

Das 10×10-Schlachtfeld enthält **5 Geländearten**, die jeweils Bewegung, Kampf und Strategie beeinflussen:

| Gelände   | Bewegungskosten                | Verteidigungsbonus | Fraktions-Effekte                                                                          |
|-----------|--------------------------------|--------------------|--------------------------------------------------------------------------------------------|
| **Lava**  | Normal: 2, Feuer: 1, Wasser: 3 | 0                  | **Feuer:** +2 Angriff / **Wasser:** -5 LP/Runde                                            |
| **Eis**   | Normal: 3, Wasser: 1, Feuer: 2 | +1 Verteidigung    | **Wasser:** +3 Verteidigung, Heilt 5 LP/Runde / **Feuer:** Schmilzt zu Wüste nach Bewegung |
| **Wald**  | Normal: 2, Luft: 1 (fliegend)  | +2 Verteidigung    | Blockiert Fernkampf-Sichtlinie                                                             |
| **Wüste** | Normal: 1                      | 0                  | Neutrales Gelände, keine Boni                                                              |
| **Stein** | Normal: 3, Erde: 2             | 0                  | **Erde:** +2 Verteidigung                                                                  |

**Gelände-Verteilung:** Das Schlachtfeld startet mit zufälligem Gelände (30% Wüste, 20% Wald, 20% Stein, 15% Lava, 15% Eis).
    
---    
## **Design-Pattern-Architektur**

ElementarClash implementiert **10 GoF Design Patterns**, um Modularität, Erweiterbarkeit und saubere Architektur sicherzustellen. Jedes Pattern adressiert direkt spezifische Spielmechaniken:

| #  | **Pattern**                 | **Kategorie** | **Anwendungsfall in ElementarClash**                                                                                      | **Warum dieses Pattern?**                                                                                                                                                                                                                                                                                                                                                                                                                                                                            | **Wer?** |  
|----|-----------------------------|---------------|---------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|  
| 1  | **Factory Method**          | Erzeugung     | Erstellung fraktionsspezifischer Einheiten (FeuerKrieger, WasserHeiler, etc.)                                             | Jede der 4 Fraktionen hat 3 einzigartige Einheitentypen. Factory Method kapselt Erstellungslogik und ermöglicht das Hinzufügen neuer Fraktionen ohne Core-Code-Änderungen.                                                                                                                                                                                                                                                                                                                           | @crstmkt |  
| 2  | **Builder**                 | Erzeugung     | Schrittweise Erstellung des Spielfelds (10×10 Raster mit Geländeverteilung, Einheiten-Platzierung, Validierung)           | Das Battlefield ist komplex: 100 Zellen, zufällige Geländeverteilung (5 Typen mit Prozent-Vorgaben), faire Startpositionen für 2-4 Fraktionen, benutzerdefinierte Terrain-Konfigurationen. Builder ermöglicht flexible Konfiguration und wiederverwendbare Setup-Logik.                                                                                                                                                                                                                              | @mmukex  |  
| 3  | **Composite**               | Struktur      | Raster-Hierarchie (Battlefield → Regionen → Zellen) für flexible Operationen auf Teilbereichen des Schlachtfelds          | Das 10×10-Raster enthält 100 Zellen organisiert in Regionen. Composite ermöglicht einheitliche Operationen auf einzelnen Zellen UND ganzen Regionen. Vereinfacht Geländeeffekte und dynamische Ereignisse durch gemeinsame Schnittstelle für Leaf (Cell) und Composite (Region, Battlefield).                                                                                                                                                                                                        | @mmukex  |  
| 4  | **Decorator**               | Struktur      | (Stapeln temporärer Buffs/Debuffs)                                                                            | Gelände-Effekte werden aktuell direkt über Visitor Pattern berechnet. Decorator würde zusätzliches Stapeln von temporären Buffs/Debuffs ermöglichen. Diese werden in einer Spielphase (z.B. Event-Phase) vergeben. Die Idee ist: In einer Spielphase wird mit einer Wahrscheinlichkeit von x % einer von y fest definierten (De-)Buffs als Decorator auf eine der lebenden Units der tbd (endenden / startenden) Fraktion gegeben. Außerdem werden Synergieboni über das Decorator Pattern vergeben. | @crstmkt |  
| 5  | **Strategy**                | Verhalten     | Bewegungsstrategien (Boden, Fliegend für Luft-Einheiten) und Angriffsstrategien (Nahkampf, Fernkampf)             | Jede Fraktion hat einzigartige Spielstile: Luft ist "mobil" (fliegende Bewegung), verschiedene Angriffstypen (Nahkampf vs. Fernkampf). Strategy kapselt diese Verhaltensweisen als austauschbare Algorithmen.                                                                                                                                                                                                                                                                                        | @mmukex  |  
| 6  | **State**                   | Verhalten     | Spielphasen (GameStatus Enum: Setup, InProgress, GameOver)      |                                                                                                                                                                                                                                                               | @crstmkt |  
| 7  | **Observer**                | Verhalten     | (Event-System, Entkopplung)                                                           | UI wird aktuell direkt durch den GameController aufgerufen. Observer würde UI-Updates, Achievements und dynamische Ereignisse entkoppeln.                                                                                                                                                                                                                                                                                                                                                            | @crstmkt |  
| 8  | **Command**                 | Verhalten     | Spieler-Aktionssystem (MoveCommand, AttackCommand) mit eingebetteter Validierungs- und Ausführungslogik | Kapselt alle Spielzüge als Objekte. "2 Aktionen pro Runde" = 2 Commands in Queue. Jedes Command enthält eigene Validierung (Reichweite, Gelände, Position), Ausführungslogik und Rollback-Fähigkeit. CommandExecutor verwaltet CommandHistory für Undo/Redo (per-turn rollback). DamageCalculator zeigt Pattern-Integration: kombiniert Command mit Strategy (AttackStrategy) und Visitor (TerrainEffects).                                                                                          | @mmukex  |  
| 9  | **Chain of Responsibility** | Verhalten     | (Schadensberechnung-Pipeline)                   | Schadensberechnung ist aktuell in einer einzelnen Methode (DamageCalculator) implementiert. Chain würde modulare Erweiterung um Fraktions-Boni (+25%/-25%), Synergien und weitere Modifikatoren ermöglichen.                                                                                                                                                                                            | @crstmkt |  
| 10 | **Visitor**                 | Verhalten     | Gelände-Effekte auf verschiedene Einheitentypen                                                                           | Lava-Gelände hat unterschiedliche Effekte pro Fraktion: Feuer-Einheiten erhalten +2 Angriff, Wasser-Einheiten nehmen -5 LP/Runde Schaden, Erde/Luft haben keinen Bonus. Visitor vermeidet verschachtelte if-Statements (5 Gelände × 4 Fraktionen = 20 Kombinationen) und macht neue Geländetypen leicht erweiterbar durch Double Dispatch.                                                                                                                                                           | @mmukex  |  

Diese Patterns ergeben sich aus den Kernmechaniken von ElementarClashs:
- **4 Fraktionen × 3 Einheitentypen** → Factory Method
- **Gamboard-Erstellung** -> Builder
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
============================================================
################################################################################################
               ELEMENTARCLASH - Runde 1
################################################################################################

Aktive Fraktion: Feuer
Status: IN_PROGRESS

     0    1    2    3    4    5    6    7    8    9  
0 | F1 | ❄️ | 🌋 | 🌵 | 🌋 | 🌵 | 🌋 | W1 | 🌲 | 🌋 |

1 | F2 | 🌵 | 🌵 | 🌋 | 🌵 | ❄️ | ❄️ | W2 | ❄️ | 🌲 |

2 | F3 | 🌲 | ⛰️ | 🌋 | ❄️ | 🌲 | ⛰️ | W3 | 🌋 | 🌵 |

3 | 🌵 | 🌵 | ⛰️ | ❄️ | 🌲 | 🌋 | ⛰️ | 🌲 | ❄️ | ⛰️ |

4 | 🌋 | 🌵 | 🌋 | 🌲 | 🌲 | ❄️ | ❄️ | ⛰️ | 🌵 | 🌵 |

5 | ⛰️ | 🌋 | 🌋 | 🌵 | 🌵 | 🌋 | 🌵 | ⛰️ | 🌲 | 🌵 |

6 | ⛰️ | 🌋 | ⛰️ | 🌵 | 🌋 | 🌋 | 🌲 | ⛰️ | ⛰️ | 🌲 |

7 | ❄️ | 🌋 | ⛰️ | 🌲 | ⛰️ | ❄️ | ❄️ | ❄️ | ❄️ | 🌋 |

8 | 🌵 | ⛰️ | 🌋 | ⛰️ | 🌲 | ⛰️ | 🌵 | ⛰️ | 🌲 | ❄️ |

9 | 🌲 | ❄️ | 🌲 | ⛰️ | 🌲 | ❄️ | ❄️ | 🌲 | 🌲 | 🌋 |


Legende Terrain:  🌋 Lava |   ❄️ Eis |   🌲 Wald |   🌵 Wüste |   ⛰️ Stein | 

### Einheiten ###
F1 = Inferno-Krieger      (F) | HP: 100/100 | ATK: 15 | DEF:  5 | MOV: 3 | RNG: 1 | Pos: (0, 0) [DEF+1]
F2 = Flammen-Bogenschütze (F) | HP:  70/ 70 | ATK: 12 | DEF:  3 | MOV: 4 | RNG: 3 | Pos: (0, 1) [DEF+1]
F3 = Phönix               (F) | HP:  80/ 80 | ATK: 10 | DEF:  4 | MOV: 5 | RNG: 1 | Pos: (0, 2)
W1 = Gezeiten-Wächter     (W) | HP: 120/120 | Pos: (7, 0)
W2 = Frost-Magier         (W) | HP:  60/ 60 | Pos: (7, 1)
W3 = Wellen-Reiter        (W) | HP:  90/ 90 | Pos: (7, 2)

Lebende Einheiten: 6/6

============================================================

Aktion [B]ewegen | [A]ngreifen | [U]ndo | [R]edo | [Z]ug beenden | [Q] Beenden:
```
---

## **Zusätzliche Features**

### **Undo/Redo System**
Vollständiges **Undo/Redo System** für alle Aktionen innerhalb einer Runde:

- **`[U]ndo`:** Macht die letzte Aktion rückgängig (Bewegung oder Angriff)
- **`[R]edo`:** Wiederholt eine rückgängig gemachte Aktion
- **Per-Turn Rollback:** Historie wird bei Rundenwechsel geleert
- **Pattern-Integration:** Implementiert über **Command Pattern** - `CommandHistory` verwaltet zwei Stacks (Undo/Redo), jedes `Command` kennt seine eigene Rollback-Logik

**Gameplay-Vorteil:** Experimentieren mit verschiedenen Strategien ohne Commit, Fehlerkorrektur, bessere Lernkurve für neue Spieler.

---

## **Technologies**
- **Language:** Java 21
- **Architecture:** Modular, testable, extensible (SOLID principles)
- **Build Tool:** Gradle 8.14 with wrapper
- **Documentation:** PlantUML (UML diagrams), Javadoc