import javax.swing.*;

/**
 * Created by jonathan on 2/16/19.
 */
public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        Component component = new Component();

        frame.add( component );
        frame.pack();
        frame.setResizable( false );
        component.requestFocusInWindow();

        frame.setVisible( true );

        while( true )
        {
            try { Thread.sleep( 50 ); }
            catch( Exception e ){}
            frame.repaint();
        }
    }
}
