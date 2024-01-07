package robots;

import org.junit.Test;
import robots.Robot;
import robots.Service;

import static org.junit.Assert.*;

public class ServiceTests {
    @Test
    public void testRobotGetName() {
        Robot robot = new Robot("Vesko");
        assertEquals("Vesko", robot.getName());
    }

    @Test
    public void testIsRobotReadyForSale() {
        Robot robot = new Robot("Vesko");
        assertTrue(robot.isReadyForSale());
    }

    @Test
    public void testRobotReadyForSaleChanged() {
        Robot robot = new Robot("Vesko");
        robot.setReadyForSale(false);
        assertFalse(robot.isReadyForSale());
    }

    @Test
    public void testServiceGetName() {
        Service service = new Service("Veskos", 10);
        assertEquals("Veskos", service.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testServiceSetName() {
        Service service = new Service("   ", 10);
    }
    @Test
    public void testServiceGetCapacity(){
        Service service = new Service("Veskos", 10);
        assertEquals(10, service.getCapacity());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testServiceSetCapacity(){
        Service service = new Service("Veskos", -1);
    }
    @Test
    public void testRobotsCountWithNoRobots9(){
        Service service = new Service("Veskos", 10);
        assertEquals(0, service.getCount());
    }
    @Test
    public void testAddRobotToServiceWithCapacity(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        service.add(robot);
        assertEquals(1, service.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddRobotToServiceWithoutCapacity(){
        Service service = new Service("Veskos", 0);
        Robot robot = new Robot("Vesko");
        service.add(robot);
    }
    @Test
    public void testRemoveWithOneExistingRobot(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        service.add(robot);
        service.remove("Vesko");
        assertEquals(0, service.getCount());
    }
    @Test
    public void testRemoveMultipleRobotsWithManyExistingRobots(){
        Service service = new Service("Veskos", 10);
        Robot robot1 = new Robot("Vesko1");
        Robot robot2 = new Robot("Vesko2");
        Robot robot3 = new Robot("Vesko3");
        Robot robot4 = new Robot("Vesko4");
        service.add(robot1);
        service.add(robot2);
        service.add(robot3);
        service.add(robot4);
        service.remove("Vesko1");
        service.remove("Vesko3");
        assertEquals(2, service.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNonExistingRobot(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        service.add(robot);
        service.remove("Bobi");
    }
    @Test
    public void testForSale(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        service.add(robot);
        service.forSale("Vesko");
        assertFalse(robot.isReadyForSale());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testForSaleOnNonExistingRobot(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        service.add(robot);
        service.forSale("Bobi");
        assertFalse(robot.isReadyForSale());
    }
    @Test
    public void testReportWithRobots(){
        Service service = new Service("Veskos", 10);
        Robot robot = new Robot("Vesko");
        Robot robot1 = new Robot("Vesko1");
        Robot robot2 = new Robot("Vesko2");
        service.add(robot);
        service.add(robot1);
        service.add(robot2);
        assertEquals("The robot Vesko, Vesko1, Vesko2 is in the service Veskos!", service.report());
    }
    @Test
    public void testReportWithoutRobots(){
        Service service = new Service("Veskos", 10);
        assertEquals("The robot  is in the service Veskos!", service.report());
    }
}
