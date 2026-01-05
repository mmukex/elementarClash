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
| **Einheiten & Fähigkeiten** | 3 Einheitentypen pro Fraktion mit **aktiven Fähigkeiten** und **passiven Boni**.                            |
| **Synergien & Konter**      | Einheitenkombinationen und elementare Interaktionen (Feuer schmilzt Eis).                                   |
| **Rundenbasierte Logik**    | 2 Aktionen pro Runde: **Bewegen** (geländeabhängig), **Angreifen**.              |
| **Dynamische Ereignisse**   | Zufällige Ereignisse wie **Geysire**, **Waldbrände** oder **Erdrutsche** können das Schlachtfeld verändern. |
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

**Passiver Fraktions-Bonus:** Feuer-Einheiten verursachen +25% Schaden gegen Erde, -25% gegen Wasser. Benachbarte Verbündete gewähren einander +1 Angriff (Synergy-Bonus).
### **Wasser-Fraktion** (Defensiv)

| **Einheit**          | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                                             |
| -------------------- | ------ | ----------- | ---------------- | ------------ | -------------- | ------------------------------------------------------- |
| **Gezeiten-Wächter** | 120    | 10          | 8                | 2            | 1              | +3 Verteidigung auf Eis, +5 LP/Runde Heilung            |
| **Frost-Magier**     | 60     | 13          | 4                | 3            | 4              | Fernkampf-Magier (höchste Reichweite: 4)                |
| **Wellen-Reiter**    | 90     | 11          | 6                | 4            | 1              | Schnelle Bewegung auf Eis (Kosten: 1)                   |

**Passiver Fraktions-Bonus:** Wasser-Einheiten verursachen +25% Schaden gegen Feuer, -25% gegen Erde. Heilt 5 LP pro Runde auf Eis-Gelände.

### **Erde-Fraktion** (Kontrollierend)

| **Einheit**         | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                                     |
| ------------------- | ------ | ----------- | ---------------- | ------------ | -------------- | ----------------------------------------------- |
| **Stein-Golem**     | 150    | 8           | 10               | 2            | 1              | Massiver Tank (höchste LP & Verteidigung)       |
| **Terra-Schamane**  | 75     | 11          | 5                | 3            | 2              | Fernkampf-Unterstützung (Reichweite: 2)         |
| **Erdbeben-Titan**  | 130    | 14          | 7                | 2            | 1              | Starker Nahkämpfer (höchster Angriff)           |

**Passiver Fraktions-Bonus:** Erde-Einheiten verursachen +25% Schaden gegen Wasser, -25% gegen Luft. +5 Verteidigung auf Stein-Gelände (+3 Basis, +2 Erde-Bonus).

### **Luft-Fraktion** (Mobil)

| **Einheit**         | **LP** | **Angriff** | **Verteidigung** | **Bewegung** | **Reichweite** | **Beschreibung**                         |
| ------------------- | ------ | ----------- | ---------------- | ------------ | -------------- |------------------------------------------|
| **Wind-Tänzer**     | 70     | 12          | 3                | 6            | 1              | Fliegend, höchste Mobilität (6 Bewegung) |
| **Sturm-Rufer**     | 65     | 14          | 2                | 4            | 3              | Fliegend, Fernkampf (Reichweite: 3)      |
| **Himmels-Wächter** | 85     | 10          | 5                | 5            | 2              | Fliegend, ausgewogener Verteidiger       |

**Passiver Fraktions-Bonus:** Luft-Einheiten verursachen +25% Schaden gegen Erde, -25% gegen Feuer. Alle Einheiten haben Fliegend (ignorieren Gelände-Bewegungsstrafen).

---    
## **Geländearten**

Das 10×10-Schlachtfeld enthält **5 Geländearten**, die jeweils Bewegung, Kampf und Strategie beeinflussen:

| Gelände   | Bewegungskosten                | Verteidigungsbonus | Fraktions-Effekte                                                                          |
|-----------|--------------------------------|--------------------|--------------------------------------------------------------------------------------------|
| **Lava**  | Normal: 2, Feuer: 1, Wasser: 3 | 0                  | **Feuer:** +2 Angriff / **Wasser:** -5 LP/Runde                                            |
| **Eis**   | Normal: 3, Wasser: 1, Feuer: 2 | +1 Verteidigung    | **Wasser:** +3 Verteidigung, Heilt 5 LP/Runde / **Feuer:** Schmilzt zu Wüste nach Bewegung |
| **Wald**  | Normal: 2, Luft: 1 (fliegend)  | +2 Verteidigung    | Blockiert Fernkampf-Sichtlinie                                                             |
| **Wüste** | Normal: 1                      | 0                  | Neutrales Gelände, keine Boni                                                              |
| **Stein** | Normal: 3, Erde: 2             | +3 Verteidigung    | **Erde:** +2 Verteidigung                                                                  |

**Gelände-Verteilung:** Das Schlachtfeld startet mit zufälligem Gelände (30% Wüste, 20% Wald, 20% Stein, 15% Lava, 15% Eis).
    
---    
## **Design-Pattern-Architektur**

ElementarClash implementiert **10 GoF Design Patterns**, um Modularität, Erweiterbarkeit und saubere Architektur sicherzustellen. Jedes Pattern adressiert direkt spezifische Spielmechaniken:

| #  | **Pattern**                 | **Kategorie** | **Anwendungsfall in ElementarClash**                                                                                       | **Warum dieses Pattern?**                                                                                                                                                                                                                                                                                                                                                                    | **Wer?** |  
|----|-----------------------------|---------------|----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|  
| 1  | **Factory Method**          | Erzeugung     | Erstellung fraktionsspezifischer Einheiten (FeuerKrieger, WasserHeiler, etc.)                                              | Jede der 4 Fraktionen hat 3 einzigartige Einheitentypen. Factory Method kapselt Erstellungslogik und ermöglicht das Hinzufügen neuer Fraktionen ohne Core-Code-Änderungen.                                                                                                                                                                                                                   | @crstmkt |  
| 2  | **Builder**                 | Erzeugung     | Schrittweise Erstellung des Spielfelds (10×10 Raster mit Geländeverteilung, Einheiten-Platzierung, Validierung)            | Das Battlefield ist komplex: 100 Zellen, zufällige Geländeverteilung (5 Typen mit Prozent-Vorgaben), faire Startpositionen für 2-4 Fraktionen, benutzerdefinierte Terrain-Konfigurationen. Builder ermöglicht flexible Konfiguration und wiederverwendbare Setup-Logik.                                                                                                       | @mmukex  |  
| 3  | **Composite**               | Struktur      | Raster-Hierarchie (Battlefield → Regionen → Zellen) für flexible Operationen auf Teilbereichen des Schlachtfelds           | Das 10×10-Raster enthält 100 Zellen organisiert in Regionen. Composite ermöglicht einheitliche Operationen auf einzelnen Zellen UND ganzen Regionen (z.B. "applyForestFire()"). Vereinfacht Geländeeffekte und dynamische Ereignisse durch gemeinsame Schnittstelle für Leaf (Cell) und Composite (Region, Battlefield).                                                                    | @mmukex  |  
| 4  | **Decorator**               | Struktur      | Stapeln temporärer Buffs/Debuffs auf Einheiten                                                                             | Einheiten erhalten dynamische Boni: Gelände-Bonus (Feuer auf Lava: +2 Angriff), Synergien (benachbarte Feuer-Einheiten: +1 Angriff), Fähigkeits-Buffs (Feuersturm: +3 Angriff für 2 Runden). Decorator ermöglicht Stapeln ohne Änderung der Unit-Klasse.                                                                                                                                     | @crstmkt |  
| 5  | **Strategy**                | Verhalten     | Bewegungsstrategien (Boden, Fliegend für Luft-Einheiten) und Angriffsstrategien (Nahkampf, Fernkampf)              | Jede Fraktion hat einzigartige Spielstile: Luft ist "mobil" (fliegende Bewegung), verschiedene Angriffstypen (Nahkampf vs. Fernkampf). Strategy kapselt diese Verhaltensweisen als austauschbare Algorithmen.                                                                                                                                                                               | @mmukex  |  
| 6  | **State**                   | Verhalten     | Einheiten-Zustände (Idle, Moving, Attacking, Stunned, Dead) und Spielphasen (Setup, PlayerTurn, EventPhase, GameOver)      | Einheiten können nur bestimmte Aktionen in bestimmten Zuständen ausführen (kein Angriff wenn betäubt). Das Spiel hat klare Phasen (3 Aktionen → dynamische Ereignisse → nächster Spieler). State Pattern verwaltet Übergänge sauber.                                                                                                                                                         | @crstmkt |  
| 7  | **Observer**                | Verhalten     | Event-System für UI-Updates, Synergien und dynamische Ereignisse                                                           | Wenn eine Einheit stirbt → UI-Updates, Achievements triggern, Synergien neu berechnen. "Dynamische Ereignisse" wie Waldbrände betreffen mehrere Einheiten. Observer entkoppelt Komponenten (Spiellogik ↔ UI ↔ Events).                                                                                                                                                                       | @crstmkt |  
| 8  | **Command**                 | Verhalten     | Spieler-Aktionssystem (MoveCommand, AttackCommand) mit eingebetteter Validierungs- und Ausführungslogik | Kapselt alle Spielzüge als Objekte. "2 Aktionen pro Runde" = 2 Commands in Queue. Jedes Command enthält eigene Validierung (Reichweite, Gelände, Position), Ausführungslogik und Rollback-Fähigkeit. CommandExecutor verwaltet CommandHistory für Undo/Redo (per-turn rollback). DamageCalculator zeigt Pattern-Integration: kombiniert Command mit Strategy (AttackStrategy) und Visitor (TerrainEffects). | @mmukex  |  
| 9  | **Chain of Responsibility** | Verhalten     | Schadensberechnung-Pipeline (Basisschaden → Elementar-Modifikator → Gelände → Synergien → Verteidigung)                    | Schaden wird beeinflusst durch: Einheiten-Angriff, elementare Vorteile (Feuer vs Eis: +25%), Gelände-Effekte (Verteidiger auf Stein: +3 Verteidigung), Synergien, Ziel-Verteidigung. Jeder Handler fügt seine Modifikation in Sequenz hinzu. Chain ermöglicht flexible Erweiterung um neue Modifikatoren.                                                                                    | @crstmkt |  
| 10 | **Visitor**                 | Verhalten     | Gelände-Effekte auf verschiedene Einheitentypen                                                                            | Lava-Gelände hat unterschiedliche Effekte pro Fraktion: Feuer-Einheiten erhalten +2 Angriff, Wasser-Einheiten nehmen -5 LP/Runde Schaden, Erde/Luft haben keinen Bonus. Visitor vermeidet verschachtelte if-Statements (5 Gelände × 4 Fraktionen = 20 Kombinationen) und macht neue Geländetypen leicht erweiterbar durch Double Dispatch.                                                   | @mmukex  |  

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
═══════════════════════════════════════════════════════════════
                   ELEMENTARCLASH - Runde 5
═══════════════════════════════════════════════════════════════
Spieler: FEUER (3 Einheiten)               Gegner: WASSER (3 Einheiten)
Verbleibende Aktionen: 2/3                 Gesamt-LP: 270/270
═══════════════════════════════════════════════════════════════
  
    0    1    2    3    4    5    6    7    8    9  
0 | 🔥 | ⛰️ | 🌵 | ❄️ | 🌵 | ⛰️ | 🌵 | 💧 | 🌵 | 🌵 |

1 | 🌋 | 🔥 | 🌵 | ⛰️ | ⛰️ | 🌲 | ⛰️ | 🌲 | 🌋 | 💧 |

2 | ⛰️ | 🌋 | ⛰️ | 🌵 | 🌵 | 🌋 | ⛰️ | 🌲 | ❄️ | 🌲 |

3 | ❄️ | ❄️ | 🌲 | 🌋 | ⛰️ | 🌋 | 🌵 | 🌵 | 🌵 | 🌲 |

4 | ❄️ | 🌵 | 🌋 | 🌵 | 🌵 | 🌲 | ❄️ | 🌵 | ❄️ | ⛰️ |

5 | ⛰️ | 🌵 | 🌵 | ❄️ | ❄️ | 🌲 | 🌵 | 🌋 | ❄️ | ⛰️ |

6 | 🌋 | 🌲 | ⛰️ | 🌵 | 🌵 | ❄️ | 🌋 | 🌵 | ❄️ | 🌋 |

7 | ❄️ | 🌲 | 🌵 | 🌲 | 🌲 | 🌲 | ⛰️ | 🌋 | 🌲 | 🌵 |

8 | 🌵 | 🔥 | 🌲 | 🌵 | 🌲 | 🌋 | ⛰️ | ⛰️ | 🌋 | 💧 |

9 | 🌵 | 🌵 | 🌵 | ⛰️ | ⛰️ | 🌵 | ⛰️ | 🌲 | ❄️ | 🌲 |

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