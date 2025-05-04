package SwingExamples;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author SAGAR
 */
public class PopupSwingEx1{
    JPopupMenu p1;
    JMenuItem cut,copy,paste;
    PopupSwingEx1(){
        JFrame f=new JFrame();
        p1=new JPopupMenu();
        cut=new JMenuItem("cut");
        copy=new JMenuItem("copy");
        paste=new JMenuItem("paste");
        f.add(p1);
        p1.add(cut);
        p1.add(copy);
        p1.add(paste);
        f.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e){
               p1.show(f, e.getX(),e.getY());
           } 
        });
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);   
    }
    public static void main(String[] args) {
        PopupSwingEx1 p=new PopupSwingEx1();
    }
}
