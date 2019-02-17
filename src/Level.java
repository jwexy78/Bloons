/**
 * Created by jonathan on 2/16/19.
 */
public abstract class Level
{
    public abstract void start();
    public abstract Balloon tick();
    public abstract boolean isDone();
}
