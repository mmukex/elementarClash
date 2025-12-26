package org.elementarclash.battlefield;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite containing exactly 10 cells representing one battlefield row.
 * <p>
 * Design Pattern: Composite (GoF #3) - Composite
 * Why: Organizes cells into rows for structured battlefield representation.
 * Propagates operations to all contained cells via default method.
 */
public record Row(List<Cell> cells) implements BattlefieldComponent {

    public Row(List<Cell> cells) {
        this.cells = new ArrayList<>(cells);
    }

    @Override
    public List<Cell> cells() {
        return new ArrayList<>(cells);
    }
}
