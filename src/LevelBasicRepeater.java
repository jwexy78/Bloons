/**
 * Created by jonathan on 2/16/19.
 */
public class LevelBasicRepeater extends Level
{
    int counter;
    int released;

    int balloon;
    int frequency;
    int total;

    public LevelBasicRepeater( int balloon, int frequency, int total )
    {
        this.balloon = balloon;
        this.frequency = frequency;
        this.total = total;
    }

    public void start()
    {
        counter = 0;
        released = 0;
    }

    public Balloon tick()
    {
        counter++;
        if( counter == frequency ) {
            released++;
            counter = 0;
            return new Balloon( balloon );
        }
        return null;
    }

    public boolean isDone()
    {
        return released >= total;
    }
}
