package org.elementarclash;

import org.elementarclash.battlefield.*;
import org.elementarclash.util.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CompositePatternTest {

    private Battlefield battlefield;

    @BeforeEach
    void setUp() {
        battlefield = new Battlefield();
    }

    @Test
    void cellImplementsBattlefieldComponent() {
        Cell cell = new Cell(new Position(0, 0), Terrain.DESERT);
        assertInstanceOf(BattlefieldComponent.class, cell);
    }

    @Test
    void regionImplementsBattlefieldComponent() {
        List<Cell> cells = List.of(new Cell(new Position(0, 0), Terrain.DESERT));
        Region region = new Region(cells);
        assertInstanceOf(BattlefieldComponent.class, region);
    }

    @Test
    void battlefieldImplementsBattlefieldComponent() {
        assertInstanceOf(BattlefieldComponent.class, battlefield);
    }

    @Test
    void cellCellsReturnsSingleElementListOfSelf() {
        Cell cell = new Cell(new Position(3, 4), Terrain.LAVA);
        List<Cell> cells = cell.cells();
        assertEquals(1, cells.size());
        assertSame(cell, cells.get(0));
    }

    @Test
    void regionCellsReturnsAllContainedCells() {
        Cell c1 = new Cell(new Position(0, 0), Terrain.DESERT);
        Cell c2 = new Cell(new Position(1, 0), Terrain.FOREST);
        Cell c3 = new Cell(new Position(2, 0), Terrain.LAVA);
        Region region = new Region(List.of(c1, c2, c3));

        List<Cell> cells = region.cells();

        assertEquals(3, cells.size());
        assertTrue(cells.contains(c1));
        assertTrue(cells.contains(c2));
        assertTrue(cells.contains(c3));
    }

    @Test
    void battlefieldCellsReturnsAll100Cells() {
        List<Cell> cells = battlefield.cells();
        assertEquals(100, cells.size());
    }

    @Test
    void applyEffectOnCellAffectsSingleCell() {
        Cell cell = new Cell(new Position(0, 0), Terrain.DESERT);
        cell.applyEffect(c -> c.setTerrain(Terrain.LAVA));
        assertEquals(Terrain.LAVA, cell.getTerrain());
    }

    @Test
    void applyEffectOnRegionAffectsAllContainedCells() {
        Cell c1 = new Cell(new Position(0, 0), Terrain.DESERT);
        Cell c2 = new Cell(new Position(1, 0), Terrain.FOREST);
        Cell c3 = new Cell(new Position(2, 0), Terrain.ICE);
        Region region = new Region(List.of(c1, c2, c3));

        region.applyEffect(c -> c.setTerrain(Terrain.STONE));

        assertEquals(Terrain.STONE, c1.getTerrain());
        assertEquals(Terrain.STONE, c2.getTerrain());
        assertEquals(Terrain.STONE, c3.getTerrain());
    }

    @Test
    void applyEffectOnBattlefieldAffectsAll100Cells() {
        battlefield.applyEffect(c -> c.setTerrain(Terrain.LAVA));

        long lavaCount = battlefield.cells().stream()
                .filter(c -> c.getTerrain() == Terrain.LAVA)
                .count();
        assertEquals(100, lavaCount);
    }

    @Test
    void compositeHierarchyBattlefieldContains10Rows() {
        Cell cell00 = battlefield.getCell(0, 0);
        Cell cell90 = battlefield.getCell(9, 0);
        Cell cell09 = battlefield.getCell(0, 9);
        Cell cell99 = battlefield.getCell(9, 9);

        assertNotNull(cell00);
        assertNotNull(cell90);
        assertNotNull(cell09);
        assertNotNull(cell99);
    }

    @Test
    void getRegionCreatesDynamicComposite() {
        Region region = battlefield.getRegion(2, 2, 4, 4);

        assertNotNull(region);
        assertEquals(9, region.cells().size());
    }

    @Test
    void getRegionAndApplyEffectWorksLikeOnLeaf() {
        Region region = battlefield.getRegion(0, 0, 1, 1);
        region.applyEffect(c -> c.setTerrain(Terrain.ICE));

        assertEquals(Terrain.ICE, battlefield.getCell(0, 0).getTerrain());
        assertEquals(Terrain.ICE, battlefield.getCell(1, 0).getTerrain());
        assertEquals(Terrain.ICE, battlefield.getCell(0, 1).getTerrain());
        assertEquals(Terrain.ICE, battlefield.getCell(1, 1).getTerrain());
    }

    @Test
    void uniformInterfaceCountCellsWorksSameOnAllLevels() {
        Cell cell = new Cell(new Position(0, 0), Terrain.DESERT);
        Region region = new Region(List.of(
                new Cell(new Position(0, 0), Terrain.DESERT),
                new Cell(new Position(1, 0), Terrain.DESERT),
                new Cell(new Position(2, 0), Terrain.DESERT)
        ));

        AtomicInteger cellCount = new AtomicInteger();
        AtomicInteger regionCount = new AtomicInteger();
        AtomicInteger battlefieldCount = new AtomicInteger();

        cell.applyEffect(c -> cellCount.incrementAndGet());
        region.applyEffect(c -> regionCount.incrementAndGet());
        battlefield.applyEffect(c -> battlefieldCount.incrementAndGet());

        assertEquals(1, cellCount.get());
        assertEquals(3, regionCount.get());
        assertEquals(100, battlefieldCount.get());
    }

    @Test
    void regionCellsReturnsDefensiveCopy() {
        Cell c1 = new Cell(new Position(0, 0), Terrain.DESERT);
        Region region = new Region(List.of(c1));

        List<Cell> cells1 = region.cells();
        List<Cell> cells2 = region.cells();

        assertNotSame(cells1, cells2);
        assertEquals(cells1.size(), cells2.size());
    }
}
