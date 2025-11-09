# **ElementarClash** – Turn-Based Elemental Strategy Game
*A tactical battle of Fire, Water, Earth, and Air, featuring dynamic terrain and deep synergies.*

---

## **Concept**
**ElementarClash** is a **turn-based strategy game** where four elemental factions (**Fire**, **Water**, **Earth**, **Air**) compete on a **10×10 grid** with five terrain types (**Lava**, **Ice**, **Forest**, **Desert**, **Stone**). Each faction has **unique units, abilities, and playstyles**, creating strategic depth through **elemental strengths/weaknesses** and **terrain-based effects**.

**Fire** (aggressive) | **Water** (defensive) | **Earth** (controlling) | **Air** (mobile)

---

## **Game Mechanics**

| **Feature**            | **Details**                                                                                     |
|------------------------|-------------------------------------------------------------------------------------------------|
| **Dynamic Terrain**    | Terrain types affect movement, attack, and defense (e.g., Fire dominates on Lava).              |
| **Units & Abilities**  | 3 unit types per faction with **active abilities** (e.g., "Fire Storm") and **passive bonuses**. |
| **Synergies & Counters** | Unit combinations (e.g., +1 attack for adjacent Fire units) and elemental interactions (Fire melts Ice). |
| **Turn-Based Logic**   | 3 actions per turn: **Move** (terrain-dependent), **Attack**, **Use Abilities**.                 |
| **Dynamic Events**     | Random events like **Geysers**, **Forest Fires**, or **Landslides** alter the battlefield.      |
| **Victory Conditions** | Destroy all enemies.               |
| **Modular & Extensible** | Easily add new factions, terrain types, or abilities **without core code changes**.             |

---

## **Technical Implementation**

### **Design Patterns (GoF)**
The project uses **12 design patterns** to ensure a **clean, decoupled architecture**:

| **Pattern**            | **Application**                                                                                 |
|------------------------|-------------------------------------------------------------------------------------------------|
| **Abstract Factory**   | Each faction exposes a factory that spawns coherent unit loadouts, abilities, and AIs without leaking creation details. |
| **Builder**            | A map builder assembles the 10×10 grid from terrain blueprints, weighting Lava/Ice/etc. and injecting scripted events. |
| **Strategy**           | Units plug in interchangeable attack/support strategies such as `FireStorm` or `HealingMist`, enabling runtime swaps. |
| **State**              | Terrain tiles maintain mutable states (`Normal`, `Scorched`, `Frozen`) that alter movement costs and combat modifiers. |
| **Command**            | Player actions (`Move`, `Attack`, `CastAbility`) are encapsulated commands, enabling undo, logging, and AI replays.    |
| **Observer**           | A lightweight event bus pushes notifications (terrain shifts, unit deaths, morale swings) to UI, AI, and rule systems. |
| **Mediator**           | The `TurnMediator` coordinates factions: it sequences phases, resolves conflicts, and enforces interaction rules.      |
| **Composite**          | The board treats individual tiles and grouped tile regions uniformly, simplifying area-of-effect calculations.         |
| **Decorator**          | Temporary buffs/debuffs wrap units to add synergy perks (e.g., adjacent Fire aura) without modifying core classes.    |
| **Chain of Responsibility** | Victory checks (elimination, source control, morale) run through a chain until one condition succeeds.          |
| **Template Method**    | A `TurnTemplate` defines the fixed phases (upkeep → actions → cleanup) while factions override hook methods.          |
| **Flyweight**          | Shared flyweights store immutable terrain/unit archetype data so tiles and units only keep lightweight state.          |


### **Technologies**
- **Language:** Java 21
- **Architecture:** Modular, testable, extensible
- **Tools:** UML (PlantUML), JUnit (tests), Gradle (bui****1****ld)

---
