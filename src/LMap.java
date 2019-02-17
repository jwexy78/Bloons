import java.awt.*;

/**
 * Created by jonathan on 2/16/19.
 */
public class LMap extends Map
{
    public void draw( Graphics2D g )
    {
        g.setColor( Color.GREEN );
        g.fillRect(0,0,400,400);
        g.setColor( Color.darkGray);
        g.fillRect(90,0,20,300);
        g.fillRect(90,290,310,20);
    }

    public void move( Balloon b )
    {

        if( b.y < 300 ) {
            b.y += b.getSpeed();
            return;
        }
        b.y = 300;
        b.x += b.getSpeed();
    }

    public void place( Balloon b )
    {
        b.x = 100;
        b.y = -20;
    }

    public boolean isPast( Balloon b )
    {
        return b.x >= 415;
    }

    public boolean isFurther( Balloon a, Balloon b )
    {
        if( a.y > b.y )
        {
            return true;
        }
        else if( a.y < b.y )
        {
            return false;
        }
        else
        {
            return a.x > b.x;
        }
    }

    public boolean canPlace( Tower t )
    {
        if( t.getX() + t.getWidth() <= 90 )
        {
            return true;
        }
        if( t.getX() - t.getWidth() < 110 )
        {
            return t.getY() - t.getWidth() >= 310;
        }
        return t.getY() - t.getWidth() >= 310 || t.getY() + t.getWidth() <= 290;
    }
}
