import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by jonathan on 2/16/19.
 */
public class Component extends JComponent
{
    private ArrayList<Balloon> balloonBuffer;
    private ArrayList<Balloon> balloons;

    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> bulletBuffer;

    private ArrayList<Tower> towers;
    private ArrayList<Tower> towerBuffer;

    private int currentTowerPrice;
    private Tower currentTower;

    private static boolean runningLevel;
    private LevelManager levelManager;

    private Map map;

    int money;

    private int lives;

    int mouseX;
    int mouseY;

    public Component()
    {
        balloonBuffer = new ArrayList<>();
        balloons = new ArrayList<>();
        towerBuffer = new ArrayList<>();
        towers = new ArrayList<>();
        bulletBuffer = new ArrayList<>();
        bullets = new ArrayList<>();

        this.setPreferredSize( new Dimension( 400, 400 ) );

        this.map = new LMap();
        levelManager = new LevelManager();

        lives = 100;

        money = 50;

        runningLevel = false;

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if( currentTower == null )
                {
                    return;
                }

                int x = e.getX();
                int y = e.getY();
                if( money >= currentTowerPrice && canPlaceCurrentTower() ) {
                    addTower( currentTower );
                    currentTower = null;
                    money -= currentTowerPrice;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if( currentTower != null )
                {
                    currentTower.x = e.getX();
                    currentTower.y = e.getY();
                }
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if( e.getKeyCode() == KeyEvent.VK_SPACE )
                {
                    runningLevel = true;
                    return;
                }
                if( e.getKeyCode() == KeyEvent.VK_P )
                {
                    if( money < 50 )
                    {
                        return;
                    }
                    currentTower = new PistolTower(mouseX,mouseY);
                    currentTowerPrice = 50;
                    return;
                }
                if( e.getKeyCode() == KeyEvent.VK_S )
                {
                    if( money < 75 )
                    {
                        return;
                    }
                    currentTower = new SprayTower(mouseX,mouseY);
                    currentTowerPrice = 75;
                    return;
                }
                currentTower = null;
            }
        });
    }

    public void addBalloon( Balloon b )
    {
        balloonBuffer.add( b );
    }
    public void addTower( Tower t )
    {
        towerBuffer.add( t );
    }
    public void addBullet( Bullet b )
    {
        bulletBuffer.add( b );
    }

    private boolean canPlaceCurrentTower()
    {
        for( Tower t : towerBuffer )
        {
            if( t.intersects( currentTower ) )
            {
                return false;
            }
        }
        for( Tower t : towers )
        {
            if( t.intersects( currentTower ) )
            {
                return false;
            }
        }
        return map.canPlace( currentTower );
    }


    public void tick()
    {
        // Release new balloons into the level
        Balloon released = null;
        if( !levelManager.currentLevelIsDone() )
        {
            released = levelManager.tickCurrentLevel() ;
        }
        if( released != null )
        {
            map.place( released );
            balloons.add( released );
        }

        // Tick all of the towers
        for( Tower t : towers )
        {
            t.tick( map, balloons, bullets );
        }

        // Move each balloon
        for( Balloon b : balloons )
        {
            map.move( b );
        }

        // Tick each projectile
        for( int i = 0; i < bullets.size(); ++i )
        {
            Bullet b = bullets.get( i );
            if( b.getX() < 0 || b.getX() > 400 ||
                    b.getY() < 0 || b.getY() > 400 )
            {
                bullets.remove( i-- );
            }
            else
            {
                b.tick();
            }
        }
        for( Bullet b : bullets )
        {
            b.tick();
        }

        for( int i = 0; i < bullets.size(); ++i )
        {
            for( int j = 0; j < balloons.size(); ++j )
            {
                if( bullets.get(i).intersects(balloons.get(j)))
                {
                    bullets.remove(i--);
                    if(balloons.get(j).getHit())
                    {
                        balloons.remove(j--);
                        money += 5;
                    }
                    break;
                }
            }
        }

        // Check for balloons that made it past
        for( int i = 0; i < balloons.size(); ++i )
        {
            if( map.isPast( balloons.get(i) ) )
            {
                lives -= balloons.get(i).rank;
                balloons.remove( i-- );
            }
        }

        if( lives <= 0 )
        {
            lives = 0;
            System.out.println("You Lost :(");
            System.exit(2);
        }

        // Check for beating the level
        if( balloons.size() == 0 && levelManager.currentLevelIsDone() )
        {
            boolean beaten = levelManager.nextLevel();
            runningLevel = false;
            bullets.clear();
            if( beaten )
            {
                System.out.println( "You Win!" );
                System.exit(1);
            }
        }
    }

    public void paintComponent( Graphics gi )
    {
        // Synchronously move spawned bodies into the actual lists
        for( Balloon b : balloonBuffer )
        {
            balloons.add( b );
        }
        balloonBuffer.clear();

        for( Tower t : towerBuffer )
        {
            towers.add( t );
        }
        towerBuffer.clear();

        for( Bullet b : bulletBuffer )
        {
            bullets.add( b );
        }
        bulletBuffer.clear();

        if(runningLevel)
        {
            // Tick the world
            tick();
        }

        // Draw everything in the world
        Graphics2D g = (Graphics2D)gi;
        map.draw( g );
        for( Balloon b : balloons )
        {
            b.draw( g );
        }
        for( Tower t : towers )
        {
            t.draw( g );
        }
        for( Bullet b : bullets )
        {
            b.draw( g );
        }

        if( currentTower != null )
        {
            if( canPlaceCurrentTower() )
            {
                g.setColor( new Color( 0, 0, 0, 50 ) );
            }
            else
            {
                g.setColor( new Color( 255, 0, 0, 120 ) );
            }
            g.fillOval( currentTower.getX() - currentTower.getRange(),
                    currentTower.getY() - currentTower.getRange(),
                    currentTower.getRange() * 2,
                    currentTower.getRange() * 2);
            currentTower.draw( g );
        }

        g.setColor( Color.BLACK );

        if( currentTower == null )
        {
            g.drawString("[P]: $50 Pistol Tower", 10, 30 );
            g.drawString("[S]: $75 Spray Tower", 10, 50 );
        }

        g.drawString( "Lives: " + lives, 10, 395 );
        g.drawString( "Level: " + levelManager.getLevelNumber(), 150, 395 );
        g.drawString( "Money: $" + money, 290, 395 );
        if( !runningLevel)
        {
            g.drawString( "Press [Space] to Start Next Level", 10, 370 );
        }
    }
}
