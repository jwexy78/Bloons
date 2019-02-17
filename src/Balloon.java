import java.awt.*;

/**
 * Created by jonathan on 2/16/19.
 */
public class Balloon extends MapItem
{

    public int rank;

    public Balloon( int rank )
    {
        super( 0, 0, 10 );
        this.rank = rank;
    }

    /**
     * Get hit but a bullet
     * @return if it should be poppeed
     */
    public boolean getHit()
    {
        rank--;
        return rank == 0;
    }

    public double getSpeed()
    {
        return rank;
    }

    public void draw( Graphics2D g )
    {
        Color color = Color.WHITE;
        switch ( rank )
        {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 5:
                color = Color.ORANGE;
                break;
            case 6:
                color = Color.BLACK;
                break;
            case 7:
                color = Color.WHITE;
                break;
        }
        g.setColor( color );
        g.fillOval(
                getX() - getWidth(),
                getY() - getWidth(),
                getWidth() * 2,
                getWidth() * 2 );
        g.setColor( Color.WHITE );
        g.drawOval(
                getX() - getWidth(),
                getY() - getWidth(),
                getWidth() * 2,
                getWidth() * 2 );
    }
}
