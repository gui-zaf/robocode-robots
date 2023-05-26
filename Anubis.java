// Não permito o uso do código por terceiros.

// Criado por: 
// Guilherme Campos Ferraz 
// Isabela Ferreira
// Isabela Targino
// Fatec - Itaquera | DSM 1°Semestre

package competicao;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class Anubis extends AdvancedRobot {
    public void run() {
        setColors(Color.BLACK, new Color(210, 180, 140), Color.BLACK, new Color(210, 180, 140), Color.BLACK); // Definindo as cores do robô
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);

        while (true) turnRadarRight(360);
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        double enemyDistance = event.getDistance();
        double enemyBearing = event.getBearing();
        double enemyEnergy = event.getEnergy();

        // Movimento
        setTurnRight(enemyBearing);
        setAhead(enemyDistance);

        // Arma
        double bulletPower = Math.min(3.0, enemyEnergy / 4);
        double fireAngle = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(bulletPower);
    }

    public void onHitByBullet(HitByBulletEvent event) {
        double bulletBearing = event.getBearing();
        
        // Movimento
        setTurnRight(-bulletBearing);
        setAhead(100);

        // Arma
        double fireAngle = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(2.5);
    }

    public void onHitWall(HitWallEvent event) {
        double wallBearing = event.getBearing();
        
        // Movimento
        setTurnRight(-wallBearing);
        setAhead(100);

        // Arma
        double fireAngle = getHeadingRadians() + wallBearing;
        setTurnGunRightRadians(Utils.normalRelativeAngle(fireAngle - getGunHeadingRadians()));
        fire(1);
    }
}
