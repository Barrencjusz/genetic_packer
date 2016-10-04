package genetic.packer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javafx.scene.shape.Box;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author piotr.larysz
 */
public class IntersectionBugProof {

    private static final Box contained = new Box(1, 1, 1);
    private static final Box containing = new Box(2, 2, 1);
    private static final Box loose = new Box(2, 2, 2);

    @BeforeClass
    public static void beforeClass() {
        contained.setTranslateX(1 + contained.getWidth() / 2);
        contained.setTranslateY(0 + contained.getHeight() / 2);
        contained.setTranslateZ(0 + contained.getDepth() / 2);

        containing.setTranslateX(1 + containing.getWidth() / 2);
        containing.setTranslateY(0 + containing.getHeight() / 2);
        containing.setTranslateZ(0 + containing.getDepth() / 2);

        loose.setTranslateX(1 + loose.getWidth() / 2);
        loose.setTranslateY(0 + loose.getHeight() / 2);
        loose.setTranslateZ(2 + loose.getDepth() / 2);
    }

    @Test
    public void testPositions() {
        assertEquals(1.5, contained.getTranslateX(), 0.01);
        assertEquals(0.5, contained.getTranslateY(), 0.01);
        assertEquals(0.5, contained.getTranslateZ(), 0.01);

        assertEquals(1, contained.getBoundsInParent().getMinX(), 0.01);
        assertEquals(0, contained.getBoundsInParent().getMinY(), 0.01);
        assertEquals(0, contained.getBoundsInParent().getMinZ(), 0.01);
    }

    @Test
    public void testIntersections() {
        assertTrue(contained.getBoundsInParent().intersects(containing.getBoundsInParent()));
        assertFalse(contained.getBoundsInParent().intersects(loose.getBoundsInParent()));
        assertFalse(containing.getBoundsInParent().intersects(loose.getBoundsInParent()));
    }

    /*translatedBoxes":[
    {
        "width": 1,
            "height": 1,
            "depth": 1,
            "position":{"x": 1, "y": 0, "z": 0},
        "rotation": null
    },
    {
        "width": 2,
            "height": 2,
            "depth": 1,
            "position":{"x": 1, "y": 0, "z": 0},
        "rotation": null
    },
    {
        "width": 2,
            "height": 2,
            "depth": 2,
            "position":{"x": 1, "y": 0, "z": 2},
        "rotation": null
    }
]*/
}
