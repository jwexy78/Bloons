import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jonathan on 2/17/19.
 */
public class SprayTower extends Tower
{
    public SprayTower( int x, int y )
    {
        super( x, y, 60, 50 );
    }

    public void shoot(ArrayList<Bullet> bullets, Balloon target )
    {
        double speed = 3;
        double rtspeed = speed * Math.sqrt( 2 ) / 2;
        bullets.add( new Bullet( x, y, 2, 0 ) );
        bullets.add( new Bullet( x, y, -2, 0 ) );
        bullets.add( new Bullet( x, y, 0, 2 ) );
        bullets.add( new Bullet( x, y, 0, -2 ) );
        bullets.add( new Bullet( x, y, rtspeed, rtspeed ) );
        bullets.add( new Bullet( x, y, rtspeed, -rtspeed ) );
        bullets.add( new Bullet( x, y, -rtspeed, -rtspeed ) );
        bullets.add( new Bullet( x, y, -rtspeed, rtspeed ) );
    }

    public void draw( Graphics2D g )
    {
        g.setColor( Color.MAGENTA );
        g.fillOval( getX() - getWidth(), getY() - getWidth(), getWidth() * 2, getWidth() * 2 );
        g.setColor( Color.BLACK );
        g.fillRect( getX() - 1, getY() - getWidth(), 2, 2 * getWidth() );
        g.fillRect( getX() - getWidth(), getY() - 1, 2 * getWidth(), 2 );
    }
}
