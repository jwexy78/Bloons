import java.awt.*;

/**
 * Created by jonathan on 2/16/19.
 */
public abstract class Map
{
    public abstract void draw( Graphics2D g );
    public abstract void move( Balloon b );
    public abstract void place( Balloon b );
    public abstract boolean isPast( Balloon b );
    public abstract boolean isFurther( Balloon a, Balloon b );
    public abstract boolean canPlace( Tower t );
}
