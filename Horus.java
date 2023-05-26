package competicao;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class Horus extends AdvancedRobot {
    private static final double MAX_FIRE_POWER = 3.0;
    private static final double MIN_FIRE_POWER = 0.1;
    private static final double BULLET_POWER_FACTOR = 3.0;
    private static final double MOVEMENT_DISTANCE = 100.0;
    private static final double WALL_MARGIN = 50.0;
    private static final double MAX_TURN_ANGLE = 90.0;

    private double enemyDistance;
    private double enemyBearing;

    public void run() {
        setColors(Color.BLACK, Color.YELLOW, Color.BLACK); // Definindo as cores do robô
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) {
            turnRadarRight(360);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        enemyDistance = event.getDistance();
        enemyBearing = event.getBearing();

        // Movimento
        setTurnRight(enemyBearing);
        setAhead(enemyDistance - WALL_MARGIN);

        // Arma
        double bulletPower = Math.min(MAX_FIRE_POWER, enemyDistance / BULLET_POWER_FACTOR);
        double fireAngle = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(bulletPower);
    }

    public void onHitByBullet(HitByBulletEvent event) {
        // Movimento
        setTurnRight(normalizeAngle(event.getBearing() + MAX_TURN_ANGLE));
        setAhead(MOVEMENT_DISTANCE);

        // Arma
        double bulletPower = MIN_FIRE_POWER;
        double fireAngle = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(bulletPower);
    }

    public void onHitWall(HitWallEvent event) {
        // Movimento
        setTurnRight(normalizeAngle(event.getBearing() + MAX_TURN_ANGLE));
        setAhead(MOVEMENT_DISTANCE);

        // Arma
        double bulletPower = MIN_FIRE_POWER;
        double fireAngle = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(bulletPower);
    }

    private double normalizeAngle(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }
}
