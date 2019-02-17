import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jonathan on 2/16/19.
 */
public abstract class Tower extends MapItem
{
    public Tower( int x, int y, int range, int frequency )
    {
        super( x, y, 10 );
        this.range = range;
        this.frequency = frequency;
        this.counter = 0;
    }

    /**
     * Tick the tower logic
     * @param map The map of the game
     *            Used for Map functionality, such as which balloon
     *            is "further" down the path
     * @param balloons The balloons currently on the map
     *                 Used to help aim the tower
     * @param bullets The list of bullets in the world
     *                This should only be added to if the tower
     *                creates a new bullet
     */
    public void tick( Map map, ArrayList<Balloon> balloons, ArrayList<Bullet> bullets )
    {
        if( counter > 0 )
        {
            counter--;
        }
        Balloon closest = getFurthestBalloonInRange( map, balloons );
        if( closest != null && canShoot() )
        {
            counter = frequency;
            shoot( bullets, closest );
        }
    }

    public Balloon getFurthestBalloonInRange( Map map, ArrayList<Balloon> balloons )
    {
        Balloon toShoot = null;
        for( Balloon b : balloons )
        {
            double dx = x - b.x;
            double dy = y - b.y;
            if( Math.sqrt( dx*dx + dy*dy) <= range )
            {
                if( toShoot == null || map.isFurther( b, toShoot ) )
                {
                    toShoot = b;
                }
            }
        }
        return toShoot;
    }

    public boolean canShoot()
    {
        return counter == 0;
    }

    public abstract void shoot( ArrayList<Bullet> bullets, Balloon target );

    public int getRange()
    {
        return (int)range;
    }

    public abstract void draw( Graphics2D g );

    protected double range;
    protected int frequency;
    protected int counter;
}
