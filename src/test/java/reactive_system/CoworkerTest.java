package reactive_system;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoworkerTest {


    /***
     * Testing switchState function and getCurrentState function
     */
    @Test
    public void switchStateTest() {
        Coworker coworker = new Coworker("MonkaS");

        coworker.switchState(Coworker.Input.LINKS);
        assertEquals(Coworker.State.IMGANG, coworker.getCurrentState());

        coworker.switchState(Coworker.Input.RECHTS);
        assertEquals(Coworker.State.ANWESEND, coworker.getCurrentState());
    }
}