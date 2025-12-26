package org.elementarclash.battlefield;

import lombok.Getter;
import lombok.Setter;
import org.elementarclash.util.Position;

import java.util.List;

/**
 * Leaf in the Composite Pattern representing a single battlefield cell.
 * Each cell has a position and changeable terrain type.
 * <p>
 * Design Pattern: Composite (GoF #3) - Leaf
 * Why: Represents the smallest unit in battlefield hierarchy.
 * Cannot contain other components but implements same interface as composites.
 */
@Getter
public class Cell implements BattlefieldComponent {

    private final Position position;
    @Setter
    private Terrain terrain;

    public Cell(Position position, Terrain terrain) {
        this.position = position;
        this.terrain = terrain;
    }

    @Override
    public List<Cell> cells() {
        return List.of(this);
    }

    @Override
    public void applyEffect(TerrainEffect effect) {
        this.terrain = effect.apply(this);
    }

}
