/**
 * Created by jonathan on 2/17/19.
 */
public class LevelManager
{
    private int levelNumber;
    private Level currentLevel;

    public LevelManager()
    {
        levelNumber = 1;
        currentLevel = getObjectForLevel( levelNumber );
    }

    public int getLevelNumber()
    {
        return levelNumber;
    }

    /**
     * Move to the next level.
     * @return  if the game has been won (no more levels)
     */
    public boolean nextLevel()
    {
        levelNumber++;
        currentLevel = getObjectForLevel( levelNumber );
        return currentLevel == null;
    }

    public boolean currentLevelIsDone()
    {
        return currentLevel.isDone();
    }

    public Balloon tickCurrentLevel()
    {
        return currentLevel.tick();
    }

    static Level getObjectForLevel( int levelNumber )
    {
        switch ( levelNumber )
        {
            case 1:
                return new LevelBasicRepeater( 1, 30, 10 );
            case 2:
                return new LevelBasicRepeater( 1, 30, 15 );
            case 3:
                return new LevelBasicRepeater( 2, 30, 10 );
            case 4:
                return new LevelBasicRepeater( 2, 25, 15 );
            case 5:
                return new LevelBasicRepeater( 3, 30, 10 );
            case 6:
                return new LevelBasicRepeater( 3, 20, 10 );
            case 7:
                return new LevelBasicRepeater( 4, 20, 10 );
            case 8:
                return new LevelBasicRepeater( 2, 15, 20 );
            case 9:
                return new LevelBasicRepeater( 4, 10, 40 );
            case 10:
                return new LevelBasicRepeater( 5, 10, 40 );
            case 11:
                return new LevelBasicRepeater( 6, 10, 50 );
            case 12:
                return new LevelBasicRepeater( 7, 10, 50 );
            case 13:
                return new LevelBasicRepeater( 7, 2, 100 );
            default:
                return null;
        }
    }
}
